package Domain.Business.Controllers;

import Domain.Business.Objects.*;
import Domain.Business.Objects.Employee.*;
import Domain.Business.Objects.Shift.EveningShift;
import Domain.Business.Objects.Shift.MorningShift;
import Domain.Business.Objects.Shift.Shift;
import Domain.DAL.Controllers.ShiftDataMappers.ShiftDataMapper;
import Globals.Enums.ShiftTypes;
import Globals.Pair;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ShiftController {
    private static final String SHIFT_ALREADY_EXIST = "There already exists a %s for date %s";
    private static final String SHIFT_DOES_NOT_EXIST = "There is no shift in date: %s type: %s";

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    // properties
    private final ShiftDataMapper shiftDataMapper = new ShiftDataMapper();
    private final EmployeeController employeeController = new EmployeeController();

    //CREATE

    public void createShift(LocalDate date, ShiftTypes type, String managerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount, int transport_managerCount) throws Exception {
        if (shiftDataMapper.get(date, type) != null)
            throw new Exception(String.format(SHIFT_ALREADY_EXIST, type, date.format(DATE_FORMAT)));
        switch (type) {
            case Evening:
                shiftDataMapper.save(new EveningShift(date, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount, logistics_managerCount, transport_managerCount));
                break;
            case Morning:
                shiftDataMapper.save(new MorningShift(date, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount, logistics_managerCount, transport_managerCount));
        }
    }

    //READ

    public Shift getShift(LocalDate workday, ShiftTypes type) throws Exception {
        Shift shift = shiftDataMapper.get(workday, type);
        if (shift == null)
            throw new Exception(String.format(SHIFT_DOES_NOT_EXIST, type, workday.format(DATE_FORMAT)));
        return shift;
    }

    public Set<Shift> getShiftsBetween(LocalDate start, LocalDate end) {
        return shiftDataMapper.getBetween(start, end);
    }

    //UPDATE

    public void editShiftManagerID(LocalDate workday, ShiftTypes type, String managerID) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setShiftManagerId(managerID);
        shiftDataMapper.save(shift);
    }

    public void editShiftCarrierCount(LocalDate workday, ShiftTypes type, int carrierCount) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setCarrierCount(carrierCount);
        shiftDataMapper.save(shift);
    }

    public void editShiftCashierCount(LocalDate workday, ShiftTypes type, int cashierCount) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setCashierCount(cashierCount);
        shiftDataMapper.save(shift);
    }

    public void editShiftStorekeeperCount(LocalDate workday, ShiftTypes type, int storekeeperCount) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setStorekeeperCount(storekeeperCount);
        shiftDataMapper.save(shift);
    }

    public void editShiftSorterCount(LocalDate workday, ShiftTypes type, int sorterCount) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setSorterCount(sorterCount);
        shiftDataMapper.save(shift);
    }

    public void editShiftHR_ManagerCount(LocalDate workday, ShiftTypes type, int hr_managerCount) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setHr_managersCount(hr_managerCount);
        shiftDataMapper.save(shift);
    }

    public void editShiftLogistics_ManagerCount(LocalDate workday, ShiftTypes type, int logistics_managerCount) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setLogistics_managersCount(logistics_managerCount);
        shiftDataMapper.save(shift);
    }

    public void editShiftTransport_ManagerCount(LocalDate workday, ShiftTypes type, int transport_managerCount) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setLogistics_managersCount(transport_managerCount);
        shiftDataMapper.save(shift);
    }

    public void editShiftCarrierIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setCarrierIDs(ids);
        shiftDataMapper.save(shift);
    }

    public void editShiftCashierIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setCashierIDs(ids);
        shiftDataMapper.save(shift);
    }

    public void editShiftStorekeeperIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setStorekeeperIDs(ids);
        shiftDataMapper.save(shift);
    }

    public void editShiftSorterIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setSorterIDs(ids);
        shiftDataMapper.save(shift);
    }

    public void editShiftHR_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setHr_managerIDs(ids);
        shiftDataMapper.save(shift);
    }

    public void editShiftLogistics_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setLogistics_managerIDs(ids);
        shiftDataMapper.save(shift);
    }

    public void editShiftTransport_ManagerIDs(LocalDate workday, ShiftTypes type, Set<String> ids) throws Exception {
        Shift shift = getShift(workday, type);
        shift.setLogistics_managerIDs(ids);
        shiftDataMapper.save(shift);
    }

    //DELETE

    public void removeShift(LocalDate date, ShiftTypes type) {
        shiftDataMapper.delete(date, type);
    }

    public void deleteData() {
    }

    //MISC

    public Set<Shift> getEmployeeShiftsBetween(String id, LocalDate start, LocalDate end) {
        return getShiftsBetween(start, end).stream()
                .filter((shift) -> shift.isIdInclude(id)).collect(Collectors.toSet());
    }

    public String getEmployeeWorkDetailsForCurrentMonth(String id) {
        Pair<LocalDate, LocalDate> monthEdges = getMonthDatesEdges();
        return "Shifts during " + LocalDate.now().format(DateTimeFormatter.ofPattern("MMM")) + ": " +
                Arrays.stream(ShiftTypes.values())
                        .map((type) -> type + " - " + shiftDataMapper.getBetween(monthEdges.getLeft(), monthEdges.getLeft(), type))
                        .collect(Collectors.joining(", "));
    }

    public Set<Carrier> getShiftCarriers(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getCarrier(getShift(workday, type).getCarrierIDs());
    }

    public Set<Cashier> getShiftCashiers(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getCashier(getShift(workday, type).getCashierIDs());
    }

    public Set<Sorter> getShiftSorters(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getSorter(getShift(workday, type).getSorterIDs());
    }

    public Set<Storekeeper> getShiftStorekeepers(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getStorekeeper(getShift(workday, type).getStorekeeperIDs());
    }

    public Set<HR_Manager> getShiftHR_Managers(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getHR_Manager(getShift(workday, type).getHr_managerIDs());
    }

    public Set<Logistics_Manager> getShiftLogistics_Managers(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getLogistics_Manager(getShift(workday, type).getLogistics_managerIDs());
    }

    public Set<Transport_Manager> getShiftTransport_Managers(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getTransport_Manager(getShift(workday, type).getTransport_managerIDs());
    }

    public Set<Employee> getShiftEmployees(LocalDate workday, ShiftTypes type) throws Exception {
        Set<Employee> employees = new HashSet<>();
        employees.addAll(getShiftCarriers(workday, type));
        employees.addAll(getShiftCashiers(workday, type));
        employees.addAll(getShiftSorters(workday, type));
        employees.addAll(getShiftStorekeepers(workday, type));
        employees.addAll(getShiftHR_Managers(workday, type));
        employees.addAll(getShiftLogistics_Managers(workday, type));
        employees.addAll(getShiftTransport_Managers(workday, type));
        return employees;
    }

    private Pair<LocalDate, LocalDate> getMonthDatesEdges() {
        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.getMonth().length(initial.isLeapYear()));
        return new Pair<>(start, end);
    }
}