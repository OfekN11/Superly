package SuppliersTests;

import Domain.Business.Objects.Supplier.Agreement.Agreement;
import Domain.Business.Objects.Supplier.Contact;
import Domain.Business.Objects.Supplier.Supplier;
import Domain.DAL.Abstract.DAO;
import Domain.DAL.Controllers.InventoryAndSuppliers.SuppliersDAO;
import Domain.Service.Services.SupplierService;
import Domain.Service.util.Result;
import InventoryTests.CategoryTests;
import net.jcip.annotations.NotThreadSafe;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NOTE: the tests assumes that the DB is empty.
 */

@NotThreadSafe
class SupplierTest {

    private SupplierService supplierService = new SupplierService();




    @BeforeAll
    public synchronized static void setData() {
        DAO.setDBForTests(SupplierTest.class);
    }

    @AfterAll
    public static void removeData() {
        DAO.deleteTestDB(SupplierTest.class);
    }


    /*
    @BeforeEach
    public void setUp() throws Exception{
        contacts = new ArrayList<>();
        manufacturers = new ArrayList<>();
        manufacturers.add("Osem");
        manufacturers.add("Elit");
        contacts.add(new Contact("name", "phone"));
        dao = new SuppliersDAO();

        supplier = new Supplier("name",23, "address", "credit", contacts, manufacturers, dao);
    }



    @Test
    void isTransporting() {
        try {
            supplier.addAgreement(1, "2", dao);  //transporting
            assertTrue(supplier.isTransporting());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            supplier.addAgreement(2, "2", dao);  //transporting
            assertTrue(supplier.isTransporting());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            supplier.addAgreement(3, "", dao);   //not transporting
            assertFalse(supplier.isTransporting());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            dao.removeSupplier(supplier.getId());
        }

    }


     */


    @Test
    void newOrder() {
        Result<Integer> orderId = supplierService.order(1,1);
        assertTrue(orderId.isOk());
        Result<Boolean> res = supplierService.removeOrder(orderId.getValue());
        assertTrue(res.isOk());

    }

}