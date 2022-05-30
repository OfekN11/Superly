package Domain.Service.Objects.SupplierObjects;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ServiceOrderObject {


    private int id;
    private LocalDate date;
    List<ServiceOrderItemObject> orderItems;


    public ServiceOrderObject(int id, LocalDate date, List<ServiceOrderItemObject> orderItems ){
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

    public LocalDate getDate() {
        return date;
    }

    public String toString(){
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd");
        //String strDate = formatter.format(date);
        return "Order's ID: " + id + "\nDate: " + date.toString() + "\nOrderItems:\n" + printItems();
    }

    private String printItems(){
        String items = "";

        for (ServiceOrderItemObject item : orderItems){
            items += (item.toString() + "\n");
        }

        return items;
    }
}
