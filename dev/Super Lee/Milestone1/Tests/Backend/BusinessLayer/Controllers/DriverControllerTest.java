package Backend.BusinessLayer.Controllers;

import Backend.Globals.Enums.LicenseTypes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DriverControllerTest {
    private DriverController controller;
    @Before
    public void setUp() throws Exception {
        controller = new DriverController();
    }

    @Test
    public void addDriver() {
        try {
            assertEquals(0, controller.getDrivers().size());
            controller.addDriver("ELi", LicenseTypes.B);
            assertEquals(1, controller.getDrivers().size());
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void removeDriver() {
        try {
            assertEquals(0, controller.getDrivers().size());
            controller.addDriver("ELi", LicenseTypes.B);
            assertEquals(1, controller.getDrivers().size());
            controller.removeDriver("ELi");
            assertEquals(0, controller.getDrivers().size());
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    @Test
    public void updateDriver() {
        try {
            assertEquals(0, controller.getDrivers().size());
            controller.addDriver("ELi", LicenseTypes.B);
            assertEquals(LicenseTypes.B, controller.getDrivers().get("Eli").getLicenseTypes());
            controller.updateDriver("ELi", LicenseTypes.C);
            assertEquals(LicenseTypes.C, controller.getDrivers().get("Eli").getLicenseTypes());
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}