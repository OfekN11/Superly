package PresentationLayer;

import Domain.ServiceLayer.SupplierService;
import Domain.ServiceLayer.SupplierObjects.*;
import Globals.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Domain.ServiceLayer.InventoryService;
import Domain.ServiceLayer.InventoryObjects.*;
import Domain.ServiceLayer.Result;

import java.util.*;

public class BackendController {

    //private final SupplierService supplierService = new SupplierService();
    private SupplierService supplierService ; // = new SupplierService();  //FOR TESTING!!!!
    private InventoryService inventoryService = new InventoryService();

    //For testing
    public BackendController(){
        this.supplierService = new SupplierService();
        this.inventoryService = new InventoryService();
    }

    //For testing
    public BackendController(SupplierService service){
        this.supplierService = service;
        this.inventoryService = new InventoryService();
    }

    private boolean getValueFromBooleanResult(Result<Boolean> result) throws Exception {
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean updateSupplierID(int supplierID, int input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierID(supplierID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean updateSupplierBankNumber(int supplierID, int input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierBankNumber(supplierID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean updateSupplierAddress(int supplierID, String input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierAddress(supplierID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean updateSupplierName(int supplierID, String input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierName(supplierID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean updateSupplierPayingAgreement(int supplierID, String input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierPayingAgreement(supplierID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean addAgreement(int supplierId, int input, String stringInput) throws Exception {
        Result<Boolean> result = supplierService.addAgreement(supplierId, input, stringInput);
        return getValueFromBooleanResult(result);
    }

    public List<String> getAllContacts(int supplierId) throws Exception {
        Result<List<String>> result = supplierService.getAllContacts(supplierId);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean addSupplierContact(int supplierID, String name, String phone) throws Exception {
        Result<Boolean> result = supplierService.addSupplierContact(supplierID, name, phone);
        return getValueFromBooleanResult(result);
    }

    public boolean removeContact(int supplierID, String contact) throws Exception {
        Result<Boolean> result = supplierService.removeContact(supplierID, contact);
        return getValueFromBooleanResult(result);
    }

    public boolean isSuppliersEmpty() throws Exception {
        Result<Boolean> result = supplierService.isSuppliersEmpty();
        return getValueFromBooleanResult(result);
    }

    public boolean doesSupplierExists(int id) throws Exception {
        Result<Boolean> result = supplierService.supplierExists(id);
        return getValueFromBooleanResult(result);
    }

    public ServiceSupplierObject getSupplierInfo(int supplierId) throws Exception {
        Result<ServiceSupplierObject> result = supplierService.getSupplierInfo(supplierId);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public List<String> getManufacturers(int supplierId) throws Exception {
        Result<List<String>> result = supplierService.getManufacturers(supplierId);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean addSupplierManufacturer(int supplierId, String manufacturer) throws Exception {
        Result<Boolean> result = supplierService.addSupplierManufacturer(supplierId, manufacturer);
        return getValueFromBooleanResult(result);
    }

    public boolean removeManufacturer(int supplierId, String manufacturer) throws Exception {
        Result<Boolean> result = supplierService.removeManufacturer(supplierId, manufacturer);
        return getValueFromBooleanResult(result);
    }

    public ServiceOrderObject getOrder(int supplierId, int input) throws Exception {
        Result<ServiceOrderObject> result = supplierService.getOrder(supplierId, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean order(int supplierId) throws Exception {
        Result<Boolean> result = supplierService.order(supplierId);
        return getValueFromBooleanResult(result);
    }

    public boolean addItemToOrder(int supplierID, int orderId, int itemID, int quantity) throws Exception {
        Result<Boolean> result = supplierService.addItemToOrder(supplierID, orderId, itemID, quantity);
        return getValueFromBooleanResult(result);
    }

    public boolean addSupplier(int id, String name, int bankNumber, String address, String payingAgreement, ArrayList<Pair<String, String>> contacts, ArrayList<String> manufacturers) throws Exception {
        Result<Boolean> result = supplierService.addSupplier(id, name, bankNumber, address, payingAgreement, contacts, manufacturers);
        return getValueFromBooleanResult(result);
    }

    public boolean removeSupplier(int id) throws Exception {
        Result<Boolean> result = supplierService.removeSupplier(id);
        return getValueFromBooleanResult(result);
    }

    public boolean hasAgreement(int supplierId) throws Exception {
        Result<Boolean> result = supplierService.hasAgreement(supplierId);
        return getValueFromBooleanResult(result);
    }

    public boolean isRoutineAgreement(int supplierId) throws Exception {
        Result<Boolean> result = supplierService.isRoutineAgreement(supplierId);
        return getValueFromBooleanResult(result);
    }

    public boolean isByOrderAgreement(int supplierId) throws Exception {
        Result<Boolean> result = supplierService.isByOrderAgreement(supplierId);
        return getValueFromBooleanResult(result);
    }

    public List<ServiceItemObject> itemsFromOneSupplier(int supplierID) throws Exception {
        Result<List<ServiceItemObject>> result = supplierService.itemsFromOneSupplier(supplierID);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean addItemToAgreement(int supplierID, int id, String name, String manufacturer, float pricePerUnit, Map<Integer, Integer> bulkMap) throws Exception {
        Result<Boolean> result = supplierService.addItemToAgreement(supplierID, id, name, manufacturer, pricePerUnit, bulkMap);
        return getValueFromBooleanResult(result);
    }

    public boolean deleteItemFromAgreement(int supplierID, int input) throws Exception {
        Result<Boolean> result = supplierService.deleteItemFromAgreement(supplierID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean changeAgreementType(int supplierID, int input, String days) throws Exception {
        Result<Boolean> result = supplierService.changeAgreementType(supplierID, input, days);
        return getValueFromBooleanResult(result);
    }

    public boolean setDaysOfDelivery(int supplierID, String input) throws Exception {
        Result<Boolean> result = supplierService.setDaysOfDelivery(supplierID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean addDaysOfDelivery(int supplierID, String input) throws Exception {
        Result<Boolean> result = supplierService.addDaysOfDelivery(supplierID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean removeDayOfDelivery(int supplierID, int input) throws Exception {
        Result<Boolean> result = supplierService.removeDayOfDelivery(supplierID, input);
        return getValueFromBooleanResult(result);
    }



    public boolean changeDaysUntilDelivery(int supplierID, int input) throws Exception {
        Result<Boolean> result = supplierService.changeDaysUntilDelivery(supplierID, input);
        return getValueFromBooleanResult(result);
    }

    public ServiceItemObject getItem(int supplierID, int input) throws Exception {
        Result<ServiceItemObject> result = supplierService.getItem(supplierID, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean updateItemId(int supplierID, int itemID, int input) throws Exception {
        Result<Boolean> result = supplierService.updateItemId(supplierID, itemID, input);
        return getValueFromBooleanResult(result);

    }

    public boolean updateItemName(int supplierID, int itemID, String input) throws Exception {
        Result<Boolean> result = supplierService.updateItemName(supplierID, itemID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean updateItemManufacturer(int supplierID, int itemID, String input) throws Exception {
        Result<Boolean> result = supplierService.updateItemManufacturer(supplierID, itemID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean updatePricePerUnitForItem(int supplierID, int itemID, float input) throws Exception {
        Result<Boolean> result = supplierService.updatePricePerUnitForItem(supplierID, itemID, input);
        return getValueFromBooleanResult(result);
    }

    public boolean addBulkPriceForItem(int supplierID, int itemID, int quantity, int discount) throws Exception {
        Result<Boolean> result = supplierService.addBulkPriceForItem(supplierID, itemID, quantity, discount);
        return getValueFromBooleanResult(result);

    }

    public boolean removeBulkPriceForItem(int supplierID, int itemID, int quantity) throws Exception {
        Result<Boolean> result = supplierService.removeBulkPriceForItem(supplierID, itemID, quantity);
        return getValueFromBooleanResult(result);
    }

    public boolean editBulkPriceForItem(int supplierID, int itemID, int quantity, int discount) throws Exception {
        Result<Boolean> result = supplierService.editBulkPriceForItem(supplierID, itemID, quantity, discount);
        return getValueFromBooleanResult(result);
    }

    public Double calculatePriceForItemOrder(int supplierID, int itemID, int input) throws Exception {
        Result<Double> result = supplierService.calculatePriceForItemOrder(supplierID, itemID, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Integer daysToDelivery(int supplierID) throws Exception {
        Result<Integer> result = supplierService.daysToDelivery(supplierID);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public List<Integer> getDaysOfDelivery(int supplierID) throws Exception {
        Result<List<Integer>> result = supplierService.getDaysOfDelivery(supplierID);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Integer getDeliveryDays(int supplierID) throws Exception {
        Result<Integer> result = supplierService.getDeliveryDays(supplierID);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }



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

    public Result<Boolean> orderExists(int supID, int orderID){
        return supplierService.orderExists(supID, orderID);
    }

    public Result<Boolean> removeItemFromOrder(int supID, int orderID, int itemID){
        return supplierService.removeItemFromOrder(supID, orderID, itemID);
    }

    public Result<Boolean> updateItemQuantityInOrder(int supID, int orderID, int itemID, int quantity){
        return supplierService.updateItemQuantityInOrder(supID, orderID, itemID, quantity);
    }
}
