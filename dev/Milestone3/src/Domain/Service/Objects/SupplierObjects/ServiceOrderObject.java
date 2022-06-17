package Domain.Service.Objects.SupplierObjects;

import Domain.Business.Objects.Supplier.OrderItem;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceOrderObject {


    private int orderId;
    private int supplierId;
    private LocalDate creationDate;
    private LocalDate arrivalDate;
    private int storeId;
    private String status;
    private List<ServiceOrderItemObject> orderItems;


    /*
    public ServiceOrderObject(int id, LocalDate date, List<ServiceOrderItemObject> orderItems ){
        this.orderId = id;
        this.date = date;
        this.orderItems = orderItems;
    }
     */

    public ServiceOrderObject(Domain.Business.Objects.Supplier.Order order) {
        List<ServiceOrderItemObject> items = new ArrayList<>();
        for (OrderItem oItem : order.getOrderItems()) {
            items.add(new ServiceOrderItemObject(oItem.getProductId(),oItem.getIdBySupplier(),oItem.getName(), oItem.getQuantity(), oItem.getPricePerUnit(), oItem.getDiscount(), oItem.getFinalPrice(), oItem.getMissingItems(), oItem.getDefectiveItems(), oItem.getDescription(), oItem.getWeight()));
        }
        this.orderId = order.getId();
        this.supplierId = order.getSupplierId();
        this.creationDate = order.getCreationTime();
        this.arrivalDate = order.getArrivaltime();
        this.storeId = order.getStoreID();
        this.status = order.getStatusString();
        this.orderItems = items;
    }

    private ServiceOrderObject(int orderId, int supplierId, LocalDate creationDate, LocalDate arrivalDate, int storeId, String status) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.creationDate = creationDate;
        this.arrivalDate = arrivalDate;
        this.storeId = storeId;
        this.status = status;

    }
    public ServiceOrderObject(int orderId, int supplierId, LocalDate creationDate, LocalDate arrivalDate, int storeId, String status, List<ServiceOrderItemObject> items) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.creationDate = creationDate;
        this.arrivalDate = arrivalDate;
        this.storeId = storeId;
        this.status = status;
        this.orderItems = items;

    }


    public int getId() {
        return orderId;
    }

    public List<ServiceOrderItemObject> getOrderItems() {
        return orderItems;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public String getStatus() {
        return status;
    }


    public String toString(){
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        //String strDate = formatter.format(date);
        return "Order's ID: " + orderId +  "\nSupplier's ID: " + supplierId + "\nCreation Time: " + creationDate.toString()
                + "\nArrival Time: " + arrivalDate.toString() +  "\nStoreId: "
                + storeId + "\nStatus: " + status +"\nOrderItems:\n" + printItems();
    }

    private String printItems(){
        String items = "";

        for (ServiceOrderItemObject item : orderItems){
            items += (item.toString() + "\n");
        }

        return items;
    }
}
