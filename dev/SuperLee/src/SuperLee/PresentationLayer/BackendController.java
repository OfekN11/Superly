package SuperLee.PresentationLayer;

import SuperLee.BusinessLayer.Pair;
import SuperLee.ServiceLayer.Result;
import SuperLee.ServiceLayer.ServiceOrderObject;
import SuperLee.ServiceLayer.ServiceSupplierObject;
import SuperLee.ServiceLayer.SupplierService;

import java.util.ArrayList;
import java.util.List;

public class BackendController {

    //private final SupplierService supplierService = new SupplierService();
    private SupplierService supplierService = new SupplierService();  //FOR TESTING!!!!


    //For testing
    public BackendController(SupplierService service){
        this.supplierService = service;
    }

    public boolean updateSupplierID(int supplierID, int input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierID(supplierID, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();

    }

    public boolean updateSupplierBankNumber(int supplierID, int input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierBankNumber(supplierID, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean updateSupplierAddress(int supplierID, String input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierAddress(supplierID, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean updateSupplierName(int supplierID, String input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierName(supplierID, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean updateSupplierPayingAgreement(int supplierID, String input) throws Exception {
        Result<Boolean> result = supplierService.updateSupplierPayingAgreement(supplierID, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean addAgreement(int supplierId, int input, String stringInput) throws Exception {
        Result<Boolean> result = supplierService.addAgreement(supplierId, input, stringInput);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public Result<List<String>> getAllContacts(int supplierId) throws Exception {
        Result<List<String>> result = supplierService.getAllContacts(supplierId);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return (Result<List<String>>) result.getValue();
    }

    public boolean addSupplierContact(int supplierID, String name, String phone) throws Exception {
        Result<Boolean> result = supplierService.addSupplierContact(supplierID, name, phone);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean removeContact(int supplierID, String contact) throws Exception {
        Result<Boolean> result = supplierService.removeContact(supplierID, contact);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean isSuppliersEmpty() throws Exception {
        Result<Boolean> result = supplierService.isSuppliersEmpty();
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean doesSupplierExists(int id) throws Exception {
        Result<Boolean> result = supplierService.supplierExists(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
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
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean removeManufacturer(int supplierId, String manufacturer) throws Exception {
        Result<Boolean> result = supplierService.removeManufacturer(supplierId, manufacturer);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public ServiceOrderObject getOrder(int supplierId, int input) throws Exception {
        Result<ServiceOrderObject> result = supplierService.getOrder(supplierId, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean order(int supplierId, int input) throws Exception {
        Result<Boolean> result = supplierService.order(supplierId, input);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean addItemToOrder(int supplierID, int orderId, int itemID, int quantity) throws Exception {
        Result<Boolean> result = supplierService.addItemToOrder(supplierID, orderId, itemID, quantity);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean addSupplier(int id, String name, int bankNumber, String address, String payingAgreement, ArrayList<Pair<String, String>> contacts, ArrayList<String> manufacturers) throws Exception {
        Result<Boolean> result = supplierService.addSupplier(id, name, bankNumber, address, payingAgreement, contacts, manufacturers);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }

    public boolean removeSupplier(int id) throws Exception {
        Result<Boolean> result = supplierService.removeSupplier(id);
        if (result.isError())
            throw new Exception("Error occurred: " + result.getError());
        return result.getValue();
    }
}
