package Domain.DAL.Controllers.EmployeeMappers;
import Domain.Business.Objects.*;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeCertificationDAO;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeTypeLink;

import java.sql.SQLException;
import java.util.*;

public class EmployeeDataMapper  {

    // properties
    private CarrierDataMapper carrierDataMapper;
    private CashierDataMapper cashierDataMapper;
    private EmployeeCertificationDAO employeeCertificationDAO;
    private DHR_ManagerController dhr_managerController;
    private DLogistics_ManagerController dLogistics_managerController;
    private DSorterController dSorterController;
    private DStorekeeperController dStorekeeperController;
    private TransportManagerDataMapper transportManagerDataMapper;
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
        transportManagerDataMapper = new TransportManagerDataMapper();
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

            case "transportManager":
                return transportManagerDataMapper.get(id);
            break;
            default:
                throw new IllegalArgumentException("Illegal employee type saved in the db");
        }
    }
    public void save(Employee toSave){
        toSave.save(this);
    }

    public void save(Carrier toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),"carrier");
        carrierDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Cashier toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),"cashier");
        cashierDataMapper.save(toSave.getId(),toSave);
    }

    public void save(HR_Manager toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),"HRManager");
        dhr_managerController.save(toSave.getId(),toSave);
    }

    public void save(Logistics_Manager toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),"logisticsManager");
        dLogistics_managerController.save(toSave.getId(),toSave);
    }

    public void save(Sorter toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),"sorter");
        dSorterController.save(toSave.getId(),toSave);
    }

    public void save(Storekeeper toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),"storekeeper");
        dStorekeeperController.save(toSave.getId(),toSave);
    }

    public void save(Transport_Manager toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),"transportManager");
        dStorekeeperController.save(toSave.getId(),toSave);
    }

    Transport_Manager

}
