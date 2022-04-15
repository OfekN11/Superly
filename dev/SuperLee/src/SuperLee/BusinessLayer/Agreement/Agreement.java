package SuperLee.BusinessLayer.Agreement;

import SuperLee.BusinessLayer.AgreementItem;
import SuperLee.BusinessLayer.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Agreement {

    private Map<Integer, AgreementItem> items;

    public Agreement(List<AgreementItem> _items){
        listToMap(_items);
    }

    private void listToMap(List<AgreementItem> _items){
        for(AgreementItem i : _items){
            items.put(i.getId(), i);
        }
    }

    private ArrayList<AgreementItem> mapToList(Map<Integer, AgreementItem> map){

        return new ArrayList<>(map.values());
    }

    public ArrayList<AgreementItem> getItems(){
        return mapToList(items);
    }

    public void setItems(List<AgreementItem> _items) throws Exception {
        NotNull.Check(_items);

        listToMap(_items);
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


}
