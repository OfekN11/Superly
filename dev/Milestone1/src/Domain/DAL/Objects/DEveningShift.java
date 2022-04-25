package Domain.DAL.Objects;
import Domain.Business.BusinessShiftFactory;
import Domain.Business.Objects.Shift;
import Globals.Enums.ShiftTypes;
import java.util.Date;

public class DEveningShift extends DShift{




    public DEveningShift(Date workday, String shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount) {
        super(workday, ShiftTypes.Evening,shiftManagerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managersCount, logistics_managersCount);
    }


    @Override
    public void save() {

    }

    @Override
    public Shift accept(BusinessShiftFactory businessShiftFactory) {
        return businessShiftFactory.createBusinessShift(this);
    }

    @Override
    public ShiftTypes getType() {
        return ShiftTypes.Evening;
    }
}
