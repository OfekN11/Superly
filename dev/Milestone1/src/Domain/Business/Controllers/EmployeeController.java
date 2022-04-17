package Domain.Business.Controllers;
import Domain.Business.Objects.Carrier;
import Domain.Business.Objects.Cashier;
import Domain.Business.Objects.Employee;
import Domain.Business.Objects.Enums.ShiftType;
import Domain.Business.Objects.Storekeeper;
import Domain.DAL.Controllers.DEmployeeController;
import Domain.DAL.Objects.DEmployee;

import java.util.*;

public class EmployeeController {
    // properties
    Map<Integer,Employee> employees;
    DEmployeeController dEmployeeController;

    // constructor
    public EmployeeController() {
        employees = new HashMap<>();
        dEmployeeController = new DEmployeeController();
    }


    public Set<Employee> getEmployees(Set<Integer> workersId) {
        Set<Employee> output = new HashSet<>();
        for (Integer id : workersId)
            output.add(this.employees.get(id));
        return output;
    }

    public void createFakeEmployees() {
        Set<DEmployee> dEmployees = dEmployeeController.createFakeDTOs();
        for (DEmployee dEmployee : dEmployees) {
            Employee employee = switch (dEmployee.job) {
                case "Carrier" -> new Carrier(dEmployee);
                case "Cashier" -> new Cashier(dEmployee);
                default -> new Storekeeper(dEmployee);
            };
            employees.put(employee.getId(), employee);
        }
    }

    public Set<Employee> availableEmployeeForShift(Date date, ShiftType type){
        Set<Employee> output = new HashSet<>();
        for(Employee employee: employees.values())
            if(employee.isAvailable(date, type))
                output.add(employee);

        return output;
    }
}
