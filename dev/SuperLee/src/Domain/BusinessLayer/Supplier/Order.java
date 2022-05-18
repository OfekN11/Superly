package Domain.BusinessLayer.Supplier;

import Domain.PersistenceLayer.Controllers.OrderDAO;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {



    private int id;
    private int supplierID;
    private LocalDate creationDate;
    private LocalDate arrivalTime;
    private ArrayList<OrderItem> orderItems;
    private int storeID;
    private static int globalID = 1;


    private LocalDate calculateArrivalTime(LocalDate creationDate, int daysToArrival) {
        return creationDate.plusDays(daysToArrival);
    }

    private LocalDate getTodayDate() {
        return LocalDate.now();
    }

    public Order(int daysToArrival, int supplierID, int storeID){
        this.supplierID = supplierID;
        this.id = globalID;
        globalID++;

        //this.creationDate = Calendar.getInstance().getTime();
        this.creationDate = getTodayDate();
        this.orderItems = new ArrayList<>();
        this.storeID = storeID;

        //Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, daysToArrival);
        //arrivalTime = cal.getTime();

        arrivalTime = calculateArrivalTime(creationDate, daysToArrival);
    }


    public Order(int daysToArrival, int supplierID, int storeID, OrderItem item){
        this.supplierID = supplierID;
        this.id = globalID;
        globalID++;
        //creationDate = new Date();
        this.creationDate = getTodayDate();
        this.orderItems = new ArrayList<>();
        this.storeID = storeID;

        //Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DATE, daysToArrival);
        //arrivalTime = cal.getTime();
        arrivalTime = calculateArrivalTime(creationDate, daysToArrival);

        orderItems.add(item);
    }


    //For uploading from dal
    public Order(int id, int supplierId, LocalDate creationDate, LocalDate arrivalTime, int storeID){
        this.id = id;
        this.supplierID = supplierId;
        this.creationDate = creationDate;
        this.arrivalTime = arrivalTime;
        //globalID++;
        this.storeID = storeID;
        this.orderItems = new ArrayList<>();
    }


    //copy constructor, create new Id
    public Order(Order orderArriavalTimePassed) {
        this.id = globalID;
        this.supplierID = orderArriavalTimePassed.getSupplierId();
        this.creationDate = getTodayDate();
        this.orderItems = orderArriavalTimePassed.getOrderItems();
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, 1);
//        arrivalTime = cal.getTime();
        arrivalTime = calculateArrivalTime(creationDate, 1);

        globalID++;
        this.storeID = orderArriavalTimePassed.getStoreID();
    }

    public Order(Order order, OrderItem orderItem) {
        this.id = globalID;
        globalID++;
        this.supplierID = order.getSupplierId();
        this.creationDate = getTodayDate();
        this.orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        arrivalTime = calculateArrivalTime(creationDate, 1);
        this.storeID = order.getStoreID();
    }

    public Order(Order order, ArrayList<OrderItem> orderItems) {
        this.id = order.getId();
        this.supplierID = order.getSupplierId();
        this.creationDate = getTodayDate();
        this.orderItems = orderItems;
        arrivalTime = calculateArrivalTime(creationDate, 1);
        this.storeID = order.getStoreID();
    }

    public static void setGlobalId(int i) {
        globalID = i;
    }


    public void addItem(int productId, int idBySupplier, String name, int quantity, float ppu, int discount, Double finalPrice, OrderDAO orderDAO) throws Exception {
        if(!changeable()){
            throw new Exception("This order can't be changed!");
        }
        OrderItem orderItem = new OrderItem(productId, idBySupplier,  name, quantity, ppu, discount, finalPrice);
        orderDAO.addItem(id, orderItem);
        orderItems.add(orderItem);
    }

    public boolean itemExists(int itemId) {
        for(OrderItem orderItem : orderItems){
            if(orderItem.getProductId() == itemId)
                return true;
        }
        return false;

    }

    public void removeItem(int itemId, OrderDAO orderDAO) throws Exception {
        if(!changeable()){
            throw new Exception("This order can't be changed!");
        }

        for(OrderItem orderItem : orderItems){
            if(orderItem.getProductId() == itemId){
                orderDAO.removeOrderItem(id, orderItem.getProductId());
                orderItems.remove(orderItem);
                return;
            }
        }
    }

    public void updateItemQuantity(int itemId, int quantity, int discount, double finalPrice, OrderDAO orderDAO)throws Exception{
        if(!changeable()){
            throw new Exception("This order can't be changed!");
        }

        if(!itemExists(itemId)){
            throw new Exception("The requested item is not ordered!");
        }

        if(quantity == 0){
            throw new Exception("Unable to order 0 items, try to remove the item from the order instead.");
        }

        for(OrderItem item : orderItems){
            if(item.getProductId() == id){
                orderDAO.updateItemQuantity(id, item.getProductId(), quantity);
                orderDAO.updateItemDiscount(id, item.getProductId(), discount);
                orderDAO.updateItemFinalPrice(id, item.getProductId(), finalPrice);
                item.setQuantity(quantity);
                item.setDiscount(discount);
                item.setFinalPrice(finalPrice);
                return;
            }
        }
    }

    public LocalDate getDate() {
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
        return arrivalTime.isAfter(LocalDate.now());
        //return arrivalTime.after(Calendar.getInstance().getTime());
    }

    public boolean passed(){
        return arrivalTime.isBefore(LocalDate.now());

//        return arrivalTime.before(Calendar.getInstance().getTime());
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

    public int getDaysUntilOrder(LocalDate currDate) {
        Period period = Period.between(arrivalTime, currDate);
        return period.getDays();
        //long diff = arrivalTime.getTime() - currDate.getTime();
        //return (int) (diff / (1000*60*60*24));
    }

    public int getQuantityOfItem(int productId) {
        for(OrderItem orderItem : orderItems){
            if(orderItem.getProductId() == productId)
                return orderItem.getQuantity();
        }
        return 0;
    }

    public LocalDate getCreationTime() {
        return creationDate;
    }

    public LocalDate getArrivaltime() {
        return arrivalTime;
    }

    public void uploadItemsFromDB(ArrayList<OrderItem> items) {
        this.orderItems = items;
    }
}
