package Domain.Service.Services;

import Domain.Business.Controllers.ShiftController;
import Domain.Service.Objects.*;
import Domain.Service.ServiceEmployeeFactory;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class ShiftService {
    private final ShiftController controller = new ShiftController();
    private final ServiceShiftFactory shiftFactory = new ServiceShiftFactory();
    private final ServiceEmployeeFactory employeeFactory = new ServiceEmployeeFactory();

    //CREATE

    public Result<Object> createShift(LocalDate date, ShiftTypes type, String managerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount, int transport_managerCount) {
        try {
            controller.createShift(date, type, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount, logistics_managerCount, transport_managerCount);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    //READ

    public Result<Shift> getShift(LocalDate workday, ShiftTypes type) {
        try {
            return Result.makeOk(shiftFactory.createServiceShift(controller.getShift(workday, type)));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Shift>> getShiftsBetween(LocalDate start, LocalDate end) {
        try {
            return Result.makeOk(controller.getShiftsBetween(start, end).stream().map(shiftFactory::createServiceShift).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    //UPDATE

    public Result<Object> editShiftManagerID(LocalDate workday, ShiftTypes type, String managerID){
        try {
            controller.editShiftManagerID(workday, type, managerID);
        }
        catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCarrierCount(LocalDate workday, ShiftTypes type, int newCount){
        try {
            controller.editShiftCarrierCount(workday,type, newCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCashierCount(LocalDate workday, ShiftTypes type, int newCount){
        try {
            controller.editShiftCashierCount(workday,type, newCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftStorekeeperCount(LocalDate workday, ShiftTypes type, int newCount){
        try {
            controller.editShiftStorekeeperCount(workday,type, newCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftSorterCount(LocalDate workday, ShiftTypes type, int newCount){
        try {
            controller.editShiftSorterCount(workday,type, newCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftHR_ManagerCount(LocalDate workday, ShiftTypes type, int newCount){
        try {
            controller.editShiftHR_ManagerCount(workday,type, newCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftLogistics_ManagerCount(LocalDate workday, ShiftTypes type, int newCount){
        try {
            controller.editShiftLogistics_ManagerCount(workday,type, newCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftTransport_ManagerCount(LocalDate workday, ShiftTypes type, int newCount){
        try {
            controller.editShiftTransport_ManagerCount(workday,type, newCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCarrierIDs(LocalDate workday, ShiftTypes type, Set<String> ids) {
        try {
            controller.editShiftCarrierIDs(workday, type, ids);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCashierIDs(LocalDate workday, ShiftTypes type, Set<String> ids) {
        try {
            controller.editShiftCashierIDs(workday, type, ids);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftStorekeeperIDs(LocalDate workday, ShiftTypes type, Set<String> ids) {
        try {
            controller.editShiftStorekeeperIDs(workday, type, ids);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftSorterIDs(LocalDate workday, ShiftTypes type, Set<String> ids) {
        try {
            controller.editShiftSorterIDs(workday, type, ids);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftHR_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids) {
        try {
            controller.editShiftHR_ManagerIDs(workday, type, ids);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftLogistics_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids) {
        try {
            controller.editShiftLogistics_ManagerIDs(workday, type, ids);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftTransport_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids) {
        try {
            controller.editShiftTransport_ManagerIDs(workday, type, ids);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    //REMOVE

    public Result<Object> removeShift(LocalDate date, ShiftTypes type) {
        try {
            controller.removeShift(date, type);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Calls for employee data deletion
     *
     * @return Result detailing success of operation
     */
    public Result<Object> deleteData() {
        try {
            controller.deleteData();
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    //MISC

    public Result<Set<Shift>> getEmployeeShiftsBetween(String id, LocalDate start, LocalDate end) {
        try {
            return Result.makeOk(controller.getEmployeeShiftsBetween(id, start, end).stream().map(shiftFactory::createServiceShift).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<String> getEmployeeWorkDetailsForCurrentMonth(String id) {
        try {
            return Result.makeOk(controller.getEmployeeWorkDetailsForCurrentMonth(id));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Carrier>> getShiftCarriers(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getShiftCarriers(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Cashier>> getShiftCashiers(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getShiftCashiers(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Sorter>> getShiftSorters(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getShiftSorters(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Storekeeper>> getShiftStorekeepers(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getShiftStorekeepers(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<HR_Manager>> getShiftHR_Managers(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getShiftHR_Managers(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Logistics_Manager>> getShiftLogistics_Managers(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getShiftLogistics_Managers(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Transport_Manager>> getShiftTransport_Managers(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getShiftTransport_Managers(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Employee>> getShiftEmployees(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getShiftEmployees(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }
}