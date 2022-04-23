package BusinessLayer;

public class StockReport {

    private int storeID;
    private int productID;
    private String productName;
    private int amountInStore;
    private int amountInWarehouse;
    private int amountInTotal;
    private int minAmountInStore;
    private int maxAmountInStore;

    public StockReport(int storeID, int productID, String productName, int amountInStore, int amountInWarehouse, int amountInTotal, int minAmountInStore, int maxAmountInStore) {
        this.storeID = storeID;
        this.productID = productID;
        this.productName = productName;
        this.amountInStore = amountInStore;
        this.amountInWarehouse = amountInWarehouse;
        this.amountInTotal = amountInTotal;
        this.minAmountInStore = minAmountInStore;
        this.maxAmountInStore = maxAmountInStore;
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
    public int getAmountInStore() { return amountInStore; }
    public int getAmountInWarehouse() { return amountInWarehouse; }
    public int getAmountInTotal() { return amountInTotal; }
    public int getMinAmountInStore() {
        return minAmountInStore;
    }
    public int getMaxAmountInStore() { return maxAmountInStore; }
}
