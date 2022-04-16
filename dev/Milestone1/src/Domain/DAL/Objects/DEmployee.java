package Domain.DAL.Objects;

import Domain.DAL.Abstract.DTO;

import java.util.Date;


public class DEmployee extends DTO {
    // properties
    public int id;
    public String name;
    public String bankDetails;
    public int salary;
    public String employmentConditions;
    public Date startingDate;
    public String job;

    // constructor
    public DEmployee(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, String job) {
        this.id = id;
        this.name = name;
        this.bankDetails = bankDetails;
        this.salary = salary;
        this.employmentConditions = employmentConditions;
        this.startingDate = startingDate;
        this.job = job;
    }
}
