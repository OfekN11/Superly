package Domain.Business.Controllers;

import Domain.Business.Controllers.Transport.TruckController;
import Domain.DAL.Abstract.DAO;
import Domain.DAL.Controllers.TransportMudel.TruckDAO;
import Globals.Enums.TruckModel;
import InventoryTests.CategoryTests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertTrue;

public class TruckControllerTest {
    static TruckController controller = new TruckController();
    private final String NOT_FOUND_MSG = "The truck doesn't exist!";
    static final TruckDAO truckDAO = new TruckDAO();

    @BeforeAll
    public static synchronized void setData() {
        DAO.setDBForTests(CategoryTests.class);
    }

    @AfterAll
    public static void removeData() {
        DAO.deleteTestDB(CategoryTests.class);
    }

    @BeforeEach
    public void setUp() throws Exception {
        controller.removeTruck(12345678);
    }

    @AfterEach
    public void tearDown() throws Exception {
        controller.removeTruck(12345678);
    }

    @Test
    public void removeTruck() {
        try {
            controller.addTruck(12345678, TruckModel.SemiTrailer, 2, 1);
            controller.removeTruck(12345678);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            controller.getTruck(12345678);
        }
        catch (Exception e){
            assertTrue(true);
        }

    }

    @Test
    public void addTruck() {
        try {
            controller.addTruck(12345678, TruckModel.SemiTrailer, 2, 1);
        }
        catch (Exception e){
            assertTrue(false);
        }
        try{
            controller.getTruck(12345678);
            assertTrue(true);
        }
        catch (Exception e){
            assertTrue(false);
        }

    }

    @Test
    public void getTruck() {
        try {
            controller.addTruck(12345678, TruckModel.SemiTrailer, 2, 1);
        } catch (Exception e) {
            assertTrue(false);
        }
        try {
            controller.getTruck(12345678);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }

    }
}