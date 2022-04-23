package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.Set;

public  class EveningShift extends Shift {

    public EveningShift(Date workday, int shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount, Set<Integer> carrierIDs, Set<Integer> cashierIDs, Set<Integer> storekeeperIDs, Set<Integer> sorterIDs, Set<Integer> hr_managerIDs, Set<Integer> logistics_managerIDs) throws Exception {
        super(workday, shiftManagerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managersCount, logistics_managersCount, carrierIDs, cashierIDs, storekeeperIDs, sorterIDs, hr_managerIDs, logistics_managerIDs);
        validateManagerialCount(hr_managersCount);
        validateManagerialCount(logistics_managersCount);
    }

    public EveningShift(Date workday) throws Exception {
        super(workday);
    }

    public EveningShift(DShift dShift) {
        super(dShift);
    }

    public ShiftTypes getType() {
        return ShiftTypes.Evening;
    }

    @Override
    public Domain.Service.Objects.EveningShift accept(ServiceShiftFactory factory) {
        return factory.createServiceShift(this);
    }

    @Override
    public void setHr_managersCount(int hr_managersCount) throws Exception {
        validateManagerialCount(hr_managersCount);
        super.setHr_managersCount(hr_managersCount);
    }

    @Override
    public void setLogistics_managersCount(int logistics_managersCount) throws Exception {
        validateManagerialCount(logistics_managersCount);
        super.setLogistics_managersCount(logistics_managersCount);
    }

    private void validateManagerialCount(int count) throws Exception {
        if (count > 0)
            throw new Exception("Evening shifts don't have managerial slots");
    }
}
