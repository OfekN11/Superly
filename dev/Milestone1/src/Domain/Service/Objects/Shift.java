package Domain.Service.Objects;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * Service model for Shift
 */
public abstract class Shift {
    public final Date date;
    public final int shiftManagerId;

    public final int carrierCount;
    public final int cashierCount;
    public final int storekeeperCount;
    public final int sorterCount;
    public final int hr_managersCount;
    public final int logistics_managersCount;

    public final Set<Integer> carrierIDs;
    public final Set<Integer> cashierIDs;
    public final Set<Integer> storekeeperIDs;
    public final Set<Integer> sorterIDs;
    public final Set<Integer> hr_managerIDs;
    public final Set<Integer> logistics_managerIDs;

    private Shift(Date date, int shiftManagerId,
                  int carrierCount, int cashierCount, int storekeeperCount, int sorterCount, int hr_managersCount, int logistics_managersCount,
                  Set<Integer> carrierIDs, Set<Integer> cashierIDs, Set<Integer> storekeeperIDs, Set<Integer> sorterIDs, Set<Integer> hr_managerIDs, Set<Integer> logistics_managerIDs){
        this.date = date;
        this.shiftManagerId = shiftManagerId;

        this.carrierCount = carrierCount;
        this.cashierCount = cashierCount;
        this.storekeeperCount = storekeeperCount;
        this.sorterCount = sorterCount;
        this.hr_managersCount = hr_managersCount;
        this.logistics_managersCount = logistics_managersCount;

        this.carrierIDs = Collections.unmodifiableSet(carrierIDs);
        this.cashierIDs = Collections.unmodifiableSet(cashierIDs);
        this.storekeeperIDs = Collections.unmodifiableSet(storekeeperIDs);
        this.sorterIDs = Collections.unmodifiableSet(sorterIDs);
        this.hr_managerIDs = Collections.unmodifiableSet(hr_managerIDs);
        this.logistics_managerIDs = Collections.unmodifiableSet(logistics_managerIDs);
    }

    public Shift(Domain.Business.Objects.Shift bShift){
        this(bShift.getWorkday(), bShift.getShiftManagerId(),
                bShift.getCarrierCount(), bShift.getCashierCount(), bShift.getStorekeeperCount(), bShift.getSorterCount(), bShift.getHr_managersCount(), bShift.getLogistics_managersCount(),
                bShift.getCarrierIDs(), bShift.getCashierIDs(), bShift.getStorekeeperIDs(), bShift.getSorterIDs(), bShift.getHr_managerIDs(), bShift.getLogistics_managerIDs());
    }
}
