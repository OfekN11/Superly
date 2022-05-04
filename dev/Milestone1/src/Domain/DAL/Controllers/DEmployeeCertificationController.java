package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DEmployee;
import Globals.Enums.Certifications;
import Globals.Pair;

import java.util.Date;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

public class DEmployeeCertificationController extends DalController<Pair<String,Set<Certifications>>> {

    public DEmployeeCertificationController() {
        super("tableName");
    }

    public void add(String employeeID, Certifications certification) {
        // code to update the DB
    }

    public void remove(Date date, String type, int employeeId) {
        // code to update the DB
    }

    public Set<Certifications> selectCertificationsOfEmployee(int employeeID){
        return null;
    }

    @Override
    public Set<Pair<String, Set<Certifications>>> loadData() {
        Set<Pair<String, Set<Certifications>>> output = new HashSet<>();
        for(int i=0; i<10; i++){
            Set<Certifications> licenses = new HashSet<>();
            licenses.add(Certifications.ShiftManagement);
            output.add(new Pair<>(""+i,licenses));
        }
        return output;
    }

    public void deleteAll(){}

    public void replaceCertification(DEmployee dEmployee, Set<Certifications> certifications) {
    }
}
