package Domain.Service.Objects;

import Globals.Enums.ShiftTypes;

public class EveningShift extends Shift{
    public EveningShift(Domain.Business.Objects.EveningShift bShift) {
        super(bShift);
    }

    @Override
    public ShiftTypes getType() {
        return ShiftTypes.Evening;
    }
}
