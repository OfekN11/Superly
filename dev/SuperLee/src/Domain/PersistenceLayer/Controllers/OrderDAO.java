package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Order;
import Domain.PersistenceLayer.Abstract.DataMapper;

public class OrderDAO extends DataMapper {

    public OrderDAO(){
        super("Order");

    }

    public Order getOrder(int orderId){

        // if order doesn't exist in IDMAp go to database
        return null;
    }


}
