package Domain.Business.Objects;

public class Product {
    int productSN;
    String productName;
    double weight;

    public Product(int productSN, String productName, double weight) {
        this.productSN = productSN;
        this.productName = productName;
        this.weight = weight;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
