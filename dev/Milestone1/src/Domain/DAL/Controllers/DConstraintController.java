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
            employees.add("10");
            employees.add("15");
            employees.add("30");
            employees.add("28");
            employees.add("18");
            output.add(new DConstraint(new SimpleDateFormat("dd-MM-yyyy").parse("14-07-2012"), ShiftTypes.Morning,employees));
            employees =new HashSet<>();
            employees.add("13");
            employees.add("19");
            employees.add("29");
            employees.add("49");
            employees.add("27");
            output.add(new DConstraint(new SimpleDateFormat("dd-MM-yyyy").parse("13-07-2012"), ShiftTypes.Evening,employees));
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
}
