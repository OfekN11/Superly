package Domain.Business.Objects;

import Domain.Business.Objects.Enums.ShiftType;
import Domain.DAL.Objects.DShift;

import java.util.Date;
import java.util.Set;

public  class NightShift extends Shift {
    public NightShift(DShift dShift, Set<Employee> employees) {
        super(dShift,employees);
    }

    @Override
    public ShiftType getType() {
        return ShiftType.Night;
    }

    public NightShift(Date date,  Cashier shiftManager){
        super(date,shiftManager);
    }
}
