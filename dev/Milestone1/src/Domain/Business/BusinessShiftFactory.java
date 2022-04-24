package Domain.Business;
import Domain.Business.Objects.*;
import Domain.DAL.Objects.DEveningShift;
import Domain.DAL.Objects.DMorningShift;
import Domain.DAL.Objects.DShift;

public class BusinessShiftFactory {
    public Shift createServiceShift(DShift dShift){
        return dShift.accept(this);
    }

    public MorningShift createServiceShift(DMorningShift dShift){
        return new MorningShift(dShift);
    }

    public EveningShift createServiceShift(DEveningShift dShift){
        return new EveningShift(dShift);
    }
}
