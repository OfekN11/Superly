package Domain.Business.Objects;

import Domain.Business.Objects.Enums.EmployeeJob;
import Domain.Business.Objects.Enums.ShiftType;
import Domain.DAL.Objects.DEmployee;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Employee {

    // properties
    private int id;
    private String name;
    private String bankDetails;
    private int salary;
    private String employmentConditions;
    private Date startingDate;
    private Set<Constraint> constraints;
    private DEmployee dEmployee; // represent of this object in the DAL

    public Employee(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        this.id = id;
        this.name = name;
        this.bankDetails = bankDetails;
        this.salary = salary;
        this.employmentConditions = employmentConditions;
        this.startingDate = startingDate;
        this.dEmployee = new DEmployee(id,name,bankDetails,salary,employmentConditions,startingDate,getJobTitle().toString());
        constraints = new HashSet<>();
    }

    public Employee(DEmployee dEmployee) {
        this.id = dEmployee.getId();
        this.name = dEmployee.getName();
        this.bankDetails = dEmployee.getBankDetails();
        this.salary = dEmployee.getSalary();
        this.employmentConditions = dEmployee.getEmploymentConditions();
        this.startingDate = dEmployee.getStartingDate();
        this.dEmployee = dEmployee;
    }

    public DEmployee getDEmployee() {
        return dEmployee;
    }

    public Integer getId() {
        return id;
    }

    public void addConstraint(Date date, ShiftType type){
        constraints.add(new Constraint(date, type,getId()));
    }

    public void removeConstrain(Date date, ShiftType type){
        boolean constrainFound =false;
        for(Constraint constraint: constraints)
            if (constraint.getDate().getTime()==date.getTime() & constraint.getType()==type){
                constraints.remove(constraint);
                constraint.remove();
                constrainFound =true;
            }
        if(!constrainFound)
            throw new RuntimeException("Constrain Did NotFound");
    }

    public boolean isAvailable(Date date,ShiftType shiftType){
        for(Constraint constraint: constraints)
            if (constraint.getDate().getTime()==date.getTime() & constraint.getType() == shiftType)
                return false;

        return true;
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

    public Set<Constraint> getConstraints() {
        return constraints;
    }

    public DEmployee getdEmployee() {
        return dEmployee;
    }

    public abstract EmployeeJob getJobTitle();
}
