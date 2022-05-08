package Domain.Service.Objects;

import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;

public class Constraint {
    public final LocalDate date;
    public final ShiftTypes shift;
    public final Set<String> employeeIDs;

    private Constraint(LocalDate date, ShiftTypes shift, Set<String> employeeIDs){
        this.date = date;
        this.shift = shift;
        this.employeeIDs = Collections.unmodifiableSet(employeeIDs);
    }

    public Constraint(Domain.Business.Objects.Constraint constraint){
        this(constraint.getDate(), constraint.getType(), constraint.getEmployees());
    }

    @Override
    public String toString() {
        return "Date: " + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +", Shift: " + shift;
    }
}
