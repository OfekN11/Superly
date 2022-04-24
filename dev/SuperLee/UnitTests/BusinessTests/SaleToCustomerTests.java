package BusinessTests;

import Domain.BusinessLayer.DiscountsAndSales.SaleToCustomer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.util.Date;
import java.util.LinkedList;

public class SaleToCustomerTests {

    SaleToCustomer sale1;
    SaleToCustomer sale2;
    SaleToCustomer sale3;
    SaleToCustomer sale4;
    SaleToCustomer sale5;
    SaleToCustomer sale6;
    SaleToCustomer sale7;
    SaleToCustomer sale8;
    @BeforeEach
    void createDatesAndSales() {
        Date today = new Date();
        today.setHours(0);
        today.setMinutes(0);
        today.setSeconds(0);

        Date tomorrow = new Date();
        tomorrow.setHours(0);
        tomorrow.setMinutes(0);
        tomorrow.setSeconds(0);
        tomorrow.setHours(24);

        Date yesterday = new Date();
        yesterday.setHours(0);
        yesterday.setMinutes(0);
        yesterday.setSeconds(0);
        yesterday.setHours(-24);

        Date beforeTwoDays = new Date();
        beforeTwoDays.setHours(0);
        beforeTwoDays.setMinutes(0);
        beforeTwoDays.setSeconds(0);
        beforeTwoDays.setHours(-48);

        Date afterTwoDays = new Date();
        afterTwoDays.setHours(0);
        afterTwoDays.setMinutes(0);
        afterTwoDays.setSeconds(0);
        afterTwoDays.setHours(48);

        sale1 = new SaleToCustomer(0, yesterday, tomorrow, 30, new LinkedList<>(), new LinkedList<>());
        sale2 = new SaleToCustomer(1, today, tomorrow, 30, new LinkedList<>(), new LinkedList<>());
        sale3 = new SaleToCustomer(2, yesterday, today, 30, new LinkedList<>(), new LinkedList<>());
        sale4 = new SaleToCustomer(3, today, today, 30, new LinkedList<>(), new LinkedList<>());
        sale5 = new SaleToCustomer(4, beforeTwoDays, yesterday, 30, new LinkedList<>(), new LinkedList<>());
        sale6 = new SaleToCustomer(5, tomorrow, afterTwoDays, 30, new LinkedList<>(), new LinkedList<>());
        sale7 = new SaleToCustomer(6, yesterday, yesterday, 30, new LinkedList<>(), new LinkedList<>());
        sale8 = new SaleToCustomer(7, tomorrow, tomorrow, 30, new LinkedList<>(), new LinkedList<>());
    }
    @Test
    public void testIsUpcoming() {
        try {
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
