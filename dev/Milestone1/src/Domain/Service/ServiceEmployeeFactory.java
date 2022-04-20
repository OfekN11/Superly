package Domain.Service;

import Domain.Service.Objects.*;

/**
 * Factory to produce a Service type Employee
 *
 * This Factory has 1 overloaded method createServiceEmployee to generate a service
 * type employee from business type employee or each of it's extenders
 */
public class ServiceEmployeeFactory {
    public Employee createServiceEmployee(Domain.Business.Objects.Employee bEmployee){
        return bEmployee.accept(this);
    }

    public Carrier createServiceEmployee(Domain.Business.Objects.Carrier bCarrier){
        return new Carrier(bCarrier);
    }

    public Cashier createServiceEmployee(Domain.Business.Objects.Cashier bCashier){
        return new Cashier(bCashier);
    }

    public HR_Manager createServiceEmployee(Domain.Business.Objects.HR_Manager bHR_Manager){
        return new HR_Manager(bHR_Manager);
    }

    public Logistics_Manager createServiceEmployee(Domain.Business.Objects.Logistics_Manager bLogistics_Manager){
        return new Logistics_Manager(bLogistics_Manager);
    }

    public Sorter createServiceEmployee(Domain.Business.Objects.Sorter bSorter){
        return new Sorter(bSorter);
    }

    public Storekeeper createServiceEmployee(Domain.Business.Objects.Storekeeper bStorekeeper){
        return new Storekeeper(bStorekeeper);
    }
}
