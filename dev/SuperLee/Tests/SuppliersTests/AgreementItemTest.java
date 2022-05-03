package SuppliersTests;

import Domain.BusinessLayer.AgreementItem;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class AgreementItemTest {

    AgreementItem item;

    @BeforeEach
    public void setUp(){
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(20, 15); map.put(50, 20); map.put(100, 30);
        item = new AgreementItem(1, "Bamaba", "Osem", 8.99f, map);
    }

    @Test
    public void test_setters(){
        try{
            item.setId(444);
            item.setName("Bissli");
            item.setManufacturer("Tnuva");
            item.setPrice(51.367f);
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(1000, 23);
            item.setBulkPrices(map);

            assertEquals(444, item.getId());
            assertEquals("Bissli", item.getName());
            assertEquals("Tnuva", item.getManufacturer());
            assertEquals(51.367f, item.getPricePerUnit());
            assertEquals(map, item.getBulkPrices());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_addBulkPrice(){
        try{
            item.addBulkPrice(1000, 30);

            assertEquals(30, item.getBulkPrices().get(1000));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_removeBulkPrice(){
        try{
            item.removeBulkPrice(100);
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(20, 15); map.put(50, 20);

            assertEquals(map, item.getBulkPrices());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_editBulkPrice(){
        try{
            item.editBulkPrice(50, 25);
            HashMap<Integer, Integer> map = new HashMap<>();
            map.put(20, 15); map.put(50, 20); map.put(100, 35);

            assertEquals(25, item.getBulkPrices().get(50));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_calculateTotalPrice_noBulk(){
        double price = item.calculateTotalPrice(10);

        assertEquals(10*8.99, price);
    }

    @Test
    public void test_calculateTotalPrice_withBulk(){
        double price = item.calculateTotalPrice(30);

        assertEquals(30*8.99*0.85, price);

        price = item.calculateTotalPrice(50);
        assertEquals(50*8.99*0.8, price);

        price = item.calculateTotalPrice(99);
        assertEquals(99*8.99*0.8, price);
    }

}
