import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "autos")
public class Auto {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(columnName = "modell")
    private String modell;

    @DatabaseField(columnName = "marke")
    private String marke;

    @DatabaseField(columnName = "kennzeichen")
    private String kennzeichen;

    @DatabaseField(columnName = "status")
    private String status;

    @DatabaseField(columnName = "bild_url")
    private String bildUrl;

    public Auto() {
        //leer
    }

    public Auto(int id, String modell, String marke, String kennzeichen, String status, String bildUrl) {
        this.id = id;
        this.modell = modell;
        this.marke = marke;
        this.kennzeichen = kennzeichen;
        this.status = status;
        this.bildUrl = bildUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModell() {
        return modell;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public String getMarke() {
        return marke;
    }

    public void setMarke(String marke) {
        this.marke = marke;
    }

    public String getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(String kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBildUrl() {
        return bildUrl;
    }

    public void setBildUrl(String bildUrl) {
        this.bildUrl = bildUrl;
    }
}
