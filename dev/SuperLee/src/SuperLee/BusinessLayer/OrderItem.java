package SuperLee.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getStringInfo() {
        List<String> result = new ArrayList<>();
        result.add(String.valueOf(id));
        result.add(name);
        result.add(String.valueOf(quantity));
        result.add(String.valueOf(ppu));
        result.add(String.valueOf(discount));
        result.add(String.valueOf(finalPrice));
        return result;
    }
}
