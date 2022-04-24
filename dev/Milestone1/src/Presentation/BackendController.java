package Presentation;

import Domain.Service.Objects.Employee;
import Domain.Service.Services.*;
import Globals.Enums.Certifications;

import java.util.Date;
import java.util.Set;

public class BackendController {
    private final EmployeeService employeeService;
    private final ShiftService shiftService;
    private final ConstraintService constraintService;

    public BackendController(){
        employeeService = new EmployeeService();
        shiftService = new ShiftService();
        constraintService = new ConstraintService();
    }

    public BackendController(EmployeeService employeeService, ShiftService shiftService, ConstraintService constraintService){
        this.employeeService = employeeService;
        this.shiftService = shiftService;
        this.constraintService = constraintService;
    }


    public Set<Employee> getAllEmployees() {
    }

    public Set<Employee> getAllCashiers() {
    }

    public Set<Employee> getAllCarriers() {
    }

    public Set<Employee> getAllStorekeepers() {
    }

    public Set<Employee> getAllSorters() {
    }

    public Set<Employee> getAllHR_Managers() {
    }

    public Set<Employee> getAllLogistics_Managers() {
    }

    public void removeEmployee(int id) {
    }

    public Employee getEmployee(int id) {
        return null;
    }

    public void checkUnusedEmployeeID() {
    }

    public void addEmployee(Integer id, String name, String bankDetails, Integer salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) {
    }
}
