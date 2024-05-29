import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "buchungen")
public class Buchung {
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @DatabaseField(foreign = true, columnName = "kunde_id")
    private Kunde kunde;

    @DatabaseField(foreign = true, columnName = "auto_id")
    private Auto auto;

    @DatabaseField(columnName = "startdatum")
    private Date startdatum;

    @DatabaseField(columnName = "enddatum")
    private Date enddatum;

    @DatabaseField(columnName = "gesamtpreis")
    private double gesamtpreis;

    public Buchung() {
        //leer
    }

    public Buchung(Kunde kunde, Auto auto, Date startdatum, Date enddatum, double gesamtpreis) {
        this.kunde = kunde;
        this.auto = auto;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.gesamtpreis = gesamtpreis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public Auto getAuto() {
        return auto;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public Date getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(Date startdatum) {
        this.startdatum = startdatum;
    }

    public Date getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(Date enddatum) {
        this.enddatum = enddatum;
    }

    public double getGesamtpreis() {
        return gesamtpreis;
    }

    public void setGesamtpreis(double gesamtpreis) {
        this.gesamtpreis = gesamtpreis;
    }
}
