package Domain.BusinessLayer.DiscountsAndSales;

//import jdk.jshell.spi.ExecutionControl;

import java.util.Date;

public class PurchaseFromSupplier {
    private int id;
    private int storeID;
    private int productID;
    private Date date;
    private int supplierID;
    private String description;
    private int amountBought;
    private int pricePaid;
    private int originalPrice;
    public Date getDate() {
        return date;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public String getDescription() {
        return description;
    }

    public int getAmountBought() {
        return amountBought;
    }

    public int getPricePaid() {
        return pricePaid;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public int getStoreID() {
        return storeID;
    }

    public int getProductID() {
        return productID;
    }

    public PurchaseFromSupplier(int id, int storeID, int productID, Date date, int supplierID, String description, int amountBought, int pricePaid, int originalPrice) {
        this.id = id;
        this.storeID = storeID;
        this.productID = productID;
        this.date = date;
        this.supplierID = supplierID;
        this.description = description;
        this.amountBought = amountBought;
        this.pricePaid = pricePaid;
        this.originalPrice = originalPrice;
    }

    public boolean isDiscount() {
        return pricePaid!=originalPrice;
    }
}
