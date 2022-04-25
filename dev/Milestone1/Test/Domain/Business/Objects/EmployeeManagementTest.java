package Domain.Business.Objects;

import Domain.Business.Controllers.EmployeeController;

import static org.junit.Assert.*;

public class EmployeeManagementTest {

    @org.junit.Test
    public void EditName() {
        EmployeeController employeeController = new EmployeeController();
        employeeController.loadData();
        Employee employee = employeeController.getEmployee("2");

        String preName =employee.getName();
        try {
            employeeController.editEmployeeName("2","Roi");
            assertNotEquals(preName,employee.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void EditEmployeeEmploymentConditions() {
        EmployeeController employeeController = new EmployeeController();
        employeeController.loadData();
        Employee employee = employeeController.getEmployee("2");

        String preConditions =employee.getEmploymentConditions();
        try {
            employeeController.editEmployeeEmployementConditions("2","New Conditions");
            assertNotEquals(preConditions,employee.getEmploymentConditions());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}