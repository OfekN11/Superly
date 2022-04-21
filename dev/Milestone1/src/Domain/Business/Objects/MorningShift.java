package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.Set;

public class MorningShift extends Shift {

    public MorningShift(Date workday, int shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount, Set<Integer> carrierIDs, Set<Integer> cashierIDs, Set<Integer> storekeeperIDs, Set<Integer> sorterIDs, Set<Integer> hr_managerIDs, Set<Integer> logistics_managerIDs) {
        super(workday, shiftManagerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managersCount, logistics_managersCount, carrierIDs, cashierIDs, storekeeperIDs, sorterIDs, hr_managerIDs, logistics_managerIDs);
    }

    public MorningShift(Date workday) {
        super(workday);
    }

    public MorningShift(DShift dShift) {
        super(dShift);
    }

    public ShiftTypes getType() {
        return ShiftTypes.Morning;
    }

    @Override
    public Domain.Service.Objects.Shift accept(ServiceShiftFactory factory) {
        return factory.createServiceShift(this);
    }
}
