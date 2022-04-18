package Domain.Service;

import java.util.Date;

/**
 * Abstract class representing employee for service purposes
 */
public abstract class Employee {
    public final int id;
    public final String name;
    public final String bankDetails;
    public final int salary;
    public final String employmentConditions;
    public final Date startingDate;

    /**
     * Service Employee basic constructor, private
     *
     * @param id ID of the employee
     * @param name Name of the employee
     * @param bankDetails Bank details of the employee
     * @param salary Salary of the employee
     * @param employmentConditions Employment conditions of the employee
     * @param startingDate Employee's work starting date
     */
    private Employee(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate){
        this.id = id;
        this.name = name;
        this.bankDetails = bankDetails;
        this.salary = salary;
        this.employmentConditions = employmentConditions;
        this.startingDate = startingDate;
    }

    /**
     * Service Employee constructor based on his business type counterpart
     *
     * @param bEmployee Business type representing the employee
     */
    public Employee(Domain.Business.Objects.Employee bEmployee){
        this(bEmployee.getId(), bEmployee.getName(), bEmployee.getBankDetails(), bEmployee.getSalary(), bEmployee.getEmploymentConditions(), bEmployee.getStartingDate());
    }
}
