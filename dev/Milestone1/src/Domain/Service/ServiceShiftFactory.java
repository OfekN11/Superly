package Domain.Service;

import Domain.Service.Objects.*;

public class ServiceShiftFactory {
    public Shift createServiceShift(Domain.Business.Objects.Shift bShift){
        return bShift.accept(this);
    }

    public MorningShift createServiceShift(Domain.Business.Objects.MorningShift bShift){
        return new MorningShift(bShift);
    }

    public EveningShift createServiceShift(Domain.Business.Objects.EveningShift bShift){
        return new EveningShift(bShift);
    }
}
