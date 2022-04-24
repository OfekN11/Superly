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

    public void removeEmployee(String id) {
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

    public Set<Shift> getEmployeeShiftsBetween(String id, Date start, Date end) {
        return null;
    }

    public Set<Constraint> getEmployeeConstraintsBetween(String id, Date today, Date nextMonth) {
    }

    public void registerToConstraint(String id, Date date, ShiftTypes shift) {
    }

    public void unregisterToConstraint(String id, Date date, ShiftTypes shift) {
    }

    public Set<Shift> getShiftsBetween(Date start, Date end) {
    }

    public void createShift(Date date, ShiftTypes type, String managerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount) {
    }

    public Constraint getConstraint(Date date, ShiftTypes type) {
    }

    public Set<Employee> getEmployees(Set<String> employeeIDs) {
    }

    public String getEmployeeWorkDetailsForCurrentMonth(String id) {
    }

    public Shift getShift(Date date, ShiftTypes type) {
    }

    public void removeShift(Shift shift) {
    }

    public void editShiftManagerID(Presentation.Screens.Shift shift, String shiftManagerId) {
    }

    public void editShiftCarrierCount(Presentation.Screens.Shift shift, int carrierCount) {
    }

    public void editShiftCashierCount(Presentation.Screens.Shift shift, int cashierCount) {
    }

    public void editShiftStorekeeperCount(Presentation.Screens.Shift shift, int storekeeperCount) {
    }

    public void editShiftSorterCount(Presentation.Screens.Shift shift, int sorterCount) {
    }

    public void editShiftHR_ManagerCount(Presentation.Screens.Shift shift, int hr_managersCount) {
    }

    public void editShiftLogistics_ManagerCount(Presentation.Screens.Shift shift, int logistics_managersCount) {
    }

    public void editShiftCarrierIDs(Presentation.Screens.Shift shift, Set<String> carrierIDs) {
    }

    public void editShiftCashierIDs(Presentation.Screens.Shift shift, Set<String> cashierIDs) {
    }

    public void editShiftStorekeeperIDs(Presentation.Screens.Shift shift, Set<String> storekeeperIDs) {
    }

    public void editShiftSorterIDs(Presentation.Screens.Shift shift, Set<String> sorterIDs) {
    }

    public void editShiftHR_ManagerIDs(Presentation.Screens.Shift shift, Set<String> hr_managerIDs) {
    }

    public void editShiftLogistics_ManagerIDs(Presentation.Screens.Shift shift, Set<String> logistics_managerIDs) {
    }
}
