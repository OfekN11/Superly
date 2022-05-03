package SuppliersTests;

import Domain.BusinessLayer.Agreement.Agreement;
import Domain.BusinessLayer.Agreement.NotTransportingAgreement;
import Domain.BusinessLayer.AgreementItem;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AgreementTest {

    Agreement agreement;

    @BeforeEach
    public void setUp(){
        agreement = new NotTransportingAgreement();
    }

    private List<AgreementItem> makeItemList(){
        List<AgreementItem> list = new ArrayList<>();

        list.add(new AgreementItem(1, "item1", "m1", 5.11f, new HashMap<>()));
        list.add(new AgreementItem(2, "item2", "m2", 7.11f, new HashMap<>()));
        list.add(new AgreementItem(3, "item3", "m3", 12.876f, new HashMap<>()));
        list.add(new AgreementItem(4, "item4", "m4", 184.2f, new HashMap<>()));
        list.add(new AgreementItem(5, "item5", "m5", 1123f, new HashMap<>()));
        list.add(new AgreementItem(6, "item6", "m6", 687248.45621f, new HashMap<>()));

        return list;
    }

    @Test
    public void test_setItems_getItems(){
        List<AgreementItem> list = makeItemList();

        try{
            agreement.setItems(list);
            assertEquals(list, agreement.getItems());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_setItemsFromString(){
        List<String> list = new ArrayList<>();
        List<AgreementItem> aiList = makeItemList();
        for(AgreementItem ai : aiList){
            list.add(ai.toString());
        }

        try{
            agreement.setItemsFromString(list);

            assertEquals(aiList,agreement.getItems());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_addItem(){
        AgreementItem item1 = new AgreementItem(17, "name1", "man1", 1565165, new HashMap<>());
        AgreementItem item2 = new AgreementItem(18, "name2", "man2", 1565165, new HashMap<>());
        AgreementItem item3 = new AgreementItem(19, "name3", "man3", 1565165, new HashMap<>());

        List<AgreementItem> aiList = makeItemList();

        try{
            agreement.setItems(aiList);

            agreement.addItem(item1);

            aiList = makeItemList();
            aiList.add(item1);

            assertEquals(aiList, agreement.getItems());

            agreement.addItem(item2);
            agreement.addItem(item3);

            aiList = makeItemList();
            aiList.add(item1);
            aiList.add(item2);
            aiList.add(item3);

            assertEquals(aiList, agreement.getItems());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_removeItem(){
        List<AgreementItem> aiList = makeItemList();

        try{
            agreement.setItems(aiList);

            agreement.removeItem(1);

            aiList = makeItemList();
            aiList.remove(new AgreementItem(1, "item1", "m1", 5.11f, new HashMap<>()));

            assertEquals(aiList, agreement.getItems());

            agreement.removeItem(5);

            aiList = makeItemList();
            aiList.remove(new AgreementItem(5, "item5", "m5", 1123f, new HashMap<>()));

            assertEquals(aiList, agreement.getItems());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_addAndRemove(){
        AgreementItem item1 = new AgreementItem(17, "name1", "man1", 1565165, new HashMap<>());
        AgreementItem item2 = new AgreementItem(18, "name2", "man2", 1565165, new HashMap<>());
        AgreementItem item3 = new AgreementItem(19, "name3", "man3", 1565165, new HashMap<>());

        List<AgreementItem> aiList = makeItemList();

        try{
            agreement.setItems(aiList);
            aiList = makeItemList();

            agreement.addItem(item3);
            aiList.add(item3);

            agreement.removeItem(4);
            aiList.remove(new AgreementItem(4, "item4", "m4", 184.2f, new HashMap<>()));

            agreement.removeItem(2);
            aiList.remove(new AgreementItem(2, "item2", "m2", 7.11f, new HashMap<>()));

            agreement.addItem(item1);
            aiList.add(item1);

            agreement.addItem(item2);
            aiList.add(item2);

            agreement.removeItem(3);
            aiList.remove(new AgreementItem(3, "item3", "m3", 12.876f, new HashMap<>()));

            assertEquals(aiList, agreement.getItems());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_itemExists(){
        try{
            agreement.setItems(makeItemList());

            assertTrue(agreement.itemExists(3));
            assertFalse(agreement.itemExists(95));
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



}
