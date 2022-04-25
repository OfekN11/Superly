package Domain.BusinessLayer;

public class StockReport {

    private int storeID;
    private int productID;
    private String productName;
    private int amountInStore;
    private int amountInWarehouse;
    private int minAmountInStore;
    private int maxAmountInStore;

    public StockReport(int storeID, int productID, String productName, int amountInStore, int amountInWarehouse, int minAmountInStore, int maxAmountInStore) {
        this.storeID = storeID;
        this.productID = productID;
        this.productName = productName;
        this.amountInStore = amountInStore;
        this.amountInWarehouse = amountInWarehouse;
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
    public int getAmountInTotal() { return amountInStore+amountInWarehouse; }
    public int getMinAmountInStore() {
        return minAmountInStore;
    }
    public int getMaxAmountInStore() { return maxAmountInStore; }

    public void removeItemsFromStore(int amount) {
        if (amountInStore+amountInWarehouse-amount<0)
            throw new IllegalArgumentException("Can not buy or remove more items than in the store - please check amount");
        amountInStore-=amount;
    }

    public void moveItems(int amount) {
        if (amountInWarehouse-amount<0)
            throw new IllegalArgumentException("Can not move more items than in the warehouse");
        amountInWarehouse-=amount;
        amountInStore+=amount;
    }

    public void addItems(int amount) {
        if (amountInWarehouse+amountInStore+amount>maxAmountInStore)
            throw new IllegalArgumentException("Can not add more items than the max capacity in the store");
        amountInWarehouse+=amount;
    }

    public void returnItems(int amount) {
//        if (amountInWarehouse+amountInStore+amount>maxAmountInStore)
//            throw new IllegalArgumentException("Can not return more items than the max capacity in the store");
        amountInStore+=amount;
    }

    public boolean isLow() {
        return amountInStore+amountInWarehouse<minAmountInStore;
    }

    public void changeMin(int min) {
        if (min<1)
            throw new IllegalArgumentException("New min value must be positive");
        if (min>maxAmountInStore)
            throw new IllegalArgumentException("New min cannot be greater than max. Max is currently " + maxAmountInStore);
        minAmountInStore = min;
    }

    public void changeMax(int max) {
        if (max<1)
            throw new IllegalArgumentException("New max value must be positive");
        if (max<minAmountInStore)
            throw new IllegalArgumentException("New max cannot be less than min. Min is currently " + minAmountInStore);
        maxAmountInStore=max;
    }
}
