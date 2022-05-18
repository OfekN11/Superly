package SuppliersTests;

import Domain.BusinessLayer.Supplier.Agreement.Agreement;
import Domain.BusinessLayer.Supplier.Agreement.NotTransportingAgreement;
import Domain.BusinessLayer.Supplier.AgreementItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class AgreementTest {

    private Agreement agreement;
    private HashMap<Integer, Integer> bulkPrices;

    @BeforeEach
    public void setUp(){
        agreement = new NotTransportingAgreement();
    }

    private List<AgreementItem> makeItemList(){
        List<AgreementItem> list = new ArrayList<>();
        bulkPrices = new HashMap<>();
        bulkPrices.put(5, 20);
        list.add(new AgreementItem(1,1, "item1", "m1", 5.11f, bulkPrices));
        list.add(new AgreementItem(2,2, "item2", "m2", 7.11f, bulkPrices));
        list.add(new AgreementItem(3, 3,"item3", "m3", 12.876f, bulkPrices));
        list.add(new AgreementItem(4, 4,"item4", "m4", 184.2f, bulkPrices));
        list.add(new AgreementItem(5, 5,"item5", "m5", 1123f, bulkPrices));
        list.add(new AgreementItem(6, 6,"item6", "m6", 687248.45621f, bulkPrices));

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
            agreement.setItemsFromString(list, 1, null);

            assertEquals(aiList.size(),agreement.getItems().size());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_addItem(){
        AgreementItem item1 = new AgreementItem(17, 17,"name1", "man1", 1565165f, bulkPrices);
        AgreementItem item2 = new AgreementItem(18, 18,"name2", "man2", 1565165f, bulkPrices);
        AgreementItem item3 = new AgreementItem(19, 19,"name3", "man3", 1565165f, bulkPrices);

        List<AgreementItem> aiList = makeItemList();

        try{
            agreement.setItems(aiList);

            agreement.addItem(item1);

            aiList = makeItemList();
            aiList.add(item1);


            assertEquals(item1, agreement.getItem(item1.getProductId()));

            List<AgreementItem> fromAgreement = agreement.getItems();
            fromAgreement = fromAgreement.stream().sorted(new Comparator<AgreementItem>() {
                @Override
                public int compare(AgreementItem o1, AgreementItem o2) {
                    return o1.getProductId()-o2.getProductId();
                }
            }).collect(Collectors.toList());

            AgreementItem t1;
            AgreementItem t2;

            for(int i=0; i<fromAgreement.size(); i++){
                t1 = aiList.get(i);
                t2 = fromAgreement.get(i);
                assertEquals(t1.toString(), t2.toString());
            }


            agreement.addItem(item2);
            agreement.addItem(item3);

            aiList = makeItemList();
            aiList.add(item1);
            aiList.add(item2);
            aiList.add(item3);


            fromAgreement = agreement.getItems();
            fromAgreement = fromAgreement.stream().sorted(new Comparator<AgreementItem>() {
                @Override
                public int compare(AgreementItem o1, AgreementItem o2) {
                    return o1.getProductId()-o2.getProductId();
                }
            }).collect(Collectors.toList());

            assertEquals(item1, agreement.getItem(item1.getProductId()));
            assertEquals(item2, agreement.getItem(item2.getProductId()));
            assertEquals(item3, agreement.getItem(item3.getProductId()));

            for(int i=0; i<fromAgreement.size(); i++){
                t1 = aiList.get(i);
                t2 = fromAgreement.get(i);
                assertEquals(t1.toString(), t2.toString());
            }
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

            assertEquals(aiList.size(), agreement.getItems().size());
            agreement.removeItem(1);
            aiList = makeItemList();
            assertEquals(aiList.size() - 1, agreement.getItems().size());


            agreement.removeItem(5);
            aiList = makeItemList();
            aiList.remove(0);
            aiList.remove(3);

            List<AgreementItem> fromAgreement = agreement.getItems();
            fromAgreement = fromAgreement.stream().sorted(new Comparator<AgreementItem>() {
                @Override
                public int compare(AgreementItem o1, AgreementItem o2) {
                    return o1.getProductId()-o2.getProductId();
                }
            }).collect(Collectors.toList());

            AgreementItem t1;
            AgreementItem t2;

            for(int i=0; i<fromAgreement.size(); i++){
                t1 = aiList.get(i);
                t2 = fromAgreement.get(i);
                assertEquals(t1.toString(), t2.toString());
            }


            assertEquals(aiList.size(), agreement.getItems().size());

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void test_addAndRemove(){
        AgreementItem item1 = new AgreementItem(17, 17, "name1", "man1", 1565165, bulkPrices);
        AgreementItem item2 = new AgreementItem(18, 18, "name2", "man2", 1565165, bulkPrices);
        AgreementItem item3 = new AgreementItem(19, 19,"name3", "man3", 1565165, bulkPrices);

        List<AgreementItem> aiList = makeItemList();

        try{
            agreement.setItems(aiList);

            aiList = makeItemList();

            agreement.addItem(item3);
            aiList.add(item3);
            assertEquals(item3, agreement.getItem(item3.getProductId()));
            assertEquals(aiList.size(), agreement.getItems().size());

            agreement.removeItem(4);
            aiList.remove(3);
            assertEquals(aiList.size(), agreement.getItems().size());

            agreement.removeItem(2);
            aiList.remove(1);
            assertEquals(aiList.size(), agreement.getItems().size());

            agreement.addItem(item1);
            aiList.add(item1);
            assertEquals(aiList.size(), agreement.getItems().size());
            assertEquals(item1, agreement.getItem(item1.getProductId()));

            agreement.addItem(item2);
            aiList.add(item2);
            assertEquals(aiList.size(), agreement.getItems().size());
            assertEquals(item2, agreement.getItem(item2.getProductId()));

            agreement.removeItem(3);
            aiList.remove(1);
            assertEquals(aiList.size() , agreement.getItems().size());

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
