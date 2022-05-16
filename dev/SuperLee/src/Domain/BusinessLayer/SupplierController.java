package Domain.BusinessLayer;

import Domain.BusinessLayer.Supplier.*;
import Domain.PersistenceLayer.Controllers.OrderDAO;
import Domain.PersistenceLayer.Controllers.SuppliersDAO;
import Globals.Pair;

import java.sql.SQLException;
import java.util.*;

public class SupplierController {

    private SuppliersDAO suppliersDAO;
    private OrderDAO orderDAO;


    public SupplierController(){
        suppliersDAO = new SuppliersDAO();
        orderDAO = new OrderDAO();

        //maybe add dataBase to save globalId for orders and suppliers and transfer the id to suppliersDAO and OrderDAO

    }


    public void loadSuppliersData(){
        try {
            int largestId = suppliersDAO.loadAllSuppliersInfo();
            Supplier.setGlobalId(largestId + 1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public int addSupplier(String name, int bankNumber, String address, String payingAgreement, ArrayList<Pair<String,String>> contactPairs, ArrayList<String> manufacturers) throws Exception {
        ArrayList<Contact> contacts = createContacts(contactPairs);
        Supplier supplier = new Supplier(name, bankNumber, address, payingAgreement, contacts, manufacturers);

        suppliersDAO.save(String.valueOf(supplier.getId()), supplier);
        //suppliersDAO.addSupplier(supplier, contacts, manufacturers);
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

        suppliersDAO.updateSupplierAddress(id, address);
        suppliersDAO.getSupplier(id).updateAddress(address);


    }

    public void updateSupplierBankNumber(int id, int bankNumber) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.updateSupplierBankNumber(id, bankNumber);
        suppliersDAO.getSupplier(id).updateBankNumber(bankNumber);
    }

    public void updateSupplierName(int id, String newName) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.updateSupplierName(id, newName);
        suppliersDAO.getSupplier(id).updateName(newName);

    }

    public void addSupplierContact(int id, String contactName, String contactPhone) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        if(!validPhoneNumber(contactPhone))
            throw new Exception("Phone number is Illegal");
        Contact contact = new Contact(contactName, contactPhone);

        suppliersDAO.addSupplierContact(id, contact);
        suppliersDAO.getSupplier(id).addContact(contact);
    }

    public void updateSupplierPayingAgreement(int id, String payingAgreement) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.updateSupplierPayingAgreement( id, payingAgreement);
        suppliersDAO.getSupplier(id).updatePayingAgreement(payingAgreement);
    }

    public void addSupplierManufacturer(int id, String manufacturer) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.addSupplierManufacturer(id, manufacturer);
        suppliersDAO.getSupplier(id).addManufacturer(manufacturer);
    }

    public void addAgreement(int supplierId, int agreementType, String agreementDays) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.getSupplier(supplierId).addAgreement(agreementType, agreementDays, suppliersDAO);
    }


    public void updateAgreementType(int supplierId,  int agreementType, String agreementDays) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.getSupplier(supplierId).updateAgreementType(agreementType, agreementDays);
        suppliersDAO.updateAgreementType(supplierId, agreementType);
        List<Integer> days = suppliersDAO.getSupplier(supplierId).getAgreementDays();
        suppliersDAO.getAgreementController().removeSupplier(supplierId);
        suppliersDAO.getAgreementController().updateAgreementDays(supplierId, days, agreementType);
    }

    /*
    //we don't use this function!!!!
    public void setAgreement(int supplierId, int agreementType, String agreementDays) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.getSupplier(supplierId).addAgreement(agreementType, agreementDays, null);
        suppliersDAO.updateAgreementType(supplierId, agreementType);
        List<Integer> days = suppliersDAO.getSupplier(supplierId).getAgreementDays();
    }
     */


    public void addItemsToAgreement(int supplierId, List<String> itemsString) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.getSupplier(supplierId).addAgreementItems(itemsString, suppliersDAO);

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


    /*
    //we don't use this function
    public Map<String, List<String>> itemsFromAllSuppliers() throws Exception {
        HashMap<String, List<String>> items = new HashMap<>();
        for (Supplier supplier : suppliers.values())
            items.put(supplier.getName(), supplier.getOrderedItems());
        return items;
    }*/

    public List<String> itemsFromOneSupplier(int id) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        return suppliersDAO.getSupplier(id).getAgreementItems();
    }

    public void updateBulkPriceForItem(int supplierId, int itemID, Map<Integer, Integer> newBulkPrices) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updateBulkPriceForItem(itemID, newBulkPrices, suppliersDAO.getAgreementController());
    }

    public void updatePricePerUnitForItem(int supplierId, int itemID, float newPrice) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updatePricePerUnitForItem(itemID, newPrice, suppliersDAO.getAgreementItemDAO());
    }

    public void updateItemId(int supplierId, int olditemId, int newItemId) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updateItemId(olditemId, newItemId, suppliersDAO.getAgreementItemDAO());
    }

    public void updateItemName(int supplierId, int itemId, String newName) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updateItemName(itemId, newName, suppliersDAO.getAgreementItemDAO());
    }

    public void updateItemManufacturer(int supplierId, int itemId, String manufacturer) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).updateItemManufacturer(itemId, manufacturer, suppliersDAO);
    }


    public void addItemToAgreement(int supplierId, int itemId, int idBySupplier, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");

        suppliersDAO.getSupplier(supplierId).addItem(itemId, idBySupplier, itemName, itemManu, itemPrice, bulkPrices, suppliersDAO);
    }

    public void deleteItemFromAgreement(int supplierId, int itemId) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliersDAO.getSupplier(supplierId).deleteItem(itemId, suppliersDAO);
    }

    public boolean isTransporting(int supplierId) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");

        return suppliersDAO.getSupplier(supplierId).isTransporting();
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

        suppliersDAO.getSupplier(supplierID).setDaysOfDelivery(days, suppliersDAO.getAgreementController());
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
            suppliersDAO.getSupplier(supplierID).setDaysUntilDelivery(days, suppliersDAO.getAgreementController());
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


        List<Contact> list;
        list = suppliersDAO.getSupplier(supID).getAllContact();
        if(list == null || list.size() == 0){
            list = suppliersDAO.getAllSupplierContacts(supID);
        }
        for(Contact c : list){
            contacts.add(c.toString());
        }
        return contacts;
    }

    public void removeContact(int supID, String name) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supID).removeContact(name, suppliersDAO);

    }

    public List<String> getManufacturers(int supID) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        List<String> manufacturers = suppliersDAO.getSupplier(supID).getManufacturers();
        if(manufacturers == null || manufacturers.size() == 0){
            manufacturers = suppliersDAO.getAllSupplierManufacturers(supID);
        }
        return manufacturers;
    }

    public void removeManufacturer(int supID, String name) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliersDAO.getSupplier(supID).removeManufacturer(name, suppliersDAO);
    }

    public boolean isSuppliersEmpty(){
        return suppliersDAO.isEmpty();
    }

    public boolean hasAgreement(int supID) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliersDAO.hasAgreement(supID);
    }





    public int addNewOrder(int supId, int storeId) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        Order order = suppliersDAO.getSupplier(supId).addNewOrder(storeId, orderDAO, suppliersDAO.getAgreementController());

        //insertToOrderDAO( order ); its happening inside addNewOrder
        return order.getId();
    }

    public void addItemsToOrder(int supId, int orderId, List<String> itemsString) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        for(int i = 0; i < itemsString.size(); i+=2 ){
            if(itemsString.size() <= i+2)
               throw new Exception("Some information is missing!");
            suppliersDAO.getSupplier(supId).addOneItemToOrder(orderId , Integer.parseInt(itemsString.get(i)),Integer.parseInt(itemsString.get(i+1)), orderDAO);
        }
        //suppliers.get(supId).addItemsToOrder(orderId, itemsString);
    }

    public void addItemToOrder(int supId, int orderId, int itemId, int itemQuantity) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supId).addOneItemToOrder(orderId, itemId, itemQuantity, orderDAO);
    }


    public void removeOrder(int supId, int orderId) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supId).removeOrder(orderId, orderDAO);
    }

    public void removeItemFromOrder(int supId, int orderId, int itemId) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supId).removeItemFromOrder(orderId, itemId, orderDAO);
    }

    public void updateItemQuantityInOrder(int supID, int orderID, int itemID, int quantity) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }
        suppliersDAO.getSupplier(supID).updateOrder(orderID, itemID, quantity , orderDAO);
    }


    public List<String> getOrder(int supId, int orderId) throws Exception {
        if(!supplierExist(supId)){
            throw new Exception("The supplier does not exists!");
        }
        return suppliersDAO.getSupplier(supId).getOrder(orderId, orderDAO);
    }

    public boolean doesSupplierExists(int id) {
        return suppliersDAO.supplierExist(id);
    }

    public boolean orderExists(int supID, int orderID) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }
        return suppliersDAO.getSupplier(supID).orderExists(orderID, orderDAO);
    }

    public Order orderHasArrived(int orderID, int supplierID) throws Exception {
        Order order = getOrderObject(supplierID, orderID);
        return order;
    }

    private Order getOrderObject(int supplierID, int orderID) throws Exception {
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }
        return suppliersDAO.getSupplier(supplierID).getOrderObject(orderID, orderDAO);
    }

    //returns all orders that cannot be changed anymore (routine) + everything needed because of MinAmounts
    public List<Order> createAllOrders(Map<Integer, Map<Integer, Integer>> orderItemMinAmounts) throws Exception { //map<productID, Map<store, amount>>

        Map<String, ArrayList<Order>> ordersForTomorrow = getOrdersForTomorrow();

        checkForProductInTomorrowOrders(orderItemMinAmounts, ordersForTomorrow);

        //now we have the list of all the orders for low stocks
        createOrderAccordingToCheapestSupplier(orderItemMinAmounts, ordersForTomorrow);

        ArrayList<Order> result = ordersForTomorrow.get("not deletable");
        result.addAll(ordersForTomorrow.get("deletable"));

        return result;
    }

    private void createOrderAccordingToCheapestSupplier(Map<Integer, Map<Integer, Integer>> updatedQuantity, Map<String, ArrayList<Order>> orders) throws Exception {

        for(Integer productId : updatedQuantity.keySet()){
            Map<Integer, Integer> storeAndQuantity = updatedQuantity.get(productId);
            for(Map.Entry<Integer, Integer> entry : storeAndQuantity.entrySet()){
                int supplierId = getTheCheapestSupplier(productId, entry.getValue());
                if(supplierId == -1)
                    throw new Exception("No supplier supplies product " + productId);
                if( !checkIfOrderFromThisSupplierAlreadyExists(supplierId, orders, productId, entry.getKey(), entry.getValue()) )
                    createNewOrderForThisProduct(supplierId, productId, entry.getKey(), entry.getValue());
            }
        }
    }

    private boolean checkIfOrderFromThisSupplierAlreadyExists(int supplierId, Map<String, ArrayList<Order>> orders, int productId, int storeId, int quantity) throws SQLException {
        ArrayList<Order> deletableOrders = orders.get("deletable");
        ArrayList<Order> notDeletableOrders = orders.get("not deletable");
        for(Order order : deletableOrders){
            if(order.getStoreID() == storeId && order.getSupplierId() == supplierId){
                OrderItem orderItem = createNewOrderItem(supplierId, productId, quantity);
                ArrayList<OrderItem> items = new ArrayList<>();
                items.add(orderItem);
                Order newOrder = new Order(order, items);

                deletableOrders.remove(order);
                notDeletableOrders.add(newOrder);
                deleteOrderFromDAO(order.getId());
                insertToOrderDAO(newOrder);
                return true;
            }
        }

        for(Order order : notDeletableOrders){
            if(order.getStoreID() == storeId && order.getSupplierId() == supplierId){
                OrderItem orderItem = createNewOrderItem(supplierId, productId, quantity);
                ArrayList<OrderItem> items = order.getOrderItems();
                items.add(orderItem);
                Order newOrder = new Order(order, items);
                notDeletableOrders.remove(order);
                notDeletableOrders.add(newOrder);
                updateOrderDAO(newOrder);
                return true;
            }
        }
        return false;
    }



    private void createNewOrderForThisProduct(int supplierId, int productId, int storeId, int quantity) {
        Supplier supplier = suppliersDAO.getSupplier(supplierId);
        OrderItem orderItem  = createNewOrderItem(supplierId, productId, quantity);
        try {
            insertToOrderDAO( new Order(supplier.daysToDelivery() , supplierId, storeId, orderItem));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private OrderItem createNewOrderItem(int supplierId, int productId, int quantity) {
        Supplier supplier = suppliersDAO.getSupplier(supplierId);
        AgreementItem currItem = null;
        try {
            currItem = supplier.getItem(productId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new OrderItem( productId, currItem.getIdBySupplier() , currItem.getName() , quantity, currItem.getPricePerUnit(), currItem.getDiscount(quantity), currItem.calculateTotalPrice(quantity));
    }

    private int getTheCheapestSupplier(int productId, int quantity) {
        int supplierId = -1;
        double finalPrice = Double.MAX_VALUE;
        try {
        for(Supplier supplier : suppliersDAO.getAllSuppliers()){
            if( supplier.itemExists(productId)){
                double currFinalPrice = supplier.getToatalPriceForItem(productId, quantity);
                if( currFinalPrice < finalPrice){
                    finalPrice = currFinalPrice;
                    supplierId = supplier.getId();
                }
            }
        }
        } catch (Exception e) {               //will never reach here
            System.out.println(e.getMessage());
        }
        return supplierId;
    }


    private void checkForProductInTomorrowOrders(Map<Integer, Map<Integer, Integer>> orderItemMinAmounts, Map<String, ArrayList<Order>> ordersForTomorrow) {

        for(Integer productId : orderItemMinAmounts.keySet()){
            Map<Integer, Integer> storeAndQuantity = orderItemMinAmounts.get(productId);
            for(Map.Entry<Integer, Integer> entry : storeAndQuantity.entrySet()){
                int quantityFound = searchForOrderToUpdateShortage( productId, entry.getKey() , entry.getValue(), ordersForTomorrow);
                if(quantityFound > 0)
                    storeAndQuantity.put(entry.getKey(), quantityFound);
                else
                    storeAndQuantity.remove(entry.getKey());
            }
        }
    }

    private int searchForOrderToUpdateShortage(int productId, int storeId, int quantity, Map<String, ArrayList<Order>> orders) {
        ArrayList<Order> deletableOrders = orders.get("deletable");
        ArrayList<Order> notDeletableOrders = orders.get("not deletable");
        int quantityInOrder = quantity;
        for(Order order : deletableOrders){
            if(order.getStoreID() == storeId && order.itemExists(productId)){
                quantityInOrder -= order.getQuantityOfItem(productId);
                notDeletableOrders.add(order);
                deletableOrders.remove(order);
            }
        }
        return quantityInOrder;
    }


    private Map<String, ArrayList<Order>> getOrdersForTomorrow() throws SQLException {
        Map<String, ArrayList<Order>> result = new HashMap<>();

        loadSuppliersData();
        ArrayList<Integer> supplierIds = getAllRoutineSuppliersDeliveringTomorrow();
        ArrayList<Order> lastOrderForRoutineSupplier = uploadLastOrderForRoutineSupplier(supplierIds);

        ArrayList<Order> ordersArrivalTimePassed = filterOrdersArrivalTimePassed(lastOrderForRoutineSupplier);        //This we create new order with all the old information

        ArrayList<Order> ordersArrivalTomorrow = filterOrdersArrivalTomorrow(lastOrderForRoutineSupplier);            // This orders cannot be deleted, only add items!!
        result.put("not deletable", ordersArrivalTomorrow);

        ArrayList<Order> newOrdersFromArrivalTimePassed = createNewOrdersFromArrivalTimePassed(ordersArrivalTimePassed);
        result.put("deletable", newOrdersFromArrivalTimePassed);

        return result;
    }


    private ArrayList<Integer> getAllRoutineSuppliersDeliveringTomorrow() {
        ArrayList<Integer> result = new ArrayList<>();
        for(Supplier supplier : suppliersDAO.getAllSuppliers()){
            try {
                if(supplier.isRoutineAgreement() && supplier.daysToDelivery() == 1)
                    result.add(supplier.getId());
            } catch (Exception e) { //exception will never be caught because && before
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    private ArrayList<Order> uploadLastOrderForRoutineSupplier(ArrayList<Integer> supplierIds) {
        ArrayList<Integer> orderIds = new ArrayList<>();
        Supplier currSupplier;
        for(Integer supplierId : supplierIds){
            currSupplier = suppliersDAO.getSupplier(supplierId);

            int lastOrderId = currSupplier.getLastOrderId();
            if(lastOrderId != -1)
                orderIds.add(lastOrderId);
        }

        //return orderDAO.getLastOrdersFromALlSuppliers(orderIds);
        //ArrayList<Order> result = upload and get Orders with matching Ids from dataBase.
        //return result;

        return null;
    }


    private ArrayList<Order> filterOrdersArrivalTomorrow(ArrayList<Order> orders) {
        ArrayList<Order> result = new ArrayList<>();
        for(Order order : orders){
            if(order.getDaysUntilOrder(Calendar.getInstance().getTime()) == 1);
                result.add(order);
        }
        return result;
    }

    private ArrayList<Order> filterOrdersArrivalTimePassed(ArrayList<Order> orders) {
        ArrayList<Order> result = new ArrayList<>();
        for(Order order : orders){
            if(order.passed())
                result.add(order);
        }
        return result;
    }



    private ArrayList<Order> createNewOrdersFromArrivalTimePassed(ArrayList<Order> ordersArrivalTimePassed) throws SQLException {
        ArrayList<Order> result = new ArrayList<>();
        for(Order order : ordersArrivalTimePassed){
            Order newOrder = new Order(order);
            //on comment until orderDAO will work
            //orderDAO.removeOrder(order.getId());
            //orderDAO.addOrder(newOrder);
            result.add(newOrder);
        }
        return result;
    }



    private void insertToOrderDAO(Order order) throws SQLException {
        orderDAO.addOrder(order);
    }

    private void deleteOrderFromDAO(int id) throws SQLException {
        orderDAO.removeOrder(id);
    }


    private void updateOrderDAO(Order newOrder) throws SQLException {
        orderDAO.updateOrder(newOrder);
    }


}
