package Domain.BusinessLayer.Supplier.Agreement;

import Domain.BusinessLayer.Supplier.AgreementItem;
import Domain.PersistenceLayer.Controllers.AgreementController;
import Globals.NotNull;

import java.util.*;

public abstract class Agreement {

    private Map<Integer, AgreementItem> items;

    public Agreement(){
        items = new HashMap<>();
    }

    private void listToMap(List<AgreementItem> _items) throws Exception {
        for(AgreementItem i : _items){
            if(itemExists(i.getProductId()))
                throw new Exception("Item with this ID already exists!");
            items.put(i.getProductId(), i);
        }
    }


    private List<AgreementItem> mapToList(Map<Integer, AgreementItem> map){

        return new ArrayList<>(map.values());
    }


    public List<AgreementItem> getItems(){
        return mapToList(items);
    }


    public void setItemsFromString(List<String> itemsString, int supplierId, AgreementController agreementController) throws Exception {
        NotNull.Check(itemsString);
        List<AgreementItem> _items = transformStringToItems(itemsString);

        items = new HashMap<>();
        agreementController.addAgreementItems( _items, supplierId);
        listToMap(_items);
    }


    public void setItems(List<AgreementItem> _items) throws Exception {
        NotNull.Check(_items);

        items = new HashMap<>();

        listToMap(_items);
    }


    //Format : " productId, idBySupplier , name , manufacturer , pricePerUnit , quantity , percent , quantity , percent ..."
    private List<AgreementItem> transformStringToItems(List<String> itemsString) throws Exception {
        List<AgreementItem> items = new ArrayList<>();
        for(String curr : itemsString){
            String[] arr = curr.split(",");
            //if(arr.length % 2 != 0)
            //    throw new Exception("You forgot something!");

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


    public void addItem(AgreementItem item) throws Exception {
        NotNull.Check(item);

        if(items.containsKey(item.getProductId())){
            throw new Exception("This item already exists!");
        }

        items.put(item.getProductId(), item);
    }


    public void removeItem(int id) throws Exception {
        if(!items.containsKey(id)){
            throw new Exception("No such item exists!");
        }

        items.remove(id);
    }


    public AgreementItem getItem(int id) throws Exception {
        if(!items.containsKey(id)){
            throw new Exception("No such item exists!");
        }

        return items.get(id);
    }


    public abstract boolean isTransporting();


    public abstract int daysToDelivery();


    // This method calculates the order's price of only one item
    public double getOrderPrice(int id, int quantity) throws Exception {
        return getItem(id).calculateTotalPrice(quantity);
    }


    public boolean itemExists(int id) {
        return items.containsKey(id);
    }


    public List<String> getItemsInMapFormat() {
        ArrayList<String> result = new ArrayList<>();
        for( Map.Entry<Integer, AgreementItem> currItem : items.entrySet()){
            String currItemInfo = currItem.getValue().getInfoInStringFormat();
            result.add(currItemInfo);
        }
        return result;
    }


    public void setItemId(int oldItemId, int newItemId) throws Exception {
        if(!itemExists(oldItemId))
            throw new Exception("Item with this ID does not exist");
        if(itemExists(newItemId))
            throw new Exception("The new ID you gave has already been used!");
        AgreementItem item = items.remove(oldItemId);
        item.setProductId(newItemId);
        items.put(newItemId, item);

    }


    public boolean isManufacturerRepresented(String manu){
        for(AgreementItem item : items.values()){
            if(Objects.equals(item.getManufacturer(), manu)){
                return true;
            }
        }

        return false;
    }


    public abstract List<Integer> getDays();

    public void addAgreementItems(Map<Integer, AgreementItem> items) {
        this.items = items;
    }
}
