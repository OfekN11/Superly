package Domain.DAL.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.DateMapper;
import Domain.DAL.Controllers.ShiftEmployeesLink.ConstraintsEmployeesLink;
import Globals.Enums.ShiftTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstraintDAO extends DateMapper<Constraint> {
    private final static Map<String, Constraint> CONSTRAINT_IDENTITY_MAP = new HashMap<>();
    // properties
    ConstraintsEmployeesLink constraintsEmployeesLink;

    //constructor
    public ConstraintDAO() {
        super("Constraints");
        constraintsEmployeesLink = new ConstraintsEmployeesLink();
    }


    // function


    public Constraint get(LocalDate localDate,ShiftTypes shiftTypes) throws Exception {
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
