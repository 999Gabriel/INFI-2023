import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.List;

public class Kunden
{
    private Connection connection;

    public Kunden()
    {
        connection = Connector.getConnection();
    }


    public void createKundenTable()
    {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS kunden " +
                "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), nachname VARCHAR(255), email VARCHAR(255))"))
        {
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateTable()
    {
        try (PreparedStatement statement = connection.prepareStatement("ALTER TABLE kunden ADD COLUMN nachname " +
                    "VARCHAR(255) NOT NULL"))
        {
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();

        }
    }

    public void insertKunde(String name, String email, String nachname)
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO kunden (name, nachname, email) " +
                "VALUES (?, ?, ?)"))
        {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, nachname);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteKunde(int kundenIDToDelete)
    {
        // SQL-Befehl, um einen Kunden zu löschen
        String sql = "DELETE FROM kunden WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            // Setzen des Parameters (Kunden-ID)
            preparedStatement.setInt(1, kundenIDToDelete);

            // Ausführen des Löschbefehls
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0)
            {
                System.out.println("Kunde erfolgreich gelöscht.");
            } else
            {
                System.out.println("Kein Kunde mit der angegebenen ID gefunden.");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateKunde(int kundenIDToUpdate, String updatedName, String updateNachname, String updatedEmail)
    {
        // SQL-Befehl, um einen Kunden zu aktualisieren
        String sql = "UPDATE kunden SET name = ?, nachname = ?, email = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            // Setzen der Parameter
            preparedStatement.setString(1, updatedName);
            preparedStatement.setString(2, updateNachname);
            preparedStatement.setString(3, updatedEmail);
            preparedStatement.setInt(4, kundenIDToUpdate);

            // Ausführen des Update-Befehls
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0)
            {
                System.out.println("Kundendaten erfolgreich aktualisiert.");
            } else
            {
                System.out.println("Kein Kunde mit der angegebenen ID gefunden.");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void loadKundenFromJson(String filePath) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<List<Kunde>>(){}.getType();
            List<Kunde> kundenListe = gson.fromJson(reader, listType);

            for (Kunde kunde : kundenListe) {
                insertKunde(kunde.getName(), kunde.getNachname(), kunde.getEmail());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Kunde {
        private int id;
        private String name;
        private String nachname;
        private String email;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getNachname()
        {
            return nachname;
        }

        public void setNachname(String nachname)
        {
            this.nachname = nachname;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
        }
    }
}
