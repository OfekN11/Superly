package PresentationLayer;

import Domain.BusinessLayer.DefectiveItems;
import Domain.BusinessLayer.DiscountsAndSales.PurchaseFromSupplier;
import Domain.BusinessLayer.DiscountsAndSales.SaleToCustomer;
import Domain.ServiceLayer.InventoryService;
import Domain.ServiceLayer.Objects.*;
import Domain.ServiceLayer.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BackendController {
    private final InventoryService inventoryService = new InventoryService();
//    private final ShiftService shiftService = new ShiftService();
//    private final ConstraintService constraintService = new ConstraintService();

//    public Set<Employee> getAllEmployees() throws Exception {
//        Result<Set<Employee>> result = inventoryService.getAllEmployees();
//        if (result.isError())
//            throw new Exception("Error occurred: " + result.getError());
//        return result.getValue();
//    }
//
//    public Set<Employee> getAllCashiers() throws Exception {
//        return getAllEmployees().stream().filter((x) -> x.getType() == JobTitles.Cashier).collect(Collectors.toSet());
//    }
    public Result<Object> loadTestData(){
        return inventoryService.loadTestData();
    }

    public Result<List<Integer>> getStoreIDs(){
        return inventoryService.getStoreIDs();
    }

    public Result<List<Sale>> getRemovableSales(){
        return inventoryService.getRemovableSales();
    }

    public Result<Object> loadData(){
        return inventoryService.loadData();
    }

    public Result<Product> newProduct(String name, int categoryID, int weight, double price, Map<Integer, Integer> suppliers, int manufacturerID){
        return inventoryService.newProduct(name, categoryID, weight, price, suppliers, manufacturerID);
    }

    public Result<Object> deleteProduct(int id){
        return inventoryService.deleteProduct(id);
    }

    public Result<Sale> addSale(List<Integer> categories, List<Integer> products, int percent, Date start, Date end){
        return inventoryService.addSale(categories, products, percent, start, end);
    }

    public Result<Object> removeSale(int saleID){
        return inventoryService.removeSale(saleID);
    }

    public Result<Product> addProductToStore(int storeID, List<Integer> shelvesInStore, List<Integer> shelvesInWarehouse, int productID, int minAmount, int maxAmount){
        return inventoryService.addProductToStore(storeID, shelvesInStore, shelvesInWarehouse, productID, minAmount, maxAmount);
    }

    public Result<Product> removeProductFromStore(int storeID, int productID){
        return inventoryService.removeProductFromStore(storeID, productID);
    }

    public Result<PurchaseFromSupplierReport> addItems(int storeID, int productID, int supplierID, String description, int amountBought, int pricePaid, int originalPrice){
        return inventoryService.addItems(storeID, productID, supplierID, description, amountBought, pricePaid, originalPrice);
    }

    public Result<List<PurchaseFromSupplierReport>> getPurchaseFromSupplierHistory(int productId){
        return inventoryService.getPurchaseFromSupplierHistory(productId);
    }

    public Result<List<PurchaseFromSupplierReport>> getDiscountFromSupplierHistory(int productId){
        return inventoryService.getDiscountFromSupplierHistory(productId);
    }

    public Result<List<Sale>> getSaleHistoryByProduct(int productId){
        return inventoryService.getSaleHistoryByProduct(productId);
    }

    public Result<List<Sale>> getSaleHistoryByCategory(int categoryID){
        return inventoryService.getSaleHistoryByCategory(categoryID);
    }

    public Result<List<DefectiveItemReport>> getDefectiveItemsByStore(Date start, Date end, List<Integer> storeIDs){
        return inventoryService.getDefectiveItemsByStore(start, end, storeIDs);
    }

    public Result<List<DefectiveItemReport>> getDefectiveItemsByCategory(Date start, Date end, List<Integer> categoryIDs){
        return inventoryService.getDefectiveItemsByCategory(start, end, categoryIDs);
    }

    public Result<List<DefectiveItemReport>> getDefectiveItemsByProduct(Date start, Date end, List<Integer> productIDs){
        return inventoryService.getDefectiveItemsByProduct(start, end, productIDs);
    }

    public Result<List<Product>> getProducts(){
        return inventoryService.getProducts();
    }

    public Result<List<Product>> getProductsFromCategory(List<Integer> categoryIDs){
        return inventoryService.getProductsFromCategory(categoryIDs);
    }

    public Result<List<Category>> getCategories(){
        return inventoryService.getCategories();
    }

    public Result<Double> buyItems(int storeID, int productID, int amount){
        return inventoryService.buyItems(storeID, productID, amount);
    }

    public Result<List<StockReport>> getMinStockReport(){
        return inventoryService.getMinStockReport();
    }

    public Result<List<StockReport>> storeStockReport(List<Integer> stores, List<Integer> categories){
        return inventoryService.storeStockReport(stores, categories);
    }

    public Result<Boolean> isUnderMin(int store, int product){
        return inventoryService.isUnderMin(store, product);
    }

    public Result<DefectiveItemReport> reportDamaged(int storeID, int productID, int amount, int employeeID, String description){
        return inventoryService.reportDamaged(storeID, productID, amount, employeeID, description);
    }

    public Result<DefectiveItemReport> reportExpired(int storeID, int productID, int amount, int employeeID, String description){
        return inventoryService.reportExpired(storeID, productID, amount, employeeID, description);
    }

    public Result<List<DefectiveItemReport>> getDamagedItemsReportByStore(Date start, Date end, List<Integer> storeIDs){
        return inventoryService.getDamagedItemsReportByStore(start, end, storeIDs);
    }

    public Result<List<DefectiveItemReport>> getDamagedItemsReportByCategory(Date start, Date end, List<Integer> categoryIDs){
        return inventoryService.getDamagedItemsReportByCategory(start, end, categoryIDs);
    }

    public Result<List<DefectiveItemReport>> getDamagedItemsReportByProduct(Date start, Date end, List<Integer> productIDs){
        return inventoryService.getDamagedItemsReportByProduct(start, end, productIDs);
    }

    public Result<List<DefectiveItemReport>> getExpiredItemReportsByStore(Date start, Date end, List<Integer> storeIDs){
        return inventoryService.getExpiredItemReportsByStore(start, end, storeIDs);
    }

    public Result<List<DefectiveItemReport>> getExpiredItemReportsByCategory(Date start, Date end, List<Integer> categoryIDs){
        return inventoryService.getExpiredItemReportsByCategory(start, end, categoryIDs);
    }

    public Result<List<DefectiveItemReport>> getExpiredItemReportsByProduct(Date start, Date end, List<Integer> productIDs){
        return inventoryService.getExpiredItemReportsByProduct(start, end, productIDs);
    }

    public Result<Object> moveItems(int storeID, int productID, int amount){
        return inventoryService.moveItems(storeID, productID, amount);
    }

    public Result<Double> returnItems(int storeID, int productID, int amount, Date dateBought){
        return inventoryService.returnItems(storeID, productID, amount, dateBought);
    }

    public Result<Integer> addStore(){
        return inventoryService.addStore();
    }

    public Result<Object> removeStore(int storeID){
        return inventoryService.removeStore(storeID);
    }

    public Result<Product> editProductPrice(int productID, double newPrice){
        return inventoryService.editProductPrice(productID, newPrice);
    }

    public Result<Product> editProductName(int productID, String newName){
        return inventoryService.editProductName(productID, newName);
    }

    public Result<Product> moveProductToCategory(int productID, int newCatID){
        return inventoryService.moveProductToCategory(productID, newCatID);
    }

    public Result<Category> editCategoryName(int categoryID, String newName){
        return inventoryService.editCategoryName(categoryID, newName);
    }

    public Result<Category> changeCategoryParent(int categoryID, int parentID){
        return inventoryService.changeCategoryParent(categoryID, parentID);
    }

    public Result<Category> addNewCategory(String name, int parentCategoryID){
        return inventoryService.addNewCategory(name, parentCategoryID);
    }

    public Result<Product> addSupplierToProduct(int productID, int supplierID, int productIDWithSupplier){
        return inventoryService.addSupplierToProduct(productID, supplierID, productIDWithSupplier);
    }

    public Result<Product> removeSupplierFromProduct(int productID, int supplierID){
        return inventoryService.removeSupplierFromProduct(productID, supplierID);
    }

    public Result<Object> deleteCategory(int catID) {
        return inventoryService.deleteCategory(catID);
    }

    public Result<Product> changeProductMin(int store, int product, int min) {
        return inventoryService.changeProductMin(store, product, min);
    }

    public Result<Product> changeProductMax(int store, int product, int max) {
        return inventoryService.changeProductMax(store, product, max);
    }

}
