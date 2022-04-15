package SuperLee.BusinessLayer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.jupiter.api.Assertions.*;

class SupplierControllerTest {

    private  SupplierController controller;

    @BeforeEach
    void setUp() {
        controller = new SupplierController();
        controller.addSupplier(1, "name", 1, "address", "credit card", "name", "phone");
        controller.addSupplier(2, "name", 2, "address", "credit card", "name", "phone");

    }

    @org.junit.jupiter.api.Test
    void addSupplier() {
        controller.addSupplier(3, "name", 3, "address", "credit card", "name", "phone");
        assertTrue(controller.supplierExist(3));
        controller.addSupplier(4, "name", 4, "address", "credit card", "name", "phone");
        assertTrue(controller.supplierExist(4));
    }

    @org.junit.jupiter.api.Test
    void removeSupplier() {
        assertTrue(controller.supplierExist(1));
        controller.removeSupplier(1);
        assertFalse(controller.supplierExist(1));

        assertTrue(controller.supplierExist(2));
        controller.removeSupplier(2);
        assertFalse(controller.supplierExist(2));
    }

    @Test
    void validPhoneNumber() {
        assertTrue(controller.validPhoneNumber("+972508644197"));
        assertFalse(controller.validPhoneNumber("050 864 419K"));
        assertTrue(controller.validPhoneNumber("050 864 4197"));
    }
}