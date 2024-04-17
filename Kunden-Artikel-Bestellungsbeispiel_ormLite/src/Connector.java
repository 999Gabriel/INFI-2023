import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector
{
    private static Connection connection = null;

    private Connector()
    {

    }
    public static Connection getConnection()
    {
        if (connection == null)
        {
            String url = "jdbc:mysql://localhost:3306/bestellungsbeispiel";
            String user = "root";
            String password = "root";

            try{
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("erfolgreich verbunden!");
            }
            catch (SQLException e)
            {
                System.out.println("Verbindung fehlgeschlagen" + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection()
    {
        if (connection != null)
        {
            try
            {
                connection.close();
                System.out.println("Verbindung erfolgreich geschlossen");
            }
            catch (SQLException e)
            {
                System.out.println("Fehler beim Schließen der Verbindung: " + e.getMessage());
            }
        }
    }
}
