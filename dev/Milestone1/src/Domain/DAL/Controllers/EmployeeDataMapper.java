package Domain.DAL.Controllers;
import Domain.Business.Objects.*;
import Domain.DAL.Objects.DEmployee;
import Globals.Enums.Certifications;
import Globals.Pair;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeDataMapper  {

    // properties
    private CarrierDataMapper carrierDataMapper;
    private CashierDataMapper cashierDataMapper;
    private EmployeeCertificationDAO employeeCertificationDAO;
    private DHR_ManagerController dhr_managerController;
    private DLogistics_ManagerController dLogistics_managerController;
    private DSorterController dSorterController;
    private DStorekeeperController dStorekeeperController;
    private EmployeeTypeLink employeeTypeLink;

    // constructor
    public EmployeeDataMapper() {
        employeeCertificationDAO = new EmployeeCertificationDAO();
        carrierDataMapper = new CarrierDataMapper();
        cashierDataMapper = new CashierDataMapper();
        dhr_managerController = new DHR_ManagerController();
        dLogistics_managerController = new DLogistics_ManagerController();
        dSorterController = new DSorterController();
        dStorekeeperController = new DStorekeeperController();
        employeeTypeLink = new EmployeeTypeLink();
    }

    public Employee get(String id) throws SQLException {
        Set<String>  type= employeeTypeLink.get(id);
        if (type.isEmpty())
            return null;

        switch (new ArrayList<>(type).get(0)){
            case "carrier":
                return carrierDataMapper.get(id);
                break;

            case "cashier":
                return cashierDataMapper.get(id);
                break;

            case "HRManager":
                return dhr_managerController.get(id);
                break;

            case "logisticsManager":
                return dLogistics_managerController.get(id);
                break;

            case "sorter":
                return dSorterController.get(id);
                break;

            case "storekeeper":
                return dStorekeeperController.get(id);
                break;
            default:
                throw new IllegalArgumentException("Illegal employee type saved in the db");
        }
    }
    public void save(Employee toSave){
        toSave.save(this);
    }

    public void save(Carrier toSave) throws SQLException {
        carrierDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Cashier toSave) throws SQLException {
        cashierDataMapper.save(toSave.getId(),toSave);
    }

    public void save(HR_Manager toSave) throws SQLException {
        dhr_managerController.save(toSave.getId(),toSave);
    }

    public void save(Logistics_Manager toSave) throws SQLException {
        dLogistics_managerController.save(toSave.getId(),toSave);
    }

    public void save(Sorter toSave) throws SQLException {
        dSorterController.save(toSave.getId(),toSave);
    }

    public void save(Storekeeper toSave) throws SQLException {
        dStorekeeperController.save(toSave.getId(),toSave);
    }

}
