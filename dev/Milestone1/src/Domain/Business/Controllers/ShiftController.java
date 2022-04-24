package Domain.Business.Controllers;

import Domain.Business.BusinessShiftFactory;
import Domain.Business.Objects.*;
import Domain.DAL.Controllers.DShiftController;
import Domain.DAL.Objects.DShift;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;

import java.text.SimpleDateFormat;
import java.util.*;

public class ShiftController {
    private static final String shiftNotFoundErrorMsg = "This shift Could not be found";

    // properties
    //Map<String, EveningShift> nightShifts; // string representing the date
    //Map<String, MorningShift> dayShifts;// string representing the date
    private final DShiftController dShiftController = new DShiftController();
    EmployeeController employeeController;

    private final Map<Date,Map<ShiftTypes,Shift>> shifts = new HashMap<>();
    private final BusinessShiftFactory shiftFactory = new BusinessShiftFactory();

    public void loadData() {
        Set<DShift> dShifts = dShiftController.loadData();
        for(DShift dShift : dShifts) {
            Map<Integer, Set<String>> employeeJobMap = employeeController.mapByJob(dShift.getEmployeesId());
            if (!shifts.containsKey(dShift.getWorkday()))
                shifts.put(dShift.getWorkday(),new HashMap<>());
            shifts.get(dShift.getWorkday()).put(dShift.getType(),shiftFactory.createServiceShift(dShift,employeeJobMap.get(JobTitles.Carrier.ordinal()),employeeJobMap.get(JobTitles.Cashier.ordinal()),employeeJobMap.get(JobTitles.Storekeeper.ordinal()),employeeJobMap.get(JobTitles.Sorter.ordinal()),employeeJobMap.get(JobTitles.HR_Manager.ordinal()),employeeJobMap.get(JobTitles.Logistics_Manager.ordinal())));
        }
    }

    public void deleteData() {
    }

    public void registerWorkday(Date workday) throws Exception {
        shifts.put(workday, new HashMap<>());
        for (ShiftTypes type: ShiftTypes.values()){
            switch (type){
                case Morning -> shifts.get(workday).put(type, new MorningShift(workday));
                case Evening -> shifts.get(workday).put(type, new EveningShift(workday));
            }
        }
    }

    public void removeWorkday(Date workday) throws Exception {
        validateWorkday(workday);
        shifts.remove(workday);
    }

    public Shift getShift(Date workday, ShiftTypes type) throws Exception {
        validateWorkday(workday);
        return shifts.get(workday).get(type);
    }

    public void editShiftManagerID(Date workday, ShiftTypes type, String managerID) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setShiftManagerId(managerID);
    }

    public void editShiftCarrierCount(Date workday, ShiftTypes type, int carrierCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCarrierCount(carrierCount);
    }

    public void editShiftCashierCount(Date workday, ShiftTypes type, int cashierCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCashierCount(cashierCount);
    }

    public void editShiftStorekeeperCount(Date workday, ShiftTypes type, int storekeeperCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setStorekeeperCount(storekeeperCount);
    }

    public void editShiftSorterCount(Date workday, ShiftTypes type, int sorterCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setSorterCount(sorterCount);
    }

    public void editShiftHR_ManagerCount(Date workday, ShiftTypes type, int hr_managerCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setHr_managersCount(hr_managerCount);
    }

    public void editShiftLogistics_ManagerCount(Date workday, ShiftTypes type, int logistics_managerCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setLogistics_managersCount(logistics_managerCount);
    }

    public void editShiftCarrierIDs(Date workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCarrierIDs(ids);
    }

    public void editShiftCashierIDs(Date workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCashierIDs(ids);
    }

    public void editShiftStorekeeperIDs(Date workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setStorekeeperIDs(ids);
    }

    public void editShiftSorterIDs(Date workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setSorterIDs(ids);
    }

    public void editShiftHR_ManagerIDs(Date workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setHr_managerIDs(ids);
    }

    public void editShiftLogistics_ManagerIDs(Date workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setLogistics_managerIDs(ids);
    }

    private void validateWorkday(Date workday) throws Exception{
        if (!shifts.containsKey(workday))
            throw new Exception(String.format("The workday %s is not registered!", new SimpleDateFormat("dd-MM-yyyy").format(workday)));
    }
}
