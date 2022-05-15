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


    public Constraint get(LocalDate localDate,ShiftTypes shiftTypes){
        return get(localDate.toString()+shiftTypes.toString());
    }

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
        String id = instance.getDate().toString()+instance.getType().toString();
        constraintsEmployeesLink.replaceSet(id,instance.getEmployees());
        super.remove(id);
        super.insert(Arrays.asList(id,instance.getDate(),instance.getType()));
    }

    @Override
    protected Set<LinkDAO> getAllLinkDTOs() {
        Set<LinkDAO> daos = new HashSet<>();
        daos.add(constraintsEmployeesLink);
        return daos;
    }

    public void update(Constraint constraint) throws SQLException {
        insert(constraint);
    }

    public void save(Constraint constraint) throws SQLException {
        save(constraint.getDate().toString()+constraint.getType().toString(),constraint);
    }

    public int delete(Constraint instance) throws SQLException {
        return super.delete(instance.getDate().toString()+instance.getType().toString(),instance);
    }
}
