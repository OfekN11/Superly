package Domain.ServiceLayer.Objects;

public class Product {
    private final int id;
    private final String name;
    private final int categoryID;
    private final double originalPrice;
    private final double currentPrice;
    private double weight;
    private int manufacturerID;
    public Product(Domain.BusinessLayer.Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.categoryID = p.getCategoryID();
        this.originalPrice = p.getOriginalPrice();
        this.currentPrice = p.getCurrentPrice();
        this.weight = p.getWeight();
        this.manufacturerID = p.getManufacturerID();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryID=" + categoryID +
                ", originalPrice=" + originalPrice +
                ", currentPrice=" + currentPrice +
                ", weight=" + weight +
                ", manufacturerID=" + manufacturerID +
                '}';
    }
}
