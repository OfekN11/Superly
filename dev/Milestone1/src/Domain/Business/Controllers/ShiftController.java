package Domain.Business.Controllers;

import Domain.Business.BusinessShiftFactory;
import Domain.Business.Objects.*;
import Domain.DAL.Controllers.DShiftController;
import Domain.DAL.Objects.DShift;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;
import Globals.Pair;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ShiftController {
    private static final String shiftNotFoundErrorMsg = "This shift Could not be found";
    private static final String SHIFT_ALREADY_EXIST = "The shift in date: %s type: %s already exist";
    private static final String SHIFT_DOES_NOT_EXIST = "There is no shift in date: %s type: %s";
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
                case Morning : shifts.get(workday).put(type, new MorningShift(workday)); break;
                case Evening : shifts.get(workday).put(type, new EveningShift(workday));
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

    public Set<Shift> getShiftsBetween(Date start, Date end) {
        Set<Date> dates = getDatesBetween(start, end);
        Set<Shift> output = new HashSet<>();
        for (Date date : dates)
            if (shifts.containsKey(date))
                output.addAll(shifts.get(date).values());

        return output;
    }

    public Set<Shift> getEmployeeShiftsBetween(String id, Date start, Date end) {
        return getShiftsBetween(start,end).stream().filter((shift -> shift.isIdInclude(id))).collect(Collectors.toSet());
    }

    private Set<Date> getDatesBetween(Date today,Date nextMonth){
        Set<Date> datesInRange = new HashSet<>();
        Calendar calendar = getCalendarWithoutTime(today);
        Calendar endCalendar = getCalendarWithoutTime(nextMonth);
        endCalendar.add(Calendar.DATE, 1);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        return datesInRange;
    }

    private Calendar getCalendarWithoutTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public void createShift(Date date, ShiftTypes type, String managerId, int carrierCount,int cashierCount, int storekeeperCount,int sorterCount, int hr_managerCount, int logistics_managerCount) throws Exception {
        if (isShiftExist(date,type))
            throw new IllegalArgumentException(String.format(SHIFT_ALREADY_EXIST,date.toString(),type.toString()));
        if (!shifts.containsKey(date))
            shifts.put(date,new HashMap<>());
        switch (type){
            case Evening:
                shifts.get(date).put(ShiftTypes.Evening,new EveningShift(date,managerId,carrierCount,cashierCount,storekeeperCount,sorterCount,hr_managerCount,logistics_managerCount));
                break;
            case Morning:
                shifts.get(date).put(ShiftTypes.Evening,new MorningShift(date,managerId,carrierCount,cashierCount,storekeeperCount,sorterCount,hr_managerCount,logistics_managerCount));
        }
    }

    private boolean isShiftExist(Date date, ShiftTypes type) {
        return shifts.containsKey(date) && shifts.get(date).containsKey(type) && shifts.get(date).get(type)!=null ;
    }

    public void removeShift(Date date, ShiftTypes type) {
        if (!isShiftExist(date,type))
            throw new IllegalArgumentException(String.format(SHIFT_DOES_NOT_EXIST,date.toString(),type.toString()));
        shifts.get(date).remove(type);
    }

    public String getEmployeeWorkDetailsForCurrentMonth(String id) {
        Pair<Date,Date> firstLastPair = getMonthDatesEdges();
        Set<Shift> employeeShift = getEmployeeShiftsBetween(id,firstLastPair.getKey(),firstLastPair.getValue());
        String workingDays = "";
        for (Shift shift :employeeShift){
            workingDays = workingDays + shift.printDayAndType() + ", ";
        }
        workingDays = workingDays.substring(0,workingDays.length()-2);
        String month = getThisMonthAsString();
        return "Shifts during " + month +": " + workingDays;
    }

    private String getThisMonthAsString() {
        Calendar cal = Calendar.getInstance();
        return new SimpleDateFormat("MMM").format(cal.getTime());
    }

    private Pair<Date,Date> getMonthDatesEdges() {
        Set<Date> dates = new HashSet<>();        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar. getInstance(). get(Calendar. MONTH));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfTheMonth = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDayOfTheMonth = cal.getTime();

        return new Pair<>(firstDayOfTheMonth,lastDayOfTheMonth);
    }
}
