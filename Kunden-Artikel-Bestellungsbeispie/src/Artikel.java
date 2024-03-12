import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Artikel
{
    private Connection connection;

    public Artikel()
    {
        connection = Connector.getConnection();
    }

    public void createArtikelTable()
    {
        try (PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS artikel " +
                "(id INT AUTO_INCREMENT PRIMARY KEY, " +
                "bezeichnung VARCHAR(255), preis DECIMAL(10, 2), erstellt_am TIMESTAMP DEFAULT CURRENT_TIMESTAMP)"))
        {
            statement.executeUpdate();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insertArtikel(String bezeichnung, double preis)
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO artikel " +
                "(bezeichnung, preis, erstellt_am) VALUES (?, ?, NOW())"))
        {
            preparedStatement.setString(1, bezeichnung);
            preparedStatement.setDouble(2, preis);
            preparedStatement.executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateLagerbestand(int artikelID, int anzahl)
    {
        try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE artikel SET lagerbestand " +
                "= lagerbestand - ? WHERE id = ?")) {
            preparedStatement.setInt(1, anzahl);
            preparedStatement.setInt(2, artikelID);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteArtikel(int artikelIDToDelete)
    {
        // SQL-Befehl, um einen Kunden zu löschen
        String sql = "DELETE FROM artikel WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            // Setzen des Parameters (Artikel-ID)
            preparedStatement.setInt(1, artikelIDToDelete);

            // Ausführen des Löschbefehls
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0)
            {
                System.out.println("Kunde erfolgreich gelöscht.");
            } else
            {
                System.out.println("Kein Artikel mit der angegebenen ID gefunden.");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateArtikel(int id, String bezeichnung, double preis)
    {
        // SQL-Befehl, um einen Artikel zu aktualisieren
        String sql = "UPDATE artikel SET bezeichnung = ?, preis = ? WHERE id = ?";

        try
        {
            // Vorbereiten des SQL-Statements
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setzen der Parameter
            preparedStatement.setString(1, bezeichnung);
            preparedStatement.setDouble(2, preis);
            preparedStatement.setInt(3, id);

            // Ausführen des Update-Befehls
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0)
            {
                System.out.println("Artikel erfolgreich aktualisiert.");
            } else
            {
                System.out.println("Kein Artikel mit der ID gefunden.");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void displayArtikelWithDate(int idOfProduct)
    {
        String query = "SELECT id, bezeichnung, preis, erstellt_am FROM artikel WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setInt(1, idOfProduct);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                System.out.println("ID: " + resultSet.getInt("id") + ", Bezeichnung: "
                        + resultSet.getString("bezeichnung") + ", Preis: "
                        + resultSet.getDouble("preis") + ", Erstellt am: "
                        + resultSet.getTimestamp("erstellt_am"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void updateArtikelErstellungsdatum(int artikelID, LocalDateTime neuesDatum)
    {
        String query = "UPDATE artikel SET erstellt_am = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setTimestamp(1, Timestamp.valueOf(neuesDatum));
            statement.setInt(2, artikelID);

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0)
            {
                System.out.println("Das Erstellungsdatum des Artikels wurde erfolgreich aktualisiert.");
            } else
            {
                System.out.println("Es konnte kein Artikel mit der angegebenen ID gefunden werden.");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    public void getArtikelNachDatum(LocalDate datum)
    {
        String query = "SELECT * FROM artikel WHERE erstellt_am > ?";

        try (PreparedStatement statement = connection.prepareStatement(query))
        {
            statement.setDate(1, Date.valueOf(datum));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                int id = resultSet.getInt("id");
                String bezeichnung = resultSet.getString("bezeichnung");
                double preis = resultSet.getDouble("preis");
                Timestamp erstelltAm = resultSet.getTimestamp("erstellt_am");

                System.out.println("ID: " + id + ", Bezeichnung: " + bezeichnung + ", Preis: "
                        + preis + ", Erstellt am: " + erstelltAm);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
