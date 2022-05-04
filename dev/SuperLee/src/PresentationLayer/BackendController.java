package PresentationLayer;

import Domain.ServiceLayer.Suppliers.*;
import Globals.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BackendController {

    //private final SupplierService supplierService = new SupplierService();
    private SupplierService supplierService = new SupplierService();  //FOR TESTING!!!!


    //For testing
    public BackendController(SupplierService service){
        this.supplierService = service;
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

    public boolean order(int supplierId, int input) throws Exception {
        Result<Boolean> result = supplierService.order(supplierId, input);
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
}
