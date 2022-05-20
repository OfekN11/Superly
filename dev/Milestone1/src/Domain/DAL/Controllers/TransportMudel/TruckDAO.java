package Domain.DAL.Controllers.TransportMudel;

import Domain.Business.Objects.Truck;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.DateMapper;
import Globals.Enums.TruckModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TruckDAO extends DateMapper<Truck> {
    private final static Map<String, Truck> TRUCK_IDENTITY_MAP = new HashMap<>();

    public TruckDAO() {
        super("Trucks");
    }

    //Methods:
    public Truck get(int licenseNumber) throws Exception {
        return get(Integer.toString(licenseNumber));
    }

    @Override
    protected Map<String, Truck> getMap() {
        return TRUCK_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    @Override
    protected Truck buildObject(ResultSet result) throws SQLException {
        return new Truck(Integer.valueOf(result.getString(1)),
                TruckModel.valueOf(result.getString(2)),
                result.getInt(3),
                result.getInt(4));
    }

    @Override
    public void insert(Truck instance) throws SQLException {
        String id = Integer.toString(instance.getLicenseNumber());
        try
        {
            super.delete(id);
            super.insert(Arrays.asList(id, instance.getModel(), instance.getNetWeight(), instance.getMaxCapacityWeight()));
        }
        catch (SQLException throwables) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }


    @Override
    protected Set<LinkDAO> getAllLinkDTOs() {
        return new HashSet<>();
    }

    public void update(Truck truck) throws Exception {
        insert(truck);
    }

    public void save(Truck truck) throws Exception {
       insert(truck);
    }

    public int delete(int licenseNumber) throws Exception {
        return super.delete(Integer.toString(licenseNumber));
    }



}
