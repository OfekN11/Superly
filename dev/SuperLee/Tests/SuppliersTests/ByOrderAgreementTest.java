package SuppliersTests;

import Domain.BusinessLayer.Supplier.Agreement.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ByOrderAgreementTest {
    private ByOrderAgreement agreement = new ByOrderAgreement(22);;

    @BeforeEach
    public void setUp(){
        //agreement = new ByOrderAgreement(22);
    }

    @Test
    public void test_isTransporting(){
        assertTrue(agreement.isTransporting());
    }

    @Test
    public void test_daysToDelivery(){
        assertEquals(22, agreement.daysToDelivery());
    }

    @Test
    public void test_setDeliveryDays(){
        try {
            agreement.setDeliveryDays(17, 1, null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(17, agreement.getDeliveryDays());
    }

}
