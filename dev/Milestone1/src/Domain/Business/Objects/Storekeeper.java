package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;
import Domain.DAL.Objects.DStorekeeper;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import java.util.Date;
import java.util.Set;

/**
 * Business model of the Storekeeper
 */
public class Storekeeper extends Employee {

    public Storekeeper(String id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) throws Exception {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, certifications,new DStorekeeper(id,name,bankDetails,salary,employmentConditions,startingDate));
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
