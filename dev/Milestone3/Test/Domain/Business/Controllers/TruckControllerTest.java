package Domain.Business.Controllers;

import Domain.Business.Controllers.Transport.DocumentController;
import Domain.Business.Controllers.Transport.TruckController;
import Domain.Business.Objects.Document.DestinationDocument;
import Domain.Business.Objects.Document.TransportDocument;
import Domain.Business.Objects.Truck;
import Domain.DAL.Abstract.DAO;
import Domain.DAL.Controllers.TransportMudel.DestinationDocumentDAO;
import Domain.DAL.Controllers.TransportMudel.TransportDocumentDataMapper;
import Domain.DAL.Controllers.TransportMudel.TruckDAO;
import Globals.Enums.TruckModel;
import InventoryTests.CategoryTests;
import junit.framework.TestCase;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TruckControllerTest {
    static TruckController controller;
    static TruckDAO dao = new TruckDAO();
    static Truck truck = new Truck(4, TruckModel.FullTrailer, 500, 1000);

    @BeforeAll
    public static synchronized void setData() {
        DAO.setDBForTests(TruckControllerTest.class);
    }

    @AfterAll
    public static void removeData() {
        DAO.deleteTestDB(TruckControllerTest.class);
    }

    @BeforeEach
    public void setUp() throws Exception {
        controller = new TruckController();
        dao.delete(4);
    }

    @AfterEach
    public void tearDown() throws Exception {
        dao.delete(4);
    }

    @Test
    public void addTruck() {
        try {
            controller.addTruck(truck.getLicenseNumber(), truck.getModel(), truck.getNetWeight(), truck.getMaxCapacityWeight());
            assertEquals(controller.getTruck(4).getLicenseNumber(), 4);
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    public void removeTruck() {
        try {
            controller.addTruck(truck.getLicenseNumber(), truck.getModel(), truck.getNetWeight(), truck.getMaxCapacityWeight());
            controller.removeTruck(4);
            controller.getTruck(4);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }

    }

    @Test
    public void getTruck() {
        try {
            controller.addTruck(truck.getLicenseNumber(), truck.getModel(), truck.getNetWeight(), truck.getMaxCapacityWeight());
            assertEquals(controller.getTruck(4).getLicenseNumber(), 4);
        } catch (Exception e) {
            fail();
        }

    }
}






    /*
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
    TruckControllerTest*/
