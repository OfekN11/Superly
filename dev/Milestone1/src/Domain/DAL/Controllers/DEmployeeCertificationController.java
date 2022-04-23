package Domain.DAL.Controllers;

import Globals.Enums.Certifications;

import java.util.Date;
import java.util.Set;

public class DEmployeeCertificationController {

    // properties
    protected final String tableName;

    public DEmployeeCertificationController() {
        tableName = "placeHolder";
    }

    public void add(int employeeID, Certifications certification) {
        // code to update the DB
    }

    public void remove(Date date, String type, int employeeId) {
        // code to update the DB
    }

    public Set<Certifications> selectCertificationsOfEmployee(int employeeID){
        return null;
    }

    public void deleteAll(){}
}
