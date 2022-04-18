package Domain.Business.Objects;
import Domain.Business.Objects.Enums.ShiftType;
import Domain.DAL.Objects.DConstraint;

import java.util.Date;
public class Constraint {

    // properties
    private Date date;
    private ShiftType type; // morning evening
    private DConstraint dConstraint;

    public Constraint(Date date, ShiftType type,int employeeId) {
        this.date = date;
        this.type = type;
        dConstraint = new DConstraint(date,getType().toString(),employeeId);
        dConstraint.save();
    }

    public Constraint(DConstraint dConstraint){
        this.date = dConstraint.getDate();
        this.type =switch (dConstraint.getShiftType()){
            case "Day" -> ShiftType.Day;
            default ->  ShiftType.Night;
        };
        this.dConstraint = dConstraint;
    }

    public Date getDate() {
        return date;
    }

    public ShiftType getType() {
        return type;
    }

    public void remove() {
        dConstraint.delete();
    }
}
