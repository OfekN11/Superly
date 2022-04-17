package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;

import java.util.Set;

public class DayShift extends Shift {
    public DayShift(DShift dShift, Set<Employee> employees,int shiftManagerId) {
        super(dShift,employees,shiftManagerId);
    }
}
