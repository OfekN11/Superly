package Domain.DAL.Controllers.TransportModul;

import Domain.Business.Objects.Transport;
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

public class TransportDataMapper extends ObjectDateMapper<Transport> {
    private final static Map<String, Transport> TRUCK_IDENTITY_MAP = new HashMap<>();

    public TransportDataMapper() {
        super("Transports");
    }

    //Methods:
    public Transport get(int transportID) throws Exception {
        return get(Integer.toString(transportID));
    }

    @Override
    protected Map<String, Transport> getMap() {
        return TRUCK_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    @Override
    protected Transport buildObject(ResultSet result) throws SQLException {
        return  null;
        /*
        return new Transport(Integer.valueOf(result.getString(1)),
                TruckModel.valueOf(result.getString(2)),
                result.getInt(3),
                result.getInt(4));
         */
    }

    @Override
    public void insert(Transport instance) throws SQLException {
        String id = Integer.toString(instance.getSN());
        try
        {
            super.insert(Arrays.asList());//TODO: id, instance.getModel(), instance.getNetWeight(), instance.getMaxCapacityWeight()));
        }
        catch (SQLException throwables) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    @Override
    protected Set<LinkDAO> getAllLinkDTOs() {
        return null;
    }

    public void update(Transport transport) throws Exception {
        insert(transport);
    }

    public void save(Transport transport) throws Exception {
        save(Integer.toString(transport.getSN()), transport);
    }

    public int delete(int transportID) throws Exception {
        return super.delete(Integer.toString(transportID));
    }
}
