package ServiceLayer;


import BusinessLayer.DefectiveItems;
import BusinessLayer.DiscountsAndSales.PurchaseFromSupplier;
import BusinessLayer.InventoryController;
import BusinessLayer.DiscountsAndSales.SaleToCustomer;
import ServiceLayer.Objects.*;

import java.util.*;

/**
 * Service controller for employee operations
 *
 * All EmployeeService's methods return Results detailing the success. encapsulating values/error messages
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
     * @return Result detailing success of operation
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
     * @return Result detailing success of operation
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
     * @return Result detailing success of operation
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
     * add sale on specified categories and/or items
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
     * add Discount from supplier on product
     *
     * @return Result detailing success of operation
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
     * add Discount from supplier on product
     *
     * @return Result detailing success of operation
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
     * add Discount from supplier on product
     *
     * @return Result detailing success of operation
     */
    public Result<DiscountReport> addPurchaseFromSupplier(int productID, Date date, int supplierID, String description, int amountBought, int pricePaid, int originalPrice){
        try {
            PurchaseFromSupplier dr = controller.addPurchaseFromSupplier(productID, date, supplierID, description, amountBought, pricePaid, originalPrice);
            return Result.makeOk(new DiscountReport(dr));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get History of all discounts from the different suppliers on specified product
     *
     * @return Result detailing success of operation
     */
    public Result<List<DiscountReport>> getDiscountFromSupplierHistory(int productId){
        try {
            List<PurchaseFromSupplier> purchaseFromSupplierList = controller.getDiscountFromSupplierHistory(productId);
            List<DiscountReport> discountReports = new ArrayList<>();
            for (PurchaseFromSupplier d : purchaseFromSupplierList)
                discountReports.add(new DiscountReport(d));
            return Result.makeOk(discountReports);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get History of all sales on a specific product
     *
     * @return Result detailing success of operation
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
     * Get History of all sales on a specific category
     *
     * @return Result detailing success of operation
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
     * Get History of recent damaged and expired items
     *
     * @return Result detailing success of operation
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
     * Get History of recent damaged and expired items
     *
     * @return Result detailing success of operation
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
     * Get History of recent damaged and expired items
     *
     * @return Result detailing success of operation
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
     * @return Result detailing success of operation
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
     * Get list of all products in a category
     *
     * @return Result detailing success of operation
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
     * @return Result detailing success of operation
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
     * Remove damage or expired items from the store
     *
     * @return Result detailing success of operation
     */
    public Result<Double> buyItems(int productID, int storeID, int amount){
        try {
            return Result.makeOk(controller.buyItems(productID, storeID, amount));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Remove damage or expired items from the store
     *
     * @return Result detailing success of operation
     */
    public Result<Map<Integer, Product>> getMinStockReport(){
        try {
            Map<Integer, BusinessLayer.Product> minStock = controller.getMinStockReport();
            Map<Integer, Product> minStockReport = new HashMap<>();
            for (int i : minStock.keySet())
                minStockReport.put(i, new Product(minStock.get(i)));
            return Result.makeOk(minStockReport);

        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Remove damage or expired items from the store
     *
     * @return Result detailing success of operation
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
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
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
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
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
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
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
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
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
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
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
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
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
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
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
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
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
    public Result<Object> moveProduct(int productID, int storeID, int amount){
        try {
            controller.moveItems(productID, storeID, amount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * add items to warehouse in specified store
     *
     * @return Result detailing success of operation
     */
    public Result<Object> addItems(int storeID, int productID, int amount){
        try {
            controller.addItems(storeID, productID, amount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Get list of all products in a category
     *
     * @return Result detailing success of operation
     */
    public Result<Double> returnProduct(int storeID, int productID, int amount, Date dateBought){
        try {
            return Result.makeOk(controller.returnItems(storeID, productID, amount, dateBought));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Get list of all products in a category
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
     * Get list of all products in a category
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
     * @return Result detailing success of operation holding the renewed Product
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
     * @return Result detailing success of operation holding the renewed Product
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
     * @return Result detailing success of operation holding the renewed Product
     */
    public Result<Product> moveProductToCategory(int productID, int newCatID){
        try {
            return Result.makeOk(new Product(controller.moveProduct(productID, newCatID)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Change Category Name
     *
     * @return Result detailing success of operation holding the renewed Product
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
     * Change Category Name
     *
     * @return Result detailing success of operation holding the renewed Product
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
     * Change Category Name
     *
     * @return Result detailing success of operation holding the renewed Product
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
     * Change Category Name
     *
     * @return Result detailing success of operation holding the renewed Product
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
     * Change Category Name
     *
     * @return Result detailing success of operation holding the renewed Product
     */
    public Result<Product> removeSupplierFromProduct(int productID, int supplierID){
        try {
            return Result.makeOk(new Product(controller.removeSupplierFromProduct(productID, supplierID)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
}
