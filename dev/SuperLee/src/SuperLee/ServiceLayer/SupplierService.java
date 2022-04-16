package SuperLee.ServiceLayer;

import SuperLee.BusinessLayer.Pair;
import SuperLee.BusinessLayer.SupplierController;

import java.util.*;


public class SupplierService {

    private SupplierController controller;

    public SupplierService(){
        controller = new SupplierController();
    }



    //Pair.first = name , Pair.second = phoneNumber
    public void addSupplier(int id, String name, int bankNumber, String address, String payingAgreement , ArrayList<Pair<String,String>> contacts, ArrayList<String> manufacturers){
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
    */



    // one entry is : < key : quantity , value : " id , name , manufacturer , pricePerUnit , quantity1 , percent1 , quantity2 , percent2 ...  " >
    public Map<Integer, ServiceItemObject> itemsFromOneSupplier(int supplierId){
        try {
            Map<Integer, String> result = controller.itemsFromOneSupplier(supplierId);
            return createServiceItemObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<Integer, ServiceItemObject> createServiceItemObject(Map<Integer, String> result) {
        HashMap<Integer,ServiceItemObject> items = new HashMap<>();
        for( Map.Entry<Integer, String> currItem : result.entrySet()) {
            ArrayList<String> info = (ArrayList<String>) Arrays.asList(currItem.getValue().split(","));
            info = (ArrayList<String>) info.stream().map(curr -> curr.trim() );  //trims all the String in the list
            int id = Integer.parseInt(info.get(0));
            String name = info.get(1);
            String manufacturer = info.get(2);
            float pricePerUnit = Float.parseFloat(info.get(3));
            Map<Integer, Integer> bulkPrices = new HashMap<>();  //create the bulkPriceMap
            for(int i = 4; i < info.size(); i++){
                bulkPrices.put( Integer.parseInt(info.get(i)) , Integer.parseInt(info.get(i + 1)));
            }
            items.put(currItem.getKey(), new ServiceItemObject(id, name , manufacturer , pricePerUnit , bulkPrices));
        }
        return items;
    }




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


    // "-1" for not transporting , "number" for days until delivery, "x1 x2 .." for supplying days for routine supplier
    // TODO: 16/04/2022 SAGI What should the return type will be in the end?
    public String getSupplingDaysFromSupplier(int supplierId){
        try {
            return controller.getSupplingDaysFromSupplier(supplierId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }



    // < id , name , bankAccount , address , payingAgreement , Contact1Name , Contact1Phone ,  Contact2Name , Contact2Phone ... >
    //Requirement 3
    public ServiceSupplierObject getSupplierInfo(int supplierId){
        try {
            ArrayList<String> result = controller.getSupplierInfo(supplierId);
            return createServiceSupplierObject(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ServiceSupplierObject createServiceSupplierObject(ArrayList<String> result) {
        int id = Integer.parseInt(result.get(0));
        String name = result.get(1);
        int bankNumber = Integer.parseInt(result.get(2));
        String address = result.get(3);
        String payingAgreement = result.get(4);
        ArrayList<ServiceContactObject> contacts = new ArrayList<>();
        for(int i = 5; i < result.size(); i+=2){
            contacts.add(new ServiceContactObject(result.get(i) , result.get(i+1) ));
        }
        return new ServiceSupplierObject(id , name, bankNumber , address , payingAgreement , contacts);
    }



}
