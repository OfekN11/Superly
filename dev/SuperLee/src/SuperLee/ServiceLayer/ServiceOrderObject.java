package SuperLee.ServiceLayer;

import SuperLee.BusinessLayer.OrderItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ServiceOrderObject {


    private int id;
    private Date date;
    List<ServiceOrderItemObject> orderItems;


    public ServiceOrderObject(int id, Date date, List<ServiceOrderItemObject> orderItems ){
        this.id = id;
        this.date = date;
        this.orderItems = orderItems;
    }

    public int getId() {
        return id;
    }

    public List<ServiceOrderItemObject> getOrderItems() {
        return orderItems;
    }

    public Date getDate() {
        return date;
    }
}
