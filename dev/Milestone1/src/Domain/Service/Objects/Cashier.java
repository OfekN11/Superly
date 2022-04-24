package Domain.Service.Objects;

import Presentation.Screens.ScreenEmployeeFactory;

/**
 * Service model of the cashier
 */
public class Cashier extends Employee{
    public Cashier(Domain.Business.Objects.Cashier bCashier){
        super(bCashier);
    }

    @Override
    public Presentation.Screens.Employee accept(ScreenEmployeeFactory screenEmployeeFactory) {
        return screenEmployeeFactory.createScreenEmployee(this);
    }
}
