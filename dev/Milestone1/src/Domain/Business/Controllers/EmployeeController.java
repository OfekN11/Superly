package Domain.Business.Controllers;
import Domain.Business.Objects.Cashier;
import Domain.Business.Objects.Carrier;
import Domain.Business.Objects.HR_Manager;
import Domain.Business.Objects.Logistics_Manager;
import Domain.Business.Objects.Storekeeper;
import Domain.Business.Objects.Employee;
import Domain.Business.Objects.Sorter;
import Domain.DAL.Controllers.DEmployeeController;
import Domain.DAL.Objects.DEmployee;
import Globals.Enums.*;

import java.util.*;

public class EmployeeController {
    private static final String EmployeeNotFoundErrorMsg ="Employee id %f could not be found";

    // properties
    private final Map<Integer,Employee> employees = new HashMap<>();
    private final DEmployeeController dEmployeeController = new DEmployeeController();
    private final Map<Date,Map<ShiftTypes, Set<Integer>>> constraints = new HashMap<>();

    public Set<Employee> getEmployees(Set<Integer> workersId) {
        Set<Employee> output = new HashSet<>();
        for (Integer id : workersId) {
            Employee employee = this.employees.get(id);
            if (employee == null)
                throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,id));
            output.add(employee);
        }
        return output;
    }

    public Employee getEmployee(int employeeID){
        Employee output =employees.get(employeeID);
        if (output== null)
            throw new RuntimeException(String.format(EmployeeNotFoundErrorMsg,employeeID));

        return output;
    }

    public void createFakeEmployees() {
        Set<DEmployee> dEmployees = dEmployeeController.createFakeDTOs();
        for (DEmployee dEmployee : dEmployees) {
            Employee employee = switch (dEmployee.getJob()) {
                case "Carrier" -> new Carrier(dEmployee);
                case "Cashier" -> new Cashier(dEmployee);
                default -> new Storekeeper(dEmployee);
            };
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

    public Set<Employee> getEmployeesForWork(Date workday, ShiftTypes shift) {
        createWorkday(workday);
        return getEmployees(constraints.get(workday).get(shift));
    }

    public void loadData() {
    }

    public void deleteData() {
    }

    public void registerEmployee(JobTitles title, int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, Set<Certifications> certifications) throws Exception {
        if (employees.containsKey(id))
            throw new Exception(String.format("An employee with ID: %o already exists: %s", id, employees.get(id).getName()));
        switch (title) {
            case Carrier ->
                    employees.put(id, new Carrier(id, name, bankDetails, salary, employmentConditions, startingDate, certifications, new HashSet<>()));
            case Cashier ->
                    employees.put(id, new Cashier(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
            case HR_Manager ->
                    employees.put(id, new HR_Manager(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
            case Storekeeper ->
                    employees.put(id, new Storekeeper(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
            case Logistics_Manager ->
                    employees.put(id, new Logistics_Manager(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
            case Sorter ->
                    employees.put(id, new Sorter(id, name, bankDetails, salary, employmentConditions, startingDate, certifications));
        }
    }

    public void editEmployeeName(int id, String name) throws Exception {
        checkIDValidity(id);
        employees.get(id).setName(name);
    }

    public void editEmployeeBankDetails(int id, String bankDetails) throws Exception {
        checkIDValidity(id);
        employees.get(id).setBankDetails(bankDetails);
    }

    public void editEmployeeEmployementConditions(int id, String employementCondition) throws Exception {
        checkIDValidity(id);
        employees.get(id).setEmploymentConditions(employementCondition);
    }

    public void editEmployeeCertifications(int id, Set<Certifications> certifications) throws Exception {
        checkIDValidity(id);
        employees.get(id).setCertifications(certifications);
    }

    public void editCarrierLicenses(int id, Set<String> licences) throws Exception {
        checkIDValidity(id);
        if (!(employees.get(id) instanceof Carrier))
            throw new Exception(String.format("Emplyee: %s, ID: %s, is not a carrier", employees.get(id).getName(), id));
        ((Carrier)employees.get(id)).setLicences(licences);
    }

    public void removeEmployee(int id) throws Exception {
        checkIDValidity(id);
        employees.remove(id);
    }

    public void addEmployeeConstraint(int id, Date workday, ShiftTypes shift) throws Exception {
        checkIDValidity(id);
        createWorkday(workday);
        constraints.get(workday).get(shift).add(id);
    }

    public void removeEmployeeConstraint(int id, Date workday, ShiftTypes shift) throws Exception {
        checkIDValidity(id);
        createWorkday(workday);
        constraints.get(workday).get(shift).remove(id);
    }

    private void checkIDValidity(int id) throws Exception{
        if (!employees.containsKey(id))
            throw new Exception(String.format("No such employee with ID: ", id));
    }

    private void createWorkday(Date workday){
        if (!constraints.containsKey(workday))
            constraints.put(workday, new HashMap<>());
            for (ShiftTypes shift: ShiftTypes.values())
                constraints.get(workday).put(shift, new HashSet<>());
    }
}
