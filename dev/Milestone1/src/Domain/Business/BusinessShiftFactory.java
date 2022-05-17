package Domain.Business;
import Domain.Business.Objects.Shift.Shift;
import Domain.DAL.ObjectsObje.DEveningShift;
import Domain.DAL.Objects.DMorningShift;
import Domain.DAL.Objects.DShift;

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
