package Domain.ServiceLayer.Suppliers;

public class ServiceOrderItemObject {

    private int id;
    private String name;
    private int quantity;
    private float ppu;
    private int discount;
    private Double finalPrice;


    public ServiceOrderItemObject(int id, String name, int quantity, float ppu, int discount, Double finalPrice) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.ppu = ppu;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public float getPricePerUnit() {
        return ppu;
    }

    public int getDiscount() {
        return discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public String toString(){
        return "ID: " + id + ", Name:" + name + ", Quantity: " + quantity + ", Price Per Unit: " + ppu + ", Discount: " + discount
                + ", Final Price: " + finalPrice;
    }

}
