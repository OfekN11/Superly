package BusinessLayer;

import BusinessLayer.DiscountsAndSales.DiscountFromSupplier;
import BusinessLayer.DiscountsAndSales.SaleToCustomer;

import java.util.*;

public class Product {
    private int id;
    private String name;
    private Category category;
    private Map<Integer, Integer> minAmounts; //<storeID, minAmount in total>
    private Map<Integer, Integer> maxAmounts; //<storeID, maxAmount in total>
    private Map<Location, Integer> inStore; //current amount in store.
    private Map<Location, Integer> inWarehouse; //current amount in warehouse.
    private List<DefectiveItems> damagedItemReport;
    private List<DefectiveItems> expiredItemReport;
    private double weight;
    private List<Supplier> suppliers;
    private double price;
    private List<SaleToCustomer> sales;
    private List<DiscountFromSupplier> discountFromSupplierList;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getOriginalPrice() {
        return price;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setCategory(Category category) {
        if (category!=null)
            category.removeProduct(this);
        this.category = category;
        category.addProduct(this);
    }
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
        minAmounts = new HashMap<>();
        maxAmounts = new HashMap<>();
        damagedItemReport = new ArrayList<>();
        expiredItemReport = new ArrayList<>();
        discountFromSupplierList = new ArrayList<>();
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

    public double returnItems(int storeID, int amount, Date dateBought) { //from customer to store
        Location l = getStoreLocation(storeID);
        inStore.put(l, inStore.get(l)+amount);
        return amount*getPriceOnDate(dateBought);
    }

    private double getPriceOnDate(Date dateBought) {
        List<SaleToCustomer> salesActive = new ArrayList<>();
        for (SaleToCustomer s : sales) {
            if (s.wasActive(dateBought))
                salesActive.add(s);
        }
        salesActive.addAll(category.getSalesOnDate(dateBought));
        SaleToCustomer bestSale = null;
        for (SaleToCustomer sale: salesActive)
            if (bestSale==null || bestSale.getPercent()<sale.getPercent())
                bestSale = sale;
        return bestSale.getPercent()*getOriginalPrice()/100; //what if price in general changed? do we need a log of the prices?
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

    public DefectiveItems reportDamaged(int storeID, int amount, String description) {
        DefectiveItems dir = new DefectiveItems(DefectiveItems.Defect.Damaged, new Date(), storeID, amount, description);
        damagedItemReport.add(dir);
        return dir;
    }
    public DefectiveItems reportExpired(int storeID, int amount, String description) {
        DefectiveItems eir = new DefectiveItems(DefectiveItems.Defect.Expired, new Date(), storeID, amount, description);
        expiredItemReport.add(eir);
        return eir;
    }
    public List<DefectiveItems> getDamagedItemReportsByStore(Date start, Date end, List<Integer> storeID) {
        List<DefectiveItems> dirList = new ArrayList<>();
        for (DefectiveItems dir: damagedItemReport) {
            if (dir.inDates(start, end) && (storeID.contains(dir.getStoreID()) || storeID.size()==0))
                dirList.add(dir);
        }
        return dirList;
    }

    public Collection<DefectiveItems> getDamagedItemReports(Date start, Date end) {
        List<DefectiveItems> dirList = new ArrayList<>();
        for (DefectiveItems dir: damagedItemReport) {
            if (dir.inDates(start, end))
                dirList.add(dir);
        }
        return dirList;
    }

    public List<DefectiveItems> getExpiredItemReportsByStore(Date start, Date end, List<Integer> storeID) {
        List<DefectiveItems> eirList = new ArrayList<>();
        for (DefectiveItems eir: expiredItemReport) {
            if (eir.inDates(start, end) && (storeID.contains(eir.getStoreID()) || storeID.size()==0))
                eirList.add(eir);
        }
        return eirList;
    }

    public Collection<DefectiveItems> getExpiredItemReports(Date start, Date end) {
        List<DefectiveItems> eirList = new ArrayList<>();
        for (DefectiveItems eir: expiredItemReport) {
            if (eir.inDates(start, end))
                eirList.add(eir);
        }
        return eirList;
    }

    public void addLocation(int storeID, int shelfInStore, int shelfInWarehouse, int minAmount, int maxAmount) {
        Location storeLocation = new Location(storeID, false, shelfInStore);
        Location warehouseLocation = new Location(storeID, true, shelfInWarehouse);
        minAmounts.put(storeID, minAmount);
        maxAmounts.put(storeID, maxAmount);
        inStore.put(storeLocation, 0);
        inWarehouse.put(warehouseLocation, 0);
    }

    public void removeLocation(int storeID) {
        Location storeLocation = getStoreLocation(storeID);
        Location warehouseLocation = getWarehouseLocation(storeID);
        minAmounts.remove(storeID);
        maxAmounts.remove(storeID);
        inStore.remove(storeLocation);
        inWarehouse.remove(warehouseLocation);
    }

    public List<SaleToCustomer> getSaleHistory() {
        List<SaleToCustomer> result = category.getSaleHistory();
        for (SaleToCustomer sale : sales) {
            if (sale.isPassed())
                result.add(sale);
        }
        return result;
    }

    public List<DiscountFromSupplier> getDiscountFromSupplierHistory() {
        return discountFromSupplierList;
    }
    public DiscountFromSupplier addDiscountFromSupplier(Date date, int supplierID, String description, int amountBought, int pricePaid, int originalPrice) {
        return new DiscountFromSupplier(discountFromSupplierList.size()+1, date, supplierID, description, amountBought, pricePaid, originalPrice);
    }

}
