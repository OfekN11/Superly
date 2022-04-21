package BusinessTests;

import BusinessLayer.DiscountsAndSales.SaleToCustomer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Date;
import java.util.LinkedList;

public class SaleToCustomerTests {
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
    @Test
    public void testIsUpcoming() {
        try {
            SaleToCustomer sale1 = new SaleToCustomer(0, new Date(122, 3, 20), new Date(122, 3, 22), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale2 = new SaleToCustomer(1, new Date(122, 3, 21), new Date(122, 3, 22), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale3 = new SaleToCustomer(2, new Date(122, 3, 20), new Date(122, 3, 21), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale4 = new SaleToCustomer(3, new Date(122, 3, 21), new Date(122, 3, 21), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale5 = new SaleToCustomer(4, new Date(122, 3, 19), new Date(122, 3, 20), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale6 = new SaleToCustomer(5, new Date(122, 3, 22), new Date(122, 3, 23), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale7 = new SaleToCustomer(6, new Date(122, 3, 20), new Date(122, 3, 20), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale8 = new SaleToCustomer(7, new Date(122, 3, 22), new Date(122, 3, 22), 30, new LinkedList<>(), new LinkedList<>());

            Assertions.assertFalse(sale1.isUpcoming());
            Assertions.assertFalse(sale2.isUpcoming());
            Assertions.assertFalse(sale3.isUpcoming());
            Assertions.assertFalse(sale4.isUpcoming());
            Assertions.assertFalse(sale5.isUpcoming());
            Assertions.assertTrue(sale6.isUpcoming());
            Assertions.assertFalse(sale7.isUpcoming());
            Assertions.assertTrue(sale8.isUpcoming());
        } catch (Exception e) {
            Assertions.fail("isUpcoming isn't working");
        }
    }

    @Test
    public void testIsPassed() {
        try {
            SaleToCustomer sale1 = new SaleToCustomer(0, new Date(122, 3, 20), new Date(122, 3, 22), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale2 = new SaleToCustomer(1, new Date(122, 3, 21), new Date(122, 3, 22), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale3 = new SaleToCustomer(2, new Date(122, 3, 20), new Date(122, 3, 21), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale4 = new SaleToCustomer(3, new Date(122, 3, 21), new Date(122, 3, 21), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale5 = new SaleToCustomer(4, new Date(122, 3, 19), new Date(122, 3, 20), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale6 = new SaleToCustomer(5, new Date(122, 3, 22), new Date(122, 3, 23), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale7 = new SaleToCustomer(6, new Date(122, 3, 20), new Date(122, 3, 20), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale8 = new SaleToCustomer(7, new Date(122, 3, 22), new Date(122, 3, 22), 30, new LinkedList<>(), new LinkedList<>());

            Assertions.assertFalse(sale1.isPassed());
            Assertions.assertFalse(sale2.isPassed());
            Assertions.assertFalse(sale3.isPassed());
            Assertions.assertFalse(sale4.isPassed());
            Assertions.assertTrue(sale5.isPassed());
            Assertions.assertFalse(sale6.isPassed());
            Assertions.assertTrue(sale7.isPassed());
            Assertions.assertFalse(sale8.isPassed());
        } catch (Exception e) {
            Assertions.fail("isPassed isn't working");
        }
    }

    @Test
    public void testIsActive() {
        try {
            SaleToCustomer sale1 = new SaleToCustomer(0, new Date(122, 3, 20), new Date(122, 3, 22), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale2 = new SaleToCustomer(1, new Date(122, 3, 21), new Date(122, 3, 22), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale3 = new SaleToCustomer(2, new Date(122, 3, 20), new Date(122, 3, 21), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale4 = new SaleToCustomer(3, new Date(122, 3, 21), new Date(122, 3, 21), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale5 = new SaleToCustomer(4, new Date(122, 3, 19), new Date(122, 3, 20), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale6 = new SaleToCustomer(5, new Date(122, 3, 22), new Date(122, 3, 23), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale7 = new SaleToCustomer(6, new Date(122, 3, 20), new Date(122, 3, 20), 30, new LinkedList<>(), new LinkedList<>());
            SaleToCustomer sale8 = new SaleToCustomer(7, new Date(122, 3, 22), new Date(122, 3, 22), 30, new LinkedList<>(), new LinkedList<>());

            Assertions.assertTrue(sale1.isActive());
            Assertions.assertTrue(sale2.isActive());
            Assertions.assertTrue(sale3.isActive());
            Assertions.assertTrue(sale4.isActive());
            Assertions.assertFalse(sale5.isActive());
            Assertions.assertFalse(sale6.isActive());
            Assertions.assertFalse(sale7.isActive());
            Assertions.assertFalse(sale8.isActive());
        } catch (Exception e) {
            Assertions.fail("isActive isn't working");
        }
    }
}
