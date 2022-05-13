package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.AgreementItem;
import Domain.BusinessLayer.Supplier.Supplier;
import Domain.PersistenceLayer.Abstract.DAO;

import java.util.*;

public class AgreementItemDAO extends DAO {

    private final static Map<String, AgreementItem> AGREEMENT_ITEM_IDENTITY_MAP = new HashMap<>();


    public AgreementItemDAO() {
        super("AgreementItem");
    }



    //Format : " productId, idBySupplier , name , manufacturer , pricePerUnit , quantity , percent , quantity , percent ..."
    private List<AgreementItem> transformStringToItems(List<String> itemsString) throws Exception {
        List<AgreementItem> items = new ArrayList<>();
        for(String curr : itemsString){
            String[] arr = curr.split(",");
            for(int i = 0; i < arr.length; i++){
                arr[i] = arr[i].trim();
            }
            int productId = Integer.parseInt(arr[0]);
            int idBuSupplier = Integer.parseInt(arr[1]);
            String name = arr[2];  String manufacturer = arr[3];
            float pricePerUnit = Float.parseFloat(arr[4]);
            HashMap<Integer, Integer> bulk = new HashMap<>();
            for(int i = 5; i < arr.length; i++ ){
                bulk.put(Integer.parseInt(arr[i]) , Integer.parseInt(arr[i+1]));
                i++;
            }
            items.add(new AgreementItem(productId, idBuSupplier, name , manufacturer, pricePerUnit, bulk));
        }
        return items;
    }

    public ArrayList<String> addItemstoAgreement(int supplierId, List<String> itemsString) throws Exception {
        ArrayList<String> newManufacturers = new ArrayList<>();
        List<AgreementItem> _items = transformStringToItems(itemsString);
        for(AgreementItem item : _items){
            insert(Arrays.asList(/*supplierId...*/));  // need to add idBySupplier????

            //also add the bulk prices to another table
            //update the Manufacturers table in the function that calling this one
            newManufacturers.add(item.getManufacturer());

            AGREEMENT_ITEM_IDENTITY_MAP.put(String.valueOf(item.getProductId()), item);

        }
        return newManufacturers;
    }



    public void addItemToAgreement(int itemId, int idBySupplier, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices) throws Exception {

        // need to implement this?
        //agreementExists();

        if(AGREEMENT_ITEM_IDENTITY_MAP.containsKey(itemId))
            throw new Exception("item with this ID already exists!");
        AgreementItem item = new AgreementItem(itemId, idBySupplier, itemName, itemManu, itemPrice, bulkPrices);
        insert(Arrays.asList(/*supplierId...*/));   // need to add idBySupplier????

        //also add the bulk prices to another table

        AGREEMENT_ITEM_IDENTITY_MAP.put(String.valueOf(item.getProductId()), item);


    }
}
