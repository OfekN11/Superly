package Backend.BusinessLayer.Controllers;

import Backend.Globals.Enums.LicenseTypes;
import org.junit.Assert;
import org.junit.Before;

import static org.junit.Assert.*;

public class TruckControllerTest {
    private TruckController controller;

    @Before
    public void setUp() throws Exception {
        controller = new TruckController();
    }

    @org.junit.Test
    public void removeTruck() {
        try {
            assertEquals(0, controller.getTrucks().size());
            controller.addTruck(12345678, LicenseTypes.B, 2, 1);
            assertEquals(1, controller.getTrucks().size());
            controller.removeTruck(12345678);
            assertEquals(0, controller.getTrucks().size());
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @org.junit.Test
    public void addTruck() {
        try {
            assertEquals(0, controller.getTrucks().size());
            controller.addTruck(12345678, LicenseTypes.B, 2, 1);
            assertEquals(1, controller.getTrucks().size());
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}