package Domain.Service.Objects;

import Globals.Enums.JobTitles;
import Presentation.Screens.ScreenEmployeeFactory;

/**
 * Service model of the Storekeeper
 */
public class Storekeeper extends Employee{
    public Storekeeper(Domain.Business.Objects.Employee.Storekeeper bStorekeeper){
        super(bStorekeeper);
    }

    @Override
    public Presentation.Screens.Employee accept(ScreenEmployeeFactory screenEmployeeFactory) {
        return screenEmployeeFactory.createScreenEmployee(this);
    }

    @Override
    public JobTitles getType() {
        return JobTitles.Storekeeper;
    }
}
