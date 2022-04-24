package Presentation.Screens;

public class ScreenEmployeeFactory {
    private Screen caller;
    public Employee createScreenEmployee(Screen caller, Domain.Service.Objects.Employee sEmployee){
        this.caller = caller;
        return sEmployee.accept(this);
    }

    public Carrier createScreenEmployee(Domain.Service.Objects.Carrier sCarrier){
        return new Carrier(caller, sCarrier);
    }

    public Cashier createScreenEmployee(Domain.Service.Objects.Cashier sCashier){
        return new Cashier(caller, sCashier);
    }

    public HR_Manager createScreenEmployee(Domain.Service.Objects.HR_Manager sHR_Manager){
        return new HR_Manager(caller, sHR_Manager);
    }

    public Logistics_Manager createScreenEmployee(Domain.Service.Objects.Logistics_Manager sLogistics_Manager){
        return new Logistics_Manager(caller, sLogistics_Manager);
    }

    public Sorter createScreenEmployee(Domain.Service.Objects.Sorter sSorter){
        return new Sorter(caller, sSorter);
    }

    public Storekeeper createScreenEmployee(Domain.Service.Objects.Storekeeper sStorekeeper){
        return new Storekeeper(caller, sStorekeeper);
    }
}
