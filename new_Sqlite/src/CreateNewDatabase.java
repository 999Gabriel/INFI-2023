import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateNewDatabase {

    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Eine neue Datenbank wurde erstellt.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        createNewDatabase("vorbereitung.db");
    }
}
