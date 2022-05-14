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
    ConstraintsEmployeesLink constraintsEmployeesLink;

    //constructor
    public ConstraintDataMapper() {
        super("Constraints");
        constraintsEmployeesLink = new ConstraintsEmployeesLink();
    }


    // function

    @Override
    protected Map<String, Constraint> getMap() {
        return CONSTRAINT_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        switch (setName){
            case "employees":
                return constraintsEmployeesLink;
            default:
                throw new IllegalArgumentException("not define set");
        }
    }

    @Override
    protected Constraint buildObject(ResultSet result) throws SQLException {
        return new Constraint(result.getDate(2).toLocalDate(),ShiftTypes.valueOf(result.getString(3)),constraintsEmployeesLink.get(result.getString(1)));
    }

    @Override
    public void insert(Constraint instance) throws SQLException {
        for (String employeeID : instance.getEmployees())
            super.insert(Arrays.asList(instance.getDate().toString()+instance.getType().toString(),instance.getDate(),instance.getType(),employeeID));
    }


}
