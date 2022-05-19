package Domain.Business.Objects.Employee;

import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import java.time.LocalDate;
import java.util.Set;

/**
 * Business model of the Storekeeper
 */
public class Storekeeper extends Employee {

    public Storekeeper(String id, String name, String bankDetails, int salary, String employmentConditions, LocalDate startingDate, Set<Certifications> certifications) throws Exception {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, certifications);
    }

    @Override
    protected void updateEmploymentConditions() {
       super.updateEmploymentConditions(JobTitles.Storekeeper);
    }

    @Override
    public Domain.Service.Objects.Employee accept(ServiceEmployeeFactory factory) {
        return factory.createServiceEmployee(this);
    }

    @Override
    public void save(EmployeeDataMapper employeeDataMapper) throws Exception {
        employeeDataMapper.save(this);
    }


    @Override
    public void update(EmployeeDataMapper employeeDataMapper) throws Exception {
        employeeDataMapper.update(this);
    }
}
