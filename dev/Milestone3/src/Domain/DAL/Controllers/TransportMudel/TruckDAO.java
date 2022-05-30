package Domain.DAL.Controllers.TransportMudel;

import Domain.Business.Objects.Truck;
import Domain.DAL.ConnectionHandler;
import Globals.Enums.TruckModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TruckDAO extends DAO {
    private final static Map<Integer, Truck> TRUCK_IDENTITY_MAP = new HashMap<>();

    public TruckDAO() {
        super("Trucks");
        try(ConnectionHandler connection = getConnectionHandler()){
            ResultSet resultSet= select(connection.get());
            while (resultSet.next()){
                Truck truck = new Truck(resultSet.getInt(1),TruckModel.valueOf(resultSet.getString(2)),resultSet.getInt(3),resultSet.getInt(4));
                TRUCK_IDENTITY_MAP.put(truck.getLicenseNumber(),truck);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("we could not load data from the db");
        }

    }

    //Methods:
    public Truck get(int licenseNumber) throws Exception {
        return TRUCK_IDENTITY_MAP.get(licenseNumber);
    }




    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }


    protected Truck buildObject(ResultSet result) throws SQLException {
        return new Truck(Integer.valueOf(result.getString(1)),
                TruckModel.valueOf(result.getString(2)),
                result.getInt(3),
                result.getInt(4));
    }


    public void insert(Truck instance) throws SQLException {
        int id = instance.getLicenseNumber();
        if(!TRUCK_IDENTITY_MAP.containsKey(id)){
            TRUCK_IDENTITY_MAP.put(id,instance);
        }
        try
        {
            super.remove(id);
            super.insert(Arrays.asList(id, instance.getModel(), instance.getNetWeight(), instance.getMaxCapacityWeight()));
        }
        catch (SQLException throwables) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }


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
        return super.remove(licenseNumber);
    }



}
