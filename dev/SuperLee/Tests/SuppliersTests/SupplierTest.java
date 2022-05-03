package SuppliersTests;

import SuperLee.BusinessLayer.Agreement.Agreement;
import SuperLee.BusinessLayer.Agreement.NotTransportingAgreement;
import SuperLee.BusinessLayer.Contact;
import SuperLee.BusinessLayer.Pair;
import SuperLee.BusinessLayer.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SupplierTest {

    private Agreement agreement;
    private Supplier supplier;
    private ArrayList<Contact> contacts;
    private ArrayList<String> manufacturers;

    @BeforeEach
    public void setUp() throws Exception{
        contacts = new ArrayList<>();
        manufacturers = new ArrayList<>();
        manufacturers.add("Osem");
        manufacturers.add("Elit");
        contacts.add(new Contact("name", "phone"));
        supplier = new Supplier(1,"name",23, "address", "credit", contacts, manufacturers);
    }

    /*
    @Test
    void removeContact() {
        supplier.
        //Don't have function for getContacts!
    }
    */


    /*
    @Test
    void addAgreement() {
        assertNull(supplier.getAgreement());
        supplier.addAgreement(1, "3");
        assertNotNull(agreement);
    }
    */

    @Test
    void isTransporting() {
        try {
            supplier.addAgreement(1, "2");  //transporting
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(supplier.isTransporting());
        try {
            supplier.addAgreement(2, "2");  //transporting
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(supplier.isTransporting());
        try {
            supplier.addAgreement(3, "");   //not transporting
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(supplier.isTransporting());

    }
}