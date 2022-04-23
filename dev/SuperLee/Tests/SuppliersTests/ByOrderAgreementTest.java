package SuppliersTests;

import SuperLee.BusinessLayer.Agreement.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import SuperLee.BusinessLayer.Agreement.Agreement;
import SuperLee.BusinessLayer.Agreement.NotTransportingAgreement;
import SuperLee.BusinessLayer.AgreementItem;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        agreement.setDeliveryDays(17);

        assertEquals(17, agreement.getDeliveryDays());
    }

}
