package Domain.Business.Objects;

import Globals.Enums.LicenseTypes;
import Globals.Enums.ShiftTypes;
import Globals.Enums.TruckModel;
import Globals.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static Globals.Enums.LicenseTypes.*;

public class Truck {
    private int licenseNumber;
    private TruckModel model;
    private int netWeight;
    private int maxCapacityWeight;
    private List<Pair<LocalDate, ShiftTypes>> takenShifts;

    public Truck(int licenseNumber, TruckModel model, int netWeight, int maxCapacityWeight) {
        this.licenseNumber = licenseNumber;
        this.model = model;
        this.netWeight = netWeight;
        this.maxCapacityWeight = maxCapacityWeight;
        takenShifts = new ArrayList<>();
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(int licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public TruckModel getModel() {
        return model;
    }

    public void setModel(TruckModel model) {
        this.model = model;
    }

    public int getNetWeight() {
        return netWeight;
    }

    public void setNetWeight(int netWeight) {
        this.netWeight = netWeight;
    }

    public int getMaxCapacityWeight() {
        return maxCapacityWeight;
    }

    public void setMaxCapacityWeight(int maxCapacityWeight) {
        this.maxCapacityWeight = maxCapacityWeight;
    }

    public boolean canDriveOn(Set<LicenseTypes> lt) {
        //TODO change the function according to the set
        boolean ans = false;
        LicenseTypes l = null;
        switch (l)
        {
            case B:
                ans = model == TruckModel.Van;
                break;
            case C:
                ans = model  == TruckModel.Van ||  model == TruckModel.SemiTrailer;
                break;
            case C1:
                ans = model  == TruckModel.Van ||  model == TruckModel.SemiTrailer ||  model == TruckModel.DoubleTrailer;
                break;
            case CE:
                ans = model  == TruckModel.Van ||  model == TruckModel.SemiTrailer ||  model == TruckModel.DoubleTrailer ||  model == TruckModel.FullTrailer;
                break;
            default:
        }
        return ans;
    }
    public boolean isFree(Pair<LocalDate, ShiftTypes> shift){
        return !takenShifts.contains(shift);
    }
    public void addShift(Pair<LocalDate, ShiftTypes> shift){
        takenShifts.add(shift);
    }
}
