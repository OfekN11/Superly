package ServiceLayer.Objects;

public class StockReport {

    private final int storeID;
    private final int productID;
    private final String productName;
    private final int amountInStore;
    private final int minAmountInStore;

    public StockReport(BusinessLayer.StockReport stockReport) {
        this.storeID = stockReport.getStoreID();
        this.productID = stockReport.getProductID();
        this.productName = stockReport.getProductName();
        this.amountInStore = stockReport.getAmountInStore();
        this.minAmountInStore = stockReport.getMinAmountInStore();
    }

    @Override
    public String toString() {
        return "StockReport{" +
                "storeID=" + storeID +
                ", productID=" + productID +
                ", productName='" + productName + '\'' +
                ", amountInStore=" + amountInStore +
                ", minAmountInStore=" + minAmountInStore +
                '}';
    }
}
