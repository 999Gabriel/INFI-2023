import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Date;

@DatabaseTable(tableName = "buchungen")
public class Buchung {
    @DatabaseField(generatedId = true)
    private int rental_id;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "customer_id")
    private Kunde customer;

    @DatabaseField(canBeNull = false, foreign = true, columnName = "car_id")
    private Auto car;

    @DatabaseField(canBeNull = true)
    private Date start_date;

    @DatabaseField(canBeNull = true)
    private Date end_date;

    @DatabaseField(canBeNull = true)
    private double total_price;

    // Leerer Konstruktor für ORMLite
    public Buchung() {}

    // Vollständiger Konstruktor
    public Buchung(Kunde customer, Auto car, Date start_date, Date end_date, double total_price) {
        this.customer = customer;
        this.car = car;
        this.start_date = start_date;
        this.end_date = end_date;
        this.total_price = total_price;
    }

    // Getter und Setter
    public int getRental_id() {
        return rental_id;
    }

    public void setRental_id(int rental_id) {
        this.rental_id = rental_id;
    }

    public Kunde getCustomer() {
        return customer;
    }

    public void setCustomer(Kunde customer) {
        this.customer = customer;
    }

    public Auto getCar() {
        return car;
    }

    public void setCar(Auto car) {
        this.car = car;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}
