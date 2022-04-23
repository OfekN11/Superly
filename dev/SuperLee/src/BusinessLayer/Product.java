package BusinessLayer;

import BusinessLayer.DiscountsAndSales.PurchaseFromSupplier;
import BusinessLayer.DiscountsAndSales.SaleToCustomer;

import java.util.*;

import static Globals.Defect.Damaged;
import static Globals.Defect.Expired;

public class Product {
    private int id;
    private String name;
    private Category category;
    private Map<Integer, Integer> minAmounts; //<storeID, minAmount in total>
    private Map<Integer, Integer> maxAmounts; //<storeID, maxAmount in total>
    private Map<Integer, Integer> inStore; //current amount in store.
    private Map<Integer, Integer> inWarehouse; //current amount in warehouse.
    private List<Location> locations;
    private List<DefectiveItems> damagedItemReport;
    private List<DefectiveItems> expiredItemReport;
    private double weight;
    private int manufacturerID;
    private Map<Integer, Integer> supplierIdToProductIdOfTheSupplier;
    private double price;
    private List<SaleToCustomer> sales;
    private List<PurchaseFromSupplier> purchaseFromSupplierList;
    public int getId() { return id; }
    public String getName() { return name; }
    public double getOriginalPrice() { return price; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(Category category) {
        if (category!=null)
            category.removeProduct(this);
        this.category = category;
        category.addProduct(this);
    }
    public Product(int id, String name, Category category, double weight, double price, Map<Integer, Integer> suppliers, int manufacturerID) {
        this.id = id;
        this.name = name;
        this.category = category;
        category.addProduct(this);
        this.weight = weight;
        this.price = price;
        this.manufacturerID = manufacturerID;
        this.supplierIdToProductIdOfTheSupplier = suppliers;
        inStore = new HashMap<Integer, Integer>(); //needs to filled with all stores locations.
        inWarehouse = new HashMap<Integer, Integer>(); //needs to filled with all warehouses locations.
        sales = new ArrayList<>();
        minAmounts = new HashMap<>();
        maxAmounts = new HashMap<>();
        damagedItemReport = new ArrayList<>();
        expiredItemReport = new ArrayList<>();
        purchaseFromSupplierList = new ArrayList<>();
        locations = new ArrayList<>();
    }

    public Integer getMinInStore(int store) {
        if (inStore.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store);
        return minAmounts.get(store);
    }
    public int getMaxInStore(int store) {
        if (inStore.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store);
        return maxAmounts.get(store);
    }

    public Integer getInStore(int store) {
        if (inStore.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not sold in store " + store);
        return inStore.get(store);
    }
    public Integer getInWarehouse(int store) {
        if (inWarehouse.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not sold in store " + store);
        return inWarehouse.get(store);
    }

    public double getCurrentPrice() {
        SaleToCustomer BestCurrentSale = getCurrentSale();
        if (BestCurrentSale==null)
            return price;
        return price*(100-BestCurrentSale.getPercent())/100;
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

    public void removeItems(int storeID, int amount) { //bought or thrown=
        if (inStore.get(storeID)-amount<0)
            throw new IllegalArgumentException("Can not buy or remove more items than in the store - please check amount");
        inStore.put(storeID, inStore.get(storeID)-amount);
    }
    public void moveItems(int storeID, int amount) { //from warehouse to store
        if (inWarehouse.get(storeID)-amount<0)
            throw new IllegalArgumentException("Can not move more items than in the warehouse");
        inWarehouse.put(storeID, inWarehouse.get(storeID)-amount);
        inStore.put(storeID, inStore.get(storeID)+amount);
    }
    public void addItems(int storeID, int amount) { //from supplier to warehouse
        inWarehouse.put(storeID, inWarehouse.get(storeID)+amount);
    }

    public double returnItems(int storeID, int amount, Date dateBought) { //from customer to store
        inStore.put(storeID, inStore.get(storeID)+amount);
        return amount*getPriceOnDate(dateBought);
    }

    private double getPriceOnDate(Date dateBought) {
        if (dateBought.after(new Date()))
            throw new IllegalArgumentException("An item isn't able to be bought after present time");
        List<SaleToCustomer> salesActive = new ArrayList<>();
        for (SaleToCustomer s : sales) {
            if (s.wasActive(dateBought))
                salesActive.add(s);
        }
        salesActive.addAll(category.getSalesOnDate(dateBought));
        SaleToCustomer bestSale = null;
        for (SaleToCustomer sale: salesActive)
            if (bestSale==null || bestSale.getPercent()<sale.getPercent()) {
                bestSale = sale;
            }
        if (bestSale==null)
            return getOriginalPrice();
        return getOriginalPrice()*(100-bestSale.getPercent())/100; //what if price in general changed? do we need a log of the prices?
    }

    public DefectiveItems reportDamaged(int storeID, int amount, int employeeID, String description) {
        removeItems(storeID, amount);
        DefectiveItems dir = new DefectiveItems(Damaged, new Date(), storeID, id, amount, employeeID, description);
        damagedItemReport.add(dir);
        return dir;
    }
    public DefectiveItems reportExpired(int storeID, int amount, int employeeID, String description) {
        removeItems(storeID, amount);
        DefectiveItems eir = new DefectiveItems(Expired, new Date(), storeID, id, amount, employeeID, description);
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

    public void addLocation(int storeID, List<Integer> shelvesInStore, List<Integer> shelvesInWarehouse, int minAmount, int maxAmount) {
        Location storeLocation = new Location(storeID, false, shelvesInStore);
        Location warehouseLocation = new Location(storeID, true, shelvesInWarehouse);
        minAmounts.put(storeID, minAmount);
        maxAmounts.put(storeID, maxAmount);
        inStore.put(storeID, 0);
        inWarehouse.put(storeID, 0);
        locations.add(storeLocation);
        locations.add(warehouseLocation);
    }

    public void removeLocation(int storeID) {
        minAmounts.remove(storeID);
        maxAmounts.remove(storeID);
        inStore.remove(storeID);
        inWarehouse.remove(storeID);
        for (int i=0; i<locations.size(); i++) {
            if (locations.get(i).getStoreID()==storeID) {
                locations.remove(i);
                i--;
            }
        }
    }

    public List<SaleToCustomer> getSaleHistory() {
        List<SaleToCustomer> result = category.getSaleHistory();
        for (SaleToCustomer sale : sales) {
            if (sale.isPassed())
                result.add(sale);
        }
        return result;
    }

    public List<PurchaseFromSupplier> getPurchaseFromSupplierList() {
        return purchaseFromSupplierList;
    }

    public List<PurchaseFromSupplier> getDiscountFromSupplierHistory() {
        List<PurchaseFromSupplier> purchaseFromSuppliers = new ArrayList<>();
        for (PurchaseFromSupplier p : purchaseFromSupplierList)
            if (p.isDiscount())
                purchaseFromSuppliers.add(p);
        return purchaseFromSuppliers;
    }

    public PurchaseFromSupplier addPurchaseFromSupplier(Date date, int supplierID, String description, int amountBought, int pricePaid, int originalPrice) {
        return new PurchaseFromSupplier(purchaseFromSupplierList.size()+1, date, supplierID, description, amountBought, pricePaid, originalPrice);
    }

    public boolean isLow(int storeID) {
        return inStore.get(storeID)+inWarehouse.get(storeID)<minAmounts.get(storeID);
    }

    public void removeSale(SaleToCustomer sale) {
        sales.remove(sale);
    }

    public void addSupplier(int supplierID, int productIDWithSupplier) {
        if (supplierIdToProductIdOfTheSupplier.containsKey(supplierID))
            throw new IllegalArgumentException("Supplier" + supplierID + " is already listed as a supplier");
        supplierIdToProductIdOfTheSupplier.put(supplierID, productIDWithSupplier);
    }

    public void removeSupplier(int supplierID) {
        if (!supplierIdToProductIdOfTheSupplier.containsKey(supplierID))
            throw new IllegalArgumentException("Supplier" + supplierID + " is not registered as a supplier of " + id);
        supplierIdToProductIdOfTheSupplier.remove(supplierID);
    }

    public boolean isUnderMin(int store) {
        return (inStore.get(store)+inWarehouse.get(store)<minAmounts.get(store));
    }

    public boolean belongsToCategory(Category category) {
        return (this.category==category || this.category.belongsToCategory(category));
    }
}
