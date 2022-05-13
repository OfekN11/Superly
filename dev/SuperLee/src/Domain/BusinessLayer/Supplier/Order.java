package Domain.BusinessLayer.Supplier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {


    private int id;
    private int supplierID;
    private Date creationDate;
    private Date arrivalTime;
    private ArrayList<OrderItem> orderItems;
    private int storeID;
    private static int globalID = 1;


    public Order(int daysToArrival, int supplierID, int storeID){
        this.supplierID = supplierID;
        this.id = globalID;
        globalID++;
        //creationDate = new Date();
        creationDate = Calendar.getInstance().getTime();
        this.orderItems = new ArrayList<>();
        this.storeID = storeID;

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, daysToArrival);
        arrivalTime = cal.getTime();
    }

    //For uploading from dal
    public Order(int id, int supplierId, Date creationDate, Date arrivalTime, int storeID){
        this.id = id;
        this.supplierID = supplierId;
        this.creationDate = creationDate;
        this.arrivalTime = arrivalTime;
        globalID++;
        this.storeID = storeID;
    }


    //copy constructor, create new Id
    public Order(Order orderArriavalTimePassed) {
        this.id = globalID;
        this.supplierID = orderArriavalTimePassed.getSupplierId();
        this.creationDate = Calendar.getInstance().getTime();
        this.orderItems = orderArriavalTimePassed.getOrderItems();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        arrivalTime = cal.getTime();
        globalID++;
        this.storeID = orderArriavalTimePassed.getStoreID();

    }


    public void addItem(int productId, int idBySupplier, String name, int quantity, float ppu, int discount, Double finalPrice) throws Exception {
        if(!changeable()){
            throw new Exception("This order can't be changed!");
        }
        orderItems.add(new OrderItem(productId, idBySupplier,  name, quantity, ppu, discount, finalPrice));
    }

    public boolean itemExists(int itemId) {
        for(OrderItem orderItem : orderItems){
            if(orderItem.getProductId() == itemId)
                return true;
        }
        return false;

    }

    public void removeItem(int itemId) throws Exception {
        if(!changeable()){
            throw new Exception("This order can't be changed!");
        }

        for(OrderItem orderItem : orderItems){
            if(orderItem.getProductId() == itemId){
                orderItems.remove(orderItem);
                return;
            }
        }
    }

    public void updateItemQuantity(int id, int quantity, int discount, double finalPrice)throws Exception{
        if(!changeable()){
            throw new Exception("This order can't be changed!");
        }

        if(!itemExists(id)){
            throw new Exception("The requested item is not ordered!");
        }

        if(quantity == 0){
            throw new Exception("Unable to order 0 items, try to remove the item from the order instead.");
        }

        for(OrderItem item : orderItems){
            if(item.getProductId() == id){
                item.setQuantity(quantity);
                item.setDiscount(discount);
                item.setFinalPrice(finalPrice);
                return;
            }
        }
    }

    public Date getDate() {
        return creationDate;
    }
    public int getStoreID() { return storeID; } //WROTE BY AMIR
    public int getId() {
        return id;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public boolean changeable(){
        return arrivalTime.after(Calendar.getInstance().getTime());
    }

    public boolean passed(){
        return arrivalTime.before(Calendar.getInstance().getTime());
    }


    public int getSupplierId() {
        return supplierID;
    }

    public boolean hasEnoughItemQuantity(int productID, int amount) {
        OrderItem currOrderItem = getOrderItem(productID);
        if(currOrderItem != null)
            return currOrderItem.getQuantity() >= amount;
        return false;
    }

    private OrderItem getOrderItem(int productID) {
        for(OrderItem orderItem : orderItems) {
            if (orderItem.getProductId() == productID) {
                return orderItem;
            }
        }
        return null;
    }

    public int getDaysUntilOrder(Date currDate) {
        long diff = arrivalTime.getTime() - currDate.getTime();
        return (int) (diff / (1000*60*60*24));
    }

    public int getQuantityOfItem(int productId) {
        for(OrderItem orderItem : orderItems){
            if(orderItem.getProductId() == productId)
                return orderItem.getQuantity();
        }
        return 0;
    }
}
