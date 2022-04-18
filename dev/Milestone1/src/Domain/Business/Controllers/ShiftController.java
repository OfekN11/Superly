package Domain.Business.Controllers;
import Domain.Business.Objects.Cashier;
import Domain.Business.Objects.DayShift;
import Domain.Business.Objects.Employee;
import Domain.Business.Objects.Enums.ShiftType;
import Domain.Business.Objects.NightShift;
import Domain.DAL.Controllers.DShiftController;
import Domain.DAL.Objects.DShift;

import java.util.*;

public class ShiftController {
    private static final String shiftNotFoundErrorMsg = "This shift Could not be found";

    // properties
    Map<String,NightShift> nightShifts; // string representing the date
    Map<String,DayShift> dayShifts;// string representing the date
    DShiftController dShiftController;
    EmployeeController employeeController;

    // constructor
    public ShiftController(EmployeeController employeeController) {
        this.employeeController = employeeController;
        dayShifts= new HashMap<>();
        nightShifts = new HashMap<>();
        dShiftController = new DShiftController();
    }

    public void CreateFakeShifts(){
        for (DShift dShift: dShiftController.createFakeDTOs()) {
            if (dShift.type.equals(ShiftType.Day.toString()))
                dayShifts.put(dShift.date.toString() ,new DayShift(dShift,employeeController.getEmployees(dShift.workersId)));
            else
                nightShifts.put(dShift.date.toString(),new NightShift(dShift,employeeController.getEmployees(dShift.workersId)));
        }
    }

    public NightShift CreateNewNightShift(Date date, Cashier shiftManager){

        NightShift newShift =new NightShift(date,shiftManager);
        nightShifts.put(date.toString(),newShift);
        return newShift;
    }

    public DayShift CreateNewDayShift(Date date, Cashier shiftManager){
        DayShift newShift =new DayShift(date,shiftManager);
        dayShifts.put(date.toString(), newShift);
        return newShift;
    }

    public void AddEmployeeToShift(Date date, ShiftType shiftType,Employee employee){
        if(shiftType == ShiftType.Day){
            DayShift dayShift = dayShifts.get(date.toString());
            if(dayShift != null) {
                dayShift.AddEmployee(employee);
                return;
            }
        }else {
            NightShift nightShift = nightShifts.get(date.toString());
            if(nightShift != null) {
                nightShift.AddEmployee(employee);
                return;
            }
        }

        throw new RuntimeException(shiftNotFoundErrorMsg);
    }

    public void removeEmployeeFromShift(Date shiftDate, ShiftType shiftType,int employeeId){
        if(shiftType == ShiftType.Day)
            dayShifts.get(shiftDate.toString()).removeEmployee(employeeController.getEmployee(employeeId)) ;
        else
            nightShifts.get(shiftDate.toString()).removeEmployee(employeeController.getEmployee(employeeId));
    }
}
