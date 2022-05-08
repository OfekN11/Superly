package Domain.Business.Controllers;

import Domain.Business.BusinessShiftFactory;
import Domain.Business.Objects.*;
import Domain.DAL.Controllers.DShiftController;
import Domain.DAL.Objects.DShift;
import Globals.Enums.ShiftTypes;
import Globals.Pair;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ShiftController {
    private static final String shiftNotFoundErrorMsg = "This shift Could not be found";
    private static final String SHIFT_ALREADY_EXIST = "The shift in date: %s type: %s already exist";
    private static final String SHIFT_DOES_NOT_EXIST = "There is no shift in date: %s type: %s";
    // properties
    //Map<String, EveningShift> nightShifts; // string representing the date
    //Map<String, MorningShift> dayShifts;// string representing the date
    private final DShiftController dShiftController = new DShiftController();
    private final EmployeeController employeeController = new EmployeeController();

    private final Map<LocalDate, Map<ShiftTypes,Shift>> shifts = new HashMap<>();
    private final BusinessShiftFactory shiftFactory = new BusinessShiftFactory();

    public void loadData() {
        Set<DShift> dShifts = dShiftController.loadData();
        for(DShift dShift : dShifts) {
            if (!shifts.containsKey(dShift.getWorkday()))
                shifts.put(dShift.getWorkday(),new HashMap<>());
            shifts.get(dShift.getWorkday()).put(dShift.getType(),shiftFactory.createBusinessShift(dShift));
        }
    }

    public void deleteData() {
    }

    public Shift getShift(LocalDate workday, ShiftTypes type) throws Exception {
        validateWorkday(workday);
        return shifts.get(workday).get(type);
    }

    public void editShiftManagerID(LocalDate workday, ShiftTypes type, String managerID) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setShiftManagerId(managerID);
    }

    public void editShiftCarrierCount(LocalDate workday, ShiftTypes type, int carrierCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCarrierCount(carrierCount);
    }

    public void editShiftCashierCount(LocalDate workday, ShiftTypes type, int cashierCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCashierCount(cashierCount);
    }

    public void editShiftStorekeeperCount(LocalDate workday, ShiftTypes type, int storekeeperCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setStorekeeperCount(storekeeperCount);
    }

    public void editShiftSorterCount(LocalDate workday, ShiftTypes type, int sorterCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setSorterCount(sorterCount);
    }

    public void editShiftHR_ManagerCount(LocalDate workday, ShiftTypes type, int hr_managerCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setHr_managersCount(hr_managerCount);
    }

    public void editShiftLogistics_ManagerCount(LocalDate workday, ShiftTypes type, int logistics_managerCount) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setLogistics_managersCount(logistics_managerCount);
    }

    public void editShiftCarrierIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCarrierIDs(ids);
    }

    public void editShiftCashierIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setCashierIDs(ids);
    }

    public void editShiftStorekeeperIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setStorekeeperIDs(ids);
    }

    public void editShiftSorterIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setSorterIDs(ids);
    }

    public void editShiftHR_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setHr_managerIDs(ids);
    }

    public void editShiftLogistics_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        validateWorkday(workday);
        shifts.get(workday).get(type).setLogistics_managerIDs(ids);
    }

    private void validateWorkday(LocalDate workday) throws Exception{
        if (!shifts.containsKey(workday))
            throw new Exception(String.format("The workday %s is not registered!", new SimpleDateFormat("dd-MM-yyyy").format(workday)));
    }

    public Set<Shift> getShiftsBetween(LocalDate start, LocalDate end) {
        Set<Shift> shifts = new HashSet<>();
        for (LocalDate date : getDatesBetween(start, end)) {
            if(this.shifts.containsKey(date))
                for (ShiftTypes type : ShiftTypes.values())
                    if (this.shifts.get(date).containsKey(type))
                        shifts.add(this.shifts.get(date).get(type));
        }
        return shifts;
    }

    public Set<Shift> getEmployeeShiftsBetween(String id, LocalDate start, LocalDate end) {
        return getShiftsBetween(start,end).stream()
                .filter((shift) -> shift.isIdInclude(id)).collect(Collectors.toSet());
    }

    /**
     * Returns all stored dates between start and end (inclusive)
     *
     * @param start first date to start filtering from
     * @param end last date to include
     * @return all stored dates between start and end (inclusive)
     */
    private Set<LocalDate> getDatesBetween(LocalDate start, LocalDate end){
        return shifts.keySet().stream()
                .filter((d) -> d.isAfter(start) || d.isEqual(start))
                .filter((d) -> d.isBefore(end) || d.isEqual(end)).collect(Collectors.toSet());
    }

    public void createShift(LocalDate date, ShiftTypes type, String managerId, int carrierCount,int cashierCount, int storekeeperCount,int sorterCount, int hr_managerCount, int logistics_managerCount) throws Exception {
        if (isShiftExist(date,type))
            throw new IllegalArgumentException(String.format(SHIFT_ALREADY_EXIST,date.toString(),type.toString()));
        if (!shifts.containsKey(date))
            shifts.put(date,new HashMap<>());
        switch (type){
            case Evening:
                shifts.get(date).put(ShiftTypes.Evening,new EveningShift(date,managerId,carrierCount,cashierCount,storekeeperCount,sorterCount,hr_managerCount,logistics_managerCount));
                break;
            case Morning:
                shifts.get(date).put(ShiftTypes.Morning,new MorningShift(date,managerId,carrierCount,cashierCount,storekeeperCount,sorterCount,hr_managerCount,logistics_managerCount));
        }
    }

    private boolean isShiftExist(LocalDate date, ShiftTypes type) {
        return shifts.containsKey(date) && shifts.get(date).containsKey(type) && shifts.get(date).get(type)!=null ;
    }

    public void removeShift(LocalDate date, ShiftTypes type) {
        if (!isShiftExist(date,type))
            throw new IllegalArgumentException(String.format(SHIFT_DOES_NOT_EXIST,date.toString(),type.toString()));
        shifts.get(date).remove(type);
    }

    public String getEmployeeWorkDetailsForCurrentMonth(String id) {
        Pair<LocalDate,LocalDate> firstLastPair = getMonthDatesEdges();
        String workDetails = "";
        Set<LocalDate> dates = getDatesBetween(firstLastPair.getLeft(), firstLastPair.getRight());
        for (ShiftTypes type: ShiftTypes.values()) {
            Set<Shift> shifts = new HashSet<>();
            for (LocalDate date : dates)
                shifts.add(this.shifts.get(date).get(type));
            workDetails = workDetails + type + " - " + shifts.stream().filter(shift -> shift.isIdInclude(id)).count() + ", ";
        }
        return "Shifts during " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMM")) + ": " +
                workDetails;
    }

    private Pair<LocalDate,LocalDate> getMonthDatesEdges() {
        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));
        return new Pair<>(start,end);
    }
}
