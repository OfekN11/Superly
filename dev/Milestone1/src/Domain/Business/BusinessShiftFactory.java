package Domain.Business;
import Domain.Business.Objects.*;
import Domain.DAL.Objects.DEveningShift;
import Domain.DAL.Objects.DMorningShift;
import Domain.DAL.Objects.DShift;

import java.util.Set;

public class BusinessShiftFactory {
    public Shift createServiceShift(DShift dShift, Set<String> carrierIDs, Set<String> cashierIDs, Set<String> storekeeperIDs, Set<String> sorterIDs, Set<String> hr_managerIDs, Set<String> logistics_managerIDs){
        return dShift.accept(this,carrierIDs,cashierIDs,storekeeperIDs,sorterIDs,hr_managerIDs,logistics_managerIDs);
    }

    public MorningShift createServiceShift(DMorningShift dShift, Set<String> carrierIDs, Set<String> cashierIDs, Set<String> storekeeperIDs, Set<String> sorterIDs, Set<String> hr_managerIDs, Set<String> logistics_managerIDs){
        return new MorningShift(dShift,carrierIDs,cashierIDs,storekeeperIDs,sorterIDs,hr_managerIDs,logistics_managerIDs);
    }

    public EveningShift createServiceShift(DEveningShift dShift, Set<String> carrierIDs, Set<String> cashierIDs, Set<String> storekeeperIDs, Set<String> sorterIDs, Set<String> hr_managerIDs, Set<String> logistics_managerIDs){
        return new EveningShift(dShift,carrierIDs,cashierIDs,storekeeperIDs,sorterIDs,hr_managerIDs,logistics_managerIDs);
    }
}
