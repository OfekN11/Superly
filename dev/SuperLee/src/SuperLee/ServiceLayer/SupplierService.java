package SuperLee.ServiceLayer;

import SuperLee.BusinessLayer.Pair;
import SuperLee.BusinessLayer.SupplierController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupplierService {

    private SupplierController controller;

    public SupplierService(){
        controller = new SupplierController();
    }



    //Pair.first = name , Pair.second = phoneNumber
    public void addSupplier(int id, int bankNumber, String address, String name, String payingAgreement , ArrayList<Pair<String,String>> contacts, ArrayList<String> manufacturers){
        try {
            controller.addSupplier(id, name, bankNumber, address, payingAgreement, contacts , manufacturers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeSupplier(int id){
        try {
            controller.removeSupplier(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSupplierAddress(int id, String address){
        try {
            controller.updateSupplierAddress(id, address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSupplierBankNumber(int id, int bankNumber){
        try {
            controller.updateSupplierBankNumber(id, bankNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSupplierID(int id, int newId){
        try {
            controller.updateSupplierID(id, newId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSupplierName(int id, String newName){
        try {
            controller.updateSupplierName(id, newName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSupplierContact(int id, String contactName, String contactPhone){
        try {
            controller.addSupplierContact(id, contactName, contactPhone);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSupplierPayingAgreement(int id, String payingAgreement){
        try {
            controller.updateSupplierPayingAgreement(id, payingAgreement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addSupplierManufacturer(int id, String manufacturer){
        try {
            controller.addSupplierManufacturer(id, manufacturer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAgreement(int supplierId, int agreementType, String agreementDays){
        try {
            controller.addAgreement(supplierId, agreementType, agreementDays);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addAgreementItems(int supplierId, List<String> itemsString) {
        try {
            controller.addItemsToAgreement(supplierId, itemsString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: 15/04/2022   What is the return type from the Controller? we cant pass objects!
    /*
    public Map itemsFromAllSuppliers(){
        try {
            //HashMap<> result = controller.itemsFromAllSuppliers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<> itemsFromOneSupplier(int id){
        try {
            //HashMap<> result = controller.itemsFromOneSupplier(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    */

    public void updateBulkPriceForItem(int supplierId, int itemID, Map<Integer, Integer> newBulkPrices){
        try {
            controller.updateBulkPriceForItem(supplierId, itemID, newBulkPrices);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePricePerUnitForItem(int supplierId, int itemID, int newPrice){
        try {
            controller.updatePricePerUnitForItem(supplierId, itemID, newPrice);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateItemId(int supplierId, int olditemId, int newItemId){
        try {
            controller.updateItemId( supplierId, olditemId, newItemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateItemName(int supplierId, int itemId, String newName){
        try {
            controller.updateItemName( supplierId, itemId, newName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateItemManufacturer(int supplierId, int itemId, String manufacturer){
        try {
            controller.updateItemManufacturer( supplierId, itemId, manufacturer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addItemToAgreement(int supplierId, int itemId, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices){
        try {
            controller.addItemToAgreement( supplierId, itemId, itemName, itemManu, itemPrice, bulkPrices);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteItemFromAgreement(int supplierId, int itemId){
        try {
            controller.deleteItemFromAgreement( supplierId, itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTransporting(int supplierId){
        try {
            return controller.isTransporting( supplierId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void changeAgreementType(int supplierId,  int agreementType, String agreementDays) {
        try {
            controller.updateAgreementType(supplierId, agreementType, agreementDays);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAgreement(int supplierId, int agreementType, String agreementDays){
        try {
            controller.setAgreement(supplierId, agreementType, agreementDays);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
