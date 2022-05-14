package Domain.ServiceLayer.InventoryObjects;

import java.util.Map;

public class StockReport {

    private final int storeID;
    private final int productID;
//    private final String productName;
    private final int amountInStore;
    private final int amountInWarehouse;
    private final Map<Integer, Integer> amountInDeliveries;
    private final int minAmountInStore;
    private final int targetAmountInStore;


    public StockReport(Domain.BusinessLayer.Inventory.StockReport stockReport) {
        this.storeID = stockReport.getStoreID();
        this.productID = stockReport.getProductID();
//        this.productName = stockReport.getProductName();
        this.amountInStore = stockReport.getAmountInStore();
        this.amountInWarehouse = stockReport.getAmountInWarehouse();
        this.amountInDeliveries = stockReport.getAmountInDeliveries();
        this.minAmountInStore = stockReport.getMinAmountInStore();
        this.targetAmountInStore = stockReport.getTargetAmountInStore();
    }

    @Override
    public String toString() {
        String deliveries = "";
        for (Map.Entry<Integer, Integer> entry : amountInDeliveries.entrySet())
        {
            deliveries += "order ID:" + entry.getKey() + "-amount:" + entry.getValue() + ", ";
        }
        return "StockReport{" +
                "storeID=" + storeID +
                ", productID=" + productID +
//                ", productName='" + productName + '\'' +
                ", amountInStore=" + amountInStore +
                ", amountInWarehouse=" + amountInWarehouse +
                ", amountInDeliveries=" + deliveries +
                ", minAmountInStore=" + minAmountInStore +
                ", targetAmountInStore=" + targetAmountInStore +
                '}';
    }
}
