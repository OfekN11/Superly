package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;

import java.util.Date;

public abstract class Employee {
    // properties
    protected int id;
    protected String name;
    protected String bankDetails;
    protected int salary;
    protected String employmentConditions;
    protected Date startingDate;
    protected DEmployee dEmployee; // represent of this object in the DAL

    public Employee(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate,String job) {
        this.id = id;
        this.name = name;
        this.bankDetails = bankDetails;
        this.salary = salary;
        this.employmentConditions = employmentConditions;
        this.startingDate = startingDate;
        this.dEmployee = new DEmployee(id,name,bankDetails,salary,employmentConditions,startingDate,job);
    }

    public Employee(DEmployee dEmployee) {
        this.id = dEmployee.id;
        this.name = dEmployee.name;
        this.bankDetails = dEmployee.bankDetails;
        this.salary = dEmployee.salary;
        this.employmentConditions = dEmployee.employmentConditions;
        this.startingDate = dEmployee.startingDate;
        this.dEmployee = dEmployee;
    }

    public DEmployee getdEmployee() {
        return dEmployee;
    }
}
