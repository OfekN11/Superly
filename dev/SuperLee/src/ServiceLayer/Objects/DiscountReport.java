package ServiceLayer.Objects;

import BusinessLayer.DiscountsAndSales.PurchaseFromSupplier;

import java.util.Date;

public class DiscountReport {
    private final int originalPrice;
    private final int finalPrice;
    private final Date date;
    private final int supplierID;
    private final String description;
    private final int amountBought;
    public DiscountReport(PurchaseFromSupplier d) {
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
