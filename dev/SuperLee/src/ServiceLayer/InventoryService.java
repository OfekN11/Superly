package ServiceLayer;


import BusinessLayer.Category;
import BusinessLayer.InventoryController;
import BusinessLayer.SaleToCustomer;
import BusinessLayer.Supplier;
import ServiceLayer.Objects.Product;
import ServiceLayer.Objects.Sale;
import ServiceLayer.Objects.SaleReport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public Result<Product> newProduct(int id, String name, Category category, int weight, double price, List<Supplier> suppliers){
        try {
            BusinessLayer.Product p = controller.newProduct(id, name, category, weight, price, suppliers);
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
    public Result<Sale> addSale(List<Category> categories, List<Integer> products, int percent, Date start, Date end){
        try {
            SaleToCustomer s = controller.addSale(categories, products, percent, start, end);
            return Result.makeOk(new Sale(s));
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
    public Result<Object> getDiscountFromSupplierHistory(int productId){
        try {
            controller.getDiscountFromSupplierHistory(productId);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Get History of all sales on a specific product
     *
     * @return Result detailing success of operation
     */
    public Result<SaleReport> getSaleHistoryByProduct(int productId){
        try {
            controller.getSaleHistoryByProduct(productId);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Get History of all sales on a specific category
     *
     * @return Result detailing success of operation
     */
    public Result<Object> getSaleHistoryByCategory(int categoryID){
        try {
            controller.getSaleHistoryByCategory(categoryID);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    /**
     * Get History of recent damaged items
     *
     * @return Result detailing success of operation
     */
    public Result<Object> getDamagedItems(){
        try {
            controller.getDamagedItems();
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
            List<BusinessLayer.Product> products = controller.getProducts();
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
            List<BusinessLayer.Product> products = controller.getProducts();
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
    public Result<List<ServiceLayer.Objects.Category>> getCategories(){
        try {
            List<BusinessLayer.Category> categories = controller.getCategories();
            List<ServiceLayer.Objects.Category> categoryList = new ArrayList<>();
            for (BusinessLayer.Category c : categories) {
                categoryList.add(new ServiceLayer.Objects.Category(c));
            }
            return Result.makeOk(categoryList);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    /**
     * Remove items from the store - either damaged or purchased
     *
     * @return Result detailing success of operation
     */
    public Result<Object> removeItems(int productID, int storeID, int amount){
        try {
            controller.removeItems(productID, storeID, amount);
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
    public Result<Object> returnProduct(int productID){
        try {
            controller.ReturnItems(productID,productID, productID);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    //edit product stuff (price, category, name)
}
