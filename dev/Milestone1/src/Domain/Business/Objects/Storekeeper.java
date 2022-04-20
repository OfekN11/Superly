package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import java.util.Date;
import java.util.Set;

/**
 * Business model of the Storekeeper
 */
public class Storekeeper extends Employee {

    public Storekeeper(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, certifications);
    }

    public Storekeeper(DEmployee dEmployee) {
        super(dEmployee);
    }

    @Override
    public JobTitles getJobTitle() {
        return JobTitles.Storekeeper;
    }

    @Override
    public Domain.Service.Objects.Employee accept(ServiceEmployeeFactory factory) {
        return factory.createServiceEmployee(this);
    }
}
