package Domain.Business.Objects;

import Domain.DAL.Objects.DMorningShift;
import Domain.DAL.Objects.DShift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MorningShift extends Shift {

    public MorningShift(Date workday, String shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount, Set<String> carrierIDs, Set<String> cashierIDs, Set<String> storekeeperIDs, Set<String> sorterIDs, Set<String> hr_managerIDs, Set<String> logistics_managerIDs) throws Exception {
        super(workday, shiftManagerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managersCount, logistics_managersCount, carrierIDs, cashierIDs, storekeeperIDs, sorterIDs, hr_managerIDs, logistics_managerIDs,new DMorningShift(workday,shiftManagerId,carrierCount,cashierCount,storekeeperCount,sorterCount,hr_managersCount,logistics_managersCount));
    }

    public MorningShift(Date workday) throws Exception {
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
