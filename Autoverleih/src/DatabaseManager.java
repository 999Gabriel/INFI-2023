import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.sql.Date;
import java.util.Scanner;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/autoverleih_db?user=root&password=root&serverTimezone=Europe/Berlin";

    // DAOs für die einzelnen Klassen
    private static Dao<Kunde, Integer> customerDao;
    private static Dao<Auto, Integer> carDao;
    private static Dao<Buchung, Integer> rentalDao;

    private static void setupDatabase(ConnectionSource connectionSource) throws Exception {
        TableUtils.createTableIfNotExists(connectionSource, Auto.class);
        TableUtils.createTableIfNotExists(connectionSource, Kunde.class);
        TableUtils.createTableIfNotExists(connectionSource, Buchung.class);

        // Initialisiere DAOs
        customerDao = DaoManager.createDao(connectionSource, Kunde.class);
        carDao = DaoManager.createDao(connectionSource, Auto.class);
        rentalDao = DaoManager.createDao(connectionSource, Buchung.class);
    }

    private static void addCustomer(Scanner scanner) throws Exception {
        System.out.println("\nNeuen Kunden hinzufügen:");
        System.out.print("ID: ");
        int customer_id = scanner.nextInt();
        scanner.nextLine(); // Konsumiere das restliche Newline-Zeichen
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Adresse: ");
        String address = scanner.nextLine();
        System.out.print("Telefonnummer: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        Kunde newCustomer = new Kunde(customer_id, name, address, phone, email);
        customerDao.create(newCustomer);
        System.out.println("Kunde wurde hinzugefügt: " + name);
    }

    private static void addCar(Scanner scanner) throws Exception {
        System.out.println("\nNeues Auto hinzufügen:");
        System.out.print("ID: ");
        int car_id = scanner.nextInt();
        scanner.nextLine(); // Konsumiere das restliche Newline-Zeichen
        System.out.print("Modell: ");
        String model = scanner.nextLine();
        System.out.print("Marke: ");
        String brand = scanner.nextLine();
        System.out.print("Kennzeichen: ");
        String licensePlate = scanner.nextLine();
        System.out.print("Status (Available, Rented, etc.): ");
        String status = scanner.nextLine();

        Auto newCar = new Auto(car_id, model, brand, licensePlate, status);
        carDao.create(newCar);
        System.out.println("Auto wurde hinzugefügt: " + model);
    }

    private static void addRental(Scanner scanner) throws Exception {
        System.out.println("\nNeue Buchung erstellen:");

        System.out.print("Kunden-ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Konsumiere das restliche Newline-Zeichen

        System.out.print("Auto-ID: ");
        int carId = scanner.nextInt();
        scanner.nextLine(); // Konsumiere das restliche Newline-Zeichen

        System.out.print("Startdatum (yyyy-mm-dd): ");
        String startDateStr = scanner.nextLine();
        Date startDate = Date.valueOf(startDateStr);

        System.out.print("Enddatum (yyyy-mm-dd): ");
        String endDateStr = scanner.nextLine();
        Date endDate = Date.valueOf(endDateStr);

        System.out.print("Gesamtpreis: ");
        double totalPrice = scanner.nextDouble();
        scanner.nextLine(); // Konsumiere das restliche Newline-Zeichen

        // Kunden- und Auto-Objekte abrufen
        Kunde customer = customerDao.queryForId(customerId);
        Auto car = carDao.queryForId(carId);

        if (customer == null) {
            System.out.println("Kunde mit ID " + customerId + " nicht gefunden.");
            return;
        }

        if (car == null) {
            System.out.println("Auto mit ID " + carId + " nicht gefunden.");
            return;
        }

        // Neue Buchung erstellen und in die Datenbank einfügen
        Buchung newRental = new Buchung(customer, car, startDate, endDate, totalPrice);
        rentalDao.create(newRental);

        System.out.println("Buchung wurde erstellt für Kunde ID: " + customerId + " und Auto ID: " + carId);
    }

    public static void main(String[] args) {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL)) {
            setupDatabase(connectionSource);

            Scanner scanner = new Scanner(System.in);
            int option;
            do {
                System.out.println("\nWähle eine Option:");
                System.out.println("1 - Neuen Kunden hinzufügen");
                System.out.println("2 - Neues Auto hinzufügen");
                System.out.println("3 - Neue Buchung erstellen");
                System.out.println("0 - Beenden");
                System.out.print("Option: ");
                option = scanner.nextInt();
                scanner.nextLine();  // Konsumiere das übrig gebliebene Newline

                switch (option) {
                    case 1:
                        addCustomer(scanner);
                        break;
                    case 2:
                        addCar(scanner);
                        break;
                    case 3:
                        addRental(scanner);
                        break;
                    case 0:
                        System.out.println("Programm beendet.");
                        break;
                    default:
                        System.out.println("Ungültige Option. Bitte wähle erneut.");
                        break;
                }
            } while (option != 0);

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}