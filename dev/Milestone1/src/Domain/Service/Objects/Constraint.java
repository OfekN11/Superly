package Domain.Service.Objects;

import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Constraint {
    public final LocalDate date;
    public final ShiftTypes shift;

    private Constraint(LocalDate date, ShiftTypes shift){
        this.date = date;
        this.shift = shift;
    }

    public Constraint(Domain.Business.Objects.Constraint constraint){
        this(constraint.getDate(), constraint.getType());
    }

    @Override
    public String toString() {
        return "Date: " + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +", Shift: " + shift;
    }
}
