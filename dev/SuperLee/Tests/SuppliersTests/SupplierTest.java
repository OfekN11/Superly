package SuppliersTests;

import Domain.BusinessLayer.Supplier.Agreement.Agreement;
import Domain.BusinessLayer.Supplier.Contact;
import Domain.BusinessLayer.Supplier.Supplier;
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


    @Test
    void isTransporting() {
        try {
            supplier.addAgreement(1, "2");  //transporting
            assertTrue(supplier.isTransporting());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            supplier.addAgreement(2, "2");  //transporting
            assertTrue(supplier.isTransporting());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            supplier.addAgreement(3, "");   //not transporting
            assertFalse(supplier.isTransporting());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}