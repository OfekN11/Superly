package Domain.Service.Objects;

import Globals.Enums.ShiftTypes;
import Presentation.Screens.ScreenShiftFactory;

import java.time.format.DateTimeFormatter;

public class EveningShift extends Shift{
    public EveningShift(Domain.Business.Objects.EveningShift bShift) {
        super(bShift);
    }

    @Override
    public String toString() {
        return "Evening shift: " + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    public Presentation.Screens.Shift accept(ScreenShiftFactory screenShiftFactory) {
        return screenShiftFactory.createScreenShift(this);
    }
}
