package Domain.DAL.Objects;

import Domain.Business.BusinessEmployeeFactory;
import Domain.Business.Objects.Employee;

import java.util.Date;

public class DStorekeeper extends DEmployee{
    public DStorekeeper(String id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super("PlaceHolder", id, name, bankDetails, salary, employmentConditions, startingDate);
    }

    @Override
    public void save() {

    }

    @Override
    public Employee accept(BusinessEmployeeFactory businessEmployeeFactory) {
        return businessEmployeeFactory.createBusinessEmployee(this);
    }
}
