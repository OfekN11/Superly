package Domain.BusinessLayer;

import Domain.BusinessLayer.DiscountsAndSales.PurchaseFromSupplier;
import Domain.BusinessLayer.DiscountsAndSales.SaleToCustomer;
import Globals.Defect;

import java.util.*;

import static Globals.Defect.Damaged;
import static Globals.Defect.Expired;

public class Product {
    private int id;
    private String name;
    private Category category;
    private Map<Integer, StockReport> stockreports; //<storeID, stockReports>
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
    public int getCategoryID() {return category.getID();}
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
        this.name = name;
        this.category = category;
        this.weight = weight;
        this.price = price;
        this.manufacturerID = manufacturerID;
        this.supplierIdToProductIdOfTheSupplier = suppliers;
        sales = new ArrayList<>();
        damagedItemReport = new ArrayList<>();
        expiredItemReport = new ArrayList<>();
        purchaseFromSupplierList = new ArrayList<>();
        locations = new ArrayList<>();
        stockreports = new HashMap<>();
    }

    public double getWeight() {
        return weight;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public Integer getMinInStore(int store) {
        if (stockreports.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store);
        return stockreports.get(store).getMinAmountInStore();
    }
    public int getMaxInStore(int store) {
        if (stockreports.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store);
        return stockreports.get(store).getMaxAmountInStore();
    }

    public Integer getInStore(int store) {
//        if (inStore.get(store)==null)
//            throw new IllegalArgumentException("Product " + id + " is not sold in store " + store);
        return stockreports.get(store).getAmountInStore();
    }
    public Integer getInWarehouse(int store) {
//        if (inWarehouse.get(store)==null)
//            throw new IllegalArgumentException("Product " + id + " is not sold in store " + store);
        return stockreports.get(store).getAmountInWarehouse();
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

    public void removeItems(int storeID, int amount) { //bought
        if (!stockreports.containsKey(storeID))
            throw new IllegalArgumentException("Product: " + name + ", hasn't been added to the store");
        stockreports.get(storeID).removeItemsFromStore(amount);
    }

    public void moveItems(int storeID, int amount) { //from warehouse to store
        if (!stockreports.containsKey(storeID))
            throw new IllegalArgumentException("Product: " + name + ", hasn't been added to the store");
        stockreports.get(storeID).moveItems(amount);
    }
    public PurchaseFromSupplier addItems(int storeId, Date date, int supplierID, String description, int amountBought, int pricePaid, int originalPrice) {
        if (!stockreports.containsKey(storeId))
            throw new IllegalArgumentException("Product: " + name + ", hasn't been added to the store");
        stockreports.get(storeId).addItems(amountBought);
        PurchaseFromSupplier p = new PurchaseFromSupplier(purchaseFromSupplierList.size()+1, storeId, id, date, supplierID, description, amountBought, pricePaid, originalPrice);
        purchaseFromSupplierList.add(p);
        return p;

    }
    public double returnItems(int storeId, int amount, Date dateBought) { //from customer to store
        if (!stockreports.containsKey(storeId))
            throw new IllegalArgumentException("Product: " + name + ", hasn't been added to the store");
        stockreports.get(storeId).returnItems(amount);
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

    public DefectiveItems reportDefectiveForTest(int storeID, int amount, int employeeID, String description, Defect defect, Date date) {
        removeItems(storeID, amount);
        DefectiveItems eir = new DefectiveItems(defect, date, storeID, id, amount, employeeID, description);
        if (defect==Expired)
            expiredItemReport.add(eir);
        else
            damagedItemReport.add(eir);
        return eir;
    }

    public List<DefectiveItems> getDamagedItemReportsByStore(Date start, Date end, List<Integer> storeID) {
        end.setHours(24);
        end.setMinutes(0);
        end.setSeconds(-1);
        List<DefectiveItems> dirList = new ArrayList<>();
        for (DefectiveItems dir: damagedItemReport) {
            if (dir.inDates(start, end) && (storeID.contains(dir.getStoreID()) || storeID.size()==0))
                dirList.add(dir);
        }
        return dirList;
    }

    public Collection<DefectiveItems> getDamagedItemReports(Date start, Date end) {
        end.setHours(24);
        end.setMinutes(0);
        end.setSeconds(-1);
        List<DefectiveItems> dirList = new ArrayList<>();
        for (DefectiveItems dir: damagedItemReport) {
            if (dir.inDates(start, end))
                dirList.add(dir);
        }
        return dirList;
    }

    public List<DefectiveItems> getExpiredItemReportsByStore(Date start, Date end, List<Integer> storeID) {
        end.setHours(24);
        end.setMinutes(0);
        end.setSeconds(-1);
        List<DefectiveItems> eirList = new ArrayList<>();
        for (DefectiveItems eir: expiredItemReport) {
            if (eir.inDates(start, end) && (storeID.contains(eir.getStoreID()) || storeID.size()==0))
                eirList.add(eir);
        }
        return eirList;
    }

    public Collection<DefectiveItems> getExpiredItemReports(Date start, Date end) {
        end.setHours(24);
        end.setMinutes(0);
        end.setSeconds(-1);
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
        if (stockreports.containsKey(storeID))
            throw new IllegalArgumentException("Product " + name + " is already sold at store " + storeID);
        stockreports.put(storeID, new StockReport(storeID, id, name, 0, 0, minAmount, maxAmount));
        locations.add(storeLocation);
        locations.add(warehouseLocation);
    }

    public void removeLocation(Integer storeID) {
        if (!stockreports.containsKey(storeID))
            throw new IllegalArgumentException("Product " + name + " is not being sold at store " + storeID);
        stockreports.remove(storeID);
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
            if (sale.isPassed() || sale.isActive())
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

    public boolean isLow(int storeID) {
        return stockreports.get(storeID).isLow();
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

    public boolean belongsToCategory(Category category) {
        return (this.category==category || this.category.belongsToCategory(category));
    }

    public void changeProductMin(int store, int min) {
        if (stockreports.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store + " and has no min");
        stockreports.get(store).changeMin(min);
    }

    public void changeProductMax(int store, int max) {
        if (stockreports.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store + " and has no min");
        stockreports.get(store).changeMax(max);
    }

    public StockReport getStockReport(Integer store) {
        return stockreports.get(store);
    }
}
