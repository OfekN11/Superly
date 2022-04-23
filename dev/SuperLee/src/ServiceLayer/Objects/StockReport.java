package ServiceLayer.Objects;

public class StockReport {

    private final int storeID;
    private final int productID;
    private final String productName;
    private final int amountInStore;
    private final int amountInWarehouse;
    private final int amountInTotal;
    private final int minAmountInStore;
    private final int maxAmountInStore;


    public StockReport(BusinessLayer.StockReport stockReport) {
        this.storeID = stockReport.getStoreID();
        this.productID = stockReport.getProductID();
        this.productName = stockReport.getProductName();
        this.amountInStore = stockReport.getAmountInStore();
        this.amountInWarehouse = stockReport.getAmountInWarehouse();
        this.amountInTotal = stockReport.getAmountInTotal();
        this.minAmountInStore = stockReport.getMinAmountInStore();
        this.maxAmountInStore = stockReport.getMaxAmountInStore();
    }

    @Override
    public String toString() {
        return "StockReport{" +
                "storeID=" + storeID +
                ", productID=" + productID +
                ", productName='" + productName + '\'' +
                ", amountInStore=" + amountInStore +
                ", amountInWarehouse=" + amountInWarehouse +
                ", amountInTotal=" + amountInTotal +
                ", minAmountInStore=" + minAmountInStore +
                ", maxAmountInStore=" + maxAmountInStore +
                '}';
    }
}
