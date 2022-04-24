package Globals.util;

import Domain.Service.Objects.Constraint;
import Domain.Service.Objects.Shift;

import java.util.Comparator;

public class ConstraintComparator implements Comparator<Constraint> {
    private final DateComparator dateComparator = new DateComparator();
    private final ShiftTypeComparator shiftTypeComparator = new ShiftTypeComparator();

    @Override
    public int compare(Constraint o1, Constraint o2) {
        int byDate = dateComparator.compare(o1.date, o2.date);
        if (byDate == 0)
            return shiftTypeComparator.compare(o1.shift, o2.shift);
        return byDate;
    }
}
