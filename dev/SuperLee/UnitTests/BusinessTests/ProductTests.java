package BusinessTests;

import BusinessLayer.Category;
import BusinessLayer.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

public class ProductTests {
    //Tests formula
    /*@Test
    public void testX() {
        try
        {

        }
        catch (Exception e)
        {

        }
    }*/
    Category category0 = new Category("Milk", new HashSet<>(), new ArrayList<>(), null);
    Product product0 = new Product(0, "Milk-Tnuva-1L", category0, 1, 4.5, new ArrayList<>());
    Product product1 = new Product(1, "Milk-Tara-1L", category0, 1.0, 4, new ArrayList<>());

    @Test
    public void testRemoveItems() {
        try
        {

        }
        catch (Exception e)
        {

        }
    }
    @Test
    public void testMoveItems() {
        try
        {

        }
        catch (Exception e)
        {

        }
    }
    @Test
    public void testAddItems() {
        try
        {

        }
        catch (Exception e)
        {

        }
    }
    @Test
    public void testReturnItems() {
        try
        {

        }
        catch (Exception e)
        {

        }
    }
    @Test
    public void testGetPriceOnDate() {
        try
        {

        }
        catch (Exception e)
        {

        }
    }
    @Test
    public void testGetStoreLocation() {
        try
        {

        }
        catch (Exception e)
        {

        }
    }
    @Test
    public void testGetWarehouseLocation() {
        try
        {

        }
        catch (Exception e)
        {

        }
    }
}
