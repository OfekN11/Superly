package Domain.Service.Objects;

import Domain.Business.Objects.Shift.MorningShift;
import Globals.Enums.ShiftTypes;
import Presentation.Screens.ScreenShiftFactory;

import java.text.SimpleDateFormat;

public class MorningShift extends Shift{
    public MorningShift(Domain.Business.Objects.Shift.MorningShift bShift) {
        super(bShift);
    }

    @Override
    public String toString() {
        return "Morning shift: " + new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    @Override
    public Presentation.Screens.Shift accept(ScreenShiftFactory screenShiftFactory) {
        return screenShiftFactory.createScreenShift(this);
    }

    @Override
    public ShiftTypes getType() {
        return ShiftTypes.Morning;
    }
}
