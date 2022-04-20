package ServiceLayer;


import BusinessLayer.Category;
import BusinessLayer.InventoryController;
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
     * Loads data form persistence layer
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

}
