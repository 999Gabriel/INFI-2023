import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.joda.time.DateTime;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main
{
    private static final String DATABASE_URL = "jdbc:sqlite:bestellungsbeispiel_ormLite.db";

    public static void main(String[] args)
    {
        try {
            ConnectionSource connectionSource = new JdbcConnectionSource(DATABASE_URL);

            Dao<Kunden, Integer> kundenDao = DaoManager.createDao(connectionSource, Kunden.class);
            Dao<Artikel, Integer> artikelDao = DaoManager.createDao(connectionSource, Artikel.class);
            Dao<Bestellung, Integer> bestellungDao = DaoManager.createDao(connectionSource, Bestellung.class);

            TableUtils.createTableIfNotExists(connectionSource, Kunden.class);
            TableUtils.createTableIfNotExists(connectionSource, Artikel.class);
            TableUtils.createTableIfNotExists(connectionSource, Bestellung.class);

            Scanner scanner = new Scanner(System.in);
            boolean exit = false;

            while (!exit)
            {
                printMenu();
                System.out.print("Wählen Sie eine Option: ");
                int option = scanner.nextInt();
                scanner.nextLine(); // Konsumieren des Zeilenumbruchs

                switch (option)
                {
                    case 1:
                        Kunden neuerKunde = new Kunden();
                        System.out.print("Name: ");
                        neuerKunde.setName(scanner.nextLine());
                        System.out.print("Nachname: ");
                        neuerKunde.setNachname(scanner.nextLine());
                        System.out.print("Email: ");
                        neuerKunde.setEmail(scanner.nextLine());
                        kundenDao.create(neuerKunde);
                        System.out.println("Kunde erfolgreich angelegt.");
                        break;
                    case 2:
                        List<Kunden> alleKunden = kundenDao.queryForAll();
                        alleKunden.forEach(System.out::println);
                        break;
                    case 3:
                        Artikel neuerArtikel = new Artikel();
                        System.out.print("Bezeichnung: ");
                        neuerArtikel.setBezeichnung(scanner.nextLine());
                        System.out.print("Preis: ");
                        neuerArtikel.setPreis(scanner.nextDouble());
                        //neuerArtikel.setErstelltAm(new Timestamp(System.currentTimeMillis()).toLocalDateTime());// Ersetzen durch Timestamp
                        neuerArtikel.setErstelltAm(new DateTime());
                        artikelDao.create(neuerArtikel);
                        System.out.println("Artikel erfolgreich angelegt.");
                        break;
                    case 4:
                        List<Artikel> alleArtikel = artikelDao.queryForAll();
                        alleArtikel.forEach(System.out::println);
                        break;

                    case 5:
                        // Neue Bestellung anlegen
                        Bestellung neueBestellung = new Bestellung();
                        System.out.print("Kunden-ID: ");
                        neueBestellung.setKundenID(scanner.nextInt());
                        System.out.print("Artikel-ID: ");
                        neueBestellung.setArtikelID(scanner.nextInt());
                        System.out.print("Anzahl: ");
                        neueBestellung.setAnzahl(scanner.nextInt());
                        neueBestellung.setBestelltAm(new DateTime());
                        bestellungDao.create(neueBestellung);
                        break;
                    case 6:
                        // Bestellungen eines Kunden anzeigen
                        System.out.print("Kunden-ID: ");
                        int kundenId = scanner.nextInt();
                        List<Bestellung> bestellungen = bestellungDao.queryForEq("kundenID", kundenId);
                        for (Bestellung b : bestellungen) {
                            System.out.println(b);
                        }
                        break;
                    case 7:
                        // Artikel löschen
                        System.out.print("Artikel-ID zum Löschen: ");
                        int artikelIdToDelete = scanner.nextInt();
                        artikelDao.deleteById(artikelIdToDelete);
                        System.out.println("Artikel gelöscht.");
                        break;
                    case 8:
                        // Kunden aktualisieren
                        System.out.print("Kunden-ID zum Aktualisieren: ");
                        int kundenIdToUpdate = scanner.nextInt();
                        Kunden kundeToUpdate = kundenDao.queryForId(kundenIdToUpdate);
                        System.out.print("Neuer Name: ");
                        kundeToUpdate.setName(scanner.next());
                        System.out.print("Neuer Nachname: ");
                        kundeToUpdate.setNachname(scanner.next());
                        System.out.print("Neue Email: ");
                        kundeToUpdate.setEmail(scanner.next());
                        kundenDao.update(kundeToUpdate);
                        System.out.println("Kunde aktualisiert.");
                        break;
                    case 9:
                        // Beenden
                        exit = true;
                        break;
                    default:
                        System.out.println("Ungültige Option. Bitte wählen Sie eine gültige Option.");
                }

            }

        } catch (Exception e) {
            System.err.println("Fehler beim Aufbau der Datenbankverbindung oder beim Zugriff auf die Datenbank.");
            e.printStackTrace();
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Hauptmenü ---");
        System.out.println("1. Neuen Kunden anlegen");
        // Menü weiter
        System.out.println("2. alle Kunden anzeigen");
        System.out.println("3. neuen Artikel anlegen");
        System.out.println("4. alle Artikel anzeigen");
        System.out.println("5. neuen Bestellung anlegen");
        System.out.println("6. alle Bestellung anzeigen");
        System.out.println("7. Bestellung eines Kunden anzeigen");
        System.out.println("8. Kunden aktualisieren");
        System.out.println("9. Beenden");
    }
}
