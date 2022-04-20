package ServiceLayer.Objects;

public class Product {
    private final int id;
    private final String name;
    private final double originalPrice;
    private final double currentPrice;
    public Product(BusinessLayer.Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.originalPrice = p.getOriginalPrice();
        this.currentPrice = p.getCurrentPrice();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalPrice=" + originalPrice +
                ", currentPrice=" + currentPrice +
                '}';
    }
}
