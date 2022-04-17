package BusinessLayer;

import java.util.*;

public class Product {
    public Product(int id, String name, int weight, double price) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.price = price;
        Items = new ArrayList<>();
        inStore = new HashMap<Location, Integer>(); //needs to filled with all stores locations.
        inWarehouse = new HashMap<Location, Integer>(); //needs to filled with all warehouses locations.

    }
    private int id;
    private String name;
    private List<Item> Items; //list of items in stock in the whole chain.
    private Map<Location, Integer> minAmount; //min amount which indicates for lack of the specific product in each location.
    private Map<Location, Integer> maxAmount; //max amount available to store in the warehouse for this product at once.
    private Map<Location, Integer> inStore; //current amount in store.
    private Map<Location, Integer> inWarehouse; //current amount in warehouse.
    private int weight;
    private List<Supplier> suppliers;
    private double price;

    public int getId() {
        return id;
    }

    private void RemoveItems(List<Item> itemList) { //bought or thrown
        int amount = itemList.size();
        Items.removeAll(itemList);
        Location l = itemList.get(0).getLocation(); //ASSUME all items of this product are at the same location.
        inStore.put(l, inStore.get(l)-amount);
    }
    private void MoveItems(List<Item> itemList) { //from warehouse to store
        int amount = itemList.size();
        Location from = itemList.get(0).getLocation(); //ASSUME all items of this product are at the same location.
        Location to = getStoreLocation(from.getStoreID()); //the storeID of the product is the same in the warehouse and in the store.
        //change location of Items
        for (Item item: itemList)
            item.setLocation(to);
        //remove from warehouse
        inWarehouse.put(from, inWarehouse.get(from)-amount);
        //add to store
        inStore.put(to, inStore.get(to)+amount);
    }
    private void AddItems(List<Item> itemList) { //from supplier to warehouse
        int amount = itemList.size();
        Location l = itemList.get(0).getLocation(); //Do we have the items instances before the function call?
    }
    private void ReturnedToStore(List<Item> itemList, int storeId) { //from customer to store
        int amount = itemList.size();
        Location l = itemList.get(0).getLocation(); //Do we have the items instances before the function call?
    }
    private Location getStoreLocation(int storeId) {
        Location l = null;
        for (Map.Entry<Location, Integer> entry : inStore.entrySet())
            if (entry.getKey().getStoreID()==storeId)
                l = entry.getKey();
        if (l==null)
            throw new IllegalArgumentException("Product: getStoreLocation: location not found");
        return l;
    }
    private Location getWarehouseLocation(int storeId) {
        Location l = null;
        for (Map.Entry<Location, Integer> entry : inWarehouse.entrySet())
            if (entry.getKey().getStoreID()==storeId)
                l = entry.getKey();
        if (l==null)
            throw new IllegalArgumentException("Product: getWarehouseLocation: location not found");
        return l;
    }
}
