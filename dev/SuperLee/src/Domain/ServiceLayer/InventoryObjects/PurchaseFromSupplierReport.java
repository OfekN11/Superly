package Domain.ServiceLayer.InventoryObjects;

import Domain.BusinessLayer.Inventory.DiscountsAndSales.PurchaseFromSupplier;

import java.util.Date;

public class PurchaseFromSupplierReport {
    private final int storeID;
    private final int productID;
    private final int amountBought;
    private final double originalPrice;
    private final double finalPrice;
    private final Date date;
    private final int supplierID;

    public PurchaseFromSupplierReport(PurchaseFromSupplier d) {
        this.storeID = d.getStoreID();
        this.productID = d.getProductID();
        this.originalPrice = d.getOriginalPrice();
        this.finalPrice = d.getPricePaid();
        this.date = d.getDate();
        this.supplierID = d.getSupplierID();
        this.amountBought = d.getAmountBought();
    }

    @Override
    public String toString() {
        return "PurchaseFromSupplierReport{" +
                "storeID=" + storeID +
                ", productID=" + productID +
                ", amountBought=" + amountBought +
                ", originalPrice=" + originalPrice +
                ", finalPrice=" + finalPrice +
                ", date=" + date +
                ", supplierID=" + supplierID + '\'' +
                '}';
    }
}
