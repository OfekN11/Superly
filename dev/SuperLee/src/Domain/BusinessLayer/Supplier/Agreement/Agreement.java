package Domain.BusinessLayer.Supplier.Agreement;

import Domain.BusinessLayer.Supplier.AgreementItem;
import Globals.NotNull;

import java.util.*;

public abstract class Agreement {

    private Map<Integer, AgreementItem> items;

    public Agreement(){
        items = new HashMap<>();
    }

    private void listToMap(List<AgreementItem> _items) throws Exception {
        for(AgreementItem i : _items){
            if(itemExists(i.getId()))
                throw new Exception("Item with this ID already exists!");
            items.put(i.getId(), i);
        }
    }


    private List<AgreementItem> mapToList(Map<Integer, AgreementItem> map){

        return new ArrayList<>(map.values());
    }


    public List<AgreementItem> getItems(){
        return mapToList(items);
    }


    public void setItemsFromString(List<String> itemsString) throws Exception {
        NotNull.Check(itemsString);
        List<AgreementItem> _items = transformStringToItems(itemsString);

        items = new HashMap<>();

        listToMap(_items);
    }


    public void setItems(List<AgreementItem> _items) throws Exception {
        NotNull.Check(_items);

        items = new HashMap<>();

        listToMap(_items);
    }


    //Format : " id , name , manufacturer , pricePerUnit , quantity , percent , quantity , percent ..."
    private List<AgreementItem> transformStringToItems(List<String> itemsString) throws Exception {
        List<AgreementItem> items = new ArrayList<>();
        for(String curr : itemsString){
            String[] arr = curr.split(",");
            //if(arr.length % 2 != 0)
            //    throw new Exception("You forgot something!");

            for(int i = 0; i < arr.length; i++){
                arr[i] = arr[i].trim();
            }
            int itemId = Integer.parseInt(arr[0]);
            String name = arr[1];  String manufacturer = arr[2];
            float pricePerUnit = Float.parseFloat(arr[3]);
            HashMap<Integer, Integer> bulk = new HashMap<>();
            for(int i = 4; i < arr.length; i++ ){
                bulk.put(Integer.parseInt(arr[i]) , Integer.parseInt(arr[i+1]));
                i++;
            }
            items.add(new AgreementItem(itemId, name , manufacturer, pricePerUnit, bulk));
        }
        return items;
    }


    public void addItem(AgreementItem item) throws Exception {
        NotNull.Check(item);

        if(items.containsKey(item.getId())){
            throw new Exception("This item already exists!");
        }

        items.put(item.getId(), item);
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
        item.setId(newItemId);
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


}
