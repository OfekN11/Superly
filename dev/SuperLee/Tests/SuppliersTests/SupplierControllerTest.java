package SuppliersTests;

import Domain.BusinessLayer.Supplier.Order;
import Domain.BusinessLayer.Supplier.OrderItem;
import Globals.Pair;
import Domain.BusinessLayer.SupplierController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
//import net.jcip.annotations.NotThreadSafe;
import javax.annotation.concurrent.NotThreadSafe;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NOTE: The Tests assumes that the DB is empty
 */

@NotThreadSafe
class SupplierControllerTest {

    private SupplierController controller;
    private ArrayList<Pair<String,String>> contacts;
    private ArrayList<String> manufacturers;
    private int supId1 = -1;
    private int supId2 = -1;
    private ArrayList<Integer> supplierIds;
    private int storeId = 1;

    @BeforeEach
    void setUp() {
        supplierIds = new ArrayList<>();
        controller = new SupplierController();
        contacts = new ArrayList<>();
        manufacturers = new ArrayList<>();
        manufacturers.add("Osem");
        manufacturers.add("Elit");
        contacts.add(new Pair<String,String>("name1", "0508644177"));
        contacts.add(new Pair<String,String>("name2", "0508644177"));

        try {
            supId1 = controller.addSupplier( "name", 1, "address", "credit card", contacts, manufacturers);
            supId2 = controller.addSupplier( "name", 2, "address", "credit card", contacts, manufacturers);
            supplierIds.add(supId1);
            supplierIds.add(supId2);
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
            supplierIds.add(supID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cleanUp();
        }


        try {
            supID = controller.addSupplier("name", 4, "address", "credit card", contacts, manufacturers);
            assertTrue(controller.supplierExist(supID));
            supplierIds.add(supID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cleanUp();
        }

    }

    @Test
    void removeSupplier() {
        assertTrue(controller.supplierExist(supId1));
        try {
            controller.removeSupplier(supId1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(controller.supplierExist(supId1));

        assertTrue(controller.supplierExist(supId2));
        try {
            controller.removeSupplier(supId2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(controller.supplierExist(supId2));
    }

    @Test
    void validPhoneNumber() {
        assertTrue(controller.validPhoneNumber("+972508644197"));
        assertFalse(controller.validPhoneNumber("050 864 419K"));
        assertTrue(controller.validPhoneNumber("050 864 4197"));
        cleanUp();
    }


    /**
     * New Tests!
     */


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
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cleanUp();
        }

    }


    @Test
    void getAllRoutineSuppliersDeliveringTomorrow(){

        try {
            controller.addAgreement(supId1, 1, "1 2 3 4 5 6 7");
            controller.addAgreement(supId2, 1, "1");
            ArrayList<Integer> result = controller.getAllRoutineSuppliersDeliveringTomorrow();
            assertEquals(result.get(0), supId1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cleanUp();
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


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cleanUp();
        }

    }

    @Test
    void getManufacturers() {

        try {
            List<String> manufacturers = controller.getManufacturers(supId1);
            assertEquals(manufacturers.get(0), "Osem");
            assertEquals(manufacturers.get(1), "Elit");
        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            cleanUp();
        }
    }

    @Test
    void filterOrdersArrivalTimePassed(){
        Map<Integer, Integer> prices = new HashMap<>();
        prices.put(10, 30);   prices.put( 20, 40);
        Map<Integer, Integer> prices2 = new HashMap<>();
        prices2.put(10, 20);   prices2.put( 20, 50);

        try {
            controller.addAgreement(supId1, 1, "1");
            controller.addItemToAgreement(supId1, 1, 1, "name", "manu", 4, prices);
            controller.addAgreement(supId2, 1, "1");
            controller.addItemToAgreement(supId2, 1, 1, "name", "manu", 4, prices2);

            int orderId1 = controller.addNewOrder(supId1, storeId);
            Order order1 = controller.getOrderObject(supId1, orderId1);
            int orderId2 = controller.addNewOrder(supId1, storeId);
            Order order2 = controller.getOrderObject(supId1, orderId2);
            ArrayList<Order> orders = new ArrayList<>();
            orders.add(order1);
            orders.add(order2);

            ArrayList<Order> result = controller.filterOrdersArrivalTimePassed(orders);
            assertFalse(result.contains(order1));
            assertFalse(result.contains(order2));

            cleanUp();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cleanUp();
        }
    }


    @Test
    void filterOrdersArrivalTomorrow(){
        Map<Integer, Integer> prices = new HashMap<>();
        prices.put(10, 30);   prices.put( 20, 40);
        Map<Integer, Integer> prices2 = new HashMap<>();
        prices2.put(10, 20);   prices2.put( 20, 50);

        try {
            controller.addAgreement(supId1, 2, "1");
            controller.addItemToAgreement(supId1, 1, 1, "name", "manu", 4, prices);
            controller.addAgreement(supId2, 2, "2");
            controller.addItemToAgreement(supId2, 1, 1, "name", "manu", 4, prices2);

            int orderId1 = controller.addNewOrder(supId1, storeId);
            Order order1 = controller.getOrderObject(supId1, orderId1);
            int orderId2 = controller.addNewOrder(supId2, storeId);
            Order order2 = controller.getOrderObject(supId2, orderId2);
            ArrayList<Order> orders = new ArrayList<>();
            orders.add(order1);
            orders.add(order2);

            ArrayList<Order> result = controller.filterOrdersArrivalTomorrow(orders);
            assertTrue(result.contains(order1));
            assertFalse(result.contains(order2));

            cleanUp();

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cleanUp();
        }
    }


    @Test
    void createNewOrderItem(){

        Map<Integer, Integer> prices = new HashMap<>();
        prices.put(10, 30);   prices.put( 20, 40);

        try {
            controller.addAgreement(supId1, 1, "1");
            controller.addItemToAgreement(supId1, 1, 1, "name", "manu", 4, prices);
            OrderItem orderItem = controller.createNewOrderItem(supId1 , 1, 10);

            assertEquals(orderItem.getProductId(), 1);
            assertEquals(orderItem.getQuantity(), 10);
            assertEquals(orderItem.getDiscount(), 30);
            assertEquals(orderItem.getFinalPrice(), 28);
            assertEquals(orderItem.getName(), "name");
            assertEquals(orderItem.getPricePerUnit(), 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            cleanUp();
        }

    }


    void cleanUp() {
        try {
            for(Integer id : supplierIds) {
                if(controller.supplierExist(id))
                    controller.removeSupplier(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}