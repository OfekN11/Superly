package Domain.ServiceLayer.SupplierObjects;

import java.text.SimpleDateFormat;
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

    public String toString(){
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/YY");
        return "Order's ID: " + id + "\nDate: " + d.format(date) + "\nOrderItems:\n" + printItems();
    }

    private String printItems(){
        String items = "";

        for (ServiceOrderItemObject item : orderItems){
            items += (item.toString() + "\n");
        }

        return items;
    }
}
