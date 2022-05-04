package InventoryTests;

import Domain.BusinessLayer.InventoryController;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryControllerTest {
    private InventoryController is;
    @BeforeEach
    void init() {
        is = new InventoryController();
    }
    @org.junit.jupiter.api.Test
    void addStore() {
        //empty
        assertIterableEquals(new ArrayList<>(), is.getStoreIDs());

        assertEquals(1, is.addStore());
        Integer[] actual = is.getStoreIDs().toArray(new Integer[0]);
        Integer[] expected = {1};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(1, 1));

        assertEquals(2, is.addStore());
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{1, 2};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(2, 2));

        assertEquals(3, is.addStore());
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{1, 2, 3};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(3, 3));

        is.removeStore(2);
        assertEquals(4, is.addStore());
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{1, 3, 4};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(4, 4));
    }

    @org.junit.jupiter.api.Test
    void removeStore() {
        is.loadTestData();

        //first
        Integer[] actual = is.getStoreIDs().toArray(new Integer[0]);
        Integer[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        assertArrayEquals(actual, expected);
        for (int i=1; i<=10; i++) {
            int finalI = i;
            assertDoesNotThrow(()->is.getAmountInStore(finalI, 3));
        }

        is.removeStore(10);
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(10, 3));

        is.removeStore(1);
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(1, 3));

        is.removeStore(2);
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(2, 3));

        is.removeStore(6);
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{3, 4, 5, 7, 8, 9};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(6, 3));

        assertThrows(IllegalArgumentException.class, () -> is.removeStore(6));
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{3, 4, 5, 7, 8, 9};
        assertArrayEquals(actual, expected);
        for (int i=0; i<expected.length; i++) {
            int finalI = expected[i];
            assertDoesNotThrow(()->is.getAmountInStore(finalI, 3));
        }
    }

    @org.junit.jupiter.api.Test
    void getExpiredItemReportsByProductIllegalEntries() {
        is.loadTestData();
        Date today = new Date();
        Date yesterday = new Date();
        yesterday.setHours(-24);

        Date beforeTwoDays = new Date();
        beforeTwoDays.setHours(-48);

        Date tomorrow = new Date();
        tomorrow.setHours(24);

        Date afterTwoDays = new Date();
        afterTwoDays.setHours(48);

        List<Integer> pIDs = new ArrayList<>();
        pIDs.add(1);
        //empty
        assertIterableEquals(new ArrayList<>(), is.getExpiredItemReportsByProduct(yesterday, today, pIDs));
        //illegal - future
        assertThrows(IllegalArgumentException.class, () -> is.getExpiredItemReportsByProduct(tomorrow, afterTwoDays, pIDs));
        //illegal - start>end
        assertThrows(IllegalArgumentException.class, () -> is.getExpiredItemReportsByProduct(today, yesterday, pIDs));
    }
}