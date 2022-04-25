package Domain.Business;
import Domain.Business.Objects.*;
import Domain.DAL.Objects.DEveningShift;
import Domain.DAL.Objects.DMorningShift;
import Domain.DAL.Objects.DShift;

import java.util.Set;

public class BusinessShiftFactory {
    public Shift createBusinessShift(DShift dShift){
        return dShift.accept(this);
    }

    public MorningShift createBusinessShift(DMorningShift dShift){
        return new MorningShift(dShift);
    }

    public EveningShift createBusinessShift(DEveningShift dShift){
        return new EveningShift(dShift);
    }
}
