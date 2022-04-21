package Domain.Business.Objects;
import Domain.DAL.Objects.DShift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Shift {
    private static final String employeeAlreadyInShiftErrorMsg = "this employee already in this shift";
    private static final String employeeNotInShiftErrorMsg = "Employee %o was not in the shift";
    // properties
    private Date date;
    private Set<Employee> employees;
    private int shiftManagerId;
    private DShift dShift; // represent of this object in the DAL
    private int carrierCount;
    private int cashierCount;
    private int hr_managersCount;
    private int logistics_managersCount;
    private int storekeeperCount;
    private int sorterCount;

    private Set<Integer> carrierIDs;
    private Set<Integer> cashierIDs;
    private Set<Integer> hr_managerIDs;
    private Set<Integer> logistics_managerIDs;
    private Set<Integer> storekeeperIDs;
    private Set<Integer> sorterIDs;

    // constructors
    public Shift(Date date,Cashier shiftManager) {
        this.employees = new HashSet<>();
        this.date = date;
        this.employees.add(shiftManager);
        this.shiftManagerId =shiftManager.getId();
        Set<Integer> employeesId = new HashSet<>();
        employeesId.add(shiftManagerId);
        this.dShift = new DShift(date,getType().toString(),employeesId,shiftManager.getId());
    }

    public Shift(DShift dShift, Set<Employee> employees) {
        this.dShift = dShift;
        this.date = dShift.getDate();
        this.employees =employees;
        this.shiftManagerId = dShift.getShiftManagerId();
    }

    public void AddEmployee(Employee employee){
        if(employees.contains(employee))
            throw new RuntimeException(employeeAlreadyInShiftErrorMsg);
        employees.add(employee);
    }

    public void replaceShiftManager(Cashier newManager){
        if(employees.contains(newManager))
            shiftManagerId = newManager.getId();
    }

    public static String getEmployeeAlreadyInShiftErrorMsg() {
        return employeeAlreadyInShiftErrorMsg;
    }

    public Date getDate() {
        return date;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public int getShiftManagerId() {
        return shiftManagerId;
    }

    public DShift getdShift() {
        return dShift;
    }

    public abstract ShiftTypes getType();

    public void removeEmployee(Employee employee){
        if (!employees.remove(employee))
            throw new RuntimeException(String.format(employeeNotInShiftErrorMsg,employee.getId()));
    }

    public int getCarrierCount() {
        return carrierCount;
    }

    public void setCarrierCount(int carrierCount) {
        this.carrierCount = carrierCount;
    }

    public int getCashierCount() {
        return cashierCount;
    }

    public void setCashierCount(int cashierCount) {
        this.cashierCount = cashierCount;
    }

    public int getHr_managersCount() {
        return hr_managersCount;
    }

    public void setHr_managersCount(int hr_managersCount) {
        this.hr_managersCount = hr_managersCount;
    }

    public int getLogistics_managersCount() {
        return logistics_managersCount;
    }

    public void setLogistics_managersCount(int logistics_managersCount) {
        this.logistics_managersCount = logistics_managersCount;
    }

    public int getStorekeeperCount() {
        return storekeeperCount;
    }

    public void setStorekeeperCount(int storekeeperCount) {
        this.storekeeperCount = storekeeperCount;
    }

    public int getSorterCount() {
        return sorterCount;
    }

    public void setSorterCount(int sorterCount) {
        this.sorterCount = sorterCount;
    }

    public Set<Integer> getCarrierIDs() {
        return carrierIDs;
    }

    public void setCarrierIDs(Set<Integer> carrierIDs) {
        this.carrierIDs = new HashSet<>(carrierIDs);
    }

    public Set<Integer> getCashierIDs() {
        return cashierIDs;
    }

    public void setCashierIDs(Set<Integer> cashierIDs) {
        this.cashierIDs = new HashSet<>(cashierIDs);
    }

    public Set<Integer> getHr_managerIDs() {
        return hr_managerIDs;
    }

    public void setHr_managerIDs(Set<Integer> hr_managerIDs) {
        this.hr_managerIDs = new HashSet<>(hr_managerIDs);
    }

    public Set<Integer> getLogistics_managerIDs() {
        return logistics_managerIDs;
    }

    public void setLogistics_managerIDs(Set<Integer> logistics_managerIDs) {
        this.logistics_managerIDs = new HashSet<>(logistics_managerIDs);
    }

    public Set<Integer> getStorekeeperIDs() {
        return storekeeperIDs;
    }

    public void setStorekeeperIDs(Set<Integer> storekeeperIDs) {
        this.storekeeperIDs = new HashSet<>(storekeeperIDs);
    }

    public Set<Integer> getSorterIDs() {
        return sorterIDs;
    }

    public void setSorterIDs(Set<Integer> sorterIDs) {
        this.sorterIDs = new HashSet<>(sorterIDs);
    }

    public abstract Domain.Service.Objects.Shift accept(ServiceShiftFactory factory);
}
