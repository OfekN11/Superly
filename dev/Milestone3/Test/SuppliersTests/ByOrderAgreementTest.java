package SuppliersTests;

import Domain.Business.Objects.Supplier.Agreement.ByOrderAgreement;
import Domain.DAL.Controllers.InventoryAndSuppliers.AgreementController;
import net.jcip.annotations.NotThreadSafe;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@NotThreadSafe
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


}
