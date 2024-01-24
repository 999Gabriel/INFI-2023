import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Bestellung {
    private Connection connection;

    public Bestellung() {
        String url = "jdbc:mysql://localhost:3306/bestellungsbeispiel";
        String user = "root";
        String password = "root";

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createBestellungTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS bestellung (kundenID INT, artikelID INT, anzahl INT, FOREIGN KEY (kundenID) REFERENCES kunden(id), FOREIGN KEY (artikelID) REFERENCES artikel(id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void bestellen(int kundenID, int artikelID, int anzahl) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO bestellung (kundenID, artikelID, anzahl) VALUES (?, ?, ?)")) {
            preparedStatement.setInt(1, kundenID);
            preparedStatement.setInt(2, artikelID);
            preparedStatement.setInt(3, anzahl);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void zeigeBestellungVonKunde(int kundenID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM bestellung WHERE kundenID = ?")) {
            preparedStatement.setInt(1, kundenID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int artikelID = resultSet.getInt("artikelID");
                int anzahl = resultSet.getInt("anzahl");
                System.out.println("Kunde " + kundenID + " hat " + anzahl + " Stück(e) von Artikel " + artikelID + " bestellt.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertBestellung(int kundeID, int artikelID, int anzahl) {
        // SQL-Befehl zum Einfügen einer neuen Bestellung
        String sql = "INSERT INTO bestellung (kundenID, artikelID, anzahl, bestellt_am) VALUES (?, ?, ?, NOW())";

        try {
            // Vorbereiten des SQL-Statements
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setzen der Parameter
            preparedStatement.setInt(1, kundeID);
            preparedStatement.setInt(2, artikelID);
            preparedStatement.setInt(3, anzahl);

            // Ausführen des Befehls
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Bestellung erfolgreich hinzugefügt.");
            } else {
                System.out.println("Bestellung konnte nicht hinzugefügt werden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteBestellung(int kundeIDForDelete, int artikelIDForDelete) {
        // SQL-Befehl zum Löschen einer Bestellung
        String sql = "DELETE FROM bestellung WHERE kundenID = ? AND artikelID = ?";

        try
        {
            // Vorbereiten des SQL-Statements
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setzen der Parameter
            preparedStatement.setInt(1, kundeIDForDelete);
            preparedStatement.setInt(2, artikelIDForDelete);

            // Ausführen des Befehls
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Bestellung erfolgreich gelöscht.");
            } else {
                System.out.println("Keine Bestellung gefunden, die gelöscht werden kann.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBestellung(int artikelIDToUpdate, String updatedBezeichnungBestellung, double updatedPreis)
    {
        String sql = "UPDATE bestellung SET artikelID = ?, updatedBezeichnung = ?, updatedPreis = ? WHERE id = ?";

        try {
            // Vorbereiten des SQL-Statements
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setzen der Parameter
            preparedStatement.setInt(1, artikelIDToUpdate);
            preparedStatement.setDouble(2, updatedPreis);
            preparedStatement.setString(3, updatedBezeichnungBestellung);

            // Ausführen des Befehls
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Bestellung erfolgreich hinzugefügt.");
            } else {
                System.out.println("Bestellung konnte nicht hinzugefügt werden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void displayBestellungenWithDate(int idOfOrder) {
        ResultSet resultSet = Main.executeQuery("SELECT kundenID, artikelID, anzahl, bestellt_am FROM bestellung");
        try {
            while (resultSet.next()) {
                System.out.println("KundenID: " + resultSet.getInt("kundenID") + ", ArtikelID: "
                        + resultSet.getInt("artikelID") + ", Anzahl: " + resultSet.getInt("anzahl")
                        + ", Bestellt am: " + resultSet.getTimestamp("bestellt_am"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getBestellungenInZeitraum(LocalDateTime startDatum, LocalDateTime endDatum) {
        String query = "SELECT * FROM bestellung WHERE bestellt_am BETWEEN ? AND ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setTimestamp(1, Timestamp.valueOf(startDatum));
            statement.setTimestamp(2, Timestamp.valueOf(endDatum));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Beispiel, wie man die Ergebnisse anzeigt. Passen Sie es an Ihre Bedürfnisse an.
                System.out.println("Bestellung ID: " + resultSet.getInt("id") + ", Kunden ID: " + resultSet.getInt("kundenID") +
                        ", Artikel ID: " + resultSet.getInt("artikelID") + ", Anzahl: " + resultSet.getInt("anzahl") +
                        ", Bestellt am: " + resultSet.getTimestamp("bestellt_am"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteArtikelVorDatum(LocalDate datum) {
        String query = "DELETE FROM artikel WHERE erstellt_am < ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(datum));

            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " Artikel gelöscht.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void displayBestellungenVonKundeInZeitraum(int kundenID, LocalDate startDatum, LocalDate endDatum) {
        String query = "SELECT * FROM bestellung WHERE kundenID = ? AND bestellt_am BETWEEN ? AND ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, kundenID);
            statement.setTimestamp(2, Timestamp.valueOf(startDatum.atStartOfDay()));
            statement.setTimestamp(3, Timestamp.valueOf(endDatum.atTime(23, 59, 59)));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Beispiel, wie man die Ergebnisse anzeigt. Passen Sie es an Ihre Bedürfnisse an.
                System.out.println("Bestellung ID: " + resultSet.getInt("id") + ", Kunden ID: " + resultSet.getInt("kundenID") +
                        ", Artikel ID: " + resultSet.getInt("artikelID") + ", Anzahl: " + resultSet.getInt("anzahl") +
                        ", Bestellt am: " + resultSet.getTimestamp("bestellt_am"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBestelldatum(int bestellID, LocalDate neuesDatum) {
        String query = "UPDATE bestellung SET bestellt_am = ? WHERE kundenID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(neuesDatum));
            statement.setInt(2, bestellID);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Das Bestelldatum wurde erfolgreich aktualisiert.");
            } else {
                System.out.println("Es konnte keine Bestellung mit der angegebenen ID gefunden werden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
