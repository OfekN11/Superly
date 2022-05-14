package Domain.DAL.Objects;
import Domain.Business.BusinessEmployeeFactory;
import Domain.Business.Objects.Employee;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeCertificationDAO;
import Globals.Enums.Certifications;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public abstract class DEmployee extends DTO {
    // properties
    private String name;
    private String bankDetails;
    private int salary;
    private String employmentConditions;
    private Date startingDate;
    private Set<Certifications> certifications;
    private EmployeeCertificationDAO employeeCertificationDAO;

    // constructor
    public DEmployee(String tableName,String id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super(id,tableName);
        this.name = name;
        this.bankDetails = bankDetails;
        this.salary = salary;
        this.employmentConditions = employmentConditions;
        this.startingDate = startingDate;
        this.certifications = new HashSet<>();
        this.employeeCertificationDAO = new EmployeeCertificationDAO();

    }

    // functions
    public String getName() {
        return name;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public int getSalary() {
        return salary;
    }

    public String getEmploymentConditions() {
        return employmentConditions;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setName(String name) {
        if (isPersist()){
            update("Name",name);
        }
        this.name = name;
    }

    public void setBankDetails(String bankDetails) {
        if (isPersist()){
            update("BankDetails",bankDetails);
        }
        this.bankDetails = bankDetails;
    }

    public void setSalary(int salary) {
        if (isPersist()){
            update("Salary",salary);
        }
        this.salary = salary;
    }

    public void setEmploymentConditions(String employmentConditions) {
        if (isPersist()){
            update("EmploymentConditions",employmentConditions);
        }
        this.employmentConditions = employmentConditions;
    }

    public void setStartingDate(Date startingDate) {
        if (isPersist()){
            update("StartingDate",startingDate);
        }
        this.startingDate = startingDate;
    }

    public void setCertifications(Set<Certifications> certifications) {
        if (isPersist())
            employeeCertificationDAO.updateCertificationOfEmployee(this,certifications);
        this.certifications = certifications;
    }

    public void addCertification(Certifications certification){
        employeeCertificationDAO.addCertification(getId(),certification);
    }

    public Set<Certifications> getCertifications() {
        return certifications;
    }

    public abstract Employee accept(BusinessEmployeeFactory businessEmployeeFactory);
}
