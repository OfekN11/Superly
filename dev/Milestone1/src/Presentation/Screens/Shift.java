package Presentation.Screens;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

public abstract class Shift extends Screen{

    private static final String[] menuOptions = {
            "Print shift details",          //1
            "Update employee count(s)",     //2
            "Assign employees",             //3
            "Update salary per shift",      //4
            "Update certifications",        //5
            "Calculate Salary",             //6
            "Manage work constraints",      //7
            "Print upcoming shifts"         //8
    };

    protected final Date date;
    protected int shiftManagerId;

    protected int carrierCount;
    protected int cashierCount;
    protected int storekeeperCount;
    protected int sorterCount;
    protected int hr_managersCount;
    protected int logistics_managersCount;

    protected Set<Integer> carrierIDs;
    protected Set<Integer> cashierIDs;
    protected Set<Integer> storekeeperIDs;
    protected Set<Integer> sorterIDs;
    protected Set<Integer> hr_managerIDs;
    protected Set<Integer> logistics_managerIDs;

    public Shift(Screen caller, Domain.Service.Objects.Shift sShift, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
        date = sShift.date;
        shiftManagerId =

    }
}
