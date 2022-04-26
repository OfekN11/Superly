package Backend.ServiceLayer;

import Backend.BusinessLayer.Controllers.DriverController;
import Backend.BusinessLayer.Controllers.TruckController;

public class DriverService {
    private DriverController controller;

    public DriverService() {
        this.controller = new DriverController();
    }
}
