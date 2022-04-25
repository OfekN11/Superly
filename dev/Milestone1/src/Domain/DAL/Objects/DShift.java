package Domain.DAL.Objects;

import Domain.Business.BusinessShiftFactory;
import Domain.Business.Objects.Shift;
import Domain.DAL.Abstract.DTO;
import Domain.DAL.Controllers.DEmployeeShiftController;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DShift extends DTO {
    // properties
    private Date workday;
    private String shiftManagerId;
    private DEmployeeShiftController dEmployeeShiftController;

    private int carrierCount;
    private int cashierCount;
    private int storekeeperCount;
    private int sorterCount;
    private int hr_managersCount;
    private int logistics_managersCount;

    private Set<String> carrierIDs;
    private Set<String> cashierIDs;
    private Set<String> storekeeperIDs;
    private Set<String> sorterIDs;
    private Set<String> hr_managerIDs;
    private Set<String> logistics_managerIDs;

    // constructor

    public DShift(Date workday,ShiftTypes type, String shiftManagerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount) {
        super(workday.toString()+type.toString(), "tableName"); //no id to shift
        this.workday = workday;
        this.shiftManagerId = shiftManagerId;
        this.dEmployeeShiftController = new DEmployeeShiftController();
        this.carrierCount = carrierCount;
        this.cashierCount = cashierCount;
        this.storekeeperCount = storekeeperCount;
        this.sorterCount = sorterCount;
        this.hr_managersCount = hr_managersCount;
        this.logistics_managersCount = logistics_managersCount;

        this.carrierIDs = new HashSet<>();
        this.cashierIDs = new HashSet<>();
        this.storekeeperIDs = new HashSet<>();
        this.sorterIDs = new HashSet<>();
        this.hr_managerIDs = new HashSet<>();
        this.logistics_managerIDs = new HashSet<>();
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

    public void setShiftManagerId(String shiftManagerId) {
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

    public String getShiftManagerId() {
        return shiftManagerId;
    }

    public void delete(){
        //need to overwrite the delete because there is no id
    }

    public void setHr_managersCount(int hr_managersCount) {
        update("Hr_ManagersCount",hr_managersCount);
        this.hr_managersCount = hr_managersCount;
    }

    public void setLogistics_managersCount(int logistics_managersCount) {
        update("Logistics_ManagersCount",logistics_managersCount);
        this.logistics_managersCount = logistics_managersCount;
    }

    public void setCarrierIDs(Set<String> carrierIDs) {
        if (isPersist())
            //do code to save changes
        this.carrierIDs = new HashSet<>(carrierIDs);
    }

    public void setCashierIDs(Set<String> cashierIDs) {
        if (isPersist())
            //do code to save changes
        this.cashierIDs = new HashSet<>(cashierIDs);
    }

    public void setStorekeeperIDs(Set<String> storekeeperIDs) {
        if (isPersist())
            //do code to save changes
        this.storekeeperIDs = new HashSet<>(storekeeperIDs);
    }

    public void setSorterIDs(Set<String> sorterIDs) {
        if (isPersist())
            //do code to save changes
        this.sorterIDs = new HashSet<>(sorterIDs);
    }

    public void setHr_managerIDs(Set<String> hr_managerIDs){
        if (isPersist())
            //do code to save changes
        this.hr_managerIDs = new HashSet<>(hr_managerIDs);
    }

    public void setLogistics_managerIDs(Set<String> logistics_managerIDs) {
        if (isPersist())
            //do code to save changes
        this.logistics_managerIDs = new HashSet<>(logistics_managerIDs);
    }

    public Set<String> getCarrierIDs() {
        return carrierIDs;
    }

    public Set<String> getCashierIDs() {
        return cashierIDs;
    }

    public Set<String> getStorekeeperIDs() {
        return storekeeperIDs;
    }

    public Set<String> getSorterIDs() {
        return sorterIDs;
    }

    public Set<String> getHr_managerIDs() {
        return hr_managerIDs;
    }

    public Set<String> getLogistics_managerIDs() {
        return logistics_managerIDs;
    }

    public abstract Shift accept(BusinessShiftFactory businessShiftFactory);

    public abstract ShiftTypes getType();


}
