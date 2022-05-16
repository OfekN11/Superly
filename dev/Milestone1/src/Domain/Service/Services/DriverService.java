package Domain.Service.Services;

public class DriverService {
    private DriverController controller;

    public DriverService() {
        this.controller = new DriverController();
    }
    //TODO deliver this functionality to the employee service


    /*public Result addDriver(String name, LicenseTypes licenseTypes) {
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
    }*/
}
