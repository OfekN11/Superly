package Domain.DAL.Objects;

import Domain.DAL.Abstract.DTO;
import Domain.DAL.Controllers.DEmployeeShiftController;

import java.util.Date;
import java.util.Set;

public abstract class DShift extends DTO {
    // properties
    private Date workday;
    private int shiftManagerId;
    private DEmployeeShiftController dEmployeeShiftController;

    private int carrierCount;
    private int cashierCount;
    private int storekeeperCount;
    private int sorterCount;
    private int hr_managersCount;
    private int logistics_managersCount;
    private Set<Integer> employeesId;

    // constructor


    public DShift(Date workday,int shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount,Set<Integer> employeesId) {
        super(0, "tableName"); //no id to shift
        this.workday = workday;
        this.shiftManagerId = shiftManagerId;
        this.dEmployeeShiftController = new DEmployeeShiftController();
        this.carrierCount = carrierCount;
        this.cashierCount = cashierCount;
        this.storekeeperCount = storekeeperCount;
        this.sorterCount = sorterCount;
        this.hr_managersCount = hr_managersCount;
        this.logistics_managersCount = logistics_managersCount;
        this.employeesId = employeesId;
    }
    public DShift(Date workday,int shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount) {
        super(0, "tableName"); //no id to shift
        this.workday = workday;
        this.shiftManagerId = shiftManagerId;
        this.dEmployeeShiftController = new DEmployeeShiftController();
        this.carrierCount = carrierCount;
        this.cashierCount = cashierCount;
        this.storekeeperCount = storekeeperCount;
        this.sorterCount = sorterCount;
        this.hr_managersCount = hr_managersCount;
        this.logistics_managersCount = logistics_managersCount;
    }

    public void setEmployeesId(Set<Integer> employeesId) {
        if (isPersist())
            dEmployeeShiftController.replaceSetOfEmployees(this,employeesId);
        this.employeesId = employeesId;
    }

    public void setCarrierCount(int carrierCount) {
        update("CarrierCount",carrierCount);
        this.carrierCount = carrierCount;
    }

    public void setCashierCount(int cashierCount) {
        update("CashierCount",cashierCount);
        this.cashierCount = cashierCount;
    }

    public void setStorekeeperCount(int storekeeperCount) {
        update("StoreKeeperCount",storekeeperCount);
        this.storekeeperCount = storekeeperCount;
    }

    public void setSorterCount(int sorterCount) {
        update("SorterCount",sorterCount);
        this.sorterCount = sorterCount;
    }

    public void setHr_ManagersCount(int hr_managersCount) {
        update("Hr_ManagersCount",hr_managersCount);
        this.hr_managersCount = hr_managersCount;
    }

    public void setLogistics_ManagersCount(int logistics_managersCount) {
        update("Logistics_ManagersCount",logistics_managersCount);
        this.logistics_managersCount = logistics_managersCount;
    }

    public void setWorkday(Date workday) {
        update("Workday",workday);
        this.workday = workday;
    }

    public void setShiftManagerId(int shiftManagerId) {
        update("ShiftManagerId",shiftManagerId);
        this.shiftManagerId = shiftManagerId;
    }

    public int getCarrierCount() {
        return carrierCount;
    }

    public int getCashierCount() {
        return cashierCount;
    }

    public int getStorekeeperCount() {
        return storekeeperCount;
    }

    public int getSorterCount() {
        return sorterCount;
    }

    public int getHr_managersCount() {
        return hr_managersCount;
    }

    public int getLogistics_managersCount() {
        return logistics_managersCount;
    }

    public Date getWorkday() {
        return workday;
    }

    public Set<Integer> getEmployeesId() {
        return employeesId;
    }

    public int getShiftManagerId() {
        return shiftManagerId;
    }

    public void addEmployee(int employeeId){
        if (isPersist()) {
            dEmployeeShiftController.addEmployee(this, employeeId);
        }
        employeesId.add(employeeId);
    }

    public void removeEmployee(int employeeId){
        if (isPersist()) {
            dEmployeeShiftController.removeEmployee(this, employeeId);
        }
        employeesId.remove(employeeId);
    }


    public void delete(){
        //need to overwrite the delete because there is no id
    }
}
