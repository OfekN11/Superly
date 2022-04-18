package Domain.Business.Objects;
import Globals.Enums.ShiftTypes;

import java.util.Date;
public class Constraint {

    // properties
    private Date date;
    private ShiftTypes type; // morning evening

    public Constraint(Date date, ShiftTypes type) {
        this.date = date;
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public ShiftTypes getType() {
        return type;
    }
}
