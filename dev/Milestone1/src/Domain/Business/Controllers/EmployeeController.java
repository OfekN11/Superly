package Domain.Business.Controllers;

import Domain.Business.Objects.*;
import Globals.Enums.*;
import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class EmployeeController {
    private static final String EmployeeNotFoundErrorMsg ="Employee id %s could not be found";

    // properties
    private final EmployeeDataMapper employeeDataMapper = new EmployeeDataMapper();

    public Set<Employee> getEmployees(Set<String> workersId) {
        Set<Employee> output = new HashSet<>();
        for (String id : workersId) {
            try {
                Employee employee = employeeDataMapper.get(id);
                if (employee == null)
                    throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,id));
                output.add(employee);
            } catch (SQLException e) {
                throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
            }
        }
        return output;
    }

    public Employee getEmployee(String employeeID){
        try {
            Employee output = employeeDataMapper.get(employeeID);
            if (output== null)
                throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,employeeID));
            return output;
        } catch (SQLException e) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public void deleteData() {
    }

    public void registerEmployee(JobTitles title, String id, String name, String bankDetails, int salary, String employmentConditions, LocalDate startingDate, Set<Certifications> certifications) throws Exception {
        checkUnusedEmployeeID(id);
        try {
            Employee employee = employeeDataMapper.get(id);
            if (employee != null)
                throw new Exception(String.format("An employee with ID: %s already exists: %s", id, employee.getName()));
        } catch (SQLException e) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
        switch (title) {
            case Carrier:
                employeeDataMapper.save(new Carrier(id, name, bankDetails, salary, employmentConditions, startingDate, certifications, new HashSet<>()));
                break;
            case Cashier:
                employeeDataMapper.save(new Cashier(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                break;
            case HR_Manager:
                employeeDataMapper.save(new HR_Manager(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                break;
            case Storekeeper:
                employeeDataMapper.save(new Storekeeper(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                break;
            case Logistics_Manager:
                employeeDataMapper.save(new Logistics_Manager(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                break;
            case Sorter:
                employeeDataMapper.save(new Sorter(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                break;
            case Transport_Manager:
                employeeDataMapper.save(new Transport_Manager(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                break;
        }
    }

    public void editEmployeeName(String id, String name) throws Exception {
        try {
            Employee employee = employeeDataMapper.get(id);
            if (employee == null)
                throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,id));
            employee.setName(name);
            employeeDataMapper.save(employee);
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public void editEmployeeBankDetails(String id, String bankDetails) throws Exception {
        try {
            Employee employee = employeeDataMapper.get(id);
            if (employee == null)
                throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,id));
            employee.setBankDetails(bankDetails);
            employeeDataMapper.save(employee);
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public void editEmployeeCertifications(String id, Set<Certifications> certifications) throws Exception {
        try {
            Employee employee = employeeDataMapper.get(id);
            if (employee == null)
                throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,id));
            employee.setCertifications(certifications);
            employeeDataMapper.save(employee);
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public void editEmployeeSalary(String id, int newSalary) throws Exception {
        try {
            Employee employee = employeeDataMapper.get(id);
            if (employee == null)
                throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,id));
            employee.setSalary(newSalary);
            employeeDataMapper.save(employee);
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public void editCarrierLicenses(String id, Set<LicenseTypes> licences) throws Exception {
        try {
            Carrier carrier = employeeDataMapper.getCarrier(id);
            if (carrier == null)
                throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,id));
            carrier.setLicences(licences);
            employeeDataMapper.save(carrier);
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public void removeEmployee(String id) throws Exception {
        try {
            employeeDataMapper.delete(id);
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public Set<Employee> getAllEmployees() throws Exception {
        try {
            return employeeDataMapper.getAll();
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public Set<Carrier> getAllCarriers() throws Exception {
        try {
            return employeeDataMapper.getAllCarriers();
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public Set<Cashier> getAllCashiers() throws Exception {
        try {
            return employeeDataMapper.getAllCashiers();
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public Set<Sorter> getAllSorters() throws Exception {
        try {
            return employeeDataMapper.getAllSorters();
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public Set<Storekeeper> getAllStorekeepers() throws Exception {
        try {
            return employeeDataMapper.getAllStorekeepers();
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public Set<HR_Manager> getAllHR_Managers() throws Exception {
        try {
            return employeeDataMapper.getAllHR_Managers();
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public Set<Logistics_Manager> getAllLogistics_Managers() throws Exception {
        try {
            return employeeDataMapper.getAllLogistics_Managers();
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public Set<Transport_Manager> getAllTransport_Managers() throws Exception {
        try {
            return employeeDataMapper.getAllTransport_Managers();
        }
        catch (SQLException e){
            throw new Exception("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }

    public void checkUnusedEmployeeID(String id) throws Exception {
        try {
            Employee employee = employeeDataMapper.get(id);
            if (employee != null)
                throw new Exception(String.format("An employee with ID: %s already exists: %s", id, employee.getName()));
        } catch (SQLException e) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
        Employee.validateLegalID(id);
    }

    //TODO: get rid of this if possible
    public String getEmploymentConditionsOf(String id) throws Exception {
        try {
            Employee employee = employeeDataMapper.get(id);
            if (employee == null)
                throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,id));
            return employee.getEmploymentConditions();
        } catch (SQLException e) {
            throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
        }
    }
}
