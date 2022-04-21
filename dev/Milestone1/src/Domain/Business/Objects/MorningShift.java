package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.Set;

public class MorningShift extends Shift {
    public MorningShift(DShift dShift, Set<Employee> employees) {
        super(dShift,employees);
    }

    public MorningShift(Date date, Cashier shiftManager){
        super(date,shiftManager);
    }

    @Override
    public ShiftTypes getType() {
        return ShiftTypes.Morning;
    }

    @Override
    public Domain.Service.Objects.Shift accept(ServiceShiftFactory factory) {
        return factory.createServiceShift(this);
    }
}
