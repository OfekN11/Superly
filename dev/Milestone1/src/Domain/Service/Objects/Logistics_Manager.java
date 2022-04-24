package Domain.Service.Objects;

import Presentation.Screens.ScreenEmployeeFactory;

/**
 * Service model of the logistics manager
 */
public class Logistics_Manager extends Employee{
    public Logistics_Manager(Domain.Business.Objects.Logistics_Manager bLogistics_Manager){
        super(bLogistics_Manager);
    }

    @Override
    public Presentation.Screens.Employee accept(ScreenEmployeeFactory screenEmployeeFactory) {
        return screenEmployeeFactory.createScreenEmployee(this);
    }
}
