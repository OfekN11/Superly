package BusinessLayer;

import java.util.*;

public class Product {
    public Product(int id, String name, Category category, int weight, double price, List<SaleToCustomer> sales) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.weight = weight;
        this.price = new Price(price, sales);
        Items = new ArrayList<>();
        inStore = new HashMap<Location, Integer>(); //needs to filled with all stores locations.
        inWarehouse = new HashMap<Location, Integer>(); //needs to filled with all warehouses locations.

    }
    private int id;
    private String name;
    private Category category;
    private List<Item> Items; //list of items in stock in the whole chain.
    private Map<Location, Integer> minAmount; //min amount which indicates for lack of the specific product in each location.
    private Map<Location, Integer> maxAmount; //max amount available to store in the warehouse for this product at once.
    private Map<Location, Integer> inStore; //current amount in store.
    private Map<Location, Integer> inWarehouse; //current amount in warehouse.
    private int weight;
    private List<Supplier> suppliers;
    private Price price;

    public int getId() {
        return id;
    }
    public double getOriginalPrice() {
        return price.getOriginalPrice();
    }
    public double getCurrentPrice() {
        return price.getCurrentPrice();
    }
    public void addSale(SaleToCustomer sale) {
        price.addSale(sale);
    }
    public boolean inCategory(List<Integer> categories) {
        return category.inCategory(categories);
    }
    public void RemoveItems(List<Item> itemList) { //bought or thrown
        int amount = itemList.size();
        Items.removeAll(itemList);
        Location l = itemList.get(0).getLocation(); //ASSUME all items of this product are at the same location.
        inStore.put(l, inStore.get(l)-amount);
    }
    public void MoveItems(List<Item> itemList) { //from warehouse to store
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
    public int AddItems(int storeID, Map<Date, Integer> expiryDates, long currentItemId) { //from supplier to warehouse
        Location l = getWarehouseLocation(storeID);
        int amount = 0;
        for (Map.Entry<Date, Integer> entry : expiryDates.entrySet()) {
            amount += entry.getValue();
            for (int i=0; i<entry.getValue(); i++) {
                currentItemId++;
                Items.add(new Item(currentItemId, l, name, entry.getKey()));
            }
        }
        inWarehouse.put(l, inWarehouse.get(l)+amount);
        return amount;
    }

    public int ReturnItems(int storeID, Map<Date, Integer> expiryDates, long currentItemId) { //from customer to store
        Location l = getStoreLocation(storeID);
        int amount = 0;
        for (Map.Entry<Date, Integer> entry : expiryDates.entrySet()) {
            amount += entry.getValue();
            for (int i=0; i<entry.getValue(); i++) {
                currentItemId++;
                Items.add(new Item(currentItemId, l, name, entry.getKey()));
            }
        }
        inStore.put(l, inStore.get(l)+amount);
        return amount;
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
