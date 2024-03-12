import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main
{
    private static Connection connection = null;

    public static void main(String[] args) throws SQLException
    {

        Scanner scanner = new Scanner(System.in);

        Kunden kunden = new Kunden();
        Artikel artikel = new Artikel();
        Bestellung bestellung = new Bestellung();

        try
        {
            connection = connectToDatabase();

            ResultSet resultSet = executeQuery("SELECT kunden.name, artikel.bezeichnung, " +
                    "bestellung.anzahl FROM kunden " +
                    "JOIN bestellung ON kunden.id = bestellung.kundenID JOIN artikel " +
                    "ON artikel.id = bestellung.artikelID");
            displayResultSet(resultSet);
            resultSet.close();

            kunden.createKundenTable();
            artikel.createArtikelTable();
            bestellung.createBestellungTable();

            boolean exit = false;
            while (!exit)
            {
                printMenu();
                System.out.print("Wählen Sie eine Option: ");
                int option = scanner.nextInt();

                switch (option)
                {
                    case 1:
                        System.out.print("Name: ");
                        String newName = scanner.next();
                        System.out.println("Nachname: ");
                        String newNachname = scanner.next();
                        System.out.print("Email: ");
                        String newEmail = scanner.next();
                        kunden.insertKunde(newName, newEmail, newNachname);
                        break;
                    case 2:
                        System.out.print("Geben Sie den Pfad zur JSON-Datei an: ");
                        String jsonDateiPfad = scanner.next();
                        kunden.loadKundenFromJson(jsonDateiPfad);
                        break;
                    case 3:
                        System.out.print("Kunden-ID: ");
                        int kundenIDToUpdate = scanner.nextInt();
                        System.out.print("Neuer Name: ");
                        String updatedName = scanner.next();
                        System.out.println("Neuer Nachname: ");
                        String updatedNachname = scanner.next();
                        System.out.print("Neue Email: ");
                        String updatedEmail = scanner.next();
                        kunden.updateKunde(kundenIDToUpdate, updatedName, updatedNachname, updatedEmail);
                        break;
                    case 4:
                        System.out.print("Kunden-ID zu löschen: ");
                        int kundenIDToDelete = scanner.nextInt();
                        kunden.deleteKunde(kundenIDToDelete);
                        break;
                    case 5:
                        System.out.print("Bezeichnung: ");
                        String newBezeichnung = scanner.next();
                        System.out.print("Preis: ");
                        double newPreis = scanner.nextDouble();
                        artikel.insertArtikel(newBezeichnung, newPreis);
                        break;
                    case 6:
                        System.out.print("Artikel-ID: ");
                        int artikelIDToUpdate = scanner.nextInt();
                        System.out.print("Neue Bezeichnung: ");
                        String updatedBezeichnung = scanner.next();
                        System.out.print("Neuer Preis: ");
                        double updatedPreis = scanner.nextDouble();
                        artikel.updateArtikel(artikelIDToUpdate, updatedBezeichnung, updatedPreis);
                        break;
                    case 7:
                        System.out.print("Artikel-ID zu löschen: ");
                        int artikelIDToDelete = scanner.nextInt();
                        artikel.deleteArtikel(artikelIDToDelete);
                        break;
                    case 8:
                        System.out.print("Kunden-ID: ");
                        int kundeID = scanner.nextInt();
                        System.out.print("Artikel-ID: ");
                        int artikelID = scanner.nextInt();
                        System.out.print("Anzahl: ");
                        int anzahl = scanner.nextInt();
                        bestellung.insertBestellung(kundeID, artikelID, anzahl);
                        break;
                    case 9:
                        // Bestellung aktualisieren (falls nötig)
                        System.out.print("Kunden-ID: ");
                        int artikelIdToUpdate = scanner.nextInt();
                        System.out.print("Neue Bestellung: ");
                        String updatedBezeichnungBestellung = scanner.next();
                        System.out.print("Neuer Preis: ");
                        double updatedPrice = scanner.nextDouble();
                        bestellung.updateBestellung(artikelIdToUpdate, updatedBezeichnungBestellung, updatedPrice);
                        break;
                    case 10:
                        System.out.print("Kunden-ID für Bestellungslöschung: ");
                        int kundeIDForDelete = scanner.nextInt();
                        System.out.print("Artikel-ID für Bestellungslöschung: ");
                        int artikelIDForDelete = scanner.nextInt();
                        bestellung.deleteBestellung(kundeIDForDelete, artikelIDForDelete);
                        break;
                    default:
                        System.out.println("Ungültige Option. Bitte wählen Sie eine gültige Option.");
                    case 11:
                        System.out.print("ID der Bestellung: ");
                        int idOfOrder = scanner.nextInt();
                        bestellung.displayBestellungenWithDate(idOfOrder);
                        break;
                    case 12:
                        System.out.print("Datum (yyyy-mm-dd): ");
                        LocalDate date = LocalDate.parse(scanner.next());
                        bestellung.deleteArtikelVorDatum(date);
                        break;
                    case 13:
                        System.out.print("Artikel-ID: ");
                        int IDartikel = scanner.nextInt();
                        System.out.print("Neues Erstellungsdatum (yyyy-mm-ddTHH:MM): ");
                        LocalDateTime neuesDatum = LocalDateTime.parse(scanner.next());
                        artikel.updateArtikelErstellungsdatum(IDartikel, neuesDatum);
                        break;
                    case 14:
                        System.out.print("Datum (yyyy-mm-dd): ");
                        LocalDate datum = LocalDate.parse(scanner.next());
                        artikel.getArtikelNachDatum(datum);
                        break;
                    case 15:
                        System.out.println("Geben Sie die ID der Bestellung ein, deren Datum Sie ändern möchten: ");
                        int bestellID = scanner.nextInt();
                        System.out.println("Geben Sie das neue Datum im Format JJJJ-MM-TT ein: ");
                        String datumStr = scanner.next();
                        LocalDate neuesBestellDatum = LocalDate.parse(datumStr);
                        bestellung.updateBestelldatum(bestellID, neuesBestellDatum);
                        break;
                    case 16:
                        kunden.updateTable();
                        break;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (connection != null)
                {
                    connection.close();
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        while (true)
        {
            int option2 = scanner.nextInt();
            switch (option2) {
                case 1:
                    leftJoin();
                    break;
                case 2:
                    rightJoin();
                    break;
                case 3:
                    innerJoin();
                    break;
                case 4:
                    fullJoin();
                    break;
                case 5:
                    System.out.println("Programm wird beendet.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Ungültige Option. Bitte wählen Sie erneut.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("1. Neuen Kunden anlegen");
        System.out.println("2. Kunden anlegen aus externen Dateien");
        System.out.println("3. Kunden aktualisieren");
        System.out.println("4. Kunden löschen");
        System.out.println("5. Neuen Artikel anlegen");
        System.out.println("6. Artikel aktualisieren");
        System.out.println("7. Artikel löschen");
        System.out.println("8. Artikel bestellen");
        System.out.println("9. Bestellung aktualisieren");
        System.out.println("10. Bestellung löschen");
        System.out.println("11. Artikel vor einem Datum löschen");
        System.out.println("12. Löschen eines Artikels in einem gewissen Zeitraum");
        System.out.println("13. Artiklels in einem Zeitraum erstellen");
        System.out.println("14. Artikel nach einem Datum ausgeben");
        System.out.println("15. Bestelldatum einer Bestellung ändern");
        System.out.println("16. Update Tabelle");
        System.out.println("17. Left Join");
        System.out.println("18. Right Join");
        System.out.println("19. Inner Join");
        System.out.println("20. Full Join");
        System.out.println("21. Beenden");
    }

    private static void leftJoin() {
        try {
            ResultSet leftJoinResult = executeQuery("SELECT * FROM kunden LEFT JOIN bestellung " +
                    "ON kunden.id = bestellung.kundenID");
            displayResultSet(leftJoinResult);
            leftJoinResult.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    private static void rightJoin() {
        try {
            ResultSet rightJoinResult = executeQuery("SELECT * FROM kunden RIGHT JOIN bestellung " +
                    "ON kunden.id = bestellung.kundenID");
            displayResultSet(rightJoinResult);
            rightJoinResult.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    private static void innerJoin() {
        try {
            ResultSet innerJoinResult = executeQuery("SELECT * FROM kunden INNER JOIN bestellung " +
                    "ON kunden.id = bestellung.kundenID");
            displayResultSet(innerJoinResult);
            innerJoinResult.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    private static void fullJoin() {
        try {
            ResultSet fullJoinResult = executeQuery("SELECT * FROM kunden LEFT JOIN bestellung " +
                    "ON kunden.id = bestellung.kundenID " +
                    "UNION SELECT * FROM kunden RIGHT JOIN bestellung " +
                    "ON kunden.id = bestellung.kundenID WHERE kunden.id IS NULL");
            displayResultSet(fullJoinResult);
            fullJoinResult.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    private static void displayResultSet(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                String kundenName = resultSet.getString("name");
                String artikelBezeichnung = resultSet.getString("bezeichnung");
                int anzahl = resultSet.getInt("anzahl");

                System.out.println("Kunde: " + kundenName + ", Artikel: " + artikelBezeichnung + ", Anzahl: " + anzahl);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        if (connection == null) {
            throw new SQLException("Verbindung zur Datenbank nicht initialisiert.");
        }

        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static Connection connectToDatabase() throws SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            // Erstellen einer Verbindung
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bestellungsbeispiel",
                    "root", "root");
        }
        catch (ClassNotFoundException e)
        {
            throw new SQLException("MySQL JDBC Treiber nicht gefunden.", e);
        }
    }
}
