package Domain.Service.Objects;

import Globals.Enums.ShiftTypes;

import java.util.Date;

/**
 * Service model for Shift
 */
public class Shift {
    public final Date date;
    public final ShiftTypes type;

    private Shift(Date date, ShiftTypes type){
        this.date = date;
        this.type = type;
    }

    public Shift(Domain.Business.Objects.Shift bShift){
        this(bShift.getDate(), bShift.getType());
    }
}
