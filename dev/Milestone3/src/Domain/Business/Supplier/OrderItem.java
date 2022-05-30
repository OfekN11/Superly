package Domain.Business.Supplier;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {

    private int productId;
    private int idBySupplier;
    private String name;
    private int quantity;
    private float ppu;
    private int discount;
    private Double finalPrice;


    public OrderItem(int id,int idBySupplier, String name, int quantity, float ppu, int discount, Double finalPrice) {
        this.productId = id;
        this.idBySupplier = idBySupplier;
        this.name = name;
        this.quantity = quantity;
        this.ppu = ppu;
        this.discount = discount;
        this.finalPrice = finalPrice;
    }

    public String getName() {
        return name;
    }

    public int getProductId() {
        return productId;
    }

    public int getIdBySupplier() {
        return idBySupplier;
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
        result.add(String.valueOf(productId));
        result.add(name);
        result.add(String.valueOf(quantity));
        result.add(String.valueOf(ppu));
        result.add(String.valueOf(discount));
        result.add(String.valueOf(finalPrice));
        return result;
    }

    public void setQuantity(int q){
        quantity = q;
    }

    public void setPricePerUnit(float p){
        ppu = p;
    }

    public void setDiscount(int d){
        discount = d;
    }

    public void setFinalPrice(double fp){
        finalPrice = fp;
    }

}
