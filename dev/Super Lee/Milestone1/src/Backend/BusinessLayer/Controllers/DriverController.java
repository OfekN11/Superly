package Backend.BusinessLayer.Controllers;

import Backend.BusinessLayer.Objects.Driver;
import Backend.Globals.Enums.LicenseTypes;
import Backend.ServiceLayer.Result;

import java.util.HashMap;

public class DriverController {
    private HashMap<String, Driver> drivers;

    public DriverController() {
        this.drivers = new HashMap<>();
    }

    public HashMap<String, Driver> getDrivers() {
        return drivers;
    }

    public void addDriver(String name, LicenseTypes licenseTypes) throws Exception {
        if(!drivers.containsKey(name))
        {
            drivers.put(name, new Driver(name, licenseTypes));
        }
        throw new Exception("Driver is already exits!");
    }

    public void removeDriver(String driverName) throws Exception {
        if(drivers.containsKey(driverName))
        {
            drivers.remove(driverName);
        }
        throw new Exception("Driver is doesn't exits!");
    }

    public void updateDriver(String driverName, LicenseTypes driverLicenseType) throws Exception {
        if(drivers.containsKey(driverName))
        {
            drivers.get(driverName).setLicenseTypes(driverLicenseType);
        }
        throw new Exception("Driver is doesn't exits!");
    }
}
