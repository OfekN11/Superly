package Presentation.Screens;


public class ScreenShiftFactory {
    private Screen caller;
    public Shift createScreenShift(Screen caller, Domain.Service.Objects.Shift sShift){
        this.caller = caller;
        return sShift.accept(this);
    }

    public EveningShift createScreenShift(Domain.Service.Objects.EveningShift sShift){
        return new EveningShift(caller, sShift);
    }

    public MorningShift createScreenShift(Domain.Service.Objects.MorningShift sShift){
        return new MorningShift(caller, sShift);
    }
}
