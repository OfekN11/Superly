package BusinessLayer;

public class StockReport {

    private int storeID;
    private int productID;
    private String productName;
    private int amountInStore;
    private int minAmountInStore;

    public StockReport(int storeID, int productID, String productName, int amountInStore, int minAmountInStore) {
        this.storeID = storeID;
        this.productID = productID;
        this.productName = productName;
        this.amountInStore = amountInStore;
        this.minAmountInStore = minAmountInStore;
    }

    public int getStoreID() {
        return storeID;
    }

    public int getProductID() {
        return productID;
    }

    public String getProductName() {
        return productName;
    }

    public int getAmountInStore() {
        return amountInStore;
    }

    public int getMinAmountInStore() {
        return minAmountInStore;
    }
}
