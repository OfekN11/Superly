package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DTOControllers;
import Domain.DAL.Objects.DEmployee;
import Globals.Enums.Certifications;
import Globals.Pair;

import java.util.*;

public class DEmployeeController extends DTOControllers<DEmployee> {

    // properties
    private DCarrierController dCarrierController;
    private CashierDataMapper cashierDataMapper;
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
        cashierDataMapper = new CashierDataMapper();
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
        employees.addAll(cashierDataMapper.loadData());
        employees.addAll(dhr_managerController.loadData());
        employees.addAll(dLogistics_managerController.loadData());
        employees.addAll(dSorterController.loadData());
        employees.addAll(dStorekeeperController.loadData());
        loadCertifications(employees);
        persistAll(employees);
        return employees;
    }


    private void loadCertifications(Set<DEmployee> employees) {
        Dictionary<String,DEmployee> dEmployeeDictionary =new Hashtable<>();
        for(DEmployee employee : employees)
            dEmployeeDictionary.put(employee.getId(), employee);
        for(Pair<String,Set<Certifications>> employeeCertificationPair : dEmployeeCertificationController.loadData())
            dEmployeeDictionary.get(employeeCertificationPair.getLeft()).setCertifications(employeeCertificationPair.getRight());
    }

    public Set<Certifications> getCertificationOfEmployee(int employeeId){
        return dEmployeeCertificationController.selectCertificationsOfEmployee(employeeId);
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void delete(DEmployee toDelete) {
    }
}
