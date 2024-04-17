import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

@DatabaseTable(tableName = "artikel")
public class Artikel
{

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String bezeichnung;

    @DatabaseField
    private double preis;

    @DatabaseField(dataType = DataType.DATE_TIME, persisterClass = LocalDateTimePersister.class)
    private DateTime erstelltAm; // ORMLite unterstützt Java 8 Time-API nicht direkt, Anpassung nötig

    public Artikel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public double getPreis()
    {
        return preis;
    }

    public void setPreis(double preis) {
        this.preis = preis;
    }

    public DateTime getErstelltAm() {
        return erstelltAm;
    }

    public void setErstelltAm(DateTime erstelltAm) {
        this.erstelltAm = erstelltAm;
    }
}
