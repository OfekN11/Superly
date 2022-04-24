package Frontend.Objects;

import Backend.Globals.Enums.LicenseTypes;

public class Truck {
    private int licenseNumber;
    private LicenseTypes model;
    private int netWeight;
    private int maxCapacityWeight;

    public Truck(int licenseNumber, LicenseTypes model, int netWeight, int maxCapacityWeight) {
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

    public LicenseTypes getModel() {
        return model;
    }

    public void setModel(LicenseTypes model) {
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
}
