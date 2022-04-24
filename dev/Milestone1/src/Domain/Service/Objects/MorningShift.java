package Domain.Service.Objects;

import Globals.Enums.ShiftTypes;

public class MorningShift extends Shift{
    public MorningShift(Domain.Business.Objects.MorningShift bShift) {
        super(bShift);
    }

    @Override
    public ShiftTypes getType() {
        return ShiftTypes.Morning;
    }
}
