package Domain.Service;

import Domain.Service.Objects.*;

public class ServiceShiftFactory {
    public Shift createServiceShift(Domain.Service.Shift.Shift bShift){
        return bShift.accept(this);
    }

    public MorningShift createServiceShift(Domain.Service.Shift.MorningShift bShift){
        return new MorningShift(bShift);
    }

    public EveningShift createServiceShift(Domain.Service.Shift.EveningShift bShift){
        return new EveningShift(bShift);
    }

}
