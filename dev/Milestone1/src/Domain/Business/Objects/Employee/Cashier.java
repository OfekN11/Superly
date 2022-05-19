package Domain.Business.Objects.Employee;

import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

/**
 * Business model of the Cashier
 */
public class Cashier extends Employee {

    public Cashier(String id, String name, String bankDetails, int salary, String employmentConditions, LocalDate startingDate, Set<Certifications> certifications) throws Exception {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, certifications);
    }

    @Override
    public void save(EmployeeDataMapper employeeDataMapper) throws Exception {
        employeeDataMapper.save(this);
    }

    @Override
    protected void updateEmploymentConditions() {
       updateEmploymentConditions(JobTitles.Cashier);
    }

    @Override
    public Domain.Service.Objects.Employee accept(ServiceEmployeeFactory factory) {
        return factory.createServiceEmployee(this);
    }


    @Override
    public void update(EmployeeDataMapper employeeDataMapper) throws Exception {
        employeeDataMapper.update(this);
    }
}
