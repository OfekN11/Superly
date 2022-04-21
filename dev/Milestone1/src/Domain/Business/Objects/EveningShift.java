package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.Set;

public  class EveningShift extends Shift {
    public EveningShift(DShift dShift, Set<Employee> employees) {
        super(dShift,employees);
    }

    @Override
    public ShiftTypes getType() {
        return ShiftTypes.Evening;
    }

    @Override
    public Domain.Service.Objects.EveningShift accept(ServiceShiftFactory factory) {
        return factory.createServiceShift(this);
    }

    public EveningShift(Date date, Cashier shiftManager){
        super(date,shiftManager);
    }
}
