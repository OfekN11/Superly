package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DTOControllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DConstraint;
import Globals.Enums.ShiftTypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DConstraintController extends DTOControllers<DConstraint> {

    // properties


    //constructor
    public DConstraintController() {
        super("placeHolder");
    }

    //functions
    @Override
    public Set<DConstraint> loadData() {
        try {
            Set<DConstraint> output = new HashSet<>();
            Set<String> employees = new HashSet<>();
            employees.add("0");
            employees.add("1");
            employees.add("2");
            employees.add("3");
            employees.add("4");
            output.add(new DConstraint(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-2022"), ShiftTypes.Morning,employees));
            employees =new HashSet<>();
            employees.add("0");
            employees.add("1");
            employees.add("2");
            employees.add("3");
            employees.add("4");
            output.add(new DConstraint(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-2022"), ShiftTypes.Evening,employees));
            persistAll(output);
            return output;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAll() {

    }

    public void removeEmployeeToConstraint(DConstraint dConstraint, String employeeId) {
        //code to remove
    }

    public void addEmployeeToConstraint(DConstraint dConstraint, String employeeId) {
        // code to add
    }

    @Override
    public void delete(DConstraint toDelete) {

    }
}
