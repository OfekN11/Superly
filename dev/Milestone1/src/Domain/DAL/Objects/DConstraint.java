package Domain.DAL.Objects;

import Domain.DAL.Abstract.DTO;
import Domain.DAL.Controllers.DConstraintController;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DConstraint extends DTO {
    private static final String tableName = "placeHolder";
    // properties
    private Date date;
    private ShiftTypes shiftType; // morning evening
    private Set<String> employees;
    private DConstraintController dConstraintController;

    // constructor

    public DConstraint(Date date, ShiftTypes shiftType) {
        super(date.toString() + shiftType, tableName);
        this.date = date;
        this.shiftType = shiftType;
        this.employees = new HashSet<>();
        dConstraintController = new DConstraintController();
    }
    public DConstraint(Date date, ShiftTypes shiftType, Set<String> employees) {
        super(date.toString() + shiftType, tableName);
        this.date = date;
        this.shiftType = shiftType;
        this.employees = employees;
        dConstraintController = new DConstraintController();
    }


    // functions

    public void register(String employeeId){
        if (isPersist())
            dConstraintController.addEmployeeToConstraint(this,employeeId);
        employees.add(employeeId);
    }

    public void unregister(String employeeId){
        if (isPersist())
            dConstraintController.removeEmployeeToConstraint(this,employeeId);
        employees.remove(employeeId);
    }

    @Override
    public void save() {
        setPersist(true);
    }

    public Date getDate() {
        return date;
    }

    public ShiftTypes getShiftType() {
        return shiftType;
    }

    public Set<String> getEmployees() {
        return employees;
    }
}
