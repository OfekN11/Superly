package Domain.DAL.Controllers.EmployeeMappers;
import Domain.Business.Objects.*;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeTypeLink;
import Globals.Enums.JobTitles;
import java.sql.SQLException;
import java.util.*;

public class EmployeeDataMapper  {

    // properties
    private CarrierDataMapper carrierDataMapper;
    private CashierDataMapper cashierDataMapper;
    private HR_ManagerDataMapper hR_managerDataMapper;
    private Logistics_ManagerDataMapper logistics_managerDataMapper;
    private SorterDataMapper sorterDataMapper;
    private StorekeeperDataMapper storekeeperDataMapper;
    private TransportManagerDataMapper transportManagerDataMapper;
    private EmployeeTypeLink employeeTypeLink;


    // constructor
    public EmployeeDataMapper() {
        carrierDataMapper = new CarrierDataMapper();
        cashierDataMapper = new CashierDataMapper();
        hR_managerDataMapper = new HR_ManagerDataMapper();
        logistics_managerDataMapper = new Logistics_ManagerDataMapper();
        sorterDataMapper = new SorterDataMapper();
        storekeeperDataMapper = new StorekeeperDataMapper();
        employeeTypeLink = new EmployeeTypeLink();
        transportManagerDataMapper = new TransportManagerDataMapper();
    }

    public Employee get(String id) throws SQLException {
        Set<JobTitles>  type= employeeTypeLink.get(id);
        if (type.isEmpty())
            return null;

        switch (new ArrayList<>(type).get(0)){
            case Carrier:
                return carrierDataMapper.get(id);

            case Cashier:
                return cashierDataMapper.get(id);
            case HR_Manager:
                return hR_managerDataMapper.get(id);

            case Logistics_Manager:
                return logistics_managerDataMapper.get(id);

            case Sorter:
                return sorterDataMapper.get(id);

            case Storekeeper:
                return storekeeperDataMapper.get(id);

            case Transport_Manager:
                return transportManagerDataMapper.get(id);

            default:
                throw new IllegalArgumentException("Illegal employee type saved in the db");
        }
    }
    public void save(Employee toSave) throws SQLException{
        toSave.save(this);
    }

    public void save(Carrier toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),JobTitles.Carrier);
        carrierDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Cashier toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),JobTitles.Cashier);
        cashierDataMapper.save(toSave.getId(),toSave);
    }

    public void save(HR_Manager toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),JobTitles.HR_Manager);
        hR_managerDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Logistics_Manager toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),JobTitles.Logistics_Manager);
        logistics_managerDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Sorter toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),JobTitles.Sorter);
        sorterDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Storekeeper toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),JobTitles.Storekeeper);
        storekeeperDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Transport_Manager toSave) throws SQLException {
        employeeTypeLink.add(toSave.getId(),JobTitles.Transport_Manager);
        transportManagerDataMapper.save(toSave.getId(),toSave);
    }

    public void update(Employee employee) throws SQLException {
        employee.update(this);
    }

    public void update(Carrier employee) throws SQLException {
        carrierDataMapper.insert(employee);
    }
    public void update(Cashier employee) throws SQLException {
        cashierDataMapper.insert(employee);
    }
    public void update(HR_Manager employee) throws SQLException {
        hR_managerDataMapper.insert(employee);
    }
    public void update(Logistics_Manager employee) throws SQLException {
        logistics_managerDataMapper.insert(employee);
    }
    public void update(Sorter employee) throws SQLException {
        sorterDataMapper.insert(employee);
    }
    public void update(Storekeeper employee) throws SQLException {
        storekeeperDataMapper.insert(employee);
    }
    public void update(Transport_Manager employee) throws SQLException {
        transportManagerDataMapper.insert(employee);
    }

}
