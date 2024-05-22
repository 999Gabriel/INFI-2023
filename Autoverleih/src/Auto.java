import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "auto")
class Auto {
    @DatabaseField(id = true)
    private int id;

    @DatabaseField
    private String modell;

    @DatabaseField
    private String marke;

    @DatabaseField
    private String kennzeichen;

    @DatabaseField
    private String status;

    @DatabaseField
    private String bild_url;

    public Auto() {
        // ORMLite benötigt einen parameterlosen Konstruktor
    }

    public Auto(int id, String modell, String marke, String kennzeichen, String status, String bild_url) {
        this.id = id;
        this.modell = modell;
        this.marke = marke;
        this.kennzeichen = kennzeichen;
        this.status = status;
        this.bild_url = bild_url;
    }

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", modell='" + modell + '\'' +
                ", marke='" + marke + '\'' +
                ", kennzeichen='" + kennzeichen + '\'' +
                ", status='" + status + '\'' +
                ", bild_url='" + bild_url + '\'' +
                '}';
    }
}
