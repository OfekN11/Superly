package Presentation;

import Domain.Service.Objects.Constraint;
import Domain.Service.Objects.Employee;
import Domain.Service.Objects.Result;
import Domain.Service.Objects.Shift;
import Domain.Service.Services.*;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;
import Presentation.Screens.Carrier;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class BackendController {
    private final EmployeeService employeeService = new EmployeeService();
    private final ShiftService shiftService = new ShiftService();
    private final ConstraintService constraintService = new ConstraintService();

    public Set<Employee> getAllEmployees() throws Exception {
        Result<Set<Employee>> result = employeeService.getAllEmployees();
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAllCashiers() throws Exception {
        return getAllEmployees().stream().filter((x) -> x.getType() == JobTitles.Cashier).collect(Collectors.toSet());
    }

    public Set<Employee> getAllCarriers() throws Exception {
        return getAllEmployees().stream().filter((x) -> x.getType() == JobTitles.Carrier).collect(Collectors.toSet());
    }

    public Set<Employee> getAllStorekeepers() throws Exception {
        return getAllEmployees().stream().filter((x) -> x.getType() == JobTitles.Storekeeper).collect(Collectors.toSet());
    }

    public Set<Employee> getAllSorters() throws Exception {
        return getAllEmployees().stream().filter((x) -> x.getType() == JobTitles.Sorter).collect(Collectors.toSet());
    }

    public Set<Employee> getAllHR_Managers() throws Exception {
        return getAllEmployees().stream().filter((x) -> x.getType() == JobTitles.HR_Manager).collect(Collectors.toSet());
    }

    public Set<Employee> getAllLogistics_Managers() throws Exception {
        return getAllEmployees().stream().filter((x) -> x.getType() == JobTitles.Logistics_Manager).collect(Collectors.toSet());
    }

    public void removeEmployee(String id) throws Exception {
        Result<Object> result = employeeService.removeEmployee(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public Employee getEmployee(String id) throws Exception {
        Result<Employee> result = employeeService.getEmployee(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void checkUnusedEmployeeID(String id) throws Exception {
        Result<Object> result = employeeService.checkUnusedEmployeeID(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void validateID(String id) throws Exception {
        Result<Object> result = employeeService.validateID(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void validateIDs(Set<String> ids) throws Exception {
        Result<Object> result = employeeService.validateIDs(ids);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void addEmployee(JobTitles jobTitle, String id, String name, String bankDetails, Integer salary, String employmentConditions, LocalDate startingDate, Set<Certifications> certifications) throws Exception {
        Result<Object> result = employeeService.registerEmployee(jobTitle, id, name, bankDetails, salary, employmentConditions, startingDate, certifications);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editEmployeeName(Presentation.Screens.Employee employee, String newName) throws Exception {
        Result<Object> result = employeeService.editEmployeeName(employee.getID(), newName);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editEmployeeBankDetails(Presentation.Screens.Employee employee, String newNankDetails) throws Exception {
        Result<Object> result = employeeService.editEmployeeBankDetails(employee.getID(), newNankDetails);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editEmployeeSalary(Presentation.Screens.Employee employee, int newSalary) throws Exception {
        Result<Object> result = employeeService.editEmployeeSalary(employee.getID(), newSalary);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editEmployeeCertifications(Presentation.Screens.Employee employee, Set<Certifications> newCertifications) throws Exception {
        Result<Object> result = employeeService.editEmployeeCertifications(employee.getID(), newCertifications);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editCarrierLicenses(Carrier carrier, Set<String> newLicenses) throws Exception {
        Result<Object> result = employeeService.editCarrierLicenses(carrier.getID(), newLicenses);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public Set<Shift> getEmployeeShiftsBetween(String id, LocalDate start, LocalDate end) throws Exception {
        Result<Set<Shift>> result = shiftService.getEmployeeShiftsBetween(id, start, end);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Constraint> getEmployeeConstraintsBetween(String id, LocalDate today, LocalDate nextMonth) throws Exception {
        Result<Set<Constraint>> result = constraintService.getEmployeeConstraintsBetween(id, today, nextMonth);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void registerToConstraint(String id, LocalDate date, ShiftTypes shift) throws Exception {
        Result<Object> result = constraintService.registerToConstraint(id, date, shift);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void unregisterToConstraint(String id, LocalDate date, ShiftTypes shift) throws Exception {
        Result<Object> result = constraintService.unregisterFromConstraint(id, date, shift);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public Set<Shift> getShiftsBetween(LocalDate start, LocalDate end) throws Exception {
        Result<Set<Shift>> result = shiftService.getShiftsBetween(start, end);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void createShift(LocalDate date, ShiftTypes type, String managerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount) throws Exception {
        Result<Object> result = shiftService.createShift(date, type, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount, logistics_managerCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public Constraint getConstraint(LocalDate date, ShiftTypes type) throws Exception {
        Result<Constraint> result = constraintService.getConstraint(date, type);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getEmployees(Set<String> employeeIDs) throws Exception {
        Result<Set<Employee>> result = employeeService.getEmployees(employeeIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public String getEmployeeWorkDetailsForCurrentMonth(String id) throws Exception {
        Result<String> result = shiftService.getEmployeeWorkDetailsForCurrentMonth(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Shift getShift(LocalDate date, ShiftTypes type) throws Exception {
        Result<Shift> result = shiftService.getShift(date, type);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void removeShift(LocalDate date, ShiftTypes type) throws Exception {
        Result<Object> result = shiftService.removeShift(date, type);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftManagerID(Presentation.Screens.Shift shift, String shiftManagerId) throws Exception {
        validateID(shiftManagerId);
        Result<Object> result = shiftService.editShiftManagerID(shift.getDate(), shift.getType(), shiftManagerId);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftCarrierCount(Presentation.Screens.Shift shift, int newCarrierCount) throws Exception {
        Result<Object> result = shiftService.editShiftCarrierCount(shift.getDate(), shift.getType(), newCarrierCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftCashierCount(Presentation.Screens.Shift shift, int newCashierCount) throws Exception {
        Result<Object> result = shiftService.editShiftCashierCount(shift.getDate(), shift.getType(), newCashierCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftSorterCount(Presentation.Screens.Shift shift, int newSorterCount) throws Exception {
        Result<Object> result = shiftService.editShiftSorterCount(shift.getDate(), shift.getType(), newSorterCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftStorekeeperCount(Presentation.Screens.Shift shift, int newStorekeeperCount) throws Exception {
        Result<Object> result = shiftService.editShiftStorekeeperCount(shift.getDate(), shift.getType(), newStorekeeperCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftHR_ManagerCount(Presentation.Screens.Shift shift, int newHr_managersCount) throws Exception {
        Result<Object> result = shiftService.editShiftHR_ManagerCount(shift.getDate(), shift.getType(), newHr_managersCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftLogistics_ManagerCount(Presentation.Screens.Shift shift, int newLogistics_managersCount) throws Exception {
        Result<Object> result = shiftService.editShiftLogistics_ManagerCount(shift.getDate(), shift.getType(), newLogistics_managersCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftCarrierIDs(Presentation.Screens.Shift shift, Set<String> newCarrierIDs) throws Exception {
        validateIDs(newCarrierIDs);
        Result<Object> result = shiftService.editShiftCarrierIDs(shift.getDate(), shift.getType(), newCarrierIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftCashierIDs(Presentation.Screens.Shift shift, Set<String> newCashierIDs) throws Exception {
        validateIDs(newCashierIDs);
        Result<Object> result = shiftService.editShiftCashierIDs(shift.getDate(), shift.getType(), newCashierIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftStorekeeperIDs(Presentation.Screens.Shift shift, Set<String> newStorekeeperIDs) throws Exception {
        validateIDs(newStorekeeperIDs);
        Result<Object> result = shiftService.editShiftStorekeeperIDs(shift.getDate(), shift.getType(), newStorekeeperIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftSorterIDs(Presentation.Screens.Shift shift, Set<String> newSorterIDs) throws Exception {
        validateIDs(newSorterIDs);
        Result<Object> result = shiftService.editShiftSorterIDs(shift.getDate(), shift.getType(), newSorterIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftHR_ManagerIDs(Presentation.Screens.Shift shift, Set<String> newHr_managerIDs) throws Exception {
        validateIDs(newHr_managerIDs);
        Result<Object> result = shiftService.editShiftHR_ManagerIDs(shift.getDate(), shift.getType(), newHr_managerIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftLogistics_ManagerIDs(Presentation.Screens.Shift shift, Set<String> newLogistics_managerIDs) throws Exception {
        validateIDs(newLogistics_managerIDs);
        Result<Object> result = shiftService.editShiftLogistics_ManagerIDs(shift.getDate(), shift.getType(), newLogistics_managerIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void loadData() {
        employeeService.loadData();
        constraintService.loadData();
        shiftService.loadData();
    }

    public String getEmploymentConditionsOf(String id) throws Exception {
        Result<String> result = employeeService.getEmploymentConditionsOf(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }
}
