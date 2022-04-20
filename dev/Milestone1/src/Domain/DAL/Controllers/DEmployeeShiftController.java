package Domain.DAL.Controllers;

import java.util.Date;
import java.util.Set;

public class DEmployeeShiftController {

    // properties
    protected String tableName;

    public DEmployeeShiftController() {
        tableName = "placeHolder";
    }

    public void addEmployee(Date date, String type, int employeeId) {
        // code to update the DB
    }

    public void removeEmployee(Date date, String type, int employeeId) {
        // code to update the DB
    }

    public Set<Integer> selectEmployeesOfShift(Date date,String type){return null;}
    public void deleteAll(){}

}
