public class Device {

    private String name;
    private String type;
    private String brand;
    private float price;

    Device(String name, String type, String brand, float price){
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }
}
