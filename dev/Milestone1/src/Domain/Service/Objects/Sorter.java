package Domain.Service.Objects;

import Presentation.Screens.ScreenEmployeeFactory;

/**
 * Service model of the sorter
 */
public class Sorter extends Employee{
    public Sorter(Domain.Business.Objects.Sorter bSorter){
        super(bSorter);
    }

    @Override
    public Presentation.Screens.Employee accept(ScreenEmployeeFactory screenEmployeeFactory) {
        return screenEmployeeFactory.createScreenEmployee(this);
    }
}
