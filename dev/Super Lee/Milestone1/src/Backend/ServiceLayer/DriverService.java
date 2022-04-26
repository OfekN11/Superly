package Backend.ServiceLayer;

import Backend.BusinessLayer.Controllers.DriverController;
import Backend.BusinessLayer.Controllers.TruckController;
import Backend.Globals.Enums.LicenseTypes;

public class DriverService {
    private DriverController controller;

    public DriverService() {
        this.controller = new DriverController();
    }


    public Result addDriver(String name, LicenseTypes licenseTypes) {
        try {
            controller.addDriver(name, licenseTypes);
            return Result.makeOk(null);
        }catch (Exception e)
        {
            return Result.makeError(e.getMessage());
        }
    }

    public Result removeDriver(String driverName) {
        try {
            controller.removeDriver(driverName);
            return Result.makeOk(null);
        }catch (Exception e)
        {
            return Result.makeError(e.getMessage());
        }
    }

    public Result updateDriver(String driverName, LicenseTypes driverLicenseType) {
        try {
            controller.updateDriver(driverName, driverLicenseType);
            return Result.makeOk(null);
        }catch (Exception e)
        {
            return Result.makeError(e.getMessage());
        }
    }
}
