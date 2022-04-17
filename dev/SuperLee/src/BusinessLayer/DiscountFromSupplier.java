package BusinessLayer;

import jdk.jshell.spi.ExecutionControl;

import java.util.Date;
import java.util.Map;

public class DiscountFromSupplier {
    private int id;
    private Date date;
    private int supplierID;
    private String description;
    private Map<Product, Integer> amountBought;
    private int pricePaid;
    private int originalPrice;

    public DiscountFromSupplier(int id, Date date, int supplierID, String description, Map<Product, Integer> amountBought, int pricePaid, int originalPrice) {
        this.id = id;
        this.date = date;
        this.supplierID = supplierID;
        this.description = description;
        this.amountBought = amountBought;
        this.pricePaid = pricePaid;
        this.originalPrice = originalPrice;
    }

    public String getInfo() {
        return null;
    }
}
