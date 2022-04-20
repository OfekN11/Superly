package BusinessLayer;

import java.util.*;

public class Product {
    public Product(int id, String name, Category category, double weight, double price, List<Supplier> suppliers) {
        this.id = id;
        this.name = name;
        this.category = category;
        category.addProduct(this);
        this.weight = weight;
        this.price = price;
        this.suppliers = suppliers;
        inStore = new HashMap<Location, Integer>(); //needs to filled with all stores locations.
        inWarehouse = new HashMap<Location, Integer>(); //needs to filled with all warehouses locations.
        sales = new ArrayList<>();
    }

    private int id;
    private String name;
    private Category category;
    private Map<Location, Integer> minAmount; //min amount which indicates for lack of the specific product in each location.
    private Map<Location, Integer> maxAmount; //max amount available to store in the warehouse for this product at once.
    private Map<Location, Integer> inStore; //current amount in store.
    private Map<Location, Integer> inWarehouse; //current amount in warehouse.
    private double weight;
    private List<Supplier> suppliers;
    private double price;
    private List<SaleToCustomer> sales;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public double getOriginalPrice() {
        return price;
    }

    public double getCurrentPrice() {
        return price*getCurrentSale().getPercent()/100;
    }

    private SaleToCustomer getCurrentSale() {
        SaleToCustomer currentSale = null;
        for (SaleToCustomer sale: sales)
            if ((sale.isActive() && currentSale==null) || (sale.isActive() && currentSale.getPercent()<sale.getPercent()))
                currentSale = sale;
        return category.findCurrentBestSale(currentSale);
    }

    public void addSale(SaleToCustomer sale) {
        sales.add(sale);
    }
    public void removeItems(int storeID, int amount) { //bought or thrown
        Location l = getStoreLocation(storeID);
        inStore.put(l, inStore.get(l)-amount);
    }
    public void moveItems(int storeID, int amount) { //from warehouse to store
        Location from = getWarehouseLocation(storeID);
        Location to = getStoreLocation(storeID);
        inWarehouse.put(from, inWarehouse.get(from)-amount);
        inStore.put(to, inStore.get(to)+amount);
    }
    public void addItems(int storeID, int amount) { //from supplier to warehouse
        Location l = getWarehouseLocation(storeID);
        inWarehouse.put(l, inWarehouse.get(l)+amount);
    }

    public void returnItems(int storeID, int amount) { //from customer to store
        Location l = getStoreLocation(storeID);
        inStore.put(l, inStore.get(l)+amount);
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
