package Domain.Service.Objects.SupplierObjects;

public class ServiceOrderItemObject {

    private int id;
    private int supplierProductID;
    private String name;
    private int quantity;
    private float ppu;
    private int discount;
    private Double finalPrice;
    private int missing;
    private int defective;
    private String description;
    private double weight;


    public ServiceOrderItemObject(int id, int supplierProductID, String name, int quantity, float ppu, int discount, Double finalPrice, int missing, int defective, String description, double weight) {
        this.id = id;
        this.supplierProductID = supplierProductID;
        this.name = name;
        this.quantity = quantity;
        this.ppu = ppu;
        this.discount = discount;
        this.finalPrice = finalPrice;
        this.missing = missing;
        this.defective = defective;
        this.description = description;
        this.weight = weight;
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int supplierProductID() {
        return supplierProductID;
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

    public String getDescription() {
        return description;
    }

    public double getWeight() {
        return weight;
    }

    public int getDefective() {
        return defective;
    }

    public int getMissing() {
        return missing;
    }

    public String toString(){
        return "ID: " + id + ", Name:" + name + ", Supplier Id For Product:" + supplierProductID + ", Id by Supplier:" + supplierProductID + ", Quantity: " + quantity + ", Price Per Unit: " + ppu + ", Discount: " + discount
                + ", Final Price: " + finalPrice + "\nWeight: " + weight  + ", Missing items: " + missing + ", Defective items: " + defective + ", Description: " + description + "\n";
    }

    public String toStringDiscount(float originalPrice) {
        return "ID: " + id + ", Name: " + name + "\nQuantity: " + quantity + ", Price Per Unit: " + ppu + "\nDiscount: " + discount
                + "\nFinal Price: " + finalPrice + ", Original Price: " + originalPrice;
    }
}
