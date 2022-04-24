package Domain.DAL.Objects;

import Domain.Business.BusinessEmployeeFactory;
import Domain.Business.Objects.Employee;

import java.util.Date;

public class DLogistics_Manager extends DEmployee{
    public DLogistics_Manager(String id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super("placeHolder", id, name, bankDetails, salary, employmentConditions, startingDate);
    }

    @Override
    public Employee accept(BusinessEmployeeFactory businessEmployeeFactory) {
        return businessEmployeeFactory.createBusinessEmployee(this);
    }
    @Override
    public void save() {

    }
}
