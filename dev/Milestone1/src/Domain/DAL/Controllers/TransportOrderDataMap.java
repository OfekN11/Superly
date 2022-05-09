package Domain.DAL.Controllers;

import Domain.Business.Objects.Transport;
import Domain.Business.Objects.TransportOrder;
import Domain.Business.Objects.Truck;
import Domain.DAL.Abstract.DataMapper;
import Globals.Enums.TruckModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TransportOrderDataMap extends DataMapper {
    //Static Fields:
    private final static Map<String, TransportOrder> CONSTRAINT_IDENTITY_MAP = new HashMap<>();
    //Properties:

    //Constructor:
    public TransportOrderDataMap() {
        super("TransportOrders");
    }

    //Functions:

    /**
     *
     * @param orderID ID of the order
     * @return TransportOrder if saved, null if is not exist
     */
    public TransportOrder get(int orderID){
        String id = Integer.toString(orderID);
        TransportOrder order = CONSTRAINT_IDENTITY_MAP.get(id);
        if (order != null)
            return order;

        try(Connection connection = getConnection()) {
            ResultSet result = select(connection, Arrays.asList(1),Arrays.asList(orderID));
            if (!result.next()){
                return null;
            }


            //order = new TransportOrder(orderID, );
            CONSTRAINT_IDENTITY_MAP.put(id, order);
            return truck;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void save(Truck truck){
        try {
            insert(Arrays.asList(truck.getLicenseNumber(), truck.getModel(), truck.getNetWeight(), truck.getMaxCapacityWeight()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("Saved has failed!");
        }
    }

    public void update(Truck truck){
        try {
            update(Arrays.asList(1), Arrays.asList(truck.getLicenseNumber()),
                    Arrays.asList(2, 3, 4), Arrays.asList(truck.getModel(), truck.getNetWeight(), truck.getMaxCapacityWeight()));
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
