package Domain.Business.Controllers;
import Domain.Business.Objects.DayShift;
import Domain.Business.Objects.NightShift;
import Domain.Business.Objects.Shift;
import Domain.DAL.Controllers.DShiftController;
import Domain.DAL.Objects.DShift;
import java.util.HashSet;
import java.util.Set;

public class ShiftController {
    // properties
    Set<Shift> shifts;
    DShiftController dShiftController;
    EmployeeController employeeController;

    // constructor
    public ShiftController(EmployeeController employeeController) {
        this.employeeController = employeeController;
        dShiftController = new DShiftController();
        shifts = new HashSet<>();
        for (DShift dShift: dShiftController.createFakeDTOs()) {
            if (dShift.type.equals("Day"))
                shifts.add(new DayShift(dShift,employeeController.getEmployees(dShift.workersId), dShift.shiftManagerId));
            else
                shifts.add(new NightShift(dShift,employeeController.getEmployees(dShift.workersId),dShift.shiftManagerId));
        }
    }
}
