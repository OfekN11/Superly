package Domain.BusinessLayer.Inventory.DiscountsAndSales;

//import jdk.jshell.spi.ExecutionControl;

import java.util.Date;

public class PurchaseFromSupplier {
    private int id;
    private int storeID;
    private int productID;
    private Date date;
    private int supplierID;
    private int amountBought;
    private double pricePaid;
    private double originalPrice;
    public Date getDate() {
        return date;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public int getAmountBought() {
        return amountBought;
    }

    public double getPricePaid() {
        return pricePaid;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public int getStoreID() {
        return storeID;
    }

    public int getProductID() {
        return productID;
    }

    public PurchaseFromSupplier(int id, int storeID, int productID, Date date, int supplierID, int amountBought, double pricePaid, double originalPrice) {
        this.id = id;
        this.storeID = storeID;
        this.productID = productID;
        this.date = date;
        this.supplierID = supplierID;
        this.amountBought = amountBought;
        this.pricePaid = pricePaid;
        this.originalPrice = originalPrice;
    }

    public boolean isDiscount() {
        return pricePaid!=originalPrice;
    }
}
