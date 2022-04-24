package Domain.Business;
import Domain.Business.Objects.*;
import Domain.DAL.Objects.*;

/**
 * Factory to produce a Business type Employee
 *
 * This Factory has 1 overloaded method createBusinessEmployee to generate a service
 * type employee from business type employee or each of it's extenders
 */
public class BusinessEmployeeFactory {
    public Employee createBusinessEmployee(DEmployee dEmployee){
        return dEmployee.accept(this);
    }

    public Carrier createBusinessEmployee(DCarrier dCarrier){
        return new Carrier(dCarrier);
    }

    public Cashier createBusinessEmployee(DCashier dCashier){
        return new Cashier(dCashier);
    }

    public HR_Manager createBusinessEmployee(DHR_Manager dHR_Manager){
        return new HR_Manager(dHR_Manager);
    }

    public Logistics_Manager createBusinessEmployee(DLogistics_Manager dLogistics_Manager){
        return new Logistics_Manager(dLogistics_Manager);
    }

    public Sorter createBusinessEmployee(DSorter dSorter){
        return new Sorter(dSorter);
    }

    public Storekeeper createBusinessEmployee(DStorekeeper dStorekeeper){
        return new Storekeeper(dStorekeeper);
    }
}
