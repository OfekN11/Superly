package SuppliersTests;

import Globals.Pair;
import Domain.BusinessLayer.SupplierController;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;


class SupplierControllerTest {

    private SupplierController controller;
    private ArrayList<Pair<String,String>> contacts;
    private ArrayList<String> manufacturers;

    @BeforeEach
    void setUp() {
        controller = new SupplierController();
        contacts = new ArrayList<>();
        manufacturers = new ArrayList<>();
        manufacturers.add("Osem");
        manufacturers.add("Elit");
        contacts.add(new Pair<String,String>("name", "0508644177"));
        try {
            controller.addSupplier( "name", 1, "address", "credit card", contacts, manufacturers);
            controller.addSupplier( "name", 2, "address", "credit card", contacts, manufacturers);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void addSupplier() {
        int supID = -1;

        try {
            supID = controller.addSupplier( "name", 3, "address", "credit card", contacts, manufacturers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(controller.supplierExist(supID));

        try {
            supID = controller.addSupplier("name", 4, "address", "credit card", contacts, manufacturers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(controller.supplierExist(supID));
    }

    @Test
    void removeSupplier() {
        assertTrue(controller.supplierExist(1));
        try {
            controller.removeSupplier(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(controller.supplierExist(1));

        assertTrue(controller.supplierExist(2));
        try {
            controller.removeSupplier(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertFalse(controller.supplierExist(2));
    }

    @Test
    void validPhoneNumber() {
        assertTrue(controller.validPhoneNumber("+972508644197"));
        assertFalse(controller.validPhoneNumber("050 864 419K"));
        assertTrue(controller.validPhoneNumber("050 864 4197"));
    }
}