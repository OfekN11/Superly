package Domain.Service.Objects;

import Globals.Enums.ShiftTypes;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class Constraint {
    public final Date date;
    public final ShiftTypes shift;
    public final Set<String> employeeIDs;

    private Constraint(Date date, ShiftTypes shift, Set<String> employeeIDs){
        this.date = date;
        this.shift = shift;
        this.employeeIDs = Collections.unmodifiableSet(employeeIDs);
    }

    public Constraint(Domain.Business.Objects.Constraint constraint){
        this(constraint.getDate(), constraint.getType(), constraint.getEmployees());
    }

    @Override
    public String toString() {
        return "Date: " + new SimpleDateFormat("dd-MM-yyyy").format(date) +", Shift: " + shift;
    }
}
