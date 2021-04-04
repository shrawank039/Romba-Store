package in.rombashop.romba;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "address")
public class AddressList {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String address_id;
    private String firstname;
    private String lastlast;
    private String phone;
    private String alt_phone;
    private String email;
    private String add1;
    private String add2;
    private String pincode;
    private String city;
    private String state;
    private String country;
    private String type;

    @Ignore
    public AddressList(String address_id, String firstname, String lastlast, String phone, String alt_phone, String email, String add1, String add2, String pincode, String city, String state, String country, String type) {
        this.address_id = address_id;
        this.firstname = firstname;
        this.lastlast = lastlast;
        this.phone = phone;
        this.alt_phone = alt_phone;
        this.email = email;
        this.add1 = add1;
        this.add2 = add2;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.type = type;
    }

    public AddressList(String address_id, String firstname, String lastlast, String phone, String alt_phone, String email, String add1, String add2, String pincode, String city, String state, String country, int id, String type) {
        this.address_id = address_id;
        this.firstname = firstname;
        this.lastlast = lastlast;
        this.phone = phone;
        this.alt_phone = alt_phone;
        this.email = email;
        this.add1 = add1;
        this.add2 = add2;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.id = id;
        this.type = type;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getAlt_phone() {
        return alt_phone;
    }

    public void setAlt_phone(String alt_phone) {
        this.alt_phone = alt_phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastlast() {
        return lastlast;
    }

    public void setLastlast(String lastlast) {
        this.lastlast = lastlast;
    }

    public String getAdd1() {
        return add1;
    }

    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    public String getAdd2() {
        return add2;
    }

    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
