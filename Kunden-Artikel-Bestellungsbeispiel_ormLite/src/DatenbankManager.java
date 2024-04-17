import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;

public class DatenbankManager
{
    private final String databaseUrl = "jdbc:sqlite:bestellungsbeispiel_ormLite.db"; // Pfad zu Ihrer SQLite-Datenbank
    private ConnectionSource connectionSource;

    public DatenbankManager() {
        try {
            // Verbindung aufbauen
            connectionSource = new JdbcConnectionSource(databaseUrl);

            // Tabellen erstellen, falls sie noch nicht existieren
            TableUtils.createTableIfNotExists(connectionSource, Kunden.class);
            TableUtils.createTableIfNotExists(connectionSource, Artikel.class);
            TableUtils.createTableIfNotExists(connectionSource, Bestellung.class);

            // Beispieldaten einfügen
            insertBeispielDaten();
        } catch (SQLException e) {
            System.err.println("Konnte keine Verbindung zur Datenbank herstellen: " + e.getMessage());
        }
    }

    private void insertBeispielDaten() throws SQLException {
        // Kunden-Dao erstellen
        Dao<Kunden, Integer> kundenDao = DaoManager.createDao(connectionSource, Kunden.class);
        Kunden kunde = new Kunden();
        kunde.setName("Max Mustermann");
        kunde.setNachname("Mustermann");
        kunde.setEmail("max@example.com");
        kundenDao.create(kunde);

        // Artikel-Dao erstellen
        Dao<Artikel, Integer> artikelDao = DaoManager.createDao(connectionSource, Artikel.class);
        Artikel artikel = new Artikel();
        artikel.setBezeichnung("Tischlampe");
        artikel.setPreis(39.99);
        artikelDao.create(artikel);

        // Bestellung-Dao erstellen
        Dao<Bestellung, Integer> bestellungDao = DaoManager.createDao(connectionSource, Bestellung.class);
        Bestellung bestellung = new Bestellung();
        bestellung.setKundenID(kunde.getId()); // Setzt die ID des gerade eingefügten Kunden
        bestellung.setArtikelID(artikel.getId()); // Setzt die ID des gerade eingefügten Artikels
        bestellung.setAnzahl(2);
        bestellungDao.create(bestellung);
    }

    public static void main(String[] args) {
        new DatenbankManager();
    }
}
