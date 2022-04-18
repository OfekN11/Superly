package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.Set;

public  class NightShift extends Shift {
    public NightShift(DShift dShift, Set<Employee> employees) {
        super(dShift,employees);
    }

    @Override
    public ShiftTypes getType() {
        return ShiftTypes.Evening;
    }

    public NightShift(Date date,  Cashier shiftManager){
        super(date,shiftManager);
    }
}
