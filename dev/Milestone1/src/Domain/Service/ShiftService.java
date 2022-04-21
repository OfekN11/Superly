package Domain.Service;

import Domain.Business.Controllers.ShiftController;
import Domain.Service.Objects.Result;
import Domain.Service.Objects.Shift;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.Set;

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

    public Result<Object> registerWorkday(Date workday){
        try {
            controller.registerWorkday(workday);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> removeWorkday(Date workday){
        try {
            controller.removeWorkday(workday);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Shift> getShift(Date workday, ShiftTypes type){
        try {
            return Result.makeOk(factory.createServiceShift(controller.getShift(workday,type)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Object> editShiftManagerID(Date workday, ShiftTypes type, int managerID){
        try {
            controller.editShiftManagerID(workday, type, managerID);
        }
        catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCarrierCount(Date workday, ShiftTypes type, int carrierCount){
        try {
            controller.editShiftCarrierCount(workday,type, carrierCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCashierCount(Date workday, ShiftTypes type, int cashierCount){
        try {
            controller.editShiftCashierCount(workday,type, cashierCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftStorekeeperCount(Date workday, ShiftTypes type, int storekeeperCount){
        try {
            controller.editShiftStorekeeperCount(workday,type, storekeeperCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftSorterCount(Date workday, ShiftTypes type, int sorterCount){
        try {
            controller.editShiftSorterCount(workday,type, sorterCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftHR_ManagerCount(Date workday, ShiftTypes type, int hr_managerCount){
        try {
            controller.editShiftHR_ManagerCount(workday,type, hr_managerCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftLogistics_ManagerCount(Date workday, ShiftTypes type, int logistics_managerCount){
        try {
            controller.editShiftLogistics_ManagerCount(workday,type, logistics_managerCount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCarrierIDs(Date workday, ShiftTypes type, Set<Integer> ids){
        try {
            controller.editShiftCarrierIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftCashierIDs(Date workday, ShiftTypes type, Set<Integer> ids){
        try {
            controller.editShiftCashierIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftStorekeeperIDs(Date workday, ShiftTypes type, Set<Integer> ids){
        try {
            controller.editShiftStorekeeperIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftSorterIDs(Date workday, ShiftTypes type, Set<Integer> ids){
        try {
            controller.editShiftSorterIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftHR_ManagerIDs(Date workday, ShiftTypes type, Set<Integer> ids){
        try {
            controller.editShiftHR_ManagerIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> editShiftLogistics_ManagerIDs(Date workday, ShiftTypes type, Set<Integer> ids){
        try {
            controller.editShiftLogistics_ManagerIDs(workday,type, ids);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }
}