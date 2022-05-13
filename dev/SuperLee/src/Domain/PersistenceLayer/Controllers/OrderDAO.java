package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Order;
import Domain.PersistenceLayer.Abstract.DAO;

public class OrderDAO extends DAO {

    public OrderDAO(){
        super("Order");

    }

    public Order getOrder(int orderId){

        // if order doesn't exist in IDMAp go to database
        return null;
    }


}
