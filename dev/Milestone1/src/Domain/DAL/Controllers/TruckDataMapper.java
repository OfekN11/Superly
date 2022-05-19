package Domain.DAL.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.Business.Objects.Truck;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Globals.Enums.ShiftTypes;
import Globals.Enums.TruckModel;

import java.lang.reflect.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TruckDataMapper extends ObjectDateMapper<Truck> {
    private final static Map<String, Truck> TRUCK_IDENTITY_MAP = new HashMap<>();

    public TruckDataMapper() {
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
            super.insert(Arrays.asList(id, instance.getModel(), instance.getNetWeight(), instance.getMaxCapacityWeight()));
        }
        catch (SQLException throwables) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }


    @Override
    protected Set<LinkDAO> getAllLinkDTOs() {
        return null;
    }

    public void update(Truck truck) throws Exception {
        insert(truck);
    }

    public void save(Truck truck) throws Exception {
        save(Integer.toString(truck.getLicenseNumber()), truck);
    }

    public int delete(int licenseNumber) throws Exception {
        return super.delete(Integer.toString(licenseNumber));
    }


}
