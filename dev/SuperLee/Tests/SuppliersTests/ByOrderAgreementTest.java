package SuppliersTests;

import Domain.BusinessLayer.Supplier.Agreement.*;
import Domain.PersistenceLayer.Controllers.AgreementController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ByOrderAgreementTest {
    private ByOrderAgreement agreement;
    private AgreementController controller;

    @BeforeEach
    public void setUp(){
        agreement = new ByOrderAgreement(22);
        controller = new AgreementController();
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
            agreement.setDeliveryDays(17, 1, controller);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assertEquals(17, agreement.getDeliveryDays());
    }

}
