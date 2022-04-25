package Domain.Business.Objects;

import Domain.DAL.Objects.DEveningShift;
import Domain.DAL.Objects.DShift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.Set;

public  class EveningShift extends Shift {

    public EveningShift(Date workday, String shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount, Set<String> carrierIDs, Set<String> cashierIDs, Set<String> storekeeperIDs, Set<String> sorterIDs, Set<String> hr_managerIDs, Set<String> logistics_managerIDs) throws Exception {
        super(workday, shiftManagerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managersCount, logistics_managersCount, carrierIDs, cashierIDs, storekeeperIDs, sorterIDs, hr_managerIDs, logistics_managerIDs);
        validateManagerialCount(hr_managersCount);
        validateManagerialCount(logistics_managersCount);
    }

    public EveningShift(Date workday, String managerID) throws Exception {
        super(workday, managerID);
    }

    public EveningShift(DShift dShift,Set<String> carrierIDs, Set<String> cashierIDs, Set<String> storekeeperIDs, Set<String> sorterIDs, Set<String> hr_managerIDs, Set<String> logistics_managerIDs) {
        super(dShift,carrierIDs,cashierIDs,storekeeperIDs,sorterIDs,hr_managerIDs,logistics_managerIDs);
    }

    public EveningShift(Date date, String managerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount) throws Exception {
        super(date, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount,logistics_managerCount);
    }

    public ShiftTypes getType() {
        return ShiftTypes.Evening;
    }

    @Override
    public Domain.Service.Objects.EveningShift accept(ServiceShiftFactory factory) {
        return factory.createServiceShift(this);
    }

    @Override
    public DShift createDShift() {
        return new DEveningShift(getWorkday(),getShiftManagerId(),getCarrierCount(),getCashierCount(),getStorekeeperCount(),getSorterCount(),getHr_managersCount(),getLogistics_managersCount());
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

    @Override
    public String printDayAndType() {
        return "Evening- " + getWorkday().getDate();
    }
}
