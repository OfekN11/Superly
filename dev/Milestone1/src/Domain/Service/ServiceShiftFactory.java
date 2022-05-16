package Domain.Service;

import Domain.Service.Objects.*;

public class ServiceShiftFactory {
    public Shift createServiceShift(Domain.Business.Objects.Shift.Shift bShift){
        return bShift.accept(this);
    }

    public MorningShift createServiceShift(MorningShift bShift){
        return new MorningShift(bShift);
    }

    public EveningShift createServiceShift(EveningShift bShift){
        return new EveningShift(bShift);
    }
}
