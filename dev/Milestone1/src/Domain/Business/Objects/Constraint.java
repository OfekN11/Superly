package Domain.Business.Objects;
import Domain.Business.Objects.Enums.ShiftType;

import java.util.Date;
public class Constraint {

    // properties
    private Date date;
    private ShiftType type; // morning evening

    public Constraint(Date date, ShiftType type) {
        this.date = date;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public ShiftType getType() {
        return type;
    }
}
