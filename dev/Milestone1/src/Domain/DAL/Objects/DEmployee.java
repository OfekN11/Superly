package Domain.DAL.Objects;
import Domain.DAL.Abstract.DTO;
import java.util.Date;


public class DEmployee extends DTO {
    // properties
    private String name;
    private String bankDetails;
    private int salary;
    private String employmentConditions;
    private Date startingDate;
    private String job;

    // constructor
    public DEmployee(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, String job) {
        super(id, "placeHolder");
        this.name = name;
        this.bankDetails = bankDetails;
        this.salary = salary;
        this.employmentConditions = employmentConditions;
        this.startingDate = startingDate;
        this.job = job;
    }

    @Override
    public void save() {
        // saves this object in the DB  code
        setPersist(true);
    }

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

    public String getJob() {
        return job;
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

    public void setJob(String job) {
        if (isPersist()){
            update("Job",job);
        }
        this.job = job;
    }
}
