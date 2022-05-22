package Domain.Business.Controllers;

import Domain.DAL.Controllers.TransportMudel.TruckDAO;
import Globals.Enums.TruckModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.junit.Assert.*;

public class TruckControllerTest {
    static TruckController controller = new TruckController();
    static TruckDAO truckDAO = new TruckDAO();
    private final String NOT_FOUND_MSG = "The truck doesn't exist!";
    @Before
    public void setUp() throws Exception {
        truckDAO.delete("12345678");
    }

    @After
    public void tearDown() throws Exception {
        truckDAO.delete("12345678");
    }

    @Test
    public void removeTruck() {
        try {
            controller.addTruck(12345678, TruckModel.SemiTrailer, 2, 1);
            controller.removeTruck(12345678);
            assertNull(truckDAO.get(12345678));
        }catch(Exception e)
        {
        }
    }

    @Test
    public void addTruck() {
        try {
            controller.addTruck(12345678, TruckModel.SemiTrailer, 2, 1);
            assertNotNull(truckDAO.get(12345678));
        }catch(Exception e)
        {
        }
    }

    @Test
    public void getTruck() {
        try {
            controller.addTruck(12345678, TruckModel.SemiTrailer, 2, 1);
            assertEquals(12345670, controller.getTruck(12345670).getLicenseNumber());
        }catch(Exception e)
        {
        }
    }
}