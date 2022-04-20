package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import java.util.Date;
import java.util.Set;

/**
 * Business model of the Cashier
 */
public class Cashier extends Employee {

    public Cashier(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, certifications);
    }

    public Cashier(DEmployee dEmployee) {
        super(dEmployee);
    }

    @Override
    public JobTitles getJobTitle() {
        return JobTitles.Cashier;
    }

    @Override
    public Domain.Service.Objects.Employee accept(ServiceEmployeeFactory factory) {
        return factory.createServiceEmployee(this);
    }
}
