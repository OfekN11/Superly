package Domain.DAL.Controllers;

import Globals.Enums.Certifications;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.Set;

public class DEmployeeConstraint {
    private final String tableName;
    public DEmployeeConstraint() {
        tableName = "placeHolder";
    }

    public void addEmployeeConstraint(Date date, ShiftTypes shiftType, int employeeId ) {
        // code to update the DB
    }

    public void removeEmployeeConstraint(Date date, String type, int employeeId) {
        // code to update the DB
    }

    public Set<Certifications> selectConstraintOfEmployee(int employeeID){
        return null;
    }

    public void deleteAll(){}
}
