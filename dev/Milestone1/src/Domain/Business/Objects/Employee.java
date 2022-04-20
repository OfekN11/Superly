package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class representing Employee for business purposes
 */
public abstract class Employee {
    // properties
    private final int id;
    private String name;
    private String bankDetails;
    private int salary;
    private String employmentConditions;
    private Date startingDate;
    private Set<Certifications> certifications;
    private Set<Constraint> constraints;
    private DEmployee dEmployee; // represent of this object in the DAL

    /**
     * Raw data constructor
     *
     * @param id Employee's ID
     * @param name Employee's Name
     * @param bankDetails Employee's bank details
     * @param salary Employee's salary
     * @param employmentConditions Employee's employment conditions
     * @param startingDate Employee's Starting date
     * @param certifications Employees Certifications
     */
    public Employee(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) {
        this.id = id;
        this.name = name;
        this.bankDetails = bankDetails;
        this.salary = salary;
        this.employmentConditions = employmentConditions;
        this.startingDate = startingDate;
        this.certifications = certifications;
        this.dEmployee = new DEmployee(id,name,bankDetails,salary,employmentConditions,startingDate,getJobTitle().toString());
        constraints = new HashSet<>();
    }

    /**
     * Reconstractor from DAL type employee
     *
     * @param dEmployee DAL type representing the employee
     */
    public Employee(DEmployee dEmployee) {
        this.id = dEmployee.id;
        this.name = dEmployee.name;
        this.bankDetails = dEmployee.bankDetails;
        this.salary = dEmployee.salary;
        this.employmentConditions = dEmployee.employmentConditions;
        this.startingDate = dEmployee.startingDate;
        this.dEmployee = dEmployee;
    }

    public DEmployee getDEmployee() {
        return dEmployee;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }

    public int getSalary() {
        return salary;
    }

    public String getEmploymentConditions() {
        return employmentConditions;
    }

    public void setEmploymentConditions(String employmentConditions) {
        this.employmentConditions = employmentConditions;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public Set<Certifications> getCertifications() {
        return certifications;
    }

    public void setCertifications(Set<Certifications> certifications) {
        this.certifications = new HashSet<>(certifications);
    }

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public void addConstraint(Date date, ShiftTypes type){
        constraints.add(new Constraint(date, type));
    }

    public boolean isAvailable(Date date,ShiftTypes shiftType){
        for(Constraint constraint: constraints)
            if (constraint.getDate().getTime()==date.getTime() && constraint.getType() == shiftType)
                return false;
        return true;
    }

    public DEmployee getdEmployee() {
        return dEmployee;
    }

    public abstract JobTitles getJobTitle();

    public abstract Domain.Service.Objects.Employee accept(ServiceEmployeeFactory factory);
}
