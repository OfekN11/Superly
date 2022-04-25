package Domain.Business.Controllers;

import Domain.Business.BusinessEmployeeFactory;
import Domain.Business.Objects.*;
import Globals.Enums.*;
import Domain.DAL.Controllers.DEmployeeController;
import Domain.DAL.Objects.DEmployee;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeController {
    private static final String EmployeeNotFoundErrorMsg ="Employee id %s could not be found";

    // properties
    private final Map<String,Employee> employees = new HashMap<>();
    private final DEmployeeController dEmployeeController = new DEmployeeController();
    private final BusinessEmployeeFactory employeeFactory= new BusinessEmployeeFactory();

    public Set<Employee> getEmployees(Set<String> workersId) {
        Set<Employee> output = new HashSet<>();
        for (String id : workersId) {
            Employee employee = this.employees.get(id);
            if (employee == null)
                throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,id));
            output.add(employee);
        }
        return output;
    }

    public Employee getEmployee(String employeeID){
        Employee output =employees.get(employeeID);
        if (output== null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,employeeID));

        return output;
    }

    public void loadData() {
        Set<DEmployee> dEmployees = dEmployeeController.loadData();
        for (DEmployee dEmployee : dEmployees) {
            Employee employee = employeeFactory.createBusinessEmployee(dEmployee);
            employees.put(employee.getId(), employee);
        }
    }

    public Set<Employee> availableEmployeeForShift(Date date, ShiftTypes type){
        Set<Employee> output = new HashSet<>();
        for(Employee employee: employees.values())
            if(employee.isAvailable(date, type))
                output.add(employee);

        return output;
    }
    public void deleteData() {
    }

    public void registerEmployee(JobTitles title, String id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) throws Exception {
        if (employees.containsKey(id))
            throw new Exception(String.format("An employee with ID: %o already exists: %s", id, employees.get(id).getName()));
        switch (title) {
            case Carrier :
                    employees.put(id, new Carrier(id, name, bankDetails, salary, employmentConditions, startingDate, certifications, new HashSet<>()));
                    break;
            case Cashier :
                    employees.put(id, new Cashier(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                    break;
            case HR_Manager :
                    employees.put(id, new HR_Manager(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                    break;
            case Storekeeper :
                    employees.put(id, new Storekeeper(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                    break;
            case Logistics_Manager :
                    employees.put(id, new Logistics_Manager(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                    break;
            case Sorter :
                    employees.put(id, new Sorter(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
                    break;
        }
    }

    public void editEmployeeName(String id, String name) throws Exception {
        checkIDValidity(id);
        employees.get(id).setName(name);
    }

    public void editEmployeeBankDetails(String id, String bankDetails) throws Exception {
        checkIDValidity(id);
        employees.get(id).setBankDetails(bankDetails);
    }

    public void editEmployeeEmployementConditions(String id, String employementCondition) throws Exception {
        checkIDValidity(id);
        employees.get(id).setEmploymentConditions(employementCondition);
    }

    public void editEmployeeCertifications(String id, Set<Certifications> certifications) throws Exception {
        checkIDValidity(id);
        employees.get(id).setCertifications(certifications);
    }

    public void editCarrierLicenses(String id, Set<String> licences) throws Exception {
        checkIDValidity(id);
        if (!(employees.get(id) instanceof Carrier))
            throw new Exception(String.format("Emplyee: %s, ID: %s, is not a carrier", employees.get(id).getName(), id));
        ((Carrier)employees.get(id)).setLicences(licences);
    }

    public void removeEmployee(String id) throws Exception {
        checkIDValidity(id);
        employees.remove(id);
    }
    private void checkIDValidity(String id) throws Exception{
        if (!employees.containsKey(id))
            throw new Exception(String.format("No such employee with ID: %s", id));
    }

    public Map<Integer,Set<String>> mapByJob(Set<String> employeesToCheck){
        Map<Integer,Set<String>> output = new HashMap<>();
        for(JobTitles titles :JobTitles.values())
            output.put(titles.ordinal(),new HashSet<>());
        for (String employee: employeesToCheck)
            output.get(employees.get(employee).getJobTitle().ordinal()).add(employee);

        return output;
    }

    public Set<Employee> getAllEmployees() {
        return new HashSet<>(employees.values());
    }

    public void editEmployeeSalary(String id, int newSalary) throws Exception {
        checkIDValidity(id);
        employees.get(id).setSalary(newSalary);
    }

    public void validateID(String id) throws Exception {
        checkIDValidity(id);
    }

    public void validateIDs(Set<String> ids) throws Exception {
        for (String id:ids)
            checkIDValidity(id);
    }

    public void checkUnusedEmployeeID(String id) throws Exception {
        if (employees.containsKey(id))
            throw new Exception(String.format("employee with ID: %s is already taken", id));
    }
}
