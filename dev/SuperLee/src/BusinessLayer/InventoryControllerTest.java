package BusinessLayer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;
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

        assertEquals(0, is.addStore());
        Integer[] actual = is.getStoreIDs().toArray(new Integer[0]);
        Integer[] expected = {0};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(0, 1));

        assertEquals(1, is.addStore());
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{0, 1};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(1, 2));

        assertEquals(2, is.addStore());
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{0, 1, 2};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(2, 3));

        is.removeStore(1);
        assertEquals(3, is.addStore());
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{0, 2, 3};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(3, 4));
    }

    @org.junit.jupiter.api.Test
    void removeStore() {
        is.loadTestData();

        //first
        Integer[] actual = is.getStoreIDs().toArray(new Integer[0]);
        Integer[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(actual, expected);
        for (int i=0; i<10; i++) {
            int finalI = i;
            assertDoesNotThrow(()->is.getAmountInStore(finalI, 3));
        }

        is.removeStore(9);
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(9, 3));

        is.removeStore(0);
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(0, 3));

        is.removeStore(1);
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{2, 3, 4, 5, 6, 7, 8};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(1, 3));

        is.removeStore(6);
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{2, 3, 4, 5, 7, 8};
        assertArrayEquals(actual, expected);
        assertThrows(IllegalArgumentException.class, ()->is.getAmountInStore(6, 3));

        assertThrows(IllegalArgumentException.class, () -> is.removeStore(0));
        actual = is.getStoreIDs().toArray(new Integer[0]);
        expected = new Integer[]{2, 3, 4, 5, 7, 8};
        assertArrayEquals(actual, expected);
        for (int i=0; i<expected.length; i++) {
            int finalI = expected[i];
            assertDoesNotThrow(()->is.getAmountInStore(finalI, 3));
        }
    }

    @org.junit.jupiter.api.Test
    void getExpiredItemReportsByProduct() {
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

        pIDs.add(3); pIDs.add(2); pIDs.remove(0);
    }

    @org.junit.jupiter.api.Test
    void buyItems() {
        //need to see that cannot buy too many items
        //need to see that the price is right
        //need to see that itemCount is updated
        is.loadTestData();
    }
}