package Domain.BusinessLayer.Inventory;

import Domain.PersistenceLayer.Controllers.ProductDataMapper;
import Domain.PersistenceLayer.Controllers.StockReportDataMapper;
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
    private static int locationIDCounter=1;
    private static int defectReportCounter=1;
    public static final ProductDataMapper productDataMapper = new ProductDataMapper();
    private final static StockReportDataMapper stockReportDataMapper = new StockReportDataMapper();

    public Set<Integer> getStoreIDs() { return stockReports.keySet(); }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getCategoryID() {return category.getID();}
    public double getOriginalPrice() { return price; }
    public void setName(String name) {
        this.name = name;
        productDataMapper.updateName(id, name);
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
        return getStockReport(store).getAmountInStore();
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
        DefectiveItems dir = new DefectiveItems(defectReportCounter++, Damaged, new Date(), storeID, id, amount, employeeID, description, inWarehouse);
        damagedItemReport.add(dir);
        return dir;
    }

    public DefectiveItems reportExpired(int storeID, int amount, int employeeID, String description, boolean inWarehouse) {
        removeItems(storeID, amount, inWarehouse);
        DefectiveItems eir = new DefectiveItems(defectReportCounter++, Expired, new Date(), storeID, id, amount, employeeID, description, inWarehouse);
        expiredItemReport.add(eir);
        return eir;
    }

    public DefectiveItems reportDefectiveForTest(int storeID, int amount, int employeeID, String description, Defect defect, Date date, boolean inWarehouse) {
        removeItems(storeID, amount, inWarehouse);
        DefectiveItems eir = new DefectiveItems(defectReportCounter++, defect, date, storeID, id, amount, employeeID, description, inWarehouse);
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
        Location storeLocation = new Location(locationIDCounter++, storeID, false, shelvesInStore);
        Location warehouseLocation = new Location(locationIDCounter++, storeID, true, shelvesInWarehouse);
        if (getStockReport(storeID)!=null)
            throw new IllegalArgumentException("Product " + name + " is already sold at store " + storeID);
        stockReports.put(storeID, new StockReport(storeID, id, 0, 0, minAmount, targetAmount, 0));
        stockReportDataMapper.insert(getStockReport(storeID));
        locations.add(storeLocation);
        locations.add(warehouseLocation);
    }

    public void removeLocation(int storeID) {
        if (getStockReport(storeID)==null)
            throw new IllegalArgumentException("Product " + name + " is not being sold at store " + storeID);
        removeStockReport(storeID);
        for (int i=0; i<locations.size(); i++) {
            if (locations.get(i).getStoreID()==storeID) {
                locations.remove(i);
                i--;
            }
        }
    }

    private void removeStockReport(int storeID) {
        stockReportDataMapper.remove(storeID, getStockReport(storeID).getProductID());
        stockReports.remove(storeID);
    }

    public List<SaleToCustomer> getSaleHistory() {
        List<SaleToCustomer> result = category.getSaleHistory();
        for (SaleToCustomer sale : sales) {
            if (sale.isPassed() || sale.isActive())
                result.add(sale);
        }
        return result;
    }

    public boolean isLow(int storeID) {
        return getStockReport(storeID).isLow();
    }

    public void removeSale(SaleToCustomer sale) {
        sales.remove(sale);
    }

    public boolean belongsToCategory(Category category) {
        return (this.category==category || this.category.belongsToCategory(category));
    }

    public void changeProductMin(int store, int min) {
        if (getStockReport(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store + " and has no min");
        stockReports.get(store).changeMin(min);
    }

    public void changeProductTarget(int store, int target) {
        if (getStockReport(store)==null)
            throw new IllegalArgumentException("Product " + id + " is not being sold in store " + store + " and has no min");
        stockReports.get(store).changeTarget(target);
    }

    public StockReport getStockReport(int store) {
        StockReport stockReport = stockReports.get(store);
        if (stockReport==null) {
            stockReport = stockReportDataMapper.get(store, id);
        }
        return stockReport;
    }

    public int getAmountForOrder(int storeID) {
        return getStockReport(storeID).getAmountForOrder();
    }

    public void addDelivery(int orderID, int storeID, int amount) {
        getStockReport(storeID).addDelivery(amount);
    }
}
