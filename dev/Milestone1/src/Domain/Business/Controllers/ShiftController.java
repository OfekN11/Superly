package Domain.Business.Controllers;
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
    Map<Long,NightShift> nightShifts;
    Map<Long,DayShift> dayShifts;
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
                dayShifts.put(dShift.date.getTime() ,new DayShift(dShift,employeeController.getEmployees(dShift.workersId)));
            else
                nightShifts.put(dShift.date.getTime(),new NightShift(dShift,employeeController.getEmployees(dShift.workersId)));
        }
    }

    public NightShift CreateNewNightShift(Date date, Employee shiftManager){

        NightShift newShift =new NightShift(date,shiftManager);
        nightShifts.put(date.getTime(),newShift);
        return newShift;
    }

    public DayShift CreateNewDayShift(Date date, Employee shiftManager){
        DayShift newShift =new DayShift(date,shiftManager);
        dayShifts.put(date.getTime(), newShift);
        return newShift;
    }

    public void AddEmployeeToShift(Date date, ShiftType shiftType,Employee employee){
        if(shiftType == ShiftType.Day){
            DayShift dayShift = dayShifts.get(date.getTime());
            if(dayShift != null) {
                dayShift.AddEmployee(employee);
                return;
            }
        }else {
            NightShift nightShift = nightShifts.get(date.getTime());
            if(nightShift != null) {
                nightShift.AddEmployee(employee);
                return;
            }
        }

        throw new RuntimeException(shiftNotFoundErrorMsg);
    }
}
