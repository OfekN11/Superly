package BusinessTests;

import BusinessLayer.Category;
import BusinessLayer.DiscountsAndSales.SaleToCustomer;
import BusinessLayer.Location;
import BusinessLayer.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.*;

public class ProductTests {
    Category category0 = new Category(0,"Milk", new HashSet<>(), new ArrayList<>(), null);
    Product product0 = new Product(0, "Milk-Tnuva-1L", category0, 1, 4.5, new HashMap<>(), 18);
    Product product1 = new Product(1, "Milk-Tara-1L", category0, 1.0, 4, new HashMap<>(), 25);

    @BeforeEach
    void addLocations() {
        List<Integer> shelves1 = new LinkedList<Integer>();
        List<Integer> shelves2 = new LinkedList<Integer>();
        List<Integer> shelves3 = new LinkedList<Integer>();
        List<Integer> shelves4 = new LinkedList<Integer>();
        List<Integer> shelves5 = new LinkedList<Integer>();
        List<Integer> shelves6 = new LinkedList<Integer>();
        shelves1.add(8);
        shelves1.add(9);
        shelves2.add(2);
        shelves3.add(40);
        shelves3.add(39);
        shelves3.add(41);
        shelves4.add(9);
        shelves5.add(10);
        shelves6.add(40);
        shelves6.add(39);
        product0.addLocation(0, shelves1, shelves2, 80, 300);
        product0.addLocation(1, shelves3, shelves4, 65, 250);
        product1.addLocation(0, shelves5, shelves6, 40, 150);
    }

    @Test
    public void testAddItems() {
        try
        {
            Assertions.assertEquals(0, product0.getInStore(0));
            Assertions.assertEquals(0, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(0, product0.getInWarehouse(1));
            Assertions.assertEquals(0, product1.getInStore(0));
            Assertions.assertEquals(0, product1.getInWarehouse(0));
            product0.addItems(0, 100);
            Assertions.assertEquals(0, product0.getInStore(0));
            Assertions.assertEquals(100, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(0, product0.getInWarehouse(1));
            Assertions.assertEquals(0, product1.getInStore(0));
            Assertions.assertEquals(0, product1.getInWarehouse(0));
            product0.addItems(0, 40);
            product0.addItems(1, 30);
            product1.addItems(0, 99);
            Assertions.assertEquals(0, product0.getInStore(0));
            Assertions.assertEquals(140, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(30, product0.getInWarehouse(1));
            Assertions.assertEquals(0, product1.getInStore(0));
            Assertions.assertEquals(99, product1.getInWarehouse(0));
        }
        catch (Exception e)
        {
            Assertions.fail("addItems does not work");
        }
    }
    @Test
    public void testMoveItems() {
        try
        {
            Assertions.assertEquals(0, product0.getInStore(0));
            Assertions.assertEquals(0, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(0, product0.getInWarehouse(1));
            Assertions.assertEquals(0, product1.getInStore(0));
            Assertions.assertEquals(0, product1.getInWarehouse(0));
            product0.addItems(0, 100);
            product0.moveItems(0, 58);
            Assertions.assertEquals(58, product0.getInStore(0));
            Assertions.assertEquals(42, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(0, product0.getInWarehouse(1));
            Assertions.assertEquals(0, product1.getInStore(0));
            Assertions.assertEquals(0, product1.getInWarehouse(0));
            product0.addItems(0, 40);
            product0.addItems(1, 30);
            product1.addItems(0, 99);
            product0.moveItems(0, 40);
            product0.moveItems(1, 25);
            product1.moveItems(0, 18);
            Assertions.assertEquals(98, product0.getInStore(0));
            Assertions.assertEquals(42, product0.getInWarehouse(0));
            Assertions.assertEquals(25, product0.getInStore(1));
            Assertions.assertEquals(5, product0.getInWarehouse(1));
            Assertions.assertEquals(18, product1.getInStore(0));
            Assertions.assertEquals(81, product1.getInWarehouse(0));
        }
        catch (Exception e)
        {
            Assertions.fail("moveItems does not work");
        }
    }
    @Test
    public void testRemoveItems() {
        try
        {
            Assertions.assertEquals(0, product0.getInStore(0));
            Assertions.assertEquals(0, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(0, product0.getInWarehouse(1));
            Assertions.assertEquals(0, product1.getInStore(0));
            Assertions.assertEquals(0, product1.getInWarehouse(0));
            product0.addItems(0, 100);
            product0.moveItems(0, 58);
            product0.removeItems(0, 37);
            Assertions.assertEquals(21, product0.getInStore(0));
            Assertions.assertEquals(42, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(0, product0.getInWarehouse(1));
            Assertions.assertEquals(0, product1.getInStore(0));
            Assertions.assertEquals(0, product1.getInWarehouse(0));
            product0.addItems(0, 40);
            product0.addItems(1, 30);
            product1.addItems(0, 99);
            product0.moveItems(0, 40);
            product0.moveItems(1, 25);
            product1.moveItems(0, 18);
            product0.removeItems(0, 15);
            product0.removeItems(1, 25);
            product1.removeItems(0, 0);
            Assertions.assertEquals(46, product0.getInStore(0));
            Assertions.assertEquals(42, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(5, product0.getInWarehouse(1));
            Assertions.assertEquals(18, product1.getInStore(0));
            Assertions.assertEquals(81, product1.getInWarehouse(0));
        }
        catch (Exception e)
        {
            Assertions.fail("removeItems does not work");
        }
    }
    @Test
    public void testReturnItems() {
        try
        {
            Date yesterday = new Date();
            yesterday.setHours(0);
            yesterday.setMinutes(0);
            yesterday.setSeconds(0);
            yesterday.setHours(-24);

            Date beforeTwoDays = new Date();
            beforeTwoDays.setHours(0);
            beforeTwoDays.setMinutes(0);
            beforeTwoDays.setSeconds(0);
            beforeTwoDays.setHours(-24*2);

            Date beforeThreeDays = new Date();
            beforeThreeDays.setHours(0);
            beforeThreeDays.setMinutes(0);
            beforeThreeDays.setSeconds(0);
            beforeThreeDays.setHours(-24*3);

            Date beforeFourDays = new Date();
            beforeFourDays.setHours(0);
            beforeFourDays.setMinutes(0);
            beforeFourDays.setSeconds(0);
            beforeFourDays.setHours(-24*4);

            Date beforeFiveDays = new Date();
            beforeFiveDays.setHours(0);
            beforeFiveDays.setMinutes(0);
            beforeFiveDays.setSeconds(0);
            beforeFiveDays.setHours(-24*5);

            Date beforeSixDays = new Date();
            beforeSixDays.setHours(0);
            beforeSixDays.setMinutes(0);
            beforeSixDays.setSeconds(0);
            beforeSixDays.setHours(-24*6);

            SaleToCustomer sale0 = new SaleToCustomer(0, beforeFourDays, beforeTwoDays, 30, new LinkedList<>(), new ArrayList<>());
            product0.addSale(sale0);
            List<Integer> categories = new ArrayList<>();
            categories.add(0);
            SaleToCustomer sale1 = new SaleToCustomer(0, beforeFiveDays, beforeFourDays, 50, new LinkedList<>(), new ArrayList<>());
            SaleToCustomer sale2 = new SaleToCustomer(0, beforeFourDays, beforeTwoDays, 40, categories, new ArrayList<>());
            SaleToCustomer sale3 = new SaleToCustomer(0, beforeTwoDays, yesterday, 80, new LinkedList<>(), new ArrayList<>());
            product0.addSale(sale1);
            category0.addSale(sale2);
            category0.addSale(sale3);

            Assertions.assertEquals(0, product0.getInStore(0));
            Assertions.assertEquals(0, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(0, product0.getInWarehouse(1));
            Assertions.assertEquals(0, product1.getInStore(0));
            Assertions.assertEquals(0, product1.getInWarehouse(0));
            double returnedValue = product0.returnItems(0, 100, beforeThreeDays);
            Assertions.assertEquals(100, product0.getInStore(0));
            Assertions.assertEquals(0, product0.getInWarehouse(0));
            Assertions.assertEquals(0, product0.getInStore(1));
            Assertions.assertEquals(0, product0.getInWarehouse(1));
            Assertions.assertEquals(0, product1.getInStore(0));
            Assertions.assertEquals(0, product1.getInWarehouse(0));
            Assertions.assertEquals(270 ,returnedValue);
            returnedValue = product0.returnItems(0, 40, beforeFourDays);
            Assertions.assertEquals(90 ,returnedValue);
            returnedValue = product0.returnItems(1, 30, beforeTwoDays);
            Assertions.assertEquals(27 ,returnedValue);
            returnedValue = product1.returnItems(0, 99, beforeSixDays);
            Assertions.assertEquals(396 ,returnedValue);
            returnedValue = product1.returnItems(0, 5, beforeTwoDays);
            Assertions.assertEquals(4 ,returnedValue);
            returnedValue = product0.returnItems(0, 99, beforeSixDays);
            Assertions.assertEquals(445.5 ,returnedValue);
            Assertions.assertEquals(239, product0.getInStore(0));
            Assertions.assertEquals(0, product0.getInWarehouse(0));
            Assertions.assertEquals(30, product0.getInStore(1));
            Assertions.assertEquals(0, product0.getInWarehouse(1));
            Assertions.assertEquals(104, product1.getInStore(0));
            Assertions.assertEquals(0, product1.getInWarehouse(0));
        }
        catch (Exception e)
        {
            Assertions.fail("returnItems does not work");
        }
    }
}
