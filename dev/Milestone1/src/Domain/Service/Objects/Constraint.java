package Domain.Service.Objects;

import Globals.Enums.ShiftTypes;

import java.util.Date;

public class Constraint {
    public final Date date;
    public final ShiftTypes shift;

    private Constraint(Date date, ShiftTypes shift){
        this.date = date;
        this.shift = shift;
    }

    public Constraint(Domain.Business.Objects.Constraint constraint){
        date = constraint.getDate();
        shift = constraint.getType();
    }
}
