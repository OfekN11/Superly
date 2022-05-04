package Domain.BusinerssLayer.Supplier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Order {


    private int id;
    private Date date;
    private ArrayList<OrderItem> orderItems;


    public Order(int id){
        this.id = id;
        date = new Date();
        date = Calendar.getInstance().getTime();
        this.orderItems = new ArrayList<>();
    }


    public void addItem(int id, String name, int quantity, float ppu, int discount, Double finalPrice) {
        orderItems.add(new OrderItem(id, name, quantity, ppu, discount, finalPrice));
    }

    public boolean itemExists(int itemId) {
        for(OrderItem orderItem : orderItems){
            if(orderItem.getId() == itemId)
                return true;
        }
        return false;

    }

    public void removeItem(int itemId) {
        for(OrderItem orderItem : orderItems){
            if(orderItem.getId() == itemId){
                orderItems.remove(orderItem);
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
}
