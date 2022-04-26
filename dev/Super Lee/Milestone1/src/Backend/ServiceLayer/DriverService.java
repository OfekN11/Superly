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
        //TODO:
        return  null;
    }
}
