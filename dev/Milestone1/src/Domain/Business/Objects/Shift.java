package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;
import Domain.Service.ServiceShiftFactory;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Shift {
    private static final String employeeAlreadyInShiftErrorMsg = "this employee already in this shift";
    private static final String employeeNotInShiftErrorMsg = "Employee %o was not in the shift";

    private static final int MIN_CARRIERS= 1;
    private static final int MIN_CASHIERS = 1;
    private static final int MIN_STOREKEEPERS= 1;
    private static final int MIN_SORTERS = 1;
    private static final int MIN_HR_MANAGERS = 0;
    private static final int MIN_LOGISTICS_MANAGERS = 0;
    private DShift dShift; // represent of this object in the DAL
    // properties
    private Date workday;
    private String shiftManagerId;
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

    // constructors
    public Shift(Date workday, String shiftManagerId,
                 int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount,
                 Set<String> carrierIDs, Set<String> cashierIDs, Set<String> storekeeperIDs, Set<String> sorterIDs, Set<String> hr_managerIDs, Set<String> logistics_managerIDs,DShift dShift) throws Exception {
        this.workday = workday;
        if (shiftManagerId == null)
            throw new Exception("A shift has to have a shift manager");
        this.shiftManagerId = shiftManagerId;

        checkCountValidity(carrierCount, MIN_CARRIERS, JobTitles.Carrier);
        this.carrierCount = carrierCount;
        checkCountValidity(cashierCount, MIN_CASHIERS, JobTitles.Cashier);
        this.cashierCount = cashierCount;
        checkCountValidity(storekeeperCount, MIN_STOREKEEPERS, JobTitles.Storekeeper);
        this.storekeeperCount = storekeeperCount;
        checkCountValidity(sorterCount, MIN_SORTERS, JobTitles.Sorter);
        this.sorterCount = sorterCount;
        checkCountValidity(hr_managersCount, MIN_HR_MANAGERS, JobTitles.HR_Manager);
        this.hr_managersCount = hr_managersCount;
        checkCountValidity(logistics_managersCount, MIN_LOGISTICS_MANAGERS, JobTitles.Logistics_Manager);
        this.logistics_managersCount = logistics_managersCount;

        this.carrierIDs =new HashSet<>(carrierIDs);
        this.carrierIDs = new HashSet<>(cashierIDs);
        this.cashierIDs = new HashSet<>(cashierIDs);
        this.storekeeperIDs = new HashSet<>(storekeeperIDs);
        this.sorterIDs = new HashSet<>(sorterIDs);
        this.hr_managerIDs = new HashSet<>(hr_managerIDs);
        this.logistics_managerIDs = new HashSet<>(logistics_managerIDs);
        this.dShift =dShift;
        dShift.save();
    }

    public Shift(Date date, String managerId, int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managerCount, int logistics_managerCount,DShift dShift) throws Exception {
        this(date,managerId,
                carrierCount,cashierCount,storekeeperCount,sorterCount,hr_managerCount,logistics_managerCount,
                new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(),dShift);

    }

    public Shift(DShift shift){
        this.dShift = shift;
        this.workday = shift.getWorkday();
        this.shiftManagerId = shift.getShiftManagerId();
        this.carrierCount = shift.getCarrierCount();
        this.cashierCount = shift.getCashierCount();
        this.storekeeperCount= shift.getStorekeeperCount();
        this.sorterCount = shift.getSorterCount();
        this.hr_managersCount = shift.getHr_managersCount();
        this.logistics_managersCount = shift.getLogistics_managersCount();
        this.carrierIDs = new HashSet<>(shift.getCarrierIDs());
        this.cashierIDs = new HashSet<>(shift.getCashierIDs());
        this.storekeeperIDs  =new HashSet<>(shift.getStorekeeperIDs());
        this.sorterIDs =new HashSet<>(shift.getSorterIDs());
        this.hr_managerIDs =new HashSet<>(shift.getHr_managerIDs());
        this.logistics_managerIDs =new HashSet<>(shift.getLogistics_managerIDs());
        dShift.setPersist(true);
    }

    public Date getWorkday() {
        return workday;
    }

    public String getShiftManagerId() {
        return shiftManagerId;
    }

    public void setShiftManagerId(String shiftManagerId) throws Exception {
        if (shiftManagerId == null)
            throw new Exception("A shift has to have a shift manager");
        if (carrierIDs.contains(shiftManagerId))
            throw new Exception("This manager is already assigned for this shift as a carrier. " +
                    "\nIf you'd like him to manager this shift please remove him from from the carrier list of this shift");
        if (cashierIDs.contains(shiftManagerId))
            throw new Exception("This manager is already assigned for this shift as a cashier. " +
                    "\nIf you'd like him to manager this shift please remove him from from the cashier list of this shift");
        if (storekeeperIDs.contains(shiftManagerId))
            throw new Exception("This manager is already assigned for this shift as a storekeeper. " +
                    "\nIf you'd like him to manager this shift please remove him from from the storekeeper list of this shift");
        if (sorterIDs.contains(shiftManagerId))
            throw new Exception("This manager is already assigned for this shift as a sorter. " +
                    "\nIf you'd like him to manager this shift please remove him from from the sorter list of this shift");
        if (hr_managerIDs.contains(shiftManagerId))
            throw new Exception("This manager is already assigned for this shift as a HR manager. " +
                    "\nIf you'd like him to manager this shift please remove him from from the HR manager list of this shift");
        if (logistics_managerIDs.contains(shiftManagerId))
            throw new Exception("This manager is already assigned for this shift as a logistics manager. " +
                    "\nIf you'd like him to manager this shift please remove him from from the logistics manager list of this shift");
        dShift.setShiftManagerId(shiftManagerId);
        this.shiftManagerId = shiftManagerId;
    }

    public DShift getdShift() {
        return dShift;
    }

    public int getCarrierCount() {
        return carrierCount;
    }

    public void setCarrierCount(int carrierCount) throws Exception {
        checkCountValidity(carrierCount, MIN_CARRIERS, JobTitles.Carrier);
        checkSizeValidity(carrierCount, carrierIDs.size());
        dShift.setCarrierCount(carrierCount);
        this.carrierCount = carrierCount;
    }

    public int getCashierCount() {
        return cashierCount;
    }

    public void setCashierCount(int cashierCount) throws Exception {
        checkCountValidity(cashierCount, MIN_CASHIERS, JobTitles.Cashier);
        checkSizeValidity(cashierCount, cashierIDs.size());
        dShift.setCashierCount(cashierCount);
        this.cashierCount = cashierCount;
    }

    public int getStorekeeperCount() {
        return storekeeperCount;
    }

    public void setStorekeeperCount(int storekeeperCount) throws Exception {
        checkCountValidity(storekeeperCount, MIN_STOREKEEPERS, JobTitles.Storekeeper);
        checkSizeValidity(storekeeperCount, storekeeperIDs.size());
        dShift.setStorekeeperCount(storekeeperCount);
        this.storekeeperCount = storekeeperCount;
    }

    public int getSorterCount() {
        return sorterCount;
    }

    public void setSorterCount(int sorterCount) throws Exception {
        checkCountValidity(sorterCount, MIN_SORTERS, JobTitles.Sorter);
        checkSizeValidity(sorterCount, sorterIDs.size());
        dShift.setSorterCount(sorterCount);
        this.sorterCount = sorterCount;
    }

    public int getHr_managersCount() {
        return hr_managersCount;
    }

    public void setHr_managersCount(int hr_managersCount) throws Exception {
        checkCountValidity(hr_managersCount, MIN_HR_MANAGERS, JobTitles.HR_Manager);
        checkSizeValidity(hr_managersCount, hr_managerIDs.size());
        dShift.setHr_ManagersCount(hr_managersCount);
        this.hr_managersCount = hr_managersCount;
    }

    public int getLogistics_managersCount() {
        return logistics_managersCount;
    }

    public void setLogistics_managersCount(int logistics_managersCount) throws Exception {
        checkCountValidity(logistics_managersCount, MIN_LOGISTICS_MANAGERS, JobTitles.Logistics_Manager);
        checkSizeValidity(logistics_managersCount, logistics_managerIDs.size());
        dShift.setLogistics_ManagersCount(logistics_managersCount);
        this.logistics_managersCount = logistics_managersCount;
    }

    public Set<String> getCarrierIDs() {
        return carrierIDs;
    }

    public void setCarrierIDs(Set<String> carrierIDs) throws Exception {
        checkSizeValidity(carrierCount, carrierIDs.size());
        if (carrierIDs.contains(shiftManagerId))
            throw new Exception("One of these carriers (" + shiftManagerId + ") is assigned as manager of this shift, please assign someone else");
        dShift.setCarrierIDs(carrierIDs);
        this.carrierIDs = new HashSet<>(carrierIDs);
    }

    public Set<String> getCashierIDs() {
        return cashierIDs;
    }

    public void setCashierIDs(Set<String> cashierIDs) throws Exception {
        checkSizeValidity(cashierCount, cashierIDs.size());
        if (cashierIDs.contains(shiftManagerId))
            throw new Exception("One of these cashiers (" + shiftManagerId + ") is assigned as manager of this shift, please assign someone else");
        dShift.setCashierIDs(cashierIDs);
        this.cashierIDs = new HashSet<>(cashierIDs);
    }

    public Set<String> getStorekeeperIDs() {
        return storekeeperIDs;
    }

    public void setStorekeeperIDs(Set<String> storekeeperIDs) throws Exception {
        checkSizeValidity(storekeeperCount, storekeeperIDs.size());
        if (storekeeperIDs.contains(shiftManagerId))
            throw new Exception("One of these storekeepers (" + shiftManagerId + ") is assigned as manager of this shift, please assign someone else");
        dShift.setStorekeeperIDs(storekeeperIDs);
        this.storekeeperIDs = new HashSet<>(storekeeperIDs);
    }

    public Set<String> getSorterIDs() {
        return sorterIDs;
    }

    public void setSorterIDs(Set<String> sorterIDs) throws Exception {
        checkSizeValidity(sorterCount, sorterIDs.size());
        if (sorterIDs.contains(shiftManagerId))
            throw new Exception("One of these sorters (" + shiftManagerId + ") is assigned as manager of this shift, please assign someone else");
        dShift.setSorterIDs(sorterIDs);
        this.sorterIDs = new HashSet<>(sorterIDs);
    }

    public Set<String> getHr_managerIDs() {
        return hr_managerIDs;
    }

    public void setHr_managerIDs(Set<String> hr_managerIDs) throws Exception {
        checkSizeValidity(hr_managersCount, hr_managerIDs.size());
        if (hr_managerIDs.contains(shiftManagerId))
            throw new Exception("One of these HR managers (" + shiftManagerId + ") is assigned as manager of this shift, please assign someone else");
        dShift.setHr_managerIDs(hr_managerIDs);
        this.hr_managerIDs = new HashSet<>(hr_managerIDs);
    }

    public Set<String> getLogistics_managerIDs() {
        return logistics_managerIDs;
    }

    public void setLogistics_managerIDs(Set<String> logistics_managerIDs) throws Exception {
        checkSizeValidity(logistics_managersCount, logistics_managerIDs.size());
        if (logistics_managerIDs.contains(shiftManagerId))
            throw new Exception("One of these logistics managers (" + shiftManagerId + ") is assigned as manager of this shift, please assign someone else");
        dShift.setLogistics_managerIDs(logistics_managerIDs);
        this.logistics_managerIDs = new HashSet<>(logistics_managerIDs);
    }

    public abstract Domain.Service.Objects.Shift accept(ServiceShiftFactory factory);

    private void checkCountValidity(int count, int minimum, JobTitles type) throws Exception {
        if (count < minimum)
            throw new Exception(String.format("A shift can't have less than %s %s(s)", minimum, type));
    }

    private void checkSizeValidity(int count, int size) throws Exception {
        if (count < size)
            throw new Exception("A shift can't hold more employees more than configured count");
    }

    public boolean isIdInclude(String id){
        return shiftManagerId.equals(id) ||
                carrierIDs.contains(id) ||
                cashierIDs.contains(id) ||
                storekeeperIDs.contains(id) ||
                sorterIDs.contains(id) ||
                hr_managerIDs.contains(id) ||
                logistics_managerIDs.contains(id);
    }

    public abstract DShift createDShift();
    public abstract String printDayAndType();
}
