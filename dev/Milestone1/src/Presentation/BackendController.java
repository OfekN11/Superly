package Presentation;

import Domain.Service.Objects.*;
import Domain.Service.Services.*;
import Globals.Enums.*;
import Globals.Pair;
import Presentation.Factories.PresentationDocumentFactory;
import Presentation.Objects.Document.*;
import Presentation.Objects.Transport.Transport;
import Presentation.Objects.Transport.TransportOrder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BackendController {
    private final EmployeeService employeeService = new EmployeeService();
    private final ShiftService shiftService = new ShiftService();
    private final TruckService truckService = new TruckService();
    private final DocumentService documentService = new DocumentService();
    private final TransportService transportService = new TransportService();
    private final OrderService orderService = new OrderService();
    private final PresentationDocumentFactory presentationDocumentFactory = new PresentationDocumentFactory();
    ///EMPLOYEES
    //CREATE

    public void addEmployee(JobTitles jobTitle, String id, String name, String bankDetails, Integer salary, LocalDate startingDate, Set<Certifications> certifications) throws Exception {
        Result<Object> result = employeeService.registerEmployee(jobTitle, id, name, bankDetails, salary, startingDate, certifications);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    //READ

    public Employee getEmployee(String id) throws Exception {
        Result<Employee> result = employeeService.getEmployee(id);
        isError(result);
        return result.getValue();
    }

    public Carrier getCarrier(String id) throws Exception {
        Result<Carrier> result = employeeService.getCarrier(id);
        isError(result);
        return result.getValue();
    }

    public Cashier getCashier(String id) throws Exception {
        Result<Cashier> result = employeeService.getCashier(id);
        isError(result);
        return result.getValue();
    }

    public Sorter getSorter(String id) throws Exception {
        Result<Sorter> result = employeeService.getSorter(id);
        isError(result);
        return result.getValue();
    }

    public Storekeeper getStorekeeper(String id) throws Exception {
        Result<Storekeeper> result = employeeService.getStorekeeper(id);
        isError(result);
        return result.getValue();
    }

    public HR_Manager getHR_Manager(String id) throws Exception {
        Result<HR_Manager> result = employeeService.getHR_Manager(id);
        isError(result);
        return result.getValue();
    }

    public Logistics_Manager getLogistics_Manager(String id) throws Exception {
        Result<Logistics_Manager> result = employeeService.getLogistics_Manager(id);
        isError(result);
        return result.getValue();
    }

    public Transport_Manager getTransport_Manager(String id) throws Exception {
        Result<Transport_Manager> result = employeeService.getTransport_Manager(id);
        isError(result);
        return result.getValue();
    }

    public Set<Employee> getEmployees(Set<String> employeeIDs) throws Exception {
        Result<Set<Employee>> result = employeeService.getEmployees(employeeIDs);
        isError(result);
        return result.getValue();
    }

    public Set<Carrier> getCarriers(Set<String> employeeIDs) throws Exception {
        Result<Set<Carrier>> result = employeeService.getCarriers(employeeIDs);
        isError(result);
        return result.getValue();
    }

    public Set<Cashier> getCashiers(Set<String> employeeIDs) throws Exception {
        Result<Set<Cashier>> result = employeeService.getCashiers(employeeIDs);
        isError(result);
        return result.getValue();
    }

    public Set<Sorter> getSorters(Set<String> employeeIDs) throws Exception {
        Result<Set<Sorter>> result = employeeService.getSorters(employeeIDs);
        isError(result);
        return result.getValue();
    }

    public Set<Storekeeper> getStorekeepers(Set<String> employeeIDs) throws Exception {
        Result<Set<Storekeeper>> result = employeeService.getStorekeepers(employeeIDs);
        isError(result);
        return result.getValue();
    }

    public Set<HR_Manager> getHR_Managers(Set<String> employeeIDs) throws Exception {
        Result<Set<HR_Manager>> result = employeeService.getHR_Managers(employeeIDs);
        isError(result);
        return result.getValue();
    }

    public Set<Logistics_Manager> getLogistics_Managers(Set<String> employeeIDs) throws Exception {
        Result<Set<Logistics_Manager>> result = employeeService.getLogistics_Managers(employeeIDs);
        isError(result);
        return result.getValue();
    }

    public Set<Transport_Manager> getTransport_Managers(Set<String> employeeIDs) throws Exception {
        Result<Set<Transport_Manager>> result = employeeService.getTransport_Managers(employeeIDs);
        isError(result);
        return result.getValue();
    }

    public Set<Employee> getAllEmployees() throws Exception {
        Result<Set<Employee>> result = employeeService.getAllEmployees();
        isError(result);
        return result.getValue();
    }

    public Set<Cashier> getAllCashiers() throws Exception {
        Result<Set<Cashier>> result = employeeService.getAllCashiers();
        isError(result);
        return result.getValue();
    }

    public Set<Carrier> getAllCarriers() throws Exception {
        Result<Set<Carrier>> result = employeeService.getAllCarriers();
        isError(result);
        return result.getValue();
    }

    public Set<Storekeeper> getAllStorekeepers() throws Exception {
        Result<Set<Storekeeper>> result = employeeService.getAllStorekeepers();
        isError(result);
        return result.getValue();
    }

    public Set<Sorter> getAllSorters() throws Exception {
        Result<Set<Sorter>> result = employeeService.getAllSorters();
        isError(result);
        return result.getValue();
    }

    public Set<HR_Manager> getAllHR_Managers() throws Exception {
        Result<Set<HR_Manager>> result = employeeService.getAllHR_Managers();
        isError(result);
        return result.getValue();
    }

    public Set<Logistics_Manager> getAllLogistics_Managers() throws Exception {
        Result<Set<Logistics_Manager>> result = employeeService.getAllLogistics_Managers();
        isError(result);
        return result.getValue();
    }

    public Set<Transport_Manager> getAllTransport_Managers() throws Exception {
        Result<Set<Transport_Manager>> result = employeeService.getAllTransport_Managers();
        isError(result);
        return result.getValue();
    }

    //TODO: try to get rid of this

    public String getEmploymentConditionsOf(String id) throws Exception {
        Result<String> result = employeeService.getEmploymentConditionsOf(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    //UPDATE

    public void editEmployeeName(Presentation.Screens.Employee employee, String newName) throws Exception {
        Result<Object> result = employeeService.editEmployeeName(employee.getID(), newName);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editEmployeeBankDetails(Presentation.Screens.Employee employee, String newNankDetails) throws Exception {
        Result<Object> result = employeeService.editEmployeeBankDetails(employee.getID(), newNankDetails);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editEmployeeSalary(Presentation.Screens.Employee employee, int newSalary) throws Exception {
        Result<Object> result = employeeService.editEmployeeSalary(employee.getID(), newSalary);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editEmployeeCertifications(Presentation.Screens.Employee employee, Set<Certifications> newCertifications) throws Exception {
        Result<Object> result = employeeService.editEmployeeCertifications(employee.getID(), newCertifications);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editCarrierLicenses(Presentation.Screens.Carrier carrier, Set<LicenseTypes> newLicenses) throws Exception {
        Result<Object> result = employeeService.editCarrierLicenses(carrier.getID(), newLicenses);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    //DELETE

    public void removeEmployee(String id) throws Exception {
        Result<Object> result = employeeService.removeEmployee(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    //MISC

    public void checkUnusedEmployeeID(String id) throws Exception {
        Result<Object> result = employeeService.checkUnusedEmployeeID(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void validateID(String id) throws Exception {
        Result<Object> result = employeeService.validateID(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void validateIDs(Set<String> ids) throws Exception {
        Result<Object> result = employeeService.validateIDs(ids);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    ///SHIFTS
    //CREATE

    public void createShift(LocalDate date, ShiftTypes type, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount, int transport_managerCount) throws Exception {
        Result<Object> result = shiftService.createShift(date, type, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount, logistics_managerCount, transport_managerCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    //READ

    public Shift getShift(LocalDate date, ShiftTypes type) throws Exception {
        Result<Shift> result = shiftService.getShift(date, type);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public String getEmployeeWorkDetailsForCurrentMonth(String id) throws Exception {
        Result<String> result = shiftService.getEmployeeWorkDetailsForCurrentMonth(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Shift> getShiftsBetween(LocalDate start, LocalDate end) throws Exception {
        Result<Set<Shift>> result = shiftService.getShiftsBetween(start, end);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Shift> getEmployeeShiftsBetween(Presentation.Screens.Employee employee, LocalDate start, LocalDate end) throws Exception {
        Result<Set<Shift>> result = shiftService.getEmployeeShiftsBetween(employee.getID(), start, end);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAvailableShiftManagersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAvailableShiftManagersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public void editShiftTransport_ManagerCount(Presentation.Screens.Shift shift, int transport_managersCount) throws Exception {
        Result<Object> result = shiftService.editShiftTransport_ManagerCount(shift.getDate(), shift.getType(), transport_managersCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public Set<Employee> getAssignedSortersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAssignedSortersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAssignedStorekeepersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAssignedStorekeepersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAssignedCarriersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAssignedCarriersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAssignedCashiersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAssignedCashiersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAssignedHR_ManagersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAssignedHR_ManagersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAssignedLogistics_ManagersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAssignedLogistics_ManagersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAssignedTransports_ManagersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAssignedTransport_ManagersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAvailableSortersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAvailableSortersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAvailableStorekeepersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAvailableStorekeepersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAvailableCarriersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAvailableCarriersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAvailableCashiersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAvailableCashiersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAvailableHR_ManagersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAvailableHR_ManagersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAvailableLogistics_ManagersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAssignedLogistics_ManagersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Employee> getAvailableTransports_ManagersFor(Presentation.Screens.Shift shift) throws Exception {
        Result<Set<Employee>> result = shiftService.getAssignedTransport_ManagersFor(shift.getDate(), shift.getType());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    //UPDATE

    public void registerToConstraint(Presentation.Screens.Employee employee, Shift shift) throws Exception {
        Result<Object> result = shiftService.registerAsAvailable(shift.date, shift.getType(), employee.getID());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void unregisterFromConstraint(Presentation.Screens.Employee employee, Shift shift) throws Exception{
        Result<Object> result = shiftService.unregisterFromAvailable(shift.date, shift.getType(), employee.getID());
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftManagerID(Presentation.Screens.Shift shift, String shiftManagerId) throws Exception {
        validateID(shiftManagerId);
        Result<Object> result = shiftService.editShiftManagerID(shift.getDate(), shift.getType(), shiftManagerId);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftCarrierCount(Presentation.Screens.Shift shift, int newCarrierCount) throws Exception {
        Result<Object> result = shiftService.editShiftCarrierCount(shift.getDate(), shift.getType(), newCarrierCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftCashierCount(Presentation.Screens.Shift shift, int newCashierCount) throws Exception {
        Result<Object> result = shiftService.editShiftCashierCount(shift.getDate(), shift.getType(), newCashierCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftSorterCount(Presentation.Screens.Shift shift, int newSorterCount) throws Exception {
        Result<Object> result = shiftService.editShiftSorterCount(shift.getDate(), shift.getType(), newSorterCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftStorekeeperCount(Presentation.Screens.Shift shift, int newStorekeeperCount) throws Exception {
        Result<Object> result = shiftService.editShiftStorekeeperCount(shift.getDate(), shift.getType(), newStorekeeperCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftHR_ManagerCount(Presentation.Screens.Shift shift, int newHr_managersCount) throws Exception {
        Result<Object> result = shiftService.editShiftHR_ManagerCount(shift.getDate(), shift.getType(), newHr_managersCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftLogistics_ManagerCount(Presentation.Screens.Shift shift, int newLogistics_managersCount) throws Exception {
        Result<Object> result = shiftService.editShiftLogistics_ManagerCount(shift.getDate(), shift.getType(), newLogistics_managersCount);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftCarrierIDs(Presentation.Screens.Shift shift, Set<String> newCarrierIDs) throws Exception {
        validateIDs(newCarrierIDs);
        Result<Object> result = shiftService.editShiftCarrierIDs(shift.getDate(), shift.getType(), newCarrierIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftCashierIDs(Presentation.Screens.Shift shift, Set<String> newCashierIDs) throws Exception {
        validateIDs(newCashierIDs);
        Result<Object> result = shiftService.editShiftCashierIDs(shift.getDate(), shift.getType(), newCashierIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftStorekeeperIDs(Presentation.Screens.Shift shift, Set<String> newStorekeeperIDs) throws Exception {
        validateIDs(newStorekeeperIDs);
        Result<Object> result = shiftService.editShiftStorekeeperIDs(shift.getDate(), shift.getType(), newStorekeeperIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftSorterIDs(Presentation.Screens.Shift shift, Set<String> newSorterIDs) throws Exception {
        validateIDs(newSorterIDs);
        Result<Object> result = shiftService.editShiftSorterIDs(shift.getDate(), shift.getType(), newSorterIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftHR_ManagerIDs(Presentation.Screens.Shift shift, Set<String> newHr_managerIDs) throws Exception {
        validateIDs(newHr_managerIDs);
        Result<Object> result = shiftService.editShiftHR_ManagerIDs(shift.getDate(), shift.getType(), newHr_managerIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftLogistics_ManagerIDs(Presentation.Screens.Shift shift, Set<String> newLogistics_managerIDs) throws Exception {
        validateIDs(newLogistics_managerIDs);
        Result<Object> result = shiftService.editShiftLogistics_ManagerIDs(shift.getDate(), shift.getType(), newLogistics_managerIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    public void editShiftTransport_ManagerIDs(Presentation.Screens.Shift shift, Set<String> newTransport_managersIDs) throws Exception {
        validateIDs(newTransport_managersIDs);
        Result<Object> result = shiftService.editShiftTransport_ManagerIDs(shift.getDate(), shift.getType(), newTransport_managersIDs);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    //DELETE

    public void removeShift(LocalDate date, ShiftTypes type) throws Exception {
        Result<Object> result = shiftService.removeShift(date, type);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    //MISC


    //private
    private static void isError(Result result) throws Exception {
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    //
    private void throwIfError(Result result) throws Exception {
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
    }

    //Truck:
    public void addTruck(int licenseNumber, TruckModel truckModel, int netWeight, int maxCapacityWeight) throws Exception {
        Result result =  truckService.addTruck(licenseNumber, truckModel, netWeight, maxCapacityWeight);
        throwIfError(result);
    }

    public void removeTruck(int licenseNumber) throws Exception {
        Result result =  truckService.removeTruck(licenseNumber);
        throwIfError(result);
    }

    //Document:
    public TransportDocument getTransportDocument(int tdSN) throws Exception {
        Result<Domain.Service.Objects.Document.TransportDocument> result = documentService.getTransportDocument(tdSN);
        throwIfError(result);
        return presentationDocumentFactory.createPresentationDocument(result.getValue());
    }

    public DestinationDocument getDestinationDocument(int ddSN) throws Exception {
        Result<Domain.Service.Objects.Document.DestinationDocument> result = documentService.getTransportDocument(ddSN);
        throwIfError(result);
        return presentationDocumentFactory.createPresentationDocument(result.getValue());
    }

    //Transport Order
    public void addTransportOrder(int srcID, int dstID, HashMap<Integer, Integer> productList) throws Exception {
        Result result = orderService.addOrder(srcID, dstID, productList);
        throwIfError(result);
    }

    //Transport
    private Set<Presentation.Objects.Transport.Transport> toPLTransports(Set<Domain.Service.Objects.Transport> transports)
    {
        Set<Presentation.Objects.Transport.Transport> transportList = new HashSet<>();
        for (Domain.Service.Objects.Transport transport: transports) {
            transportList.add(new Presentation.Objects.Transport.Transport(transport));
        }
        return transportList;
    }
    public Set<Transport> getCompleteTransports() throws Exception {
        Result<Set<Domain.Service.Objects.Transport>> result = transportService.getCompletedTransport();
        throwIfError(result);
        return toPLTransports(result.getValue());
    }

    public Set<Transport> getInProgressTransports() throws Exception {
        Result<Set<Domain.Service.Objects.Transport>> result = transportService.getInProgressTransports();
        throwIfError(result);
        return toPLTransports(result.getValue());
    }
    private Set<Presentation.Objects.Transport.TransportOrder> toPLTransportOrder(Set<Domain.Service.Objects.TransportOrder> orders)
    {
        Set<Presentation.Objects.Transport.TransportOrder> transportList = new HashSet<>();
        for (Domain.Service.Objects.TransportOrder order: orders) {
            transportList.add(new Presentation.Objects.Transport.TransportOrder(order));
        }
        return transportList;
    }
    public Set<Transport> getPendingTransports() throws Exception {
        Result<Set<Domain.Service.Objects.Transport>> result = transportService.getPaddingTransport();
        throwIfError(result);
        return toPLTransports(result.getValue());
    }

    public void addOrderToTransport(int transportID, int orderID) throws Exception {
        Result result = transportService.addOrderToTransport(transportID, orderID);
        throwIfError(result);
    }

    public void createNewTransport(Pair<LocalDate, ShiftTypes> localDateShiftTypesPair) throws Exception {
        Result result = transportService.createTransport(localDateShiftTypesPair);
        throwIfError(result);
    }

    public Set<Employee> getAvailableEmployeesFor(LocalDate date, ShiftTypes type) throws Exception {
        Result<Set<Employee>> result = shiftService.getAvailableEmployeesFor(date, type);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<Shift> getEmployeeConstraintsBetween(Presentation.Screens.Employee employee, LocalDate start, LocalDate end) throws Exception {
        Result<Set<Shift>> result = shiftService.getEmployeeConstraintsBetween(employee.getID(), start, end);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Set<TransportOrder> getPendingOrders() throws Exception {
        Result<Set<Domain.Service.Objects.TransportOrder>> result = orderService.getPendingOrders();
        throwIfError(result);
        return toPLTransportOrder(result.getValue());
    }

    public void startTransport(int transportID) throws Exception {
        Result result = transportService.startTransport(transportID);
        throwIfError(result);
    }

    public void placeCarrier(int transportID, int carrierID) throws Exception {
        Result result = transportService.placeDriver(transportID, Integer.toString(carrierID));
        throwIfError(result);
    }

    public void placeTruck(int transportID, int truckLN) throws Exception {
        Result result = transportService.placeTruck(transportID, truckLN);
        throwIfError(result);
    }

    public Set<Shift> getIncompleteShiftsBetween(LocalDate start, LocalDate end) throws Exception {
        Result<Set<Shift>> result = shiftService.getIncompleteShiftsBetween(start, end);
        throwIfError(result);
        return result.getValue();
    }

    public void advanceSite(int transportSN, int siteID) throws Exception {
        Result result = transportService.advanceSite(transportSN, siteID);
        throwIfError(result);
    }
}