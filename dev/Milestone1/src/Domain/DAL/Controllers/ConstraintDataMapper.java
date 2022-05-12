package Domain.DAL.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Abstract.DataMapper;
import Domain.DAL.Abstract.LinkDAO;
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
    @Override
    protected  <K> void addToSet(String id, String listName, K toAdd) throws SQLException{
        Constraint constraint = CONSTRAINT_IDENTITY_MAP.get(id);
        super.insert(Arrays.asList(id,constraint.getDate(),constraint.getType(),toAdd));
    }

    @Override
    protected  <K> void removeFromSet(String id, String listName, K toRemove) throws SQLException{
        super.remove(Arrays.asList(1,4),Arrays.asList(id,toRemove));
    }

    @Override
    protected <K> void replaceSet(String id, String listName, Set<K> toReplace) throws SQLException{
        super.remove(id);
        Constraint constraint = CONSTRAINT_IDENTITY_MAP.get(id);
        for(K employeeId: toReplace)
            insert(Arrays.asList(employeeId,constraint.getDate(),constraint.getType(),employeeId));
    }

    @Override
    protected Map<String, Constraint> getMap() {
        return CONSTRAINT_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        throw new IllegalArgumentException("this function should not be used cause there is no Link DAO in Constraint");
    }

    @Override
    protected Constraint buildObject(ResultSet result) throws SQLException {
        Set<String> ids = new HashSet<>();
        LocalDate date = result.getDate(2).toLocalDate();
        ShiftTypes shiftType = ShiftTypes.valueOf(result.getString(3));
        ids.add(result.getString(4));
        while (result.next())
            ids.add(result.getString(4));
        return  new Constraint(date,shiftType,ids);
    }

    @Override
    public void insert(Constraint instance) throws SQLException {
        for (String employeeID : instance.getEmployees())
            super.insert(Arrays.asList(instance.getDate().toString()+instance.getType().toString(),instance.getDate(),instance.getType(),employeeID));
    }


}
