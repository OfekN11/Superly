package Domain.Business.Controllers;

import Domain.Business.Controllers.HR.EmployeeController;
import junit.framework.TestCase;
import org.junit.Test;

public class EmployeeControllerTest extends TestCase {

    EmployeeController employeeController = new EmployeeController();
    @Test
    public void testEditEmployeeName() {
        try {
            employeeController.editEmployeeName("160","updated");
            assertEquals(employeeController.getEmployee("160").getName(),"updated");
        } catch (Exception e) {
            fail();
        }
    }
}