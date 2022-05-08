package Domain.DAL.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Abstract.DataMapper;
import Domain.DAL.Abstract.ObjectDateMapper;
import Globals.Enums.ShiftTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ConstraintDataMapper extends ObjectDateMapper<Constraint> {
    private final static Map<String, Constraint> CONSTRAINT_IDENTITY_MAP = new HashMap<>();
    // properties


    //constructor
    public ConstraintDataMapper() {
        super("Constraints");
    }


    // function

    /**
     *
     * @param date date of the constraint
     * @param shiftType shift type of the constraint
     * @return constraint if saved, null if is not exist
     */
    public Constraint get(LocalDate date, ShiftTypes shiftType){
        return super.get(Arrays.asList(1,2),Arrays.asList(date,shiftType));
    }

    public void save(Constraint constraint){
        for(String id : constraint.getEmployees()) {
            try {
                insert(Arrays.asList(constraint.getDate(),constraint.getType(),id));
                CONSTRAINT_IDENTITY_MAP.put(constraint.getDate().toString()+constraint.getType().toString(),constraint);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new RuntimeException("saved has failed");
            }
        }
    }

    public void updateEmployeesForConstraint(Constraint constraint,List<String> newIds){
        try {
            remove(Arrays.asList(1,2),Arrays.asList(constraint.getDate(),constraint.getType()));

            for (String id : newIds)
                insert(Arrays.asList(constraint.getDate(),constraint.getType(),id));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    @Override
    protected Map<String, Constraint> getMap() {
        return CONSTRAINT_IDENTITY_MAP;
    }

    @Override
    protected Constraint buildObject(ResultSet result) throws SQLException {
        if (!result.next()){
            return null;
        }
        Set<String> ids = new HashSet<>();
        LocalDate date = result.getDate(1).toLocalDate();
        ShiftTypes shiftType = ShiftTypes.valueOf(result.getString(2));
        ids.add(result.getString(3));
        while (result.next())
            ids.add(result.getString(3));
        return  new Constraint(date,shiftType,ids);
    }


}
