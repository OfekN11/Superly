package Domain.Service.Objects;

import Globals.Enums.ShiftTypes;
import Presentation.Screens.Employee;
import Presentation.Screens.ScreenShiftFactory;

import java.text.SimpleDateFormat;

public class EveningShift extends Shift{
    public EveningShift(Domain.Business.Objects.EveningShift bShift) {
        super(bShift);
    }

    @Override
    public String toString() {
        return "Evening shift: " + new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    @Override
    public Presentation.Screens.Shift accept(ScreenShiftFactory screenShiftFactory) {
        return screenShiftFactory.createScreenShift(this);
    }
}
