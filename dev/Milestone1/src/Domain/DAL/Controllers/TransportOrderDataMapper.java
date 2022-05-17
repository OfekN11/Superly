package Domain.DAL.Controllers;

import Domain.Business.Objects.TransportOrder;
import Domain.Business.Objects.Truck;
import Domain.DAL.Abstract.DataMapper;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Globals.Enums.TruckModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransportOrderDataMapper extends ObjectDateMapper<TransportOrder> {
    private final static Map<String, TransportOrder> TRUCK_IDENTITY_MAP = new HashMap<>();

    public TransportOrderDataMapper() {
        super("TransportOrders");
    }

    //Methods:
    public TransportOrder get(int licenseNumber) throws Exception {
        return get(Integer.toString(licenseNumber));
    }

    @Override
    protected Map<String, TransportOrder> getMap() {
        return TRUCK_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    @Override
    protected TransportOrder buildObject(ResultSet result) throws SQLException {
        return new TransportOrder(Integer.valueOf(result.getString(1)),
                result.getInt(2),
                result.getInt(3),
                result.getInt(4));
        //TODO: Add product quantity hash map
    }

    @Override
    public void insert(TransportOrder instance) throws SQLException {
        String id = Integer.toString(instance.getID());
        try
        {
            super.insert(Arrays.asList(id, instance.getSrc(), instance.getDst(), instance.getTransportID()));
        }
        catch (SQLException throwables) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }


    @Override
    protected Set<LinkDAO> getAllLinkDTOs() {
        return null;
    }

    public void update(TransportOrder truck) throws Exception {
        insert(truck);
    }

    public void save(TransportOrder transportOrder) throws Exception {
        save(Integer.toString(transportOrder.getID()), transportOrder);
    }

    public int delete(int orderId) throws Exception {
        return super.delete(Integer.toString(orderId));
    }
}
