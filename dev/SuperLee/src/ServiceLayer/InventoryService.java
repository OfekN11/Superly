package ServiceLayer;


import BusinessLayer.Category;
import BusinessLayer.InventoryController;
import BusinessLayer.Item;
import BusinessLayer.SaleToCustomer;

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
    public Result<Object> newProduct(int id, String name, Category category, int weight, double price){
        try {
            controller.newProduct(id, name, category, weight, price);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
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
    public Result<Object> addSale(List<Integer> categories, List<Integer> products, int percent, Date start, Date end){
        try {
            controller.addSale(categories, products, percent, start, end);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
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
    public Result<Object> getSaleHistoryByProduct(int productId){
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
    public Result<Object> getProducts(){
        try {
            controller.getProducts();
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
    public Result<Object> getProductsFromCategory(int categoryID){
        try {
            controller.getProductsFromCategory(categoryID);
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
    public Result<Object> removeProduct(int productID, List<Item> items){
        try {
            controller.removeProduct(productID, items);
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
    public Result<Object> moveProduct(int productID, List<Item> items){
        try {
            controller.move(productID, items);
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
    public Result<Object> addItems(int storeID, int productID, Map<Date, Integer> expiryDates){
        try {
            controller.AddItems(storeID, productID, expiryDates);
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
    public Result<Object> returnProduct(int productID, List<Item> items){
        try {
//            controller.returnProduct(productID, items);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    //edit product stuff (price, category, name)
}
