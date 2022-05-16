package Domain.Business.Controllers;

import Domain.Business.Objects.Employee.*;
import Globals.Enums.*;
import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;

import java.time.LocalDate;
import java.util.*;

public class EmployeeController {
    private static final String EmployeeNotFoundErrorMsg = "Employee id %s could not be found";

    // properties
    private final EmployeeDataMapper employeeDataMapper = new EmployeeDataMapper();

    //CREATE
    public void registerEmployee(JobTitles title, String id, String name, String bankDetails, int salary, String employmentConditions, LocalDate startingDate, Set<Certifications> certifications) throws Exception {
        checkUnusedEmployeeID(id);
            Employee employee = employeeDataMapper.get(id);
            if (employee != null)
                throw new Exception(String.format("An employee with ID: %s already exists: %s", id, employee.getName()));
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

    //READ
    public Employee getEmployee(String employeeID) throws Exception {
        Employee employee = employeeDataMapper.get(employeeID);
        if (employee == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, employeeID));
        return employee;
    }

    public Carrier getCarrier(String employeeID) throws Exception {
        Carrier carrier = employeeDataMapper.getCarrier(employeeID);
        if (carrier == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, employeeID));
        return carrier;
    }

    public Cashier getCashier(String employeeID) throws Exception {
        Cashier cashier = employeeDataMapper.getCashier(employeeID);
        if (cashier == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, employeeID));
        return cashier;
    }

    public Sorter getSorter(String employeeID) throws Exception {
        Sorter sorter = employeeDataMapper.getSorter(employeeID);
        if (sorter == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, employeeID));
        return sorter;
    }

    public Storekeeper getStorekeeper(String employeeID) throws Exception {
        Storekeeper storekeeper = employeeDataMapper.getStorekeeper(employeeID);
        if (storekeeper == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, employeeID));
        return storekeeper;
    }

    public HR_Manager getHR_Manager(String employeeID) throws Exception {
        HR_Manager hr_manager = employeeDataMapper.getHR_Manager(employeeID);
        if (hr_manager == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, employeeID));
        return hr_manager;
    }

    public Logistics_Manager getLogistics_Manager(String employeeID) throws Exception {
        Logistics_Manager logistics_manager = employeeDataMapper.getLogistics_Manager(employeeID);
        if (logistics_manager == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, employeeID));
        return logistics_manager;
    }

    public Transport_Manager getTransport_Manager(String employeeID) throws Exception {
        Transport_Manager transport_manager = employeeDataMapper.getTransport_Manager(employeeID);
        if (transport_manager == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, employeeID));
        return transport_manager;
    }

    public Set<Employee> getEmployee(Set<String> workersId) throws Exception {
        Set<Employee> employees = new HashSet<>();
        for (String id : workersId)
            employees.add(getEmployee(id));
        return employees;
    }

    public Set<Carrier> getCarrier(Set<String> workersId) throws Exception {
        Set<Carrier> carriers = new HashSet<>();
        for (String id : workersId)
            carriers.add(getCarrier(id));
        return carriers;
    }

    public Set<Cashier> getCashier(Set<String> workersId) throws Exception {
        Set<Cashier> cashiers = new HashSet<>();
        for (String id : workersId)
            cashiers.add(getCashier(id));
        return cashiers;
    }

    public Set<Sorter> getSorter(Set<String> workersId) throws Exception {
        Set<Sorter> sorters = new HashSet<>();
        for (String id : workersId)
            sorters.add(getSorter(id));
        return sorters;
    }

    public Set<Storekeeper> getStorekeeper(Set<String> workersId) throws Exception {
        Set<Storekeeper> storekeepers = new HashSet<>();
        for (String id : workersId)
            storekeepers.add(getStorekeeper(id));
        return storekeepers;
    }

    public Set<HR_Manager> getHR_Manager(Set<String> workersId) throws Exception {
        Set<HR_Manager> hr_managers = new HashSet<>();
        for (String id : workersId)
            hr_managers.add(getHR_Manager(id));
        return hr_managers;
    }

    public Set<Logistics_Manager> getLogistics_Manager(Set<String> workersId) throws Exception {
        Set<Logistics_Manager> logistics_managers = new HashSet<>();
        for (String id : workersId)
            logistics_managers.add(getLogistics_Manager(id));
        return logistics_managers;
    }

    public Set<Transport_Manager> getTransport_Manager(Set<String> workersId) throws Exception {
        Set<Transport_Manager> transport_managers = new HashSet<>();
        for (String id : workersId)
            transport_managers.add(getTransport_Manager(id));
        return transport_managers;
    }

    public Set<Employee> getEmployee() throws Exception {
        return employeeDataMapper.get();
    }

    public Set<Carrier> getCarrier() throws Exception {
        return employeeDataMapper.getCarrier();
    }

    public Set<Cashier> getCashier() throws Exception {
        return employeeDataMapper.getCashier();
    }

    public Set<Sorter> getSorter() throws Exception {
        return employeeDataMapper.getSorter();
    }

    public Set<Storekeeper> getStorekeeper() throws Exception {
        return employeeDataMapper.getStorekeeper();
    }

    public Set<HR_Manager> getHR_Manager() throws Exception {
        return employeeDataMapper.getHR_Manager();
    }

    public Set<Logistics_Manager> getLogistics_Manager() throws Exception {
        return employeeDataMapper.getLogistics_Manager();
    }

    public Set<Transport_Manager> getTransport_Manager() throws Exception {
        return employeeDataMapper.getTransport_Manager();
    }

    //UPDATE
    public void editEmployeeName(String id, String name) throws Exception {
        Employee employee = employeeDataMapper.get(id);
        if (employee == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, id));
        employee.setName(name);
        employeeDataMapper.save(employee);
    }

    public void editEmployeeBankDetails(String id, String bankDetails) throws Exception {
        Employee employee = employeeDataMapper.get(id);
        if (employee == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, id));
        employee.setBankDetails(bankDetails);
        employeeDataMapper.save(employee);
    }

    public void editEmployeeCertifications(String id, Set<Certifications> certifications) throws Exception {
        Employee employee = employeeDataMapper.get(id);
        if (employee == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, id));
        employee.setCertifications(certifications);
        employeeDataMapper.save(employee);
    }

    public void editEmployeeSalary(String id, int newSalary) throws Exception {
        Employee employee = employeeDataMapper.get(id);
        if (employee == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, id));
        employee.setSalary(newSalary);
        employeeDataMapper.save(employee);
    }

    public void editCarrierLicenses(String id, Set<LicenseTypes> licences) throws Exception {
        Carrier carrier = employeeDataMapper.getCarrier(id);
        if (carrier == null)
            throw new Exception(String.format(EmployeeNotFoundErrorMsg, id));
        carrier.setLicences(licences);
        employeeDataMapper.save(carrier);
    }

    //DELETE
    public void removeEmployee(String id) throws Exception {
        employeeDataMapper.delete(id);
    }

    public void deleteData() {
    }

    //MISC
    public void checkUnusedEmployeeID(String id) throws Exception {
        Employee employee = employeeDataMapper.get(id);
        if (employee != null)
            throw new Exception(String.format("An employee with ID: %s already exists: %s", id, employee.getName()));
        Employee.validateLegalID(id);
    }

    //TODO: get rid of this if possible
    public String getEmploymentConditionsOf(String id) throws Exception {
        Employee employee = employeeDataMapper.get(id);
        if (employee == null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg, id));
        return employee.getEmploymentConditions();
    }
}