package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DalController;
import Globals.Enums.Certifications;
import javafx.util.Pair;

import java.util.Date;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

public class DEmployeeCertificationController extends DalController<Pair<Integer,Set<Certifications>>> {

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
    public Set<Pair<Integer, Set<Certifications>>> loadData() {
        Set<Pair<Integer, Set<Certifications>>> output = new HashSet<>();
        for(int i=0; i<10; i++){
            Set<Certifications> licenses = new HashSet<>();
            if (i%2==0)
                licenses.add(Certifications.ShiftManagement);
            output.add(new Pair<>(i,licenses));
        }
        return output;
    }

    public void deleteAll(){}
}
