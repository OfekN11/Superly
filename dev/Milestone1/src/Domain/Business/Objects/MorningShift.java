package Domain.Business.Objects;

import Domain.DAL.Objects.DEveningShift;
import Domain.DAL.Objects.DMorningShift;
import Domain.DAL.Objects.DShift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.util.Set;

public class MorningShift extends Shift {

    public MorningShift(LocalDate workday, String managerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount,int transportManagersCount) throws Exception {
        super(workday, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount,logistics_managerCount,transportManagersCount);
    }

    public MorningShift(LocalDate workday, String managerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount, int transportManagersCount
            , Set<String> carrierIDs, Set<String> cashierIDs, Set<String> storekeeperIDs, Set<String> sorterIDs, Set<String> hr_managerIDs, Set<String> logistics_managerIDs, Set<String> transportManagersIDs
    ) throws Exception {
        super(workday, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount,logistics_managerCount,transportManagersCount,carrierIDs,
                cashierIDs,
                storekeeperIDs,
                sorterIDs,
                hr_managerIDs,
                logistics_managerIDs,
                transportManagersIDs );
    }

    public ShiftTypes getType() {
        return ShiftTypes.Morning;
    }

    @Override
    public Domain.Service.Objects.Shift accept(ServiceShiftFactory factory) {
        return factory.createServiceShift(this);
    }

    @Override
    public DShift createDShift() {
        return new DMorningShift(getWorkday(),getShiftManagerId(),getCarrierCount(),getCashierCount(),getStorekeeperCount(),getSorterCount(),getHr_managersCount(),getLogistics_managersCount());
    }

    @Override
    public String printDayAndType() {
        return "Morning- " + getWorkday().getDate();
    }
}
