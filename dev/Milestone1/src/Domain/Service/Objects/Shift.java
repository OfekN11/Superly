package Domain.Service.Objects;

import Domain.Business.Objects.Enums.ShiftType;

import java.util.Date;

/**
 * Service model for Shift
 */
public class Shift {
    public final Date date;
    public final ShiftType type;

    private Shift(Date date, ShiftType type){
        this.date = date;
        this.type = type;
    }

    public Shift(Domain.Business.Objects.Shift bShift){
        this(bShift.getDate(), bShift.getType());
    }
}
