package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;
import Domain.DAL.Objects.DLogistics_Manager;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import java.time.LocalDate;
import java.util.Set;

/**
 * Business model of the Logistics Manager
 */
public class Logistics_Manager extends Employee{
    public Logistics_Manager(String id, String name, String bankDetails, int salary, String employmentConditions, LocalDate startingDate, Set<Certifications> certifications) throws Exception {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, certifications,new DLogistics_Manager(id,name,bankDetails,salary,employmentConditions,startingDate));
    }

    public Logistics_Manager(DEmployee dEmployee) {
        super(dEmployee);
    }

    @Override
    protected void updateEmploymentConditions() {
       super.updateEmploymentConditions(JobTitles.Logistics_Manager);
    }

    @Override
    public Domain.Service.Objects.Employee accept(ServiceEmployeeFactory factory) {
        return factory.createServiceEmployee(this);
    }
}
