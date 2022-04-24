package Domain.DAL.Controllers;

import Domain.DAL.Objects.DShift;
import Globals.Enums.ShiftTypes;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DEmployeeShiftController extends DalController<Pair<Pair<Date,ShiftTypes>,Set<String>>> {

    // properties
    public DEmployeeShiftController() {
        super("tablename");
    }

    public void addEmployee(DShift dShift, String employeeId) {
        // code to update the DB
        // use visitorPattern
    }

    public void removeEmployee(DShift dShift, String employeeId) {
        // code to update the DB
        // use visitorPattern
    }

    @Override
    public Set<Pair<Pair<Date, ShiftTypes>, Set<String>>> loadData() {
        try {
            Set<Pair<Pair<Date, ShiftTypes>, Set<String>>> shiftEmployee = new HashSet<>();
            Set<String> employees = new HashSet<>();
            for (int i=0;i<40;i=i+5)
                employees.add(""+i);
            employees.add(""+8);
            shiftEmployee.add(new Pair<>(new Pair<>(new SimpleDateFormat("dd-MM-yyyy").parse("19-06-1198"),ShiftTypes.Evening),employees));
            employees = new HashSet<>(employees);
            employees.add("2");
            shiftEmployee.add(new Pair<>(new Pair<>(new SimpleDateFormat("dd-MM-yyyy").parse("23-07-1198"),ShiftTypes.Evening),employees));
            employees = new HashSet<>(employees);
            employees.add("6");
            shiftEmployee.add(new Pair<>(new Pair<>(new SimpleDateFormat("dd-MM-yyyy").parse("15-06-1198"),ShiftTypes.Evening),employees));
            employees = new HashSet<>(employees);
            employees.add("4");
            shiftEmployee.add(new Pair<>(new Pair<>(new SimpleDateFormat("dd-MM-yyyy").parse("18-06-1198"),ShiftTypes.Evening),employees));
            return shiftEmployee;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll(){}

    public void replaceSetOfEmployees(DShift dShift, Set<String> oldSet,Set<String> newSet) {
        //code to replace the sets
    }
}
