package ServiceLayer.Objects;

public class Product {
    private final int id;
    private final String name;
    private final double originalPrice;
    public Product(BusinessLayer.Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.originalPrice = p.getOriginalPrice();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", originalPrice=" + originalPrice +
                '}';
    }
}
