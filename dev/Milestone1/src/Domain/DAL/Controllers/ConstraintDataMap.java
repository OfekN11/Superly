package Domain.DAL.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Abstract.DTOControllers;
import Domain.DAL.Abstract.DataMapper;
import Domain.DAL.Objects.DConstraint;
import Globals.Enums.ShiftTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class ConstraintDataMap extends DataMapper {
    private final static Map<String, Constraint> CONSTRAINT_IDENTITY_MAP = new HashMap<>();
    // properties


    //constructor
    public ConstraintDataMap() {
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
        String id = date.toString() + shiftType.toString();
        Constraint constraint = CONSTRAINT_IDENTITY_MAP.get(id);
        if (constraint!=null)
            return constraint;

        try(Connection connection = getConnection()) {
            ResultSet result = select(connection,Arrays.asList(1,2),Arrays.asList(date,shiftType));
            if (!result.next()){
                return null;
            }
            Set<String> ids = new HashSet<>();
            ids.add(result.getString(3));
            while (result.next())
                ids.add(result.getString(3));

            constraint = new Constraint(date,shiftType,ids);
            CONSTRAINT_IDENTITY_MAP.put(id,constraint);
            return constraint;

            
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void save(Constraint constraint){
        for(String id : constraint.getEmployees()) {
            try {
                insert(Arrays.asList(constraint.getDate(),constraint.getType(),id));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                throw new RuntimeException("saved has failed");
            }
        }
    }

    public void updateEmployeeForConstraint(Constraint constraint){
        try {
            remove(Arrays.asList(1,2),Arrays.asList(constraint.getDate(),constraint.getType()));

            for (String id : constraint.getEmployees())
                insert(Arrays.asList(constraint.getDate(),constraint.getType(),id));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
