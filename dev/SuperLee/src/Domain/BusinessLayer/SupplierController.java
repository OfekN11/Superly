package Domain.BusinessLayer;

import Domain.BusinessLayer.Supplier.Contact;
import Domain.BusinessLayer.Supplier.Order;
import Domain.BusinessLayer.Supplier.Supplier;
import Globals.Pair;

import java.util.*;
import java.util.stream.Stream;

public class SupplierController {

    private SuppliersDAO suppliersDAO;


    public SupplierController(){
        suppliersDAO = new SuppliersDAO();
    }



    public void addSupplier(int id, String name, int bankNumber, String address, String payingAgreement, ArrayList<Pair<String,String>> contactPairs, ArrayList<String> manufacturers) throws Exception {
        if(supplierExist(id))
            throw new Exception("Supplier with same Id already exists");

        ArrayList<Contact> contacts = createContacts(contactPairs);
        Supplier supplier = new Supplier(id, name, bankNumber, address, payingAgreement, contacts, manufacturers);
        suppliersDAO.addSupplier(supplier);
    }

    private ArrayList<Contact> createContacts(ArrayList<Pair<String, String>> contactPairs) throws Exception {
        ArrayList<Contact> contacts = new ArrayList<>();
        for(Pair<String,String> curr : contactPairs){
            if(!validPhoneNumber(curr.getSecond()))
                throw new Exception("Invalid phone number!");
            contacts.add(new Contact( curr.getFirst(), curr.getSecond()));
        }
        return contacts;
    }


    public void removeSupplier(int id) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.removeSupplier(id);
    }

    public void updateSupplierAddress(int id, String address) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(id).updateAddress(address);
    }

    public void updateSupplierBankNumber(int id, int bankNumber) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(id).updateBankNumber(bankNumber);
    }

    public void updateSupplierID(int id, int newId) throws Exception {
        if(!supplierExist(id) || supplierExist(newId))
            throw new Exception("There is no supplier with this ID!");
        Supplier temp = suppliersDAO.getSupplier(id);
        temp.updateId(newId);
        suppliersDAO.removeSupplier(id);
        suppliersDAO.addSupplier(temp);
    }

    public void updateSupplierName(int id, String newName) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(id).updateName(newName);
    }

    public void addSupplierContact(int id, String contactName, String contactPhone) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        if(!validPhoneNumber(contactPhone))
            throw new Exception("Phone number is Illegal");
        Contact contact = new Contact(contactName, contactPhone);
        suppliersDAO.getSupplier(id).addContact(contact);
    }

    public void updateSupplierPayingAgreement(int id, String payingAgreement) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(id).changePayingAgreement(payingAgreement);
    }

    public void addSupplierManufacturer(int id, String manufacturer) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(id).addManufacturer(manufacturer);
    }

    public void addAgreement(int supplierId, int agreementType, String agreementDays) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.getSupplier(supplierId).addAgreement(agreementType, agreementDays);
    }

    public void addItemsToAgreement(int supplierId, List<String> itemsString) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).addAgreementItems(itemsString);
    }


    //SHOULD BE PRIVATE, public for testing
    public boolean supplierExist(int id){
        return suppliersDAO.supplierExist(id);
    }


    //SHOULD BE PRIVATE, public for testing
    public boolean validPhoneNumber(String phoneNumber){
        for(int i = 0; i < phoneNumber.length(); i++){
            if(Character.isLetter(phoneNumber.charAt(i)))
                return false;
        }
        if(phoneNumber.length() < 8 || phoneNumber.length() > 13)
            return false;
        return true;
    }


    /*public Map<String, List<String>> itemsFromAllSuppliers() throws Exception {
        HashMap<String, List<String>> items = new HashMap<>();
        for (Supplier supplier : suppliers.values())
            items.put(supplier.getName(), supplier.getOrderedItems());
        return items;
    }*/

    public List<String> itemsFromOneSupplier(int id) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        return suppliersDAO.getSupplier(id).getOrderedItems();
    }

    public void updateBulkPriceForItem(int supplierId, int itemID, Map<Integer, Integer> newBulkPrices) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updateBulkPriceForItem(itemID, newBulkPrices);
    }

    public void updatePricePerUnitForItem(int supplierId, int itemID, float newPrice) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updatePricePerUnitForItem(itemID, newPrice);
    }

    public void updateItemId(int supplierId, int olditemId, int newItemId) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updateItemId(olditemId, newItemId);
    }

    public void updateItemName(int supplierId, int itemId, String newName) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updateItemName(itemId, newName);
    }

    public void updateItemManufacturer(int supplierId, int itemId, String manufacturer) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updateItemManufacturer(itemId, manufacturer);
    }

    public void addItemToAgreement(int supplierId, int itemId, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).addItem(itemId, itemName, itemManu, itemPrice, bulkPrices);
    }

    public void deleteItemFromAgreement(int supplierId, int itemId) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).deleteItem(itemId);
    }

    public boolean isTransporting(int supplierId) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        return suppliersDAO.getSupplier(supplierId).isTransporting();
    }

    public void updateAgreementType(int supplierId,  int agreementType, String agreementDays) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updateAgreementType(agreementType, agreementDays);
    }

    public void setAgreement(int supplierId, int agreementType, String agreementDays) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).addAgreement(agreementType, agreementDays);
    }

    public List<Integer> getDaysOfDelivery(int supplierId) throws Exception{
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        return suppliersDAO.getSupplier(supplierId).getDaysOfDelivery();
    }

    public int getDeliveryDays(int supplierId) throws Exception{
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        return suppliersDAO.getSupplier(supplierId).getDeliveryDays();
    }

    public int daysToDelivery(int supplierId) throws Exception {
        if(!supplierExist(supplierId)){
            throw new Exception("There is no supplier with this ID!");
        }
        return suppliersDAO.getSupplier(supplierId).daysToDelivery();

    }

    public ArrayList<String> getSupplierInfo(int supplierId) throws Exception{
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        return suppliersDAO.getSupplier(supplierId).getSupplierInfo();
    }

    public boolean isRoutineAgreement(int supplierId) throws Exception {
        if(!supplierExist(supplierId)){
            throw new Exception("There is no supplier with this ID!");
        }
        return suppliersDAO.getSupplier(supplierId).isRoutineAgreement();
    }

    public boolean isByOrderAgreement(int supplierId) throws Exception {
        if(!supplierExist(supplierId)){
            throw new Exception("There is no supplier with this ID!");
        }
        return suppliersDAO.getSupplier(supplierId).isByOrderAgreement();
    }

    public boolean isNotTransportingAgreement(int supplierId) throws Exception {
        if(!supplierExist(supplierId)){
            throw new Exception("There is no supplier with this ID!");
        }
        return suppliersDAO.getSupplier(supplierId).isNotTransportingAgreement();
    }

    public void setDaysOfDelivery(int supplierID, String days) throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supplierID).setDaysOfDelivery(days);
    }

    public void addDaysOfDelivery(int supplierID, String days) throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supplierID).addDaysOfDelivery(days);
    }

    public void removeDayOfDelivery(int supplierID, int day) throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supplierID).removeDayOfDelivery(day);
    }

    public String getItem(int supplierID, int itemId) throws Exception {
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliersDAO.getSupplier(supplierID).getItem(itemId).getInfoInStringFormat();
    }

    public void editBulkPrice(int supplierID, int itemId, int quantity, int discount)throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supplierID).getItem(itemId).editBulkPrice(quantity, discount);
    }

    public void addBulkPrice(int supplierID, int itemId, int quantity, int discount)throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supplierID).getItem(itemId).addBulkPrice(quantity, discount);
    }

    public void removeBulkPrice(int supplierID, int itemId, int quantity)throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supplierID).getItem(itemId).removeBulkPrice(quantity);
    }

    public double calculatePriceForItemOrder(int supplierID, int itemId, int quantity) throws Exception {
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliersDAO.getSupplier(supplierID).getItem(itemId).calculateTotalPrice(quantity);
    }

    public void changeDaysUntilDelivery(int supplierID, int days)throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        if(suppliersDAO.getSupplier(supplierID).isByOrderAgreement()){
            suppliersDAO.getSupplier(supplierID).setDaysUntilDelivery(days);
        }
        else{
            throw new Exception("This supplier does not have a BY-ORDER-TRANSPORT agreement.");
        }
    }

    public List<String> getAllContact(int supID)throws Exception{
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        List<String> contacts = new LinkedList<>();

        List<Contact> list = suppliersDAO.getSupplier(supID).getAllContact();

        for(Contact c : list){
            contacts.add(c.toString());
        }

        return contacts;
    }

    public void removeContact(int supID, String name) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supID).removeContact(name);
    }

    public List<String> getManufacturers(int supID) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliersDAO.getSupplier(supID).getManufacturers();
    }

    public void removeManufacturer(int supID, String name) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supID).removeManufacturer(name);
    }

    public boolean isSuppliersEmpty(){
        return suppliersDAO.isEmpty();
    }

    public boolean hasAgreement(int supID) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliersDAO.getSupplier(supID).hasAgreement();
    }

    public void addNewOrder(int supId) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supId).addNewOrder();
    }

    public void addItemsToOrder(int supId, int orderId, List<String> itemsString) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        for(int i = 0; i < itemsString.size(); i+=3 ){
            if(itemsString.size() <= i+2)
               throw new Exception("Some information is missing!");
            suppliersDAO.getSupplier(supId).addOneItemToOrder(orderId , Integer.parseInt(itemsString.get(i)), Integer.parseInt(itemsString.get(i+2)));
        }
        //suppliers.get(supId).addItemsToOrder(orderId, itemsString);
    }

    public void addItemToOrder(int supId, int orderId, int itemId, int itemQuantity) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supId).addOneItemToOrder(orderId, itemId, itemQuantity);
    }

    public void removeOrder(int supId, int orderId) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supId).removeOrder(orderId);
    }

    public void removeItemFromOrder(int supId, int orderId, int itemId) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supId).removeItemFromOrder(orderId, itemId);
    }

    public void updateItemQuantityInOrder(int supID, int orderID, int itemID, int quantity) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supID).updateOrder(orderID, itemID, quantity);
    }


    public List<String> getOrder(int supId, int orderId) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        return suppliersDAO.getSupplier(supId).getOrder(orderId);
    }

    public boolean doesSupplierExists(int id) {
        return suppliersDAO.supplierExist(id);
    }

    public boolean orderExists(int supID, int orderID) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliersDAO.getSupplier(supID).orderExists(orderID);
    }

    public Order makeOrderBecauseOfMinimum(int storeID, int productID, int amount) throws Exception {

        ArrayList<Order> orders = getOrderWithinTheNextTwoDays(productID);
        if(!orders.isEmpty()) {                 //we found supplier that supplies within the next 2 days
            Order order = getTheCheapestSupplier(orders, productID, amount);
            Supplier supplier = suppliersDAO.getSupplier(order.getId());
            return supplier.getOrderForThisAmount(order.getId(), productID, amount);
        }
        else{                                   //we didn't found anyone in the next 2 days, than we will take the soonest
            Order order = getTheClosestOrder();
            Supplier supplier = suppliersDAO.getSupplier(order.getId());
            return supplier.getOrderForThisAmount(order.getId(), productID, amount);
        }
    }


    private Order getTheCheapestSupplier(ArrayList<Order> orders, int productID, int amount) throws Exception {
        HashMap<Order , Double> prices = new HashMap<>();
        for(Order currOrder : orders){
            int supplierId = currOrder.getSupplierId();
            Supplier supplier = suppliersDAO.getSupplier(supplierId);
            prices.put(currOrder, supplier.getToatalPriceForItem(productID, amount));
        }
        return sortAndReturnFirstOrder(prices);
    }

    private ArrayList<Order> getOrderWithinTheNextTwoDays(int productID) {
        ArrayList<Supplier> allSuppliers = suppliersDAO.getAllSuppliers();
        ArrayList<Order> result = new ArrayList<>();
        for(Supplier supplier : allSuppliers){
            ArrayList<Order> currOrder = supplier.itemInOrderForTheNextTwoDays(productID);
            if(currOrder.size() != 0){
                result.addAll(currOrder);
            }
        }
        return result;
    }

    private Order getTheClosestOrder() {
        ArrayList<Order> ordersFromAllSuppliers = getAllFutureOrders();
        HashMap<Order, Integer> ordersAndDays = new HashMap<>();
        for(Order order: ordersFromAllSuppliers){
            ordersAndDays.put(order, order.getDaysUntillOrder(Calendar.getInstance().getTime()));
        }
        
        return sortAndReturnFirstOrderInteger(ordersAndDays);
        
    }

    // TODO: 07/05/2022   //check with Sagi, see if we can do both double 
    private Order sortAndReturnFirstOrder(HashMap<Order, Double> orders) {
        Stream<Map.Entry<Order,Double>> sorted = orders.entrySet().stream().sorted(Map.Entry.comparingByValue());
        return sorted.findFirst().get().getKey();
    }

    private Order sortAndReturnFirstOrderInteger(HashMap<Order, Integer> orders) {
        Stream<Map.Entry<Order,Integer>> sorted = orders.entrySet().stream().sorted(Map.Entry.comparingByValue());
        return sorted.findFirst().get().getKey();
    }

    private ArrayList<Order> getAllFutureOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Supplier> suppliers = suppliersDAO.getAllSuppliers();
        for(Supplier supplier : suppliers){
            orders.addAll(supplier.getFutureOrders());
        }
        return orders;
    }


    public List<Order> getOrdersOnTheWay(int storeID) {
        //return all orders on the way tomorrow
        return null;
    }

    public void orderHasArrived(int orderID) {
        //order has arrived, can be moved from current to past
    }
}
