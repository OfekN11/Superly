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



    public int addSupplier(String name, int bankNumber, String address, String payingAgreement, ArrayList<Pair<String,String>> contactPairs, ArrayList<String> manufacturers) throws Exception {
        ArrayList<Contact> contacts = createContacts(contactPairs);
        Supplier supplier = new Supplier(name, bankNumber, address, payingAgreement, contacts, manufacturers);
        suppliersDAO.addSupplier(supplier);
        return supplier.getId();
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

    public void addItemToAgreement(int supplierId, int itemId, int idBySupplier, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).addItem(itemId, idBySupplier, itemName, itemManu, itemPrice, bulkPrices);
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

    public int addNewOrder(int supId) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        return suppliersDAO.getSupplier(supId).addNewOrder();
    }

    public void addItemsToOrder(int supId, int orderId, List<String> itemsString) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        for(int i = 0; i < itemsString.size(); i+=3 ){
            if(itemsString.size() <= i+2)
               throw new Exception("Some information is missing!");
            suppliersDAO.getSupplier(supId).addOneItemToOrder(orderId , Integer.parseInt(itemsString.get(i)),Integer.parseInt(itemsString.get(i+1)),  Integer.parseInt(itemsString.get(i+2)));
        }
        //suppliers.get(supId).addItemsToOrder(orderId, itemsString);
    }

    public void addItemToOrder(int supId, int orderId, int itemId, int idBySupplier, int itemQuantity) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supId).addOneItemToOrder(orderId, itemId, idBySupplier, itemQuantity);
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


    /*
    public Order makeOrderBecauseOfMinimum(int storeID, int productID, int amount) throws Exception {

        ArrayList<Order> orders = getOrderWithinTheNextTwoDays(productID);  //check the amount necessary
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
     */

    public void orderHasArrived(int orderID) {
        //order has arrived, can be moved from current to past
    }

    //returns all orders that cannot be changed anymore (routine) + everything needed because of MinAmounts
    public List<Order> createAllOrders(Map<Integer, Map<Integer, Integer>> orderItemMinAmounts) { //map<productID, Map<store, amount>>


        //prior to this function we need to upload all the suppliers and agreements. the last order id is uploaded too
        // check what routine suppliers delivers tomorrow (using the agreement days)
        // take the orderId of the suppliers we found and go to ordersDAO and upload the requested order.
        // upload only the orders that their arrival time passed.
        // also upload the orders that their arrival time is tomorrow (this orders were created because lack of supplies), those we don't copy construct!!
        // copy constructor to a new order with different arrival time and creation time. (only for the orders which arrival time passed)
        // now we have a list of routine orders for tomorrow!!!
        ArrayList<Order> ordersForTomorrow = getOrdersForTomorrow();


        //subtract the products that are delivered tomorrow from the map
        Map<Integer, Map<Integer, Integer>> updatedQuantity = checkForProductInTomorrowOrders(orderItemMinAmounts, ordersForTomorrow);


        // create all the orders required for the min amounts GET THE CHEAPEST ONE!
        //iterate through all the items and for every item choose the cheapest supplier
        //create Map<supplierId, List<OrderItem>> for each cheapest supplier we found and create the orders after we found all the suppliers using a new constructor
        // add a new constructor that gets all the items and put them on his list.
        // if we found that the cheapest supplier is a routine one, and he has an order tomorrow w need to delete the last order
        // and replace it with the item that are low in stocks???.  (if we do this, we need to delete the prev order from orderForTomorrow list above.
        //now we have the list of all the orders for low stocks
        ArrayList<Order> ordersFromMinSuppliers = getCheapestSupplierForEachItem(updatedQuantity);

        //combine the arrays and return them tp inventory

        // now we need to send them to the ordersDAO and save in dataBase.

        return ordersForTomorrow;
    }

    private ArrayList<Order> getCheapestSupplierForEachItem(Map<Integer, Map<Integer, Integer>> updatedQuantity) {
        Map<Integer, Integer> productsANdQuantity = organizeProductsMap(updatedQuantity);  //organize the map to normal map with id and quantity
        ArrayList<Order> orders = new ArrayList<>();
        for(Map.Entry<Integer,Integer> entry : productsANdQuantity.entrySet()){
            orders.add( getTheCheapstSupplierForThisItem(entry.getKey(), entry.getValue()));
        }
        return null;
    }

    private Order getTheCheapstSupplierForThisItem(int productId, int quantity) {
        //iterate through all suppliersAgreement, calculate the cheapest one and order from him

        //chosenSupplier.orderForThisAMount(productId, quantity);
        return null;
    }

    private Map<Integer, Integer> organizeProductsMap(Map<Integer, Map<Integer, Integer>> updatedQuantity) {
        HashMap<Integer, Integer> result = new HashMap<>();
        for(Map.Entry<Integer,Map<Integer,Integer>> entry : updatedQuantity.entrySet()){
            int productId = entry.getKey();
            int storeId = updatedQuantity.get(productId).keySet().iterator().next();
            result.put(productId, entry.getValue().get(storeId));
        }
        return result;
    }

    private Map<Integer, Map<Integer, Integer>> checkForProductInTomorrowOrders(Map<Integer, Map<Integer, Integer>> orderItemMinAmounts, ArrayList<Order> ordersForTomorrow) {
        return null;
    }

    private ArrayList<Order> getOrdersForTomorrow() {
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<Supplier> suppliers = suppliersDAO.getAllSuppliers();
        for(Supplier supplier : suppliers){
            orders.addAll(supplier.getOrdersForTomorrow());
        }
        return orders;
    }
}
