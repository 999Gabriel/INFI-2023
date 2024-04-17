import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "bestellung")
public class Bestellung {
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private int kundenID;

    @DatabaseField
    private int artikelID;

    @DatabaseField
    private int anzahl;

    @DatabaseField(persisterClass = LocalDateTimePersister.class)
    private DateTime bestelltAm;

    // ORMLite benötigt einen leeren Konstruktor
    public Bestellung() {
    }

    // Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKundenID() {
        return kundenID;
    }

    public void setKundenID(int kundenID) {
        this.kundenID = kundenID;
    }

    public int getArtikelID() {
        return artikelID;
    }

    public void setArtikelID(int artikelID) {
        this.artikelID = artikelID;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }

    public DateTime getBestelltAm() {
        return bestelltAm;
    }

    public void setBestelltAm(DateTime bestelltAm) {
        this.bestelltAm = bestelltAm;
    }
}
