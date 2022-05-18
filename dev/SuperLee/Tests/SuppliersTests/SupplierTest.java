package SuppliersTests;

import Domain.BusinessLayer.Supplier.Agreement.Agreement;
import Domain.BusinessLayer.Supplier.Contact;
import Domain.BusinessLayer.Supplier.Supplier;
import Domain.PersistenceLayer.Controllers.SuppliersDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NOTE: the tests assumes that the DB is empty.
 */

class SupplierTest {

    private Agreement agreement;
    private Supplier supplier;
    private ArrayList<Contact> contacts;
    private ArrayList<String> manufacturers;
    private SuppliersDAO dao;

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


    }


}