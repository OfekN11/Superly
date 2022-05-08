package Domain.DAL.Controllers;

import Domain.Business.Objects.Truck;
import Domain.DAL.Abstract.DataMapper;
import Globals.Enums.TruckModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TruckDataMap extends DataMapper {
    private final static Map<String, Truck> CONSTRAINT_IDENTITY_MAP = new HashMap<>();
    //Properties:

    //Constructor:
    public TruckDataMap(String tableName) {
        super(tableName);
    }

    //Functions:

    /**
     *
     * @param licenseNumber license number of the truck
     * @return truck if saved, null if is not exist
     */
    public Truck get(int licenseNumber){
        String id = Integer.toString(licenseNumber);
        Truck truck = CONSTRAINT_IDENTITY_MAP.get(id);
        if (truck!=null)
            return truck;

        try(Connection connection = getConnection()) {
            ResultSet result = select(connection, Arrays.asList(1),Arrays.asList(licenseNumber));
            if (!result.next()){
                return null;
            }

            TruckModel model = TruckModel.valueOf(result.getString(2));
            int netWeight =  result.getInt(3);
            int maxCapacityWeight = result.getInt(4);
            truck = new Truck(licenseNumber, model, netWeight, maxCapacityWeight);
            CONSTRAINT_IDENTITY_MAP.put(id,truck);
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
                    Arrays.asList(2, 3, 4), Arrays.asList(truck.getModel(), truck.getNetWeight(), truck.getMaxCapacityWeight())));
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
