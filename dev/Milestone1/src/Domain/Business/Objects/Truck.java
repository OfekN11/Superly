package Domain.Business.Objects;

import Globals.Enums.LicenseTypes;
import Globals.Enums.TruckModel;

import static Globals.Enums.LicenseTypes.*;

public class Truck {
    private int licenseNumber;
    private TruckModel model;
    private int netWeight;
    private int maxCapacityWeight;

    public Truck(int licenseNumber, TruckModel model, int netWeight, int maxCapacityWeight) {
        this.licenseNumber = licenseNumber;
        this.model = model;
        this.netWeight = netWeight;
        this.maxCapacityWeight = maxCapacityWeight;
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

    public boolean canDriveOn(LicenseTypes lt) {
        boolean ans = false;
        switch (lt)
        {
            case B:
                ans = model == TruckModel.A;
                break;
            case C:
                ans = model  == TruckModel.A ||  model == TruckModel.B;
                break;
            case C1:
                ans = model  == TruckModel.A ||  model == TruckModel.B ||  model == TruckModel.C;
                break;
            case CE:
                ans = model  == TruckModel.A ||  model == TruckModel.B ||  model == TruckModel.C ||  model == TruckModel.D;
                break;
            default:
        }
        return ans;
    }
}
