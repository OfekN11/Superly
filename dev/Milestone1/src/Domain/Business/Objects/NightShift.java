package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;

import java.util.Set;

public  class NightShift extends Shift {
    public NightShift(DShift dShift, Set<Employee> employees, int shiftManager) {
        super(dShift,employees,shiftManager);
    }
}
