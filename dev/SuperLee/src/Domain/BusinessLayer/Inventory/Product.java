package Domain.BusinessLayer.Inventory;

import Domain.BusinessLayer.Inventory.DiscountsAndSales.PurchaseFromSupplier;
import Domain.BusinessLayer.Inventory.DiscountsAndSales.SaleToCustomer;
import Domain.PersistenceLayer.Controllers.ProductDataMapper;
import Globals.Defect;

import java.util.*;

import static Globals.Defect.Damaged;
import static Globals.Defect.Expired;

public class Product {
    private int id;
    private String name;
    private Category category;
    private Map<Integer, StockReport> stockReports; //<storeID, stockReports>
    private List<Location> locations;
    private List<DefectiveItems> damagedItemReport;
    private List<DefectiveItems> expiredItemReport;
    private double weight;
    private String manufacturer;
    private double price;
    private List<SaleToCustomer> sales;
    private List<PurchaseFromSupplier> purchaseFromSupplierList;
    private static final ProductDataMapper productDataMapper = new ProductDataMapper();

    public Set<Integer> getStoreIDs() { return stockReports.keySet(); }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCategoryID() {return category.getID();}
    public double getOriginalPrice() { return price; }
    public void setName(String name) {
        this.name = name;
        productDataMapper.updateName(id, name);
//        for (StockReport s : stockReports.values()) {
//            s.changeName(name);
//        }
    }
    public void setPrice(double price) {
        this.price = price;
        productDataMapper.updatePrice(id, price);
    }
    public void setCategory(Category category) {
        if (category!=null)
            category.removeProduct(this);
        this.category = category;
        category.addProduct(this);
        productDataMapper.updateCategory(id, getCategoryID());
    }

    public Product(int id, String name, Category category, double weight, double price, String manufacturer) {
        this.id = id;
        this.name = name;
        this.name = name;
        this.category = category;
        this.weight = weight;
        this.price = price;
        this.manufacturer = manufacturer;
        sales = new ArrayList<>();
        damagedItemReport = new ArrayList<>();
        expiredItemReport = new ArrayList<>();
        purchaseFromSupplierList = new ArrayList<>();
        locations = new ArrayList<>();
        stockReports = new HashMap<>();
    }

    public double getWeight() {
        return weight;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Integer getInStore(int store) {
//        if (inStore.get(store)==null)
//            throw new IllegalArgumentException("Product " + id + " is not sold in store " + store);
        return stockReports.get(store).getAmountInStore();
    }
    public Integer getInWarehouse(int store) {
//        if (inWarehouse.get(store)==null)
//            throw new IllegalArgumentException("Product " + id + " is not sold in store " + store);
        return stockReports.get(store).getAmountInWarehouse();
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
            if ((sale.isActive() && currentSale==null) || (sale.isActive() && currentSale!=null && currentSale.getPercent()<sale.getPercent()))
                currentSale = sale;
        return category.findCurrentBestSale(currentSale);
    }

    public void addSale(SaleToCustomer sale) {
        sales.add(sale);
    }

    public void removeItems(int storeID, int amount, boolean inWarehouse) { //bought
        if (!stockReports.containsKey(storeID))
            throw new IllegalArgumentException("Product: " + name + ", hasn't been added to the store");
        stockReports.get(storeID).removeItemsFromStore(amount, inWarehouse);
    }

    public void moveItems(int storeID, int amount) { //from warehouse to store
        if (!stockReports.containsKey(storeID))
            throw new IllegalArgumentException("Product: " + name + ", hasn't been added to the store");
        stockReports.get(storeID).moveItems(amount);
    }
    public PurchaseFromSupplier addItems(int storeId, Date date, int supplierID, int amountBought, double pricePaid, double originalPrice, int orderID) {
        if (!stockReports.containsKey(storeId))
            throw new IllegalArgumentException("Product: " + name + ", hasn't been added to the store");
        stockReports.get(storeId).addItems(amountBought, orderID);
        PurchaseFromSupplier p = new PurchaseFromSupplier(purchaseFromSupplierList.size()+1, storeId, id, date, supplierID, amountBought, pricePaid, originalPrice);
        purchaseFromSupplierList.add(p);
        return p;

    }
    public double returnItems(int storeId, int amount, Date dateBought) { //from customer to store
        if (!stockReports.containsKey(storeId))
            throw new IllegalArgumentException("Product: " + name + ", hasn't been added to the store");
        stockReports.get(storeId).returnItems(amount);
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

    public DefectiveItems reportDamaged(int storeID, int amount, int employeeID, String description, boolean inWarehouse) {
        removeItems(storeID, amount, inWarehouse);
        DefectiveItems dir = new DefectiveItems(Damaged, new Date(), storeID, id, amount, employeeID, description, inWarehouse);
        damagedItemReport.add(dir);
        return dir;
    }

    public DefectiveItems reportExpired(int storeID, int amount, int employeeID, String description, boolean inWarehouse) {
        removeItems(storeID, amount, inWarehouse);
        DefectiveItems eir = new DefectiveItems(Expired, new Date(), storeID, id, amount, employeeID, description, inWarehouse);
        expiredItemReport.add(eir);
        return eir;
    }

    public DefectiveItems reportDefectiveForTest(int storeID, int amount, int employeeID, String description, Defect defect, Date date, boolean inWarehouse) {
        removeItems(storeID, amount, inWarehouse);
        DefectiveItems eir = new DefectiveItems(defect, date, storeID, id, amount, employeeID, description, inWarehouse);
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

    public void addLocation(int storeID, List<Integer> shelvesInStore, List<Integer> shelvesInWarehouse, int minAmount, int targetAmount) {
        Location storeLocation = new Location(storeID, false, shelvesInStore);
        Location warehouseLocation = new Location(storeID, true, shelvesInWarehouse);
        if (stockReports.containsKey(storeID))
            throw new IllegalArgumentException("Product " + name + " is already sold at store " + storeID);
        stockReports.put(storeID, new StockReport(storeID, id, name, 0, 0, minAmount, targetAmount));
        locations.add(storeLocation);
        locations.add(warehouseLocation);
    }

    public void removeLocation(int storeID) {
        if (!stockReports.containsKey(storeID))
            throw new IllegalArgumentException("Product " + name + " is not being sold at store " + storeID);
        stockReports.remove(storeID);
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
        return stockReports.get(storeID).isLow();
    }

    public void removeSale(SaleToCustomer sale) {
        sales.remove(sale);
    }

//    public void addSupplier(int supplierID, int productIDWithSupplier) {
//        if (supplierIdToProductIdOfTheSupplier.containsKey(supplierID))
//            throw new IllegalArgumentException("Supplier" + supplierID + " is already listed as a supplier");
//        supplierIdToProductIdOfTheSupplier.put(supplierID, productIDWithSupplier);
//    }
//
//    public void removeSupplier(int supplierID) {
//        if (!supplierIdToProductIdOfTheSupplier.containsKey(supplierID))
//            throw new IllegalArgumentException("Supplier" + supplierID + " is not registered as a supplier of " + id);
//        supplierIdToProductIdOfTheSupplier.remove(supplierID);
//    }

    public boolean belongsToCategory(Category category) {
        return (this.category==category || this.category.belongsToCategory(category));
    }

    public void changeProductMin(int store, int min) {
        if (stockReports.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store + " and has no min");
        stockReports.get(store).changeMin(min);
    }

    public void changeProductTarget(int store, int target) {
        if (stockReports.get(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store + " and has no min");
        stockReports.get(store).changeTarget(target);
    }

    public StockReport getStockReport(Integer store) {
        return stockReports.get(store);
    }

    public int getAmountForOrder(int storeID) {
        return stockReports.get(storeID).getAmountForOrder();
    }

    public void addDelivery(int orderID, int storeID, int amount) {
        getStockReport(storeID).addDelivery(orderID, amount);
    }
}
