package Domain.Service.Objects.Employee;

import Globals.Enums.JobTitles;
import Presentation.Screens.ScreenEmployeeFactory;

/**
 * Service model of the cashier
 */
public class Cashier extends Employee{
    public Cashier(Domain.Business.Objects.Employee.Cashier bCashier){
        super(bCashier);
    }

    @Override
    public Presentation.Screens.Employee accept(ScreenEmployeeFactory screenEmployeeFactory) {
        return screenEmployeeFactory.createScreenEmployee(this);
    }

    @Override
    public JobTitles getType() {
        return JobTitles.Cashier;
    }
}
