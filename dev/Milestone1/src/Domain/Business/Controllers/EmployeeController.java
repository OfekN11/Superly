package Domain.Business.Controllers;
import Domain.Business.Objects.Carrier;
import Domain.Business.Objects.Cashier;
import Domain.Business.Objects.Employee;
import Domain.Business.Objects.Storekeeper;
import Domain.DAL.Controllers.DEmployeeController;
import Domain.DAL.Objects.DEmployee;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EmployeeController {
    // properties
    Map<Integer,Employee> employees;
    DEmployeeController dEmployeeController;

    // constructor
    public EmployeeController() {
        dEmployeeController = new DEmployeeController();
        Set<DEmployee> dEmployees = dEmployeeController.createFakeDTOs();
        employees = new HashMap<>();
        for (DEmployee dEmployee :dEmployees) {
            Employee employee;
            switch (dEmployee.job){
                case "Carrier":
                    employee =new Carrier(dEmployee);
                    break;
                case "Cashier":
                    employee =new Cashier(dEmployee);
                    break;
                default:
                    employee =new Storekeeper(dEmployee);
                    break;
            }
            employees.put(employee.getId(),employee);
        }
    }

    public Set<Employee> getEmployees(Set<Integer> workersId) {
        Set<Employee> output = new HashSet<>();
        for (Integer id : workersId)
            output.add(this.employees.get(id));
        return output;
    }
}
