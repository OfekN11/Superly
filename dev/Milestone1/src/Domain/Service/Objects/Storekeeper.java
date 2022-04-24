package Domain.Service.Objects;

import Presentation.Screens.ScreenEmployeeFactory;

/**
 * Service model of the Storekeeper
 */
public class Storekeeper extends Employee{
    public Storekeeper(Domain.Business.Objects.Storekeeper bStorekeeper){
        super(bStorekeeper);
    }

    @Override
    public Presentation.Screens.Employee accept(ScreenEmployeeFactory screenEmployeeFactory) {
        return screenEmployeeFactory.createScreenEmployee(this);
    }
}
