package ServiceLayer;


import BusinessLayer.DiscountsAndSales.DiscountFromSupplier;
import BusinessLayer.InventoryController;
import BusinessLayer.DiscountsAndSales.SaleToCustomer;
import BusinessLayer.Supplier;
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
    public Result<Product> newProduct(String name, int categoryID, int weight, double price, List<Supplier> suppliers){
        try {
            BusinessLayer.Product p = controller.newProduct(name, categoryID, weight, price, suppliers);
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
     * add Discount from supplier on product
     *
     * @return Result detailing success of operation
     */
    public Result<DiscountReport> addDiscountFromSupplier(int productID, Date date, int supplierID, String description, int amountBought, int pricePaid, int originalPrice){
        try {
            DiscountFromSupplier dr = controller.addDiscountFromSupplier(productID, date, supplierID, description, amountBought, pricePaid, originalPrice);
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
            List<DiscountFromSupplier> discountFromSupplierList = controller.getDiscountFromSupplierHistory(productId);
            List<DiscountReport> discountReports = new ArrayList<>();
            for (DiscountFromSupplier d : discountFromSupplierList)
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
     * Get History of recent damaged items
     *
     * @return Result detailing success of operation
     */
    public Result<Object> getDefectiveItems(Date start, Date end, List<Integer> storeIDs){
        try {
            controller.getDefectiveItems(start, end, storeIDs);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
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
    public Result<List<Product>> getProductsFromCategory(int categoryID){
        try {
            Collection<BusinessLayer.Product> products = controller.getProductsFromCategory(categoryID);
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
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
     */
    public Result<Object> reportDamaged(int storeID, int productID, int amount, String description){
        try {
            controller.reportDamaged(storeID, productID, amount, description);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
     */
    public Result<Object> reportExpired(int storeID, int productID, int amount){
        try {
            controller.reportExpired(storeID, productID, amount);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Remove damage items from the store
     *
     * @return Result detailing success of operation
     */
    public Result<DamagedItemReport> getDamagedItemsReport(Date start, Date end, List<Integer> storeIDs){
        try {
            return Result.makeOk(new DamagedItemReport(controller.getDamagedItemReports(start, end, storeIDs)));
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
    public Result<Object> getExpiredItemReports(Date start, Date end, List<Integer> storeIDs){
        try {
            controller.getExpiredItemReports(start, end, storeIDs);
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
    public Result<Object> addStore(){
        try {
            controller.addStore();
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
            return Result.makeOk(new Product(controller.editProductname(productID, newName)));
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
    public Result<Product> moveProduct(int productID, int newCatID){
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
    public Result<Category> editCategoryname(int categoryID, String newName){
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
    public Result<Category> changeCategoryParent(int categoryID, String newName){
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
    public Result<Category> addNewCategory(String name, int parentCategoryID){
        try {
            return Result.makeOk(new Category(controller.addCategory(name, parentCategoryID)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
    //edit product stuff (price, category, name)
}
