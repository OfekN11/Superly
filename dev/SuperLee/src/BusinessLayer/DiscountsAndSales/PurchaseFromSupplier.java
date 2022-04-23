package BusinessLayer.DiscountsAndSales;

import BusinessLayer.Product;
import jdk.jshell.spi.ExecutionControl;

import java.util.Date;
import java.util.Map;

public class PurchaseFromSupplier {
    private int id;
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
    public PurchaseFromSupplier(int id, Date date, int supplierID, String description, int amountBought, int pricePaid, int originalPrice) {
        this.id = id;
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
