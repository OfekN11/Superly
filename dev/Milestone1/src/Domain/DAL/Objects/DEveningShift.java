package Domain.DAL.Objects;
import Domain.DAL.Controllers.DEmployeeShiftController;

import java.util.Date;
import java.util.Set;

public class DEveningShift extends DShift{


    public DEveningShift(Date workday, int shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount, Set<Integer> employeesId) {
        super( workday, shiftManagerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managersCount, logistics_managersCount, employeesId);
    }

    public DEveningShift(Date workday, int shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount) {
        super(workday, shiftManagerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managersCount, logistics_managersCount);
    }

    @Override
    public void save() {

    }
}
