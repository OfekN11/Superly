package Domain.ServiceLayer.InventoryObjects;

public class Product {
    private final int id;
    private final String name;
    private final int categoryID;
    private final double originalPrice;
    private final double currentPrice;
    private double weight;
    private int manufacturerID;
    public Product(Domain.BusinessLayer.Inventory.Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.categoryID = p.getCategoryID();
        this.originalPrice = p.getOriginalPrice();
        this.currentPrice = p.getCurrentPrice();
        this.weight = p.getWeight();
        this.manufacturerID = p.getManufacturerID();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public String getWeight() {
        if (weight>0)
            return String.valueOf(weight);
        return "N/A";
    }
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryID=" + categoryID +
                ", originalPrice=" + String.format("%.2f", originalPrice) +
                ", currentPrice=" + String.format("%.2f", currentPrice) +
                ", weight=" + getWeight() +
                ", manufacturerID=" + manufacturerID +
                '}';
    }
}
