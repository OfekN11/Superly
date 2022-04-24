package Backend.BusinessLayer.Objects;

import Backend.Globals.Enums.LicenseTypes;

public class Truck {
    private int licenseNumber;
    private LicenseTypes model;
    private int netWeight;
    private int maxCapacityWeight;
    private Driver driver;
    private int currWeight;

    public Truck(int licenseNumber, LicenseTypes model, int netWeight, int maxCapacityWeight) {
        this.licenseNumber = licenseNumber;
        this.model = model;
        this.netWeight = netWeight;
        this.maxCapacityWeight = maxCapacityWeight;
        currWeight = 0;
    }

    public boolean unloading() {
        return true;
    }

    public boolean loading() {
        return true;
    }

    public boolean weighing(int weight) {
        if (weight > maxCapacityWeight)
        {
            return false;
        }
        currWeight =  weight;
        return true;
    }


    public boolean driverPlacement(Driver driver) {
        if (model.compareTo(driver.getLicenseTypes()) == -1)
        {
            return false;
        }
        this.driver = driver;
        return true;
    }

    public int getLicenseNumber() {
        return licenseNumber;
    }

    public LicenseTypes getModel() {
        return model;
    }

    public int getNetWeight() {
        return netWeight;
    }

    public int getMaxCapacityWeight() {
        return maxCapacityWeight;
    }
}
