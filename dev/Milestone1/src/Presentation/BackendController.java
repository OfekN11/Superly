package Presentation;

import Domain.Service.Objects.Constraint;
import Domain.Service.Objects.Employee;
import Domain.Service.Objects.Result;
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


    public Set<Employee> getAllEmployees() throws Exception {
        Result<Set<Employee>> result = employeeService.getAllEmployees();
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAllCashiers() throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAllCarriers() throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAllStorekeepers() throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAllSorters() throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAllHR_Managers() throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAllLogistics_Managers() throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void removeEmployee(String id) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Employee getEmployee(String id) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void checkUnusedEmployeeID() throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void addEmployee(Integer id, String name, String bankDetails, Integer salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editEmployeeName(Presentation.Screens.Employee employee, String newName) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editEmployeeBankDetails(Presentation.Screens.Employee employee, String newNankDetails) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editEmployeeSalary(Presentation.Screens.Employee employee, int newSalary) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editEmployeeCertifications(Presentation.Screens.Employee employee, Set<Certifications> newCertifications) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editEmployeeEmploymentConditions(Presentation.Screens.Employee employee, String newEmploymentConditions) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editCarrierLicenses(Carrier carrier, Set<String> curr) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Shift> getEmployeeShiftsBetween(String id, Date start, Date end) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Constraint> getEmployeeConstraintsBetween(String id, Date today, Date nextMonth) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void registerToConstraint(String id, Date date, ShiftTypes shift) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void unregisterToConstraint(String id, Date date, ShiftTypes shift) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Shift> getShiftsBetween(Date start, Date end) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void createShift(Date date, ShiftTypes type, String managerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Constraint getConstraint(Date date, ShiftTypes type) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getEmployees(Set<String> employeeIDs) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public String getEmployeeWorkDetailsForCurrentMonth(String id) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Shift getShift(Date date, ShiftTypes type) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void removeShift(Shift shift) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftManagerID(Presentation.Screens.Shift shift, String shiftManagerId) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftCarrierCount(Presentation.Screens.Shift shift, int carrierCount) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftCashierCount(Presentation.Screens.Shift shift, int cashierCount) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftStorekeeperCount(Presentation.Screens.Shift shift, int storekeeperCount) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftSorterCount(Presentation.Screens.Shift shift, int sorterCount) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftHR_ManagerCount(Presentation.Screens.Shift shift, int hr_managersCount) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftLogistics_ManagerCount(Presentation.Screens.Shift shift, int logistics_managersCount) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftCarrierIDs(Presentation.Screens.Shift shift, Set<String> carrierIDs) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftCashierIDs(Presentation.Screens.Shift shift, Set<String> cashierIDs) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftStorekeeperIDs(Presentation.Screens.Shift shift, Set<String> storekeeperIDs) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftSorterIDs(Presentation.Screens.Shift shift, Set<String> sorterIDs) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftHR_ManagerIDs(Presentation.Screens.Shift shift, Set<String> hr_managerIDs) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftLogistics_ManagerIDs(Presentation.Screens.Shift shift, Set<String> logistics_managerIDs) throws Exception {
        Result<Set<Employee>> result = null;
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }
}
