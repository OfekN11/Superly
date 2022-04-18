package Domain.DAL.Objects;

import Domain.DAL.Abstract.DTO;

import java.util.Date;

public class DConstraint extends DTO {
    // fields
    private Date date;
    private String shiftType;
    private int employeeId;

    // constructor
    public DConstraint(Date date, String shiftType, int employeeId) {
        super(0, "placeHolder"); // no id
        setPersist(false);
        this.date =date;
        this.shiftType =shiftType;
        this.employeeId = employeeId;
    }

    // functions

    @Override
    public void save() {

    }

    public Date getDate() {
        return date;
    }

    public String getShiftType() {
        return shiftType;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setDate(Date date) {
        if (isPersist())
            update("Date",date);
        this.date = date;
    }

    public void setShiftType(String shiftType) {
        if (isPersist())
            update("ShiftType",shiftType);
        this.shiftType = shiftType;
    }

    public void setEmployeeId(int employeeId) {
        if (isPersist())
            update("EmployeeId",employeeId);
        this.employeeId = employeeId;
    }
}
