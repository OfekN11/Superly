package SuppliersTests;

import Domain.BusinessLayer.Supplier.Order;
import Globals.Pair;
import Domain.BusinessLayer.SupplierController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * NOTE: The Tests assumes that the DB is empty
 */


class SupplierControllerTest {

    private SupplierController controller;
    private ArrayList<Pair<String,String>> contacts;
    private ArrayList<String> manufacturers;
    private int supId1 = -1;
    private int supId2 = -1;


    @BeforeEach
    void setUp() {
        controller = new SupplierController();
        contacts = new ArrayList<>();
        manufacturers = new ArrayList<>();
        manufacturers.add("Osem");
        manufacturers.add("Elit");
        contacts.add(new Pair<String,String>("name", "0508644177"));
        try {
            supId1 = controller.addSupplier( "name", 1, "address", "credit card", contacts, manufacturers);
            supId2 = controller.addSupplier( "name", 2, "address", "credit card", contacts, manufacturers);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void addSupplier() {
        int supID = -1;

        try {
            supID = controller.addSupplier( "name", 3, "address", "credit card", contacts, manufacturers);
            assertTrue(controller.supplierExist(supID));
            controller.removeSupplier(supID);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            supID = controller.addSupplier("name", 4, "address", "credit card", contacts, manufacturers);
            assertTrue(controller.supplierExist(supID));
            controller.removeSupplier(supID);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void removeSupplier() {
        assertTrue(controller.supplierExist(1));
        try {
            controller.removeSupplier(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(controller.supplierExist(1));

        assertTrue(controller.supplierExist(2));
        try {
            controller.removeSupplier(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(controller.supplierExist(2));
    }

    @Test
    void validPhoneNumber() {
        assertTrue(controller.validPhoneNumber("+972508644197"));
        assertFalse(controller.validPhoneNumber("050 864 419K"));
        assertTrue(controller.validPhoneNumber("050 864 4197"));
    }


    @Test
    void getTheCheapestSupplier(){

        Map<Integer, Integer> prices = new HashMap<>();
        prices.put(10, 30);   prices.put( 20, 40);
        Map<Integer, Integer> prices2 = new HashMap<>();
        prices2.put(10, 20);   prices2.put( 20, 50);

        try {
            controller.addAgreement(supId1, 1, "1");
            controller.addItemToAgreement(supId1, 1, 1, "name", "manu", 4, prices);
            controller.addAgreement(supId2, 1, "1");
            controller.addItemToAgreement(supId2, 1, 1, "name", "manu", 4, prices2);

            assertEquals(controller.getTheCheapestSupplier(1, 100), supId2);
            assertEquals(controller.getTheCheapestSupplier(1, 15), supId2);

            controller.removeSupplier(supId1);
            controller.removeSupplier(supId2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Test
    void getAllRoutineSuppliersDeliveringTomorrow(){

        try {
            controller.addAgreement(supId1, 1, "1 2 3 4 5 6 7");
            controller.addAgreement(supId2, 1, "1");
            ArrayList<Integer> result = controller.getAllRoutineSuppliersDeliveringTomorrow();

            assertEquals(result.get(0), supId1);

            controller.removeSupplier(supId1);
            controller.removeSupplier(supId2);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    void uploadLastOrderForRoutineSupplier(){
        Map<Integer, Integer> prices = new HashMap<>();
        prices.put(10, 30);   prices.put( 20, 40);

        try {
            controller.addAgreement(supId1, 1, "1");
            controller.addItemToAgreement(supId1, 1, 1, "name", "manu", 4, prices);
            int orderId = controller.addNewOrder(supId1, 1);

            ArrayList<Integer> supplierIds = new ArrayList<>();
            supplierIds.add(supId1);
            ArrayList<Order> ids = controller.uploadLastOrderForRoutineSupplier(supplierIds);

            assertEquals(ids.get(0).getId(), orderId);
            assertEquals(ids.get(0).getSupplierId(), supId1);

            controller.removeSupplier(supId1);
            controller.removeSupplier(supId2);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}