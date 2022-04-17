package Domain.Business.Objects;

import Domain.Business.Objects.Enums.EmployeeJob;
import Domain.DAL.Objects.DEmployee;

import java.util.Date;

public class Cashier extends Employee {

    public Cashier(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super(id, name, bankDetails, salary, employmentConditions, startingDate);
    }

    public Cashier(DEmployee dEmployee) {
        super(dEmployee);
    }

    @Override
    public EmployeeJob getJobTitle() {
        return EmployeeJob.Cashier;
    }
}
