package Domain.Business.Controllers;

import Domain.Business.Objects.*;
import Domain.DAL.Controllers.DShiftController;
import Domain.DAL.Objects.DShift;
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

    public void CreateFakeShifts(){
        for (DShift dShift: dShiftController.createFakeDTOs()) {
            if (dShift.type.equals(ShiftTypes.Morning.toString()))
                dayShifts.put(dShift.date.toString() ,new MorningShift(dShift,employeeController.getEmployees(dShift.workersId)));
            else
                nightShifts.put(dShift.date.toString(),new EveningShift(dShift,employeeController.getEmployees(dShift.workersId)));
        }
    }

    public EveningShift CreateNewNightShift(Date date, Cashier shiftManager){

        EveningShift newShift =new EveningShift(date,shiftManager);
        nightShifts.put(date.toString(),newShift);
        return newShift;
    }

    public MorningShift CreateNewDayShift(Date date, Cashier shiftManager){
        MorningShift newShift =new MorningShift(date,shiftManager);
        dayShifts.put(date.toString(), newShift);
        return newShift;
    }

    public void AddEmployeeToShift(Date date, ShiftTypes shiftType,Employee employee){
        if(shiftType == ShiftTypes.Morning){
            MorningShift morningShift = dayShifts.get(date.toString());
            if(morningShift != null) {
                morningShift.AddEmployee(employee);
                return;
            }
        }else {
            EveningShift eveningShift = nightShifts.get(date.toString());
            if(eveningShift != null) {
                eveningShift.AddEmployee(employee);
                return;
            }
        }

        throw new RuntimeException(shiftNotFoundErrorMsg);
    }

    public void removeEmployeeFromShift(Date shiftDate, ShiftTypes shiftType,int employeeId){
        if(shiftType == ShiftTypes.Morning)
            dayShifts.get(shiftDate.toString()).removeEmployee(employeeController.getEmployee(employeeId)) ;
        else
            nightShifts.get(shiftDate.toString()).removeEmployee(employeeController.getEmployee(employeeId));
    }

    public void loadData() {
    }

    public void deleteData() {
    }

    public void registerWorkday(Date workday) {
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

    public void editShiftCarrierIDs(Date workday, ShiftTypes type, Set<Integer> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCarrierIDs(ids);
    }

    public void editShiftCashierIDs(Date workday, ShiftTypes type, Set<Integer> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCashierIDs(ids);
    }

    public void editShiftStorekeeperIDs(Date workday, ShiftTypes type, Set<Integer> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setStorekeeperIDs(ids);
    }

    public void editShiftSorterIDs(Date workday, ShiftTypes type, Set<Integer> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setSorterIDs(ids);
    }

    public void editShiftHR_ManagerIDs(Date workday, ShiftTypes type, Set<Integer> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setHr_managerIDs(ids);
    }

    public void editShiftLogistics_ManagerIDs(Date workday, ShiftTypes type, Set<Integer> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setLogistics_managerIDs(ids);
    }

    private void validateWorkday(Date workday) throws Exception{
        if (!shifts.containsKey(workday))
            throw new Exception(String.format("The workday %s is not registered!", new SimpleDateFormat("dd-MM-yyyy").format(workday)));
    }
}
