package Domain.DAL.Objects;

import Domain.DAL.Controllers.ConstraintDataMapper;
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
    private ConstraintDataMapper constraintDataMapper;

    // constructor

    public DConstraint(Date date, ShiftTypes shiftType) {
        super(date.toString() + shiftType, tableName);
        this.date = date;
        this.shiftType = shiftType;
        this.employees = new HashSet<>();
        constraintDataMapper = new ConstraintDataMapper();
    }
    public DConstraint(Date date, ShiftTypes shiftType, Set<String> employees) {
        super(date.toString() + shiftType, tableName);
        this.date = date;
        this.shiftType = shiftType;
        this.employees = employees;
        constraintDataMapper = new ConstraintDataMapper();
    }


    // functions

    public void register(String employeeId){
        if (isPersist())
            constraintDataMapper.addEmployeeToConstraint(this,employeeId);
        employees.add(employeeId);
    }

    public void unregister(String employeeId){
        if (isPersist())
            constraintDataMapper.removeEmployeeToConstraint(this,employeeId);
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
