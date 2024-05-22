import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Customers")
public class Kunde
{
    @DatabaseField(generatedId = true)
    private int customer_id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String address;

    @DatabaseField
    private String phone_number;

    @DatabaseField
    private String email;

    // Leerer Konstruktor für ORMLite
    public Kunde() {
    }

    // Vollständiger Konstruktor
    public Kunde(int customer_id, String name, String address, String phone_number, String email) {
        this.customer_id = customer_id;
        this.name = name;
        this.address = address;
        this.phone_number = phone_number;
        this.email = email;
    }

    // Getter und Setter
    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
