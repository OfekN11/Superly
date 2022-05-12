package Domain.ServiceLayer;

import Domain.ServiceLayer.SupplierObjects.*;
import Globals.Pair;
import Domain.BusinessLayer.SupplierController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class SupplierService {

    private SupplierController controller;

    public SupplierService(){
        controller = new SupplierController();
    }



    //Pair.first = name , Pair.second = phoneNumber
    public Result<Integer> addSupplier(String name, int bankNumber, String address, String payingAgreement , ArrayList<Pair<String,String>> contacts, ArrayList<String> manufacturers){
        try {
            int supplierId = controller.addSupplier(name, bankNumber, address, payingAgreement, contacts , manufacturers);
            return Result.makeOk(supplierId);
        } catch (Exception e) {
            return  Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> removeSupplier(int id){
        try {
            controller.removeSupplier(id);
            return  Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> updateSupplierAddress(int id, String address){
        try {
            controller.updateSupplierAddress(id, address);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> updateSupplierBankNumber(int id, int bankNumber){
        try {
            controller.updateSupplierBankNumber(id, bankNumber);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    /*
    public Result<Boolean> updateSupplierID(int id, int newId){
        try {
            controller.updateSupplierID(id, newId);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }*/

    public Result<Boolean> updateSupplierName(int id, String newName){
        try {
            controller.updateSupplierName(id, newName);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> addSupplierContact(int id, String contactName, String contactPhone){
        try {
            controller.addSupplierContact(id, contactName, contactPhone);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> updateSupplierPayingAgreement(int id, String payingAgreement){
        try {
            controller.updateSupplierPayingAgreement(id, payingAgreement);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> addSupplierManufacturer(int id, String manufacturer){
        try {
            controller.addSupplierManufacturer(id, manufacturer);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> addAgreement(int supplierId, int agreementType, String agreementDays){
        try {
            controller.addAgreement(supplierId, agreementType, agreementDays);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> isSuppliersEmpty(){
        return Result.makeOk(controller.isSuppliersEmpty());
    }

    //Format : " productId, idBySupplier , name , manufacturer , pricePerUnit , quantity , percent , quantity , percent ..."
    public void addAgreementItems(int supplierId, List<String> itemsString) {
        try {
            controller.addItemsToAgreement(supplierId, itemsString);
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    // TODO: 16/04/2022  Need to change the key of  the Map if we want to use this, name is not unique
    public Map<String, ServiceItemObject> itemsFromAllSuppliers(){
        /*
        try {
            Map<String, List<String>> result = controller.itemsFromAllSuppliers();
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
        return null;
    }

    // one component is : < " id , name , manufacturer , pricePerUnit , quantity1 , percent1 , quantity2 , percent2 ...  " >
    public Result<List<ServiceItemObject>> itemsFromOneSupplier(int supplierId){
        try {
            List<String> result = controller.itemsFromOneSupplier(supplierId);
            return Result.makeOk(createServiceItemObject(result));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    private List<ServiceItemObject> createServiceItemObject(List<String> result) {
        ArrayList<ServiceItemObject> items = new ArrayList<>();
        for(String currItem : result) {
            List<String> temp =  Arrays.asList(currItem.split(","));
            ArrayList<String> info = new ArrayList<>();
            for(String curr : temp){
                info.add(curr.trim());
            }


            int id = Integer.parseInt(info.get(0));
            String name = info.get(1);
            String manufacturer = info.get(2);
            float pricePerUnit = Float.parseFloat(info.get(3));
            Map<Integer, Integer> bulkPrices = new HashMap<>();  //create the bulkPriceMap
            for(int i = 4; i < info.size(); i+=2){
                bulkPrices.put( Integer.parseInt(info.get(i)) , Integer.parseInt(info.get(i + 1)));
            }
            items.add( new ServiceItemObject(id, name , manufacturer , pricePerUnit , bulkPrices));
        }
        return items;
    }

    public Result<ServiceItemObject> getItem(int supplierId, int itemId){
        try{
            return Result.makeOk(new ServiceItemObject(controller.getItem(supplierId, itemId)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> updateBulkPriceForItem(int supplierId, int itemID, Map<Integer, Integer> newBulkPrices){
        try {
            controller.updateBulkPriceForItem(supplierId, itemID, newBulkPrices);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> updatePricePerUnitForItem(int supplierId, int itemID, float newPrice){
        try {
            controller.updatePricePerUnitForItem(supplierId, itemID, newPrice);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> updateItemId(int supplierId, int olditemId, int newItemId){
        try {
            controller.updateItemId( supplierId, olditemId, newItemId);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> updateItemName(int supplierId, int itemId, String newName){
        try {
            controller.updateItemName( supplierId, itemId, newName);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> updateItemManufacturer(int supplierId, int itemId, String manufacturer){
        try {
            controller.updateItemManufacturer( supplierId, itemId, manufacturer);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> addItemToAgreement(int supplierId, int itemId, int idBySupplier, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices){
        try {
            controller.addItemToAgreement( supplierId, itemId, idBySupplier, itemName, itemManu, itemPrice, bulkPrices);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> deleteItemFromAgreement(int supplierId, int itemId){
        try {
            controller.deleteItemFromAgreement( supplierId, itemId);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> isTransporting(int supplierId){
        try {
            return Result.makeOk(controller.isTransporting( supplierId));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> changeAgreementType(int supplierId,  int agreementType, String agreementDays) {
        try {
            controller.updateAgreementType(supplierId, agreementType, agreementDays);
            return Result.makeOk(true);
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    public void setAgreement(int supplierId, int agreementType, String agreementDays){
        try {
            controller.setAgreement(supplierId, agreementType, agreementDays);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Result<Boolean> isRoutineAgreement(int supplierId){
        try{
            return Result.makeOk(controller.isRoutineAgreement(supplierId));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> isByOrderAgreement(int supplierId){
        try{
            return Result.makeOk(controller.isByOrderAgreement(supplierId));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> isNotTransportingAgreement(int supplierId){
        try{
            return Result.makeOk(controller.isNotTransportingAgreement(supplierId));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Integer> daysToDelivery(int supplierId){
        try{
            return Result.makeOk(controller.daysToDelivery(supplierId));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<List<Integer>> getDaysOfDelivery(int supplierId){
        try{
            return Result.makeOk(controller.getDaysOfDelivery(supplierId));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Integer> getDeliveryDays(int supplierId){
        try{
            return Result.makeOk(controller.getDeliveryDays(supplierId));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    // < id , name , bankAccount , address , payingAgreement , Contact1Name , Contact1Phone ,  Contact2Name , Contact2Phone ... >
    //Requirement 3
    public Result<ServiceSupplierObject> getSupplierInfo(int supplierId){
        try {
            ArrayList<String> result = controller.getSupplierInfo(supplierId);
            return Result.makeOk(createServiceSupplierObject(result));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
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

    public Result<Boolean> setDaysOfDelivery(int supplierID, String days){
        try{
            controller.setDaysOfDelivery(supplierID, days);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> addDaysOfDelivery(int supplierID, String days){
        try{
            controller.addDaysOfDelivery(supplierID, days);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> removeDayOfDelivery(int supplierID, int day){
        try{
            controller.removeDayOfDelivery(supplierID, day);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> editBulkPriceForItem(int supID, int itemID, int quantity, int discount){
        try{
            controller.editBulkPrice(supID, itemID, quantity, discount);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> addBulkPriceForItem(int supID, int itemID, int quantity, int discount){
        try{
            controller.addBulkPrice(supID, itemID, quantity, discount);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> removeBulkPriceForItem(int supID, int itemID, int quantity){
        try{
            controller.removeBulkPrice(supID, itemID, quantity);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Double> calculatePriceForItemOrder(int supID, int itemID, int quantity){
        try{
            return Result.makeOk(controller.calculatePriceForItemOrder(supID, itemID, quantity));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> changeDaysUntilDelivery(int supID, int days){
        try{
            controller.changeDaysUntilDelivery(supID, days);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<List<String>> getAllContacts(int supID){
        try{
            return Result.makeOk(controller.getAllContact(supID));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> removeContact(int supID, String name){
        try{
            controller.removeContact(supID, name);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> hasAgreement(int supID){
        try{
            return Result.makeOk(controller.hasAgreement(supID));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<List<String>> getManufacturers(int supID){
        try{
            return Result.makeOk(controller.getManufacturers(supID));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> removeManufacturer(int supID, String name){
        try{
            controller.removeManufacturer(supID, name);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Integer> order(int supId){
        try{
            int orderId = controller.addNewOrder(supId);
            return Result.makeOk(orderId);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }


    // Format :  <productId1, idBySupplier1, quantity1> , <productId2, idBySupplier2 , quantity2> , ...
    public Result<Boolean> addItemsToOrder(int supId, int orderId, List<String> itemsString){
        try{
            controller.addItemsToOrder(supId, orderId, itemsString);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> addItemToOrder(int supId, int orderId, int itemId, int idBySupplier, int itemQuantity){
        try{
            controller.addItemToOrder(supId, orderId, itemId, idBySupplier, itemQuantity);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }
    public Result<Boolean> removeItemFromOrder(int supId, int orderId, int itemId){
        try{
            controller.removeItemFromOrder(supId, orderId, itemId);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    // TODO: USE THIS METHOD
    public Result<Boolean> updateItemQuantityInOrder(int supId, int orderId, int itemId, int quantity){
        try{
            controller.updateItemQuantityInOrder(supId, orderId, itemId, quantity);
            return Result.makeOk(true);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }



    public Result<ServiceOrderObject> getOrder(int supId, int orderId){
        try{
            List<String> result = controller.getOrder(supId, orderId);
            return Result.makeOk(createServiceOrderObject(result));
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    //Format : " orderId , orderDate , item1string, item2String ... "
    private ServiceOrderObject createServiceOrderObject(List<String> result) throws ParseException {

        int orderId = Integer.parseInt(result.get(0));
        String sDate1= result.get(1) ;
        Date orderDate =new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);

        List<ServiceOrderItemObject> items = new ArrayList<>();

        for(int i = 2; i < result.size(); i+=6){
            int id = Integer.parseInt(result.get(i));
            String name = result.get(i+1);
            int quantity = Integer.parseInt(result.get(i+2));
            float ppu = Float.parseFloat(result.get(i+3));
            int discount = Integer.parseInt(result.get(i+4));
            Double finalPrice = Double.parseDouble(result.get(i+5));
            items.add(new ServiceOrderItemObject(id, name, quantity, ppu, discount, finalPrice));
        }
        return new ServiceOrderObject(orderId, orderDate, items);
    }

    public Result<Boolean> supplierExists(int id) {
        try{
            boolean result = controller.doesSupplierExists(id);
            return Result.makeOk(result);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Boolean> orderExists(int supID, int orderID) {
        try{
            boolean result = controller.orderExists(supID, orderID);
            return Result.makeOk(result);
        }
        catch(Exception e){
            return Result.makeError(e.getMessage());
        }
    }

}
