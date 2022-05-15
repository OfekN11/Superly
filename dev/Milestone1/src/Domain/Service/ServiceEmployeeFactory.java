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

    public Carrier createServiceEmployee(Domain.Business.Objects.Carrier bEmployee){
        return new Carrier(bEmployee);
    }

    public Cashier createServiceEmployee(Domain.Business.Objects.Cashier bEmployee){
        return new Cashier(bEmployee);
    }

    public HR_Manager createServiceEmployee(Domain.Business.Objects.HR_Manager bEmployee){
        return new HR_Manager(bEmployee);
    }

    public Logistics_Manager createServiceEmployee(Domain.Business.Objects.Logistics_Manager bEmployee){
        return new Logistics_Manager(bEmployee);
    }

    public Sorter createServiceEmployee(Domain.Business.Objects.Sorter bEmployee){
        return new Sorter(bEmployee);
    }

    public Storekeeper createServiceEmployee(Domain.Business.Objects.Storekeeper bEmployee){
        return new Storekeeper(bEmployee);
    }

    public Transport_Manager createServiceEmployee(Domain.Business.Objects.Transport_Manager bEmployee){
        return new Transport_Manager(bEmployee);
    }
}
