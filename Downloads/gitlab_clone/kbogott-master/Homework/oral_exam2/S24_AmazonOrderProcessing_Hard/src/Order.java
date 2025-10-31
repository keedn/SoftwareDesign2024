/**
 * Class to Hold Order of AmazonOrder Item Information
 */
public class Order {
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    private String name;
    private String item;
    private String category;
    private int shippingCenter;
    private int shippingSection;
    private int truckNumber;

    /**
     * Constructor for Order Object
     * @param streetAddress Address
     * @param city City
     * @param state State
     * @param zip Zip
     * @param name Name
     * @param item Item
     * @param category Category
     */
    public Order(String streetAddress, String city, String state, String zip, String name, String item, String category) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.name = name;
        this.item = item;
        this.category = category;
        this.shippingCenter = -1;
        this.shippingSection = -1;
        this.truckNumber = -1;
    }

    /**
     * Constructor for Default (used for End of File Detection)
     */
    public Order() {
        this.item = "END_OF_FILE";
    }

    //*************GETTERS + SETTERS ****************
    public String getStreetAddress() {
        return streetAddress;
    }
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }
    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }
    public void setItem(String item) {
        this.item = item;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public int getShippingCenter() {
        return shippingCenter;
    }
    public void setShippingCenter(int shippingCenter) {
        this.shippingCenter = shippingCenter;
    }

    public int getShippingSection() {
        return shippingSection;
    }
    public void setShippingSection(int shippingSection) {
        this.shippingSection = shippingSection;
    }

    public int getTruckNumber() {
        return truckNumber;
    }
    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }
    //*************GETTERS + SETTERS **************** ^

    @Override
    public String toString() {
        return "Name: " + name + " | " +
                "Item: " + item + " | " +
                "Category: " + category + " | " +
                "Address: " + streetAddress + ", " + city + ", " + state + " " + zip + " | " +
                "Shipping Center: " + shippingCenter + " | " +
                "Shipping Section: " + shippingSection + " | " +
                "Truck Number: " + truckNumber + "\n";
    }
}
