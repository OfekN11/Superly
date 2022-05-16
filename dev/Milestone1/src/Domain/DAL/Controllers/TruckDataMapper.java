package Domain.DAL.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.Business.Objects.Truck;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Globals.Enums.ShiftTypes;

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
    protected Truck buildObject(ResultSet result) throws SQLException {
        return new Constraint(result.getDate(2).toLocalDate(),ShiftTypes.valueOf(result.getString(3)),constraintsEmployeesLink.get(result.getString(1)));
    }

    @Override
    public void insert(Truck instance) throws SQLException {
        String id = Integer.toString(instance.getLicenseNumber());
        try
        {
            super.insert(Arrays.asList(id, instance.));
        }
    }

    @Override
    public void insert(Constraint instance) {
        String id = instance.getDate().toString()+instance.getType().toString();
        try {
            constraintsEmployeesLink.replaceSet(id,instance.getEmployees());
            super.remove(id);
            super.insert(Arrays.asList(id,instance.getDate(),instance.getType()));
        } catch (SQLException throwables) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    @Override
    protected Set<LinkDAO> getAllLinkDTOs() {
        Set<LinkDAO> daos = new HashSet<>();
        daos.add(constraintsEmployeesLink);
        return daos;
    }

    public void update(Constraint constraint) throws Exception {
        insert(constraint);
    }

    public void save(Constraint constraint) throws Exception {
        save(constraint.getDate().toString()+constraint.getType().toString(),constraint);
    }

    public int delete(Constraint instance) throws Exception {
        return super.delete(instance.getDate().toString()+instance.getType().toString());
    }

    //TODO
    public Set<Constraint> getConstraintsBetween(LocalDate start, LocalDate end)  {
        Set<Constraint> output = new HashSet<>();

        long numOfDays = ChronoUnit.DAYS.between(start, end.plusDays(1));

        List<LocalDate> listOfDates = Stream.iterate(start, date -> date.plusDays(1))
                .limit(numOfDays)
                .collect(Collectors.toList());

        for (LocalDate date:listOfDates){
            try {
                Constraint morning = get(date,ShiftTypes.Morning);
                Constraint evening = get(date,ShiftTypes.Evening);
                if (morning != null)
                    output.add(morning);
                if (evening != null)
                    output.add(evening);
            } catch (Exception e) {
                throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
            }
        }
        return output;
    }
}
