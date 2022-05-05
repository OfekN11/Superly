package Domain.BusinessLayer.Inventory;

public class StockReport {

    private int storeID;
    private int productID;
    private String productName;
    private int amountInStore;
    private int amountInWarehouse;
    private int amountInDelivery;
    private int minAmountInStore;
    private int targetAmountInStore;

    public StockReport(int storeID, int productID, String productName, int amountInStore, int amountInWarehouse, int minAmountInStore, int targetAmountInStore) {
        this.storeID = storeID;
        this.productID = productID;
        this.productName = productName;
        this.amountInStore = amountInStore;
        this.amountInWarehouse = amountInWarehouse;
        this.amountInDelivery = 0;
        this.minAmountInStore = minAmountInStore;
        this.targetAmountInStore = targetAmountInStore;
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
    public int getTargetAmountInStore() { return targetAmountInStore; }
    public int getAmountInDelivery() { return amountInDelivery; }

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
        amountInWarehouse+=amount;
    }

    public void returnItems(int amount) {
        amountInStore+=amount;
    }

    public boolean isLow() {
        return amountInStore+amountInWarehouse<minAmountInStore;
    }

    public void changeMin(int min) {
        if (min<1)
            throw new IllegalArgumentException("New min value must be positive");
        if (min > targetAmountInStore)
            throw new IllegalArgumentException("New min cannot be greater than target. target is currently " + targetAmountInStore);
        minAmountInStore = min;
    }

    public void changeTarget(int target) {
        if (target<1)
            throw new IllegalArgumentException("New target value must be positive");
        if (target < minAmountInStore)
            throw new IllegalArgumentException("New target cannot be less than min. Min is currently " + minAmountInStore);
        targetAmountInStore = target;
    }

    public void changeName(String name) {
        productName=name;
    }

    private int getTotalAmount() { return amountInDelivery+amountInStore+amountInWarehouse;}
    public int getAmountForOrder() {
        return targetAmountInStore - getTotalAmount();
    }

    public boolean gotUnderMinimum(int amount) {
        return (minAmountInStore > getTotalAmount()) && (minAmountInStore <= getTotalAmount()+amount);
    }
}
