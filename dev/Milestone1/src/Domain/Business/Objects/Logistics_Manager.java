package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import java.util.Date;
import java.util.Set;

/**
 * Business model of the Logistics Manager
 */
public class Logistics_Manager extends Employee{
    public Logistics_Manager(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, certifications);
    }

    public Logistics_Manager(DEmployee dEmployee) {
        super(dEmployee);
    }

    @Override
    public JobTitles getJobTitle() {
        return JobTitles.Logistics_Manager;
    }
}
