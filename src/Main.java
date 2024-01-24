    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.time.LocalDateTime;
    import java.util.Scanner;
    import java.time.LocalDate;

    public class Main
    {
        private static Connection connection = null;
        public static void main(String[] args) throws SQLException
        {

            Scanner scanner = new Scanner(System.in);

            Kunden kunden = new Kunden();
            Artikel artikel = new Artikel();
            Bestellung bestellung = new Bestellung();
            ResultSet resultSet = executeQuery("SELECT kunden.name, artikel.bezeichnung, bestellung.anzahl FROM kunden JOIN bestellung ON kunden.id = bestellung.kundenID JOIN artikel ON artikel.id = bestellung.artikelID");
            displayResultSet(resultSet);
            resultSet.close();

            kunden.createKundenTable();
            artikel.createArtikelTable();
            bestellung.createBestellungTable();

            System.out.println("1. Neuen Kunden anlegen");
            System.out.println("2. Kunden aktualisieren");
            System.out.println("3. Kunden löschen");
            System.out.println("4. Neuen Artikel anlegen");
            System.out.println("5. Artikel aktualisieren");
            System.out.println("6. Artikel löschen");
            System.out.println("7. Artikel bestellen");
            System.out.println("8. Bestellung aktualisieren");
            System.out.println("9. Bestellung löschen");
            System.out.println("10. Artikel vor einem Datum löschen");
            System.out.println("11. Löschen eines Artikels in einem gewissen Zeitraum");
            System.out.println("12. Artiklels in einem Zeitraum erstellen");
            System.out.println("13. Artikel nach einem Datum ausgeben");
            System.out.println("14. Bestelldatum einer Bestellung ändern");
            System.out.print("Wählen Sie eine Option: ");
            int option = scanner.nextInt();

            switch (option)
            {
                case 1:
                    System.out.print("Name: ");
                    String newName = scanner.next();
                    System.out.print("Email: ");
                    String newEmail = scanner.next();
                    kunden.insertKunde(newName, newEmail);
                    break;
                case 2:
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
                case 3:
                    System.out.print("Kunden-ID zu löschen: ");
                    int kundenIDToDelete = scanner.nextInt();
                    kunden.deleteKunde(kundenIDToDelete);
                    break;
                case 4:
                    System.out.print("Bezeichnung: ");
                    String newBezeichnung = scanner.next();
                    System.out.print("Preis: ");
                    double newPreis = scanner.nextDouble();
                    artikel.insertArtikel(newBezeichnung, newPreis);
                    break;
                case 5:
                    System.out.print("Artikel-ID: ");
                    int artikelIDToUpdate = scanner.nextInt();
                    System.out.print("Neue Bezeichnung: ");
                    String updatedBezeichnung = scanner.next();
                    System.out.print("Neuer Preis: ");
                    double updatedPreis = scanner.nextDouble();
                    artikel.updateArtikel(artikelIDToUpdate, updatedBezeichnung, updatedPreis);
                    break;
                case 6:
                    System.out.print("Artikel-ID zu löschen: ");
                    int artikelIDToDelete = scanner.nextInt();
                    artikel.deleteArtikel(artikelIDToDelete);
                    break;
                case 7:
                    System.out.print("Kunden-ID: ");
                    int kundeID = scanner.nextInt();
                    System.out.print("Artikel-ID: ");
                    int artikelID = scanner.nextInt();
                    System.out.print("Anzahl: ");
                    int anzahl = scanner.nextInt();
                    bestellung.insertBestellung(kundeID, artikelID, anzahl);
                    break;
                case 8:
                    // Bestellung aktualisieren (falls nötig)
                    System.out.print("Kunden-ID: ");
                    int artikelIdToUpdate = scanner.nextInt();
                    System.out.print("Neue Bestellung: ");
                    String updatedBezeichnungBestellung = scanner.next();
                    System.out.print("Neuer Preis: ");
                    double updatedPrice = scanner.nextDouble();
                    bestellung.updateBestellung(artikelIdToUpdate, updatedBezeichnungBestellung, updatedPrice);
                    break;


                case 9:
                    System.out.print("Kunden-ID für Bestellungslöschung: ");
                    int kundeIDForDelete = scanner.nextInt();
                    System.out.print("Artikel-ID für Bestellungslöschung: ");
                    int artikelIDForDelete = scanner.nextInt();
                    bestellung.deleteBestellung(kundeIDForDelete, artikelIDForDelete);
                    break;
                default:
                    System.out.println("Ungültige Option. Bitte wählen Sie eine gültige Option.");
                case 10:
                    System.out.print("ID der Bestellung: ");
                    int idOfOrder = scanner.nextInt();
                    bestellung.displayBestellungenWithDate(idOfOrder);
                    break;
                case 11:
                    System.out.print("Datum (yyyy-mm-dd): ");
                    LocalDate date = LocalDate.parse(scanner.next());
                    bestellung.deleteArtikelVorDatum(date);
                    break;
                case 12:
                    System.out.print("Artikel-ID: ");
                    int IDartikel = scanner.nextInt();
                    System.out.print("Neues Erstellungsdatum (yyyy-mm-ddTHH:MM): ");
                    LocalDateTime neuesDatum = LocalDateTime.parse(scanner.next());
                    artikel.updateArtikelErstellungsdatum(IDartikel, neuesDatum);
                    break;
                case 13:
                    System.out.print("Datum (yyyy-mm-dd): ");
                    LocalDate datum = LocalDate.parse(scanner.next());
                    artikel.getArtikelNachDatum(datum);
                    break;
                case 14:
                    System.out.println("Geben Sie die ID der Bestellung ein, deren Datum Sie ändern möchten: ");
                    int bestellID = scanner.nextInt();
                    System.out.println("Geben Sie das neue Datum im Format JJJJ-MM-TT ein: ");
                    String datumStr = scanner.next();
                    LocalDate neuesBestellDatum = LocalDate.parse(datumStr);
                    bestellung.updateBestelldatum(bestellID, neuesBestellDatum);
            }

            scanner.close();

        }

        private static void displayResultSet(ResultSet resultSet)
        {
            try
            {
                while (resultSet.next())
                {
                    String kundenName = resultSet.getString("name");
                    String artikelBezeichnung = resultSet.getString("bezeichnung");
                    int anzahl = resultSet.getInt("anzahl");

                    System.out.println("Kunde: " + kundenName + ", Artikel: " + artikelBezeichnung + ", Anzahl: " + anzahl);
                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        public static ResultSet executeQuery(String query) {
            if (connection == null) {
                connectToDatabase();
            }

            try (Statement statement = connection.createStatement()) {
                return statement.executeQuery(query);
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
        public static void connectToDatabase()
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                // Erstellen einer Verbindung
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bestellungsbeispiel", "root", "root");
            }
            catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

