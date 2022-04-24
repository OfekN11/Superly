package Domain.DAL.Objects;

import Domain.DAL.Controllers.DEmployeeShiftController;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.Set;

public class DMorningShift extends DShift{


    public DMorningShift(Date workday, int shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount, Set<Integer> employeesId) {
        super(workday,ShiftTypes.Morning,shiftManagerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managersCount, logistics_managersCount, employeesId);
    }

    public DMorningShift(Date workday,int shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount) {
        super(workday, ShiftTypes.Morning, shiftManagerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managersCount, logistics_managersCount);
    }

    @Override
    public void save() {

    }
}
