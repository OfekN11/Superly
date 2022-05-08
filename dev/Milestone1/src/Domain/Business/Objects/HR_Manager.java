package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;
import Domain.DAL.Objects.DHR_Manager;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import java.time.LocalDate;
import java.util.Set;

/**
 * Business model of the HR Manager
 */
public class HR_Manager extends Employee{
    public HR_Manager(String id, String name, String bankDetails, int salary, String employmentConditions, LocalDate startingDate, Set<Certifications> certifications) throws Exception {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, certifications,new DHR_Manager(id,name,bankDetails,salary,employmentConditions,startingDate));
    }

    public HR_Manager(DEmployee dEmployee) {
        super(dEmployee);
    }

    @Override
    protected void updateEmploymentConditions() {
       super.updateEmploymentConditions(JobTitles.HR_Manager);
    }

    @Override
    public Domain.Service.Objects.Employee accept(ServiceEmployeeFactory factory) {
        return factory.createServiceEmployee(this);
    }
}
