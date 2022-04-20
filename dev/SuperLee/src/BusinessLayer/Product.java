package BusinessLayer;

import java.util.*;

public class Product {
    public Product(int id, String name, Category category, int weight, double price, List<Supplier> suppliers) {
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
    private List<DamagedItemReport> damagedItemReport;
    private List<ExpiredItemReport> expiredItemReport;
    private int weight;
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

    public void reportDamaged(int storeID, int amount, String description) {
        damagedItemReport.add(new DamagedItemReport(new Date(), storeID, amount, description));
    }
    public void reportExpired(int storeID, int amount) {
        expiredItemReport.add(new ExpiredItemReport(new Date(), storeID, amount));
    }
    public List<DamagedItemReport> getDamagedItemReports(Date start, Date end, List<Integer> storeID) {
        List<DamagedItemReport> dirList = new ArrayList<>();
        for (DamagedItemReport dir: damagedItemReport) {
            if (dir.inDates(start, end) && (storeID.contains(dir.getStoreID()) || storeID.size()==0))
                dirList.add(dir);
        }
        return dirList;
    }
    public List<ExpiredItemReport> getExpiredItemReports(Date start, Date end, List<Integer> storeID) {
        List<ExpiredItemReport> eirList = new ArrayList<>();
        for (ExpiredItemReport eir: expiredItemReport) {
            if (eir.inDates(start, end) && (storeID.contains(eir.getStoreID()) || storeID.size()==0))
                eirList.add(eir);
        }
        return eirList;
    }
}
