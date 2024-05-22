import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Cars")
public class Auto {
    @DatabaseField(generatedId = true)
    private int car_id;

    @DatabaseField(canBeNull = false)
    private String model;

    @DatabaseField(canBeNull = false)
    private String brand;

    @DatabaseField(unique = true)
    private String license_plate;

    @DatabaseField(defaultValue = "Available")
    private String status;

    // Leerer Konstruktor für ORMLite
    public Auto() {}

    // Vollständiger Konstruktor
    public Auto(int car_id, String model, String brand, String license_plate, String status) {
        this.car_id = car_id;
        this.model = model;
        this.brand = brand;
        this.license_plate = license_plate;
        this.status = status;
    }

    // Getter und Setter
    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
