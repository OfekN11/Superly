package Presentation;

import Domain.Service.Objects.Constraint;
import Domain.Service.Objects.Employee;
import Domain.Service.Objects.Shift;
import Domain.Service.Services.*;
import Globals.Enums.Certifications;
import Globals.Enums.ShiftTypes;
import Presentation.Screens.Carrier;

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

    public Employee getEmployee(String id) {
        return null;
    }

    public void checkUnusedEmployeeID() {
    }

    public void addEmployee(Integer id, String name, String bankDetails, Integer salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) {
    }

    public void editEmployeeName(Presentation.Screens.Employee employee, String newName) {
    }

    public void editEmployeeBankDetails(Presentation.Screens.Employee employee, String newNankDetails) {
    }

    public void editEmployeeSalary(Presentation.Screens.Employee employee, int newSalary) {
    }

    public void editEmployeeCertifications(Presentation.Screens.Employee employee, Set<Certifications> newCertifications) {
    }

    public void editEmployeeEmploymentConditions(Presentation.Screens.Employee employee, String newEmploymentConditions) {
    }

    public void editCarrierLicenses(Carrier carrier, Set<String> curr) {
    }

    public Set<Shift> getEmployeeShiftsBetween(int id, Date start, Date end) {
        return null;
    }

    public Set<Constraint> getEmployeeConstraintsBetween(int id, Date today, Date nextMonth) {
    }

    public void registerToConstraint(int id, Date date, ShiftTypes shift) {
    }

    public void unregisterToConstraint(int id, Date date, ShiftTypes shift) {
    }
}
