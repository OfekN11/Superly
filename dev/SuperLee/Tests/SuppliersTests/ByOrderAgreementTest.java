package SuppliersTests;

import Domain.BusinessLayer.Agreement.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ByOrderAgreementTest {
    ByOrderAgreement agreement;

    @BeforeEach
    public void setUp(){
        agreement = new ByOrderAgreement(22);
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
        agreement.setDeliveryDays(17);

        assertEquals(17, agreement.getDeliveryDays());
    }

}
