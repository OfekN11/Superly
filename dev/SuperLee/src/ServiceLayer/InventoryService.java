package ServiceLayer;


import BusinessLayer.DefectiveItems;
import BusinessLayer.DiscountsAndSales.SaleToCustomer;
import BusinessLayer.DiscountsAndSales.PurchaseFromSupplier;
import BusinessLayer.InventoryController;
import BusinessLayer.DiscountsAndSales.SaleToCustomer;
import ServiceLayer.Objects.*;

import java.util.*;

/**
 * Service controller for Inventory management operations
 *
 * All InventoryService's methods return Results detailing the success. encapsulating values/error messages
 */
public class InventoryService {

    private final InventoryController controller;

    public InventoryService(){
        controller = new InventoryController();
    }

    /**
     * Loads test data
     *
     * @return Result detailing success of operation
     */
    public Result<Object> loadTestData(){
        try {
            controller.loadTestData();
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * gets store ids of existing stores
     *
     * @return Result detailing success of operation, holding list of store ids
     */
    public Result<List<Integer>> getStoreIDs(){
        try {
            return Result.makeOk(controller.getStoreIDs());
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * gets List of sales which are current or upcoming
     *
     * @return Result detailing success of operation, holding list of sales
     */
    public Result<List<Sale>> getRemovableSales(){
        try {
            List<SaleToCustomer> removableSales = controller.getRemovableSales();
            List<Sale> result = new ArrayList<>();
            for (SaleToCustomer sale: removableSales) {
                result.add(new Sale(sale));
            }
            return Result.makeOk(result);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Loads data from persistence layer
     *
     * @return Result detailing success of operation
     */
    public Result<Object> loadData(){
        try {
            controller.loadData();
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Adds new product to catalog
     *
     * @return Result detailing success of operation, containing the new Product
     */
    public Result<Product> newProduct(String name, int categoryID, int weight, double price, Map<Integer, Integer> suppliers, int manufacturerID){
        try {
            BusinessLayer.Product p = controller.newProduct(name, categoryID, weight, price, suppliers, manufacturerID);
            return Result.makeOk(new Product(p));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Removes product from catalog
     *
     * @return Result detailing success of operation
     */
    public Result<Object> deleteProduct(int id){
        try {
            controller.deleteProduct(id);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * add sale on specified categories and/or items
     *
     * @return Result detailing success of operation, containing the new sale
     */
    public Result<Sale> addSale(List<Integer> categories, List<Integer> products, int percent, Date start, Date end){
        try {
            SaleToCustomer s = controller.addSale(categories, products, percent, start, end);
            return Result.makeOk(new Sale(s));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * delete an upcoming or current sale
     *
     * @return Result detailing success of operation
     */
    public Result<Object> removeSale(int saleID){
        try {
            controller.removeSale(saleID);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * add product to the items the specified store sells
     *
     * @return Result detailing success of operation, containing the specified product
     */
    public Result<Product> addProductToStore(int storeID, List<Integer> shelvesInStore, List<Integer> shelvesInWarehouse, int productID, int minAmount, int maxAmount){
        try {
            BusinessLayer.Product p = controller.addProductToStore(storeID, shelvesInStore, shelvesInWarehouse, productID, minAmount, maxAmount);
            return Result.makeOk(new Product(p));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * remove product from items that the specified store sells
     *
     * @return Result detailing success of operation, containing the specified product
     */
    public Result<Product> removeProductFromStore(int storeID, int productID){
        try {
            BusinessLayer.Product p = controller.removeProductFromStore(storeID, productID);
            return Result.makeOk(new Product(p));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * add purchase from supplier on specified product to a store
     *
     * @return Result detailing success of operation, containing the info on the purchase
     */
    public Result<PurchaseFromSupplierReport> addItems(int storeID, int productID, int supplierID, String description, int amountBought, int pricePaid, int originalPrice){
        try {
            PurchaseFromSupplier dr = controller.addItems(storeID, productID, supplierID, description, amountBought, pricePaid, originalPrice);
            return Result.makeOk(new PurchaseFromSupplierReport(dr));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get History of all purchase from the different suppliers on specified product
     *
     * @return Result detailing success of operation, containing the info on the purchase
     */
    public Result<List<PurchaseFromSupplierReport>> getPurchaseFromSupplierHistory(int productId){
        try {
            List<PurchaseFromSupplier> purchaseFromSupplierList = controller.getPurchaseFromSupplierHistory(productId);
            List<PurchaseFromSupplierReport> purchaseFromSupplierReports = new ArrayList<>();
            for (PurchaseFromSupplier d : purchaseFromSupplierList)
                purchaseFromSupplierReports.add(new PurchaseFromSupplierReport(d));
            return Result.makeOk(purchaseFromSupplierReports);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get History of all discounts from the different suppliers on specified product
     *
     * @return Result detailing success of operation, containing the info on the discounts
     */
    public Result<List<PurchaseFromSupplierReport>> getDiscountFromSupplierHistory(int productId){
        try {
            List<PurchaseFromSupplier> purchaseFromSupplierList = controller.getDiscountFromSupplierHistory(productId);
            List<PurchaseFromSupplierReport> purchaseFromSupplierReports = new ArrayList<>();
            for (PurchaseFromSupplier d : purchaseFromSupplierList)
                purchaseFromSupplierReports.add(new PurchaseFromSupplierReport(d));
            return Result.makeOk(purchaseFromSupplierReports);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get History of all sales the store had on a specific product
     *
     * @return Result detailing success of operation, containing the list of the sales
     */
    public Result<List<Sale>> getSaleHistoryByProduct(int productId){
        try {
            List<SaleToCustomer> saleToCustomerList = controller.getSaleHistoryByProduct(productId);
            List<Sale> sales = new ArrayList<>();
            for (SaleToCustomer sale : saleToCustomerList) {
                sales.add(new Sale(sale));
            }
            return Result.makeOk(sales);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get History of all sales the store had on a specific category
     *
     * @return Result detailing success of operation, containing the list of the sales
     */
    public Result<List<Sale>> getSaleHistoryByCategory(int categoryID){
        try {
            List<SaleToCustomer> saleToCustomerList = controller.getSaleHistoryByCategory(categoryID);
            List<Sale> saleList = new ArrayList<>();
            for (SaleToCustomer s : saleToCustomerList) {
                saleList.add(new Sale(s));
            }
            return Result.makeOk(saleList);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get History of reported damaged and expired items, in the time range specified, in specified stores
     *
     * @return Result detailing success of operation, containing the list of the reports
     */
    public Result<List<DefectiveItemReport>> getDefectiveItemsByStore(Date start, Date end, List<Integer> storeIDs){
        try {
            List<DefectiveItems> dirs = controller.getDefectiveItemsByStore(start, end, storeIDs);
            List<DefectiveItemReport> SLdirs = new ArrayList<>();
            for (BusinessLayer.DefectiveItems dir : dirs)
                SLdirs.add(new DefectiveItemReport(dir));
            return Result.makeOk(SLdirs);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get History of reported damaged and expired items, in the time range specified, in specified categories
     *
     * @return Result detailing success of operation, containing the list of the reports
     */
    public Result<List<DefectiveItemReport>> getDefectiveItemsByCategory(Date start, Date end, List<Integer> categoryIDs){
        try {
            List<DefectiveItems> dirs = controller.getDefectiveItemsByCategory(start, end, categoryIDs);
            List<DefectiveItemReport> SLdirs = new ArrayList<>();
            for (BusinessLayer.DefectiveItems dir : dirs)
                SLdirs.add(new DefectiveItemReport(dir));
            return Result.makeOk(SLdirs);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get History of reported damaged and expired items, in the time range specified, of specified products
     *
     * @return Result detailing success of operation, containing the list of the reports
     */
    public Result<List<DefectiveItemReport>> getDefectiveItemsByProduct(Date start, Date end, List<Integer> productIDs){
        try {
            List<DefectiveItems> dirs = controller.getDefectiveItemsByProduct(start, end, productIDs);
            List<DefectiveItemReport> SLdirs = new ArrayList<>();
            for (BusinessLayer.DefectiveItems dir : dirs)
                SLdirs.add(new DefectiveItemReport(dir));
            return Result.makeOk(SLdirs);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get list of all products in catalog
     *
     * @return Result detailing success of operation, containing list of the products
     */
    public Result<List<Product>> getProducts(){
        try {
            Collection<BusinessLayer.Product> products = controller.getProducts();
            List<Product> productList = new ArrayList<>();
            for (BusinessLayer.Product p : products) {
                productList.add(new Product(p));
            }
            return Result.makeOk(productList);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get list of all products in specified categories
     *
     * @return Result detailing success of operation, containing list of the productsv
     */
    public Result<List<Product>> getProductsFromCategory(List<Integer> categoryIDs){
        try {
            Collection<BusinessLayer.Product> products = controller.getProductsFromCategory(categoryIDs);
            List<Product> productList = new ArrayList<>();
            for (BusinessLayer.Product p : products) {
                productList.add(new Product(p));
            }
            return Result.makeOk(productList);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get list of all Categories in the store
     *
     * @return Result detailing success of operation, containing list of the categories
     */
    public Result<List<Category>> getCategories(){
        try {
            Collection<BusinessLayer.Category> categories = controller.getCategories();
            List<Category> categoryList = new ArrayList<>();
            for (BusinessLayer.Category c : categories) {
                categoryList.add(new Category(c));
            }
            return Result.makeOk(categoryList);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * buy items from specified store
     *
     * @return Result detailing success of operation, containing price of the products
     */
    public Result<Double> buyItems(int storeID, int productID, int amount){
        try {
            return Result.makeOk(controller.buyItems(storeID, productID, amount));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Stock report of all items under the defined minimum amount per store
     *
     * @return Result detailing success of operation, with list of stockreports
     */
    public Result<List<StockReport>> getMinStockReport(){
        try {
            List<BusinessLayer.StockReport> minStock = controller.getMinStockReport();
            List<StockReport> stockReports = new ArrayList<>();
            for (BusinessLayer.StockReport s : minStock)
                stockReports.add(new StockReport(s));
            return Result.makeOk(stockReports);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Stock report of all items under the defined minimum amount in specified stores and categories
     *
     * @return Result detailing success of operation, with list of stockreports
     */
    public Result<List<StockReport>> storeStockReport(List<Integer> stores, List<Integer> categories){
        try {
            List<BusinessLayer.StockReport> stock = controller.getStockReport(stores, categories);
            List<StockReport> stockReports = new ArrayList<>();
            for (BusinessLayer.StockReport s : stock)
                stockReports.add(new StockReport(s));
            return Result.makeOk(stockReports);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Query as to whether specified item is under defined minimum in store
     *
     * @return Result detailing success of operation, with boolean true if the amount in the store is under the defined minimum
     */
    public Result<Boolean> isUnderMin(int store, int product){
        try {
            return Result.makeOk(controller.isUnderMin(store, product));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Report and remove damage items from the store
     *
     * @return Result detailing success of operation, containing the report details
     */
    public Result<DefectiveItemReport> reportDamaged(int storeID, int productID, int amount, int employeeID, String description){
        try {
            return Result.makeOk(new DefectiveItemReport(controller.reportDamaged(storeID, productID, amount, employeeID, description)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Report and remove expired items from the store
     *
     * @return Result detailing success of operation, containing the report details
     */
    public Result<DefectiveItemReport> reportExpired(int storeID, int productID, int amount, int employeeID, String description){
        try {
            return Result.makeOk(new DefectiveItemReport(controller.reportExpired(storeID, productID, amount, employeeID, description)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Query of all damaged items from specified stores in the date range
     *
     * @return Result detailing success of operation, containing a list of the reports
     */
    public Result<List<DefectiveItemReport>> getDamagedItemsReportByStore(Date start, Date end, List<Integer> storeIDs){
        try {
            List<BusinessLayer.DefectiveItems> dirs = controller.getDamagedItemReportsByStore(start, end, storeIDs);
            List<DefectiveItemReport> SLdirs = new ArrayList<>();
            for (BusinessLayer.DefectiveItems dir : dirs)
                SLdirs.add(new DefectiveItemReport(dir));
            return Result.makeOk(SLdirs);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Query of all damaged items from specified categories in the date range
     *
     * @return Result detailing success of operation, containing a list of the reports
     */
    public Result<List<DefectiveItemReport>> getDamagedItemsReportByCategory(Date start, Date end, List<Integer> categoryIDs){
        try {
            List<BusinessLayer.DefectiveItems> dirs = controller.getDamagedItemReportsByCategory(start, end, categoryIDs);
            List<DefectiveItemReport> SLdirs = new ArrayList<>();
            for (BusinessLayer.DefectiveItems dir : dirs)
                SLdirs.add(new DefectiveItemReport(dir));
            return Result.makeOk(SLdirs);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Query of all damaged items of specified products in the date range
     *
     * @return Result detailing success of operation, containing a list of the reports
     */
    public Result<List<DefectiveItemReport>> getDamagedItemsReportByProduct(Date start, Date end, List<Integer> productIDs){
        try {
            List<BusinessLayer.DefectiveItems> dirs = controller.getDamagedItemReportsByProduct(start, end, productIDs);
            List<DefectiveItemReport> SLdirs = new ArrayList<>();
            for (BusinessLayer.DefectiveItems dir : dirs)
                SLdirs.add(new DefectiveItemReport(dir));
            return Result.makeOk(SLdirs);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Query of all expired items from specified stores in the date range
     *
     * @return Result detailing success of operation, containing a list of the reports
     */
    public Result<List<DefectiveItemReport>> getExpiredItemReportsByStore(Date start, Date end, List<Integer> storeIDs){
        try {
            List<BusinessLayer.DefectiveItems> expiredItemReports = controller.getExpiredItemReportsByStore(start, end, storeIDs);
            List<DefectiveItemReport> expiredItemReportList = new ArrayList<>();
            for (BusinessLayer.DefectiveItems eir : expiredItemReports)
                expiredItemReportList.add(new DefectiveItemReport(eir));
            return Result.makeOk(expiredItemReportList);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Query of all expired items from specified categories in the date range
     *
     * @return Result detailing success of operation, containing a list of the reports
     */
    public Result<List<DefectiveItemReport>> getExpiredItemReportsByCategory(Date start, Date end, List<Integer> categoryIDs){
        try {
            List<BusinessLayer.DefectiveItems> expiredItemReports = controller.getExpiredItemReportsByCategory(start, end, categoryIDs);
            List<DefectiveItemReport> expiredItemReportList = new ArrayList<>();
            for (BusinessLayer.DefectiveItems eir : expiredItemReports)
                expiredItemReportList.add(new DefectiveItemReport(eir));
            return Result.makeOk(expiredItemReportList);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Query of all expired items of specified products in the date range
     *
     * @return Result detailing success of operation, containing a list of the reports
     */
    public Result<List<DefectiveItemReport>> getExpiredItemReportsByProduct(Date start, Date end, List<Integer> productIDs){
        try {
            List<BusinessLayer.DefectiveItems> expiredItemReports = controller.getExpiredItemReportsByProduct(start, end, productIDs);
            List<DefectiveItemReport> expiredItemReportList = new ArrayList<>();
            for (BusinessLayer.DefectiveItems eir : expiredItemReports)
                expiredItemReportList.add(new DefectiveItemReport(eir));
            return Result.makeOk(expiredItemReportList);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Move product from warehouse to store
     *
     * @return Result detailing success of operation
     */
    public Result<Object> moveItems(int storeID, int productID, int amount){
        try {
            controller.moveItems(storeID, productID, amount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Customer returns items to a store
     *
     * @return Result detailing success of operation, containing the price the customer paid for the items
     */
    public Result<Double> returnItems(int storeID, int productID, int amount, Date dateBought){
        try {
            return Result.makeOk(controller.returnItems(storeID, productID, amount, dateBought));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Add a new store to the system
     *
     * @return Result detailing success of operation
     */
    public Result<Integer> addStore(){
        try {
            return Result.makeOk(controller.addStore());
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Remove a store from the system
     *
     * @return Result detailing success of operation
     */
    public Result<Object> removeStore(int storeID){
        try {
            controller.removeStore(storeID);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Change product Price
     *
     * @return Result detailing success of operation, containing the edited Product
     */
    public Result<Product> editProductPrice(int productID, double newPrice){
        try {
            return Result.makeOk(new Product(controller.editProductPrice(productID, newPrice)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Change product Name
     *
     * @return Result detailing success of operation, containing the edited Product
     */
    public Result<Product> editProductName(int productID, String newName){
        try {
            return Result.makeOk(new Product(controller.editProductName(productID, newName)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Move Product to new Category
     *
     * @return Result detailing success of operation, containing the edited Product
     */
    public Result<Product> moveProductToCategory(int productID, int newCatID){
        try {
            return Result.makeOk(new Product(controller.moveProductToCategory(productID, newCatID)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Change Category Name
     *
     * @return Result detailing success of operation, containing the edited Category
     */
    public Result<Category> editCategoryName(int categoryID, String newName){
        try {
            return Result.makeOk(new Category(controller.editCategoryName(categoryID, newName)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Change Category parent/super-category
     *
     * @return Result detailing success of operation, containing the edited Category
     */
    public Result<Category> changeCategoryParent(int categoryID, int parentID){
        try {
            return Result.makeOk(new Category(controller.changeParentCategory(categoryID, parentID)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * add new category
     *
     * @return Result detailing success of operation, containing the new Category
     */
    public Result<Category> addNewCategory(String name, int parentCategoryID){
        try {
            return Result.makeOk(new Category(controller.addCategory(name, parentCategoryID)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Add supplier to list of suppliers who sell specified product
     *
     * @return Result detailing success of operation, containing the edited Product
     */
    public Result<Product> addSupplierToProduct(int productID, int supplierID, int productIDWithSupplier){
        try {
            return Result.makeOk(new Product(controller.addSupplierToProduct(productID, supplierID, productIDWithSupplier)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Remove supplier from list of suppliers who sell specified product
     *
     * @return Result detailing success of operation, containing the edited Product
     */
    public Result<Product> removeSupplierFromProduct(int productID, int supplierID){
        try {
            return Result.makeOk(new Product(controller.removeSupplierFromProduct(productID, supplierID)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Delete a category
     * @param catID = ID of category to remove
     * @return Result detailing success of operation
     */
    public Result<Object> deleteCategory(int catID) {
        try {
            controller.deleteCategory(catID);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * change the min amount of product in specified store before warnings will occur
     *
     * @return Result detailing success of operation, containing the edited product
     */
    public Result<Product> changeProductMin(int store, int product, int min) {
        try {
            return Result.makeOk(new Product(controller.changeProductMin(store, product, min)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * change the max recommended amount of product in specified store
     *
     * @return Result detailing success of operation, containing the edited product
     */
    public Result<Product> changeProductMax(int store, int product, int max) {
        try {
            return Result.makeOk(new Product(controller.changeProductMax(store, product, max)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
}
