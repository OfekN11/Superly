package SuperLee.BusinessLayer.Agreement;

import SuperLee.BusinessLayer.Item;
import SuperLee.BusinessLayer.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Agreement {

    Map<Integer, Item> items;

    public Agreement(List<Item> _items){
        listToMap(_items);
    }

    private void listToMap(List<Item> _items){
        for(Item i : _items){
            items.put(i.getId(), i);
        }
    }

    private List<Item> mapToList(Map<Integer, Item> map){

        return new ArrayList<>(map.values());
    }

    public List<Item> getItems(){
        return mapToList(items);
    }

    public void setItems(List<Item> _items) throws Exception {
        NotNull.Check(_items);

        listToMap(_items);
    }

    public void addItem(Item item) throws Exception {
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

    public Item getItem(int id) throws Exception {
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


}
