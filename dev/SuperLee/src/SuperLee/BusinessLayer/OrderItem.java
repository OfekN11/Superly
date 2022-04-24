package SuperLee.BusinessLayer;

public class OrderItem {

    private int id;
    private String name;
    private int quantity;
    private float ppu;
    private int discount;
    private Double finalPrice;


    public OrderItem(int id, String name, int quantity, float ppu, int discount, Double finalPrice) {
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

    public String getStringInfo() {
        String result = "";
        result += String.valueOf(id);
        result += name;
        result += String.valueOf(quantity);
        result += String.valueOf(ppu);
        result += String.valueOf(discount);
        result += String.valueOf(finalPrice);
        return result;
    }
}
