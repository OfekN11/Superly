package Domain.Service.Services;

import Domain.Business.Controllers.ShiftController;
import Domain.Service.Objects.Result;
import Domain.Service.Objects.Shift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class ShiftService {
    private final ShiftController controller = new ShiftController();
    private final ServiceShiftFactory factory = new ServiceShiftFactory();

    /**
     * Calls for data from persistent to load into the business layer
     *
     * @return Result detailing success of operation
     */
    public Result<Object> loadData(){
        try {
            controller.loadData();
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Calls for employee data deletion
     *
     * @return Result detailing success of operation
     */
    public Result<Object> deleteData(){
        try {
            controller.deleteData();
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Shift> getShift(LocalDate workday, ShiftTypes type){
        try {
            return Result.makeOk(factory.createServiceShift(controller.getShift(workday,type)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Object> editShiftManagerID(LocalDate workday, ShiftTypes type, String managerID){
        try {
            controller.editShiftManagerID(workday, type, managerID);
        }
        catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCarrierCount(LocalDate workday, ShiftTypes type, int carrierCount){
        try {
            controller.editShiftCarrierCount(workday,type, carrierCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCashierCount(LocalDate workday, ShiftTypes type, int cashierCount){
        try {
            controller.editShiftCashierCount(workday,type, cashierCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftStorekeeperCount(LocalDate workday, ShiftTypes type, int storekeeperCount){
        try {
            controller.editShiftStorekeeperCount(workday,type, storekeeperCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftSorterCount(LocalDate workday, ShiftTypes type, int sorterCount){
        try {
            controller.editShiftSorterCount(workday,type, sorterCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftHR_ManagerCount(LocalDate workday, ShiftTypes type, int hr_managerCount){
        try {
            controller.editShiftHR_ManagerCount(workday,type, hr_managerCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftLogistics_ManagerCount(LocalDate workday, ShiftTypes type, int logistics_managerCount){
        try {
            controller.editShiftLogistics_ManagerCount(workday,type, logistics_managerCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCarrierIDs(LocalDate workday, ShiftTypes type, Set<String> ids){
        try {
            controller.editShiftCarrierIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCashierIDs(LocalDate workday, ShiftTypes type, Set<String> ids){
        try {
            controller.editShiftCashierIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftStorekeeperIDs(LocalDate workday, ShiftTypes type, Set<String> ids){
        try {
            controller.editShiftStorekeeperIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftSorterIDs(LocalDate workday, ShiftTypes type, Set<String> ids){
        try {
            controller.editShiftSorterIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftHR_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids){
        try {
            controller.editShiftHR_ManagerIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftLogistics_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids){
        try {
            controller.editShiftLogistics_ManagerIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Set<Shift>> getEmployeeShiftsBetween(String id, LocalDate start, LocalDate end) {
        try {
            return Result.makeOk(controller.getEmployeeShiftsBetween(id, start, end).stream().map(factory::createServiceShift).collect(Collectors.toSet()));
        }catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Shift>> getShiftsBetween(LocalDate start, LocalDate end) {
        try {
            return Result.makeOk(controller.getShiftsBetween(start, end).stream().map(factory::createServiceShift).collect(Collectors.toSet()));
        }catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Object> createShift(LocalDate date, ShiftTypes type, String managerId, int carrierCount,int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount) {
        try {
            controller.createShift(date,type, managerId, carrierCount,cashierCount, storekeeperCount,sorterCount, hr_managerCount, logistics_managerCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<String> getEmployeeWorkDetailsForCurrentMonth(String id) {
        try {
            return Result.makeOk(controller.getEmployeeWorkDetailsForCurrentMonth(id));
        }catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Object> removeShift(LocalDate date, ShiftTypes type) {
        try {
            controller.removeShift(date,type);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }
}