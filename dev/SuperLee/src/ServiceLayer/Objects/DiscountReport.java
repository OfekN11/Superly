package ServiceLayer.Objects;

import BusinessLayer.DiscountsAndSales.DiscountFromSupplier;
import BusinessLayer.Product;

import java.util.Date;
import java.util.Map;

public class DiscountReport {
    private final int originalPrice;
    private final int finalPrice;
    private final Date date;
    private final int supplierID;
    private final String description;
    private final Map<Product, Integer> amountBought;
    public DiscountReport(DiscountFromSupplier d) {
        this.originalPrice = d.getOriginalPrice();
        this.finalPrice = d.getPricePaid();
        this.date = d.getDate();
        this.supplierID = d.getSupplierID();
        this.amountBought = d.getAmountBought();
        this.description = d.getDescription();
    }

    @Override
    public String toString() {
        return "DiscountReport{" +
                "originalPrice=" + originalPrice +
                ", finalPrice=" + finalPrice +
                ", date=" + date +
                ", supplierID=" + supplierID +
                ", description='" + description + '\'' +
                ", amountBought=" + amountBought +
                '}';
    }
}
