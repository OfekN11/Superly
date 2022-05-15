package Domain.DAL.Controllers.EmployeeMappers;
import Domain.Business.Objects.*;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeTypeLink;
import Globals.Enums.JobTitles;
import java.util.*;

public class EmployeeDataMapper  {

    // properties
    private final CarrierDataMapper carrierDataMapper;
    private final CashierDataMapper cashierDataMapper;
    private final HR_ManagerDataMapper hR_managerDataMapper;
    private final Logistics_ManagerDataMapper logistics_managerDataMapper;
    private final SorterDataMapper sorterDataMapper;
    private final StorekeeperDataMapper storekeeperDataMapper;
    private final TransportManagerDataMapper transportManagerDataMapper;
    private final EmployeeTypeLink employeeTypeLink;


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

    public Employee get(String id) throws Exception {
        Set<JobTitles> type = employeeTypeLink.get(id);
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
    public void save(Employee toSave) throws Exception{
        toSave.save(this);
    }

    public void save(Carrier toSave) throws Exception {
        employeeTypeLink.add(toSave.getId(),JobTitles.Carrier);
        carrierDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Cashier toSave) throws Exception {
        employeeTypeLink.add(toSave.getId(),JobTitles.Cashier);
        cashierDataMapper.save(toSave.getId(),toSave);
    }

    public void save(HR_Manager toSave) throws Exception {
        employeeTypeLink.add(toSave.getId(),JobTitles.HR_Manager);
        hR_managerDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Logistics_Manager toSave) throws Exception {
        employeeTypeLink.add(toSave.getId(),JobTitles.Logistics_Manager);
        logistics_managerDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Sorter toSave) throws Exception {
        employeeTypeLink.add(toSave.getId(),JobTitles.Sorter);
        sorterDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Storekeeper toSave) throws Exception {
        employeeTypeLink.add(toSave.getId(),JobTitles.Storekeeper);
        storekeeperDataMapper.save(toSave.getId(),toSave);
    }

    public void save(Transport_Manager toSave) throws Exception {
        employeeTypeLink.add(toSave.getId(),JobTitles.Transport_Manager);
        transportManagerDataMapper.save(toSave.getId(),toSave);
    }

    public void update(Employee employee) throws Exception {
        employee.update(this);
    }

    public void update(Carrier employee) throws Exception {
        carrierDataMapper.insert(employee);
    }
    public void update(Cashier employee) throws Exception {
        cashierDataMapper.insert(employee);
    }
    public void update(HR_Manager employee) throws Exception {
        hR_managerDataMapper.insert(employee);
    }
    public void update(Logistics_Manager employee) throws Exception {
        logistics_managerDataMapper.insert(employee);
    }
    public void update(Sorter employee) throws Exception {
        sorterDataMapper.insert(employee);
    }
    public void update(Storekeeper employee) throws Exception {
        storekeeperDataMapper.insert(employee);
    }
    public void update(Transport_Manager employee) throws Exception {
        transportManagerDataMapper.insert(employee);
    }

    //TODO
    public Carrier getCarrier(String id) throws Exception {}
    public Cashier getCashier(String id) throws Exception {}
    public Sorter getSorter(String id) throws Exception {}
    public Storekeeper getStorekeeper(String id) throws Exception {}
    public HR_Manager getHR_Manager(String id) throws Exception {}
    public Logistics_Manager getLogistics_Manager(String id) throws Exception {}
    public Transport_Manager getTransport_Manager(String id) throws Exception {}

    //return ALL of type
    public Set<Employee> get() throws Exception {}
    public Set<Carrier> getCarrier() throws Exception {}
    public Set<Cashier> getCashier() throws Exception {}
    public Set<Sorter> getSorter() throws Exception {}
    public Set<Storekeeper> getStorekeeper() throws Exception {}
    public Set<HR_Manager> getHR_Manager() throws Exception {}
    public Set<Logistics_Manager> getLogistics_Manager() throws Exception {}
    public Set<Transport_Manager> getTransport_Manager() throws Exception {}


    //throw Exception if ID doesnt exist
    public void delete(String id) throws Exception{}
}
