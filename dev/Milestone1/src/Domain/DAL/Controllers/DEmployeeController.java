package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DEmployee;
import Globals.Enums.Certifications;
import javafx.util.Pair;

import java.sql.Time;
import java.util.*;

public class DEmployeeController extends DalController<DEmployee> {

    // properties
    private DCarrierController dCarrierController;
    private DCashierController dCashierController;
    private DEmployeeCertificationController dEmployeeCertificationController;
    private DHR_ManagerController dhr_managerController;
    private DLogistics_ManagerController dLogistics_managerController;
    private DSorterController dSorterController;
    private DStorekeeperController dStorekeeperController;

    // constructor
    public DEmployeeController() {
        super("PlaceHolder");
        dEmployeeCertificationController = new DEmployeeCertificationController();
        dCarrierController = new DCarrierController();
        dCashierController = new DCashierController();
        dhr_managerController = new DHR_ManagerController();
        dLogistics_managerController = new DLogistics_ManagerController();
        dSorterController = new DSorterController();
        dStorekeeperController = new DStorekeeperController();
    }

    // functions
    @Override
    public Set<DEmployee> loadData() {
        Set<DEmployee> employees= new HashSet<>();
        employees.addAll(dCarrierController.loadData());
        employees.addAll(dCashierController.loadData());
        employees.addAll(dhr_managerController.loadData());
        employees.addAll(dLogistics_managerController.loadData());
        employees.addAll(dSorterController.loadData());
        employees.addAll(dStorekeeperController.loadData());
        updateCertifications(employees);
        return employees;
    }

    private void updateCertifications(Set<DEmployee> employees) {
        Dictionary<Integer,DEmployee> dEmployeeDictionary =new Hashtable<>();
        for(DEmployee employee : employees)
            dEmployeeDictionary.put(employee.getId(), employee);
        for(Pair<Integer,Set<Certifications>> employeeCertificationPair : dEmployeeCertificationController.loadData())
            dEmployeeDictionary.get(employeeCertificationPair.getKey()).setCertifications(employeeCertificationPair.getValue());
    }

    public Set<Certifications> getCertificationOfEmployee(int employeeId){
        return dEmployeeCertificationController.selectCertificationsOfEmployee(employeeId);
    }

    @Override
    public void deleteAll() {

    }
}
