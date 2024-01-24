import java.sql.*;

public class Kunden {
    private Connection connection;

    public Kunden() {
        String url = "jdbc:mysql://localhost:3306/bestellungsbeispiel";
        String user = "root";
        String password = "root";
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createKundenTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS kunden (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), email VARCHAR(255))");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertKunde(String name, String email) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kunden (name, email) VALUES (?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteKunde(int kundenIDToDelete) {
        // SQL-Befehl, um einen Kunden zu löschen
        String sql = "DELETE FROM kunden WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Setzen des Parameters (Kunden-ID)
            preparedStatement.setInt(1, kundenIDToDelete);

            // Ausführen des Löschbefehls
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Kunde erfolgreich gelöscht.");
            } else {
                System.out.println("Kein Kunde mit der angegebenen ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateKunde(int kundenIDToUpdate, String updatedName, String updateNachname, String updatedEmail) {
        // SQL-Befehl, um einen Kunden zu aktualisieren
        String sql = "UPDATE kunden SET name = ?, email = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Setzen der Parameter
            preparedStatement.setString(1, updatedName);
            //preparedStatement.setString(2, updateNachname);
            preparedStatement.setString(2, updatedEmail);
            preparedStatement.setInt(3, kundenIDToUpdate);

            // Ausführen des Update-Befehls
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Kundendaten erfolgreich aktualisiert.");
            } else {
                System.out.println("Kein Kunde mit der angegebenen ID gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
