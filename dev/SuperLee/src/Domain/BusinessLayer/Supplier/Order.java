package Domain.BusinessLayer.Supplier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {


    private int id;
    private int supplierID;
    private Date date;
    private Date arrivalTime;
    private ArrayList<OrderItem> orderItems;

    private static int globalID = 1;


    public Order(int daysToArrival){
        this.id = globalID;
        globalID++;
        date = new Date();
        date = Calendar.getInstance().getTime();
        this.orderItems = new ArrayList<>();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, daysToArrival);
        arrivalTime = cal.getTime();
    }


    public void addItem(int id, String name, int quantity, float ppu, int discount, Double finalPrice) throws Exception {
        if(!changeable()){
            throw new Exception("This order can't be changed!");
        }
        orderItems.add(new OrderItem(id, name, quantity, ppu, discount, finalPrice));
    }

    public boolean itemExists(int itemId) {
        for(OrderItem orderItem : orderItems){
            if(orderItem.getId() == itemId)
                return true;
        }
        return false;

    }

    public void removeItem(int itemId) throws Exception {
        if(!changeable()){
            throw new Exception("This order can't be changed!");
        }

        for(OrderItem orderItem : orderItems){
            if(orderItem.getId() == itemId){
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
            if(item.getId() == id){
                item.setQuantity(quantity);
                item.setDiscount(discount);
                item.setFinalPrice(finalPrice);
                return;
            }
        }
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public boolean changeable(){
        return arrivalTime.after(Calendar.getInstance().getTime());
    }
}
