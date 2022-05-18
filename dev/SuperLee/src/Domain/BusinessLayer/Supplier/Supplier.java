package Domain.BusinessLayer.Supplier;

import Domain.BusinessLayer.Supplier.Agreement.Agreement;
import Domain.BusinessLayer.Supplier.Agreement.ByOrderAgreement;
import Domain.BusinessLayer.Supplier.Agreement.NotTransportingAgreement;
import Domain.BusinessLayer.Supplier.Agreement.RoutineAgreement;
import Domain.PersistenceLayer.Controllers.AgreementController;
import Domain.PersistenceLayer.Controllers.AgreementItemDAO;
import Domain.PersistenceLayer.Controllers.OrderDAO;
import Domain.PersistenceLayer.Controllers.SuppliersDAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Supplier {

    private int id;
    private int bankNumber;
    private String address;
    private String name;
    private ArrayList<Contact> contacts;
    private String payingAgreement;
    private Agreement agreement;
    private ArrayList<String> manufacturers;

    private HashMap<Integer, Order> ordersToBe;    //orders that will be in the future
    private HashMap<Integer, Order> finishedOrders;
    private HashMap<Integer, Order> ordersInTheNext24Hours;


    private final int ROUTINE  = 1;
    private final int BY_ORDER  = 2;
    private final int NOT_TRANSPORTING  = 3;

    private static int globalID = 1;


    public Supplier(String name, int bankNumber, String address,String payingAgreement, ArrayList<Contact> contacts, ArrayList<String> manufacturers){
        this.id = globalID;
        globalID++;
        this.name = name;
        this.bankNumber = bankNumber;
        this.address = address;
        this.payingAgreement = payingAgreement;
        this.contacts = contacts;
        this.manufacturers = manufacturers;
        this.ordersToBe = new HashMap<>();
        this.finishedOrders = new HashMap<>();
        this.ordersInTheNext24Hours = new HashMap<>();
        agreement = null;
    }

    public Supplier(int id, int bankNumber, String address, String name, String payingAgreement){
        this.id = id;
        this.name = name;
        this.bankNumber = bankNumber;
        this.address = address;
        this.payingAgreement = payingAgreement;

        this.manufacturers = new ArrayList<>();
        this.contacts = new ArrayList<>();
        this.ordersToBe = new HashMap<>();
        this.finishedOrders = new HashMap<>();
        this.ordersInTheNext24Hours = new HashMap<>();
        agreement = null;
    }

    public Supplier(int id, int bankNumber, String address, String name, String payingAgreement,ArrayList<Contact> contacts, ArrayList<String> manufacturers ){
        this.id = id;
        this.name = name;
        this.bankNumber = bankNumber;
        this.address = address;
        this.payingAgreement = payingAgreement;

        this.contacts = contacts;
        this.manufacturers = manufacturers;
        this.ordersToBe = new HashMap<>();
        this.finishedOrders = new HashMap<>();
        this.ordersInTheNext24Hours = new HashMap<>();
        agreement = null;
    }

    public static void setGlobalId(int id){
        globalID = id ;
    }

    public int getId() {
        return id;
    }

    private void agreementExists() throws Exception {
        if(agreement == null){
            throw new Exception("You have no agreement with this supplier!");
        }
    }

    public String getAddress() {
        return address;
    }

    public String getPayingAgreement() {
        return payingAgreement;
    }

    public int getBankNumber() {
        return bankNumber;
    }

    public boolean removeContact(Contact contact) {
        return contacts.remove(contact);
    }


    public void addAgreement(int agreementType, String agreementDays, SuppliersDAO suppliersDAO) throws Exception {
        if(agreementType > NOT_TRANSPORTING || agreementType < ROUTINE)
            throw new Exception("Invalid agreement type!");

        createAgreement(agreementType, agreementDays, suppliersDAO);


    }




    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateBankNumber(int bankNumber) {
        this.bankNumber = bankNumber;
    }


    public void updateName(String newName) {
        this.name = newName;
    }



    public void addContact(Contact contact) {
        contacts.add(contact);
    }


    public void updatePayingAgreement(String payingAgreement) {
        this.payingAgreement = payingAgreement;
        //If we add this payingAgreement to the agreement,  need to update there too
    }


    public void addManufacturer(String manufacturer) throws Exception {
        if(manufacturers.contains(manufacturer)){
            throw new Exception("This manufacturer already exists in the system!");
        }

        manufacturers.add(manufacturer);
    }



    public String getName() {
        return name;
    }

    public List<String> getAgreementItems() throws Exception {
        agreementExists();
        return agreement.getItemsInMapFormat();
    }

    public void updateBulkPriceForItem(int itemID, Map<Integer, Integer> newBulkPrices, AgreementController agreementController) throws Exception {
        agreementExists();
        agreementController.updateBulkPriceForItem(itemID, newBulkPrices);
        agreement.getItem(itemID).setBulkPrices(newBulkPrices);
    }

    public void updatePricePerUnitForItem(int itemID, float newPrice, AgreementItemDAO agreementItemDAO) throws Exception {
        agreementExists();
        agreementItemDAO.updatePPU(itemID, newPrice);
        agreement.getItem(itemID).setPrice(newPrice);
    }


    public void addItem(int itemId, int idBySupplier, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices, SuppliersDAO suppliersDAO) throws Exception {
        agreementExists();
        if(agreement.itemExists(itemId))
            throw new Exception("item with this ID already exists!");
        AgreementItem item = new AgreementItem(itemId, idBySupplier, itemName, itemManu, itemPrice, bulkPrices);
        ArrayList<AgreementItem> _items = new ArrayList<>();
        _items.add(item);
        AgreementController agreementController = suppliersDAO.getAgreementController();
        agreementController.addAgreementItems( _items, id);
        agreement.addItem(item);
        if(!manufacturers.contains(itemManu)){
            suppliersDAO.addSupplierManufacturer(id, itemManu);
            manufacturers.add(itemManu);
        }
    }


    public void deleteItem(int itemId, SuppliersDAO suppliersDAO) throws Exception {
        agreementExists();
        String manu = agreement.getItem(itemId).getManufacturer();
        AgreementItemDAO agreementItemDAO = suppliersDAO.getAgreementItemDAO();
        agreementItemDAO.removeItem(id, itemId);
        agreement.removeItem(itemId);

        if(!agreement.isManufacturerRepresented(manu)){
            suppliersDAO.removeSupplierManufacturer(id, manu);
            manufacturers.remove(manu);
        }
    }


    public boolean isTransporting() throws Exception {
        agreementExists();
        return agreement.isTransporting();
    }



    public void updateItemId(int oldItemId, int newItemId, AgreementItemDAO agreementItemDAO) throws Exception {
        agreementExists();
        agreementItemDAO.updateItemId(oldItemId, newItemId);
        agreement.setItemId(oldItemId, newItemId);
    }


    public void updateItemName(int itemId, String newName, AgreementItemDAO agreementItemDAO) throws Exception {
        agreementExists();
        agreementItemDAO.updateItemName(itemId, newName);
        agreement.getItem(itemId).setName(newName);
    }

    public void updateItemManufacturer(int itemId, String manufacturer, SuppliersDAO suppliersDAO) throws Exception {
        AgreementItemDAO agreementItemDAO = suppliersDAO.getAgreementItemDAO();
        agreementExists();
        String manu = agreement.getItem(itemId).getManufacturer();

        agreementItemDAO.updateManufacturer(itemId, manufacturer);
        agreement.getItem(itemId).setManufacturer(manufacturer);

        if(!agreement.isManufacturerRepresented(manu)){
            suppliersDAO.removeSupplierManufacturer(id, manu);
            suppliersDAO.addSupplierManufacturer(id, manufacturer);
            manufacturers.remove(manu);
            manufacturers.add(manufacturer);
        }
    }



    public void addAgreementItems(List<String> itemsString, SuppliersDAO suppliersDAO) throws Exception {
        AgreementController agreementController =  suppliersDAO.getAgreementController();
        agreementExists();
        agreement.setItemsFromString(itemsString, id,  agreementController);

        manufacturers = new ArrayList<>();

        for(AgreementItem item : agreement.getItems()){
            suppliersDAO.addSupplierManufacturer(id, item.getManufacturer());
            manufacturers.add(item.getManufacturer());
        }
    }



    public void updateAgreementType( int agreementType, String agreementDays) throws Exception {
        agreementExists();
        List<AgreementItem> items = agreement.getItems();
        createAgreementAgain(agreementType, agreementDays);
        agreement.setItems(items);
    }

    private void createAgreementAgain(int agreementType, String agreementDays) throws Exception {
        switch(agreementType){
            case ROUTINE :
                if(!RoutineAgreement.hasDays(agreementDays)){
                    throw new Exception("You provided no days!");
                }
                List<Integer> days = RoutineAgreement.daysStringToDay(agreementDays);
                agreement = new RoutineAgreement(days);
                break;
            case BY_ORDER :
                days = new ArrayList<>();   days.add(Integer.parseInt(agreementDays));
                agreement = new ByOrderAgreement(Integer.parseInt(agreementDays));
                break;
            case NOT_TRANSPORTING :
                agreement = new NotTransportingAgreement();
                break;
        }
    }


    private void createAgreement(int agreementType, String agreementDays, SuppliersDAO suppliersDAO) throws Exception {
        AgreementController agreementController = suppliersDAO.getAgreementController();

        suppliersDAO.updateAgreementType(id, agreementType);

        switch(agreementType){
            case ROUTINE :
                if(!RoutineAgreement.hasDays(agreementDays)){
                    throw new Exception("You provided no days!");
                }
                List<Integer> days = RoutineAgreement.daysStringToDay(agreementDays);
                agreementController.updateAgreementDays(id, days, ROUTINE);
                agreement = new RoutineAgreement(days);
                break;
            case BY_ORDER :
                days = new ArrayList<>();
                days.add(Integer.parseInt(agreementDays));
                agreementController.updateAgreementDays(id, days, BY_ORDER);
                agreement = new ByOrderAgreement(Integer.parseInt(agreementDays));
                break;
            case NOT_TRANSPORTING :
                agreementController.updateAgreementDays(id, null,NOT_TRANSPORTING);
                agreement = new NotTransportingAgreement();
                break;
        }
    }

    public List<Integer> getAgreementDays(){
        return agreement.getDays();
    }



    public List<Integer> getDaysOfDelivery() {
        if(agreement instanceof RoutineAgreement){
            return ((RoutineAgreement)agreement).getDaysOfDelivery();
        }
        else{
            return null;
        }
    }

    public int getDeliveryDays(){
        if(agreement instanceof ByOrderAgreement){
            return ((ByOrderAgreement)agreement).getDeliveryDays();
        }
        else{
            return -1;
        }
    }

    // < id , name , bankAccount , address , payingAgreement , Contact1Name , Contact1Phone ,  Contact2Name , Contact2Phone ... >
    public ArrayList<String> getSupplierInfo() {
        ArrayList<String> result = new ArrayList<>();

        result.add(String.valueOf(getId()));
        result.add(getName());
        result.add(String.valueOf(getBankNumber()));
        result.add(getAddress());
        result.add(getPayingAgreement());
        for(Contact contact : contacts){
            result.add(contact.getName());
            result.add(contact.getPhone());
        }
        return result;
    }

    public int daysToDelivery() throws Exception {
        agreementExists();
        return agreement.daysToDelivery();
    }

    public boolean isRoutineAgreement() {
        return agreement != null && agreement instanceof RoutineAgreement;
    }

    public boolean isByOrderAgreement(){
        return agreement != null && agreement instanceof ByOrderAgreement;
    }

    public boolean isNotTransportingAgreement() {
        return agreement != null && agreement instanceof NotTransportingAgreement;
    }

    public void setDaysOfDelivery(String days, AgreementController agreementController) throws Exception{
        agreementExists();
        if(!(agreement instanceof RoutineAgreement)){
            throw new Exception("The supplier's agreement is not Routine agreement");
        }

        ((RoutineAgreement) agreement).setDaysOfDelivery(days, id, agreementController);
    }

    public void setDaysUntilDelivery(int days, AgreementController agreementController) throws Exception{
        agreementExists();
        if(!(agreement instanceof ByOrderAgreement)){
            throw new Exception("The supplier's agreement is not Routine agreement");
        }

        ((ByOrderAgreement) agreement).setDeliveryDays(days, id, agreementController);
    }

    public void addDaysOfDelivery(String days) throws Exception {
        agreementExists();
        if(!(agreement instanceof RoutineAgreement)){
            throw new Exception("The supplier's agreement is not Routine agreement");
        }

        ((RoutineAgreement) agreement).addDaysOfDelivery(days);
    }

    public void removeDayOfDelivery(int day) throws Exception {
        agreementExists();
        if(!(agreement instanceof RoutineAgreement)){
            throw new Exception("The supplier's agreement is not Routine agreement");
        }

        ((RoutineAgreement) agreement).removeDayOfDelivery(day);
    }

    public AgreementItem getItem(int itemId) throws Exception {
        agreementExists();
        return agreement.getItem(itemId);
    }

    public List<Contact> getAllContact(){
        return contacts;
    }


    public void removeContact(String name, SuppliersDAO suppliersDAO) throws Exception {
        for(Contact c : contacts){
            if(Objects.equals(name, c.getName())){
                suppliersDAO.removeSupplierContact(id, c.getName());
                contacts.remove(c);
                return;
            }
        }

        throw new Exception("No such contact Exists!");
    }

    public List<String> getManufacturers(){
        return new ArrayList<>(manufacturers);
    }

    public void removeManufacturer(String name, SuppliersDAO suppliersDAO) throws Exception {
        boolean found = false;

        for(String s : manufacturers){
            if(s.equals(name)){
                found = true;
                break;
            }
        }

        if(!found){
            throw new Exception("This manufacturer is not represented by the current supplier!");
        }

        if(agreement != null && agreement.isManufacturerRepresented(name)){
            throw new Exception("This manufacturer is selling items to the supplier, remove them first!");
        }

        suppliersDAO.removeSupplierManufacturer(id, name);
        manufacturers.remove(name);
    }


    public boolean hasAgreement(){
        return agreement != null;
    }



    public Order addNewOrder(int storeId, OrderDAO orderDAO, AgreementController agreementController) throws Exception {
        agreementExists();

        Order order = new Order(agreement.daysToDelivery(), id, storeId);
        orderDAO.addOrder(order);
        ordersToBe.put(order.getId(), order);

        setLastOrderId(agreementController, order.getId());

        return order;
    }


    public void addOneItemToOrder(int orderId, int itemId, int itemQuantity, OrderDAO orderDAO) throws Exception {
        agreementExists();
        if(!agreement.itemExists(itemId))
            throw new Exception(String.format("Item with ID: %d does not Exists!", itemId));
        if(!orderExists(orderId, orderDAO))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));

        if(itemQuantity == 0){
            throw new Exception("Can't add 0 items to the order!");
        }

        // TODO: 07/05/2022  this is replaced by the list?
        /*
        if(!orders.get(orderId).changeable()){
            throw new Exception("Can't change order: time exception.");
        }
         */
        if(ordersInTheNext24Hours.containsKey(orderId) || finishedOrders.containsKey(orderId)){
            throw new Exception("Can't change order: time exception.");
        }
        AgreementItem currItem = agreement.getItem(itemId);

        float ppu = currItem.getPricePerUnit();
        int discount = agreement.getItem(itemId).getDiscount(itemQuantity);
        Double finalPrice = agreement.getItem(itemId).calculateTotalPrice(itemQuantity);
        ordersToBe.get(orderId).addItem(itemId, agreement.getItem(itemId).getIdBySupplier() , agreement.getItem(itemId).getName(), itemQuantity, ppu, discount, finalPrice, orderDAO);

    }

    public void removeOrder(int orderId, OrderDAO orderDAO) throws Exception {
        if(!orderExists(orderId, orderDAO))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));

        if(ordersInTheNext24Hours.containsKey(orderId) || finishedOrders.containsKey(orderId)){
            throw new Exception("Can't change order: time exception.");
        }
        orderDAO.removeOrder(orderId);
        ordersToBe.remove(orderId);
    }

    public void updateOrder(int orderID, int itemID, int quantity, OrderDAO orderDAO) throws Exception {
        agreementExists();
        if(!orderExists(orderID, orderDAO)){
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderID));
        }

        if(ordersInTheNext24Hours.containsKey(orderID) || finishedOrders.containsKey(orderID)){
            throw new Exception("Can't change order: time exception.");
        }

        if(!agreement.itemExists(itemID)){
            throw new Exception(String.format("Item with ID: %d does not Exists!", itemID));
        }

        ordersToBe.get(orderID).updateItemQuantity(itemID, quantity, agreement.getItem(itemID).getDiscount(quantity), agreement.getOrderPrice(itemID, quantity), orderDAO);
    }

    public void removeItemFromOrder(int orderId, int itemId, OrderDAO orderDAO) throws Exception {
        if(!orderExists(orderId, orderDAO))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));
        if(ordersInTheNext24Hours.containsKey(orderId) || finishedOrders.containsKey(orderId))
            throw new Exception("Can't change order: time exception.");
        if(!ordersToBe.get(orderId).itemExists(itemId))
            throw new Exception("Item with this ID does not exist in thins order!");
        ordersToBe.get(orderId).removeItem(itemId, orderDAO);
    }


    public List<String> getOrder(int orderId, OrderDAO orderDAO) throws Exception {
        Order currOrder = getOrderFromALlLists(orderId, orderDAO);
        List<String> result = new ArrayList<>();
        result.add(String.valueOf(currOrder.getId()));

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
        String strDate = formatter.format(currOrder.getDate());
        result.add(strDate);

        List<OrderItem> items = currOrder.getOrderItems();
        for(OrderItem item : items){
            result.addAll(item.getStringInfo());
        }
        return result;
    }

    private Order getOrderFromALlLists(int orderId, OrderDAO orderDAO) throws Exception {
        if(!orderExists(orderId, orderDAO))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));
        if(ordersToBe.containsKey(orderId))
            return ordersToBe.get(orderId);
        else if(ordersInTheNext24Hours.containsKey(orderId))
            return ordersInTheNext24Hours.get(orderId);
        else
            return finishedOrders.get(orderId);
    }

    public boolean orderExists(int id, OrderDAO orderDAO) throws Exception {
        if(ordersToBe.containsKey(id) || ordersInTheNext24Hours.containsKey(id) || finishedOrders.containsKey(id))
            return true;
        if(orderDAO.containsKey(id)){
            Order order = orderDAO.getOrder(id);
            //Where do I put the Order? in what list
            return true;
        }
        return false;
    }


    public Double getToatalPriceForItem(int itemId, int quantity) throws Exception {
        agreementExists();
        return agreement.getOrderPrice(itemId, quantity);
    }

    /*
    public Order getOrderForThisAmount(int orderId, int productID, int amount) throws Exception {
        if(!orderExists(orderId))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));
        if( ordersToBe.get(orderId).hasEnoughItemQuantity(productID, amount) )    //This should be the "future" orders
            return ordersToBe.get(orderId);
        else{
            ordersToBe.get(orderId).updateItemQuantity(productID, amount, agreement.getItem(productID).getDiscount(amount), agreement.getOrderPrice(productID, amount));
        }
        return ordersToBe.get(orderId);
    }
     */

    public ArrayList<Order> getFutureOrders() {
        ArrayList<Order> result = new ArrayList<>();
        result.addAll(ordersToBe.values());    //This should be the "future" orders
        return result;
    }

    public ArrayList<Order> getOrdersForTomorrow() {

        //check all orders dates and return the ones for tomorrow
        ArrayList<Order> orders = new ArrayList<>();
        for(Order order : ordersToBe.values()){
            if(order.getDaysUntilOrder(LocalDate.now()) == 1){
                orders.add(order);
            }
        }
        // also move them to the other list (from to be to 24 hours)

        return null;
    }

    public Order orderForThisAMount(int productId, int quantity){
        //check if there is an order for this item, if there is add to this order
        // if not create new order and return it
        return null;
    }

    public int getLastOrderId() {
        if(isRoutineAgreement()){
            return ((RoutineAgreement)agreement).getLastOrderId();
        }
        return -1;
    }

    public void setLastOrderId(AgreementController agreementController, int orderId) throws SQLException {
        if(isRoutineAgreement()){
            agreementController.setLastOrderId(id, orderId);
            ((RoutineAgreement)agreement).setLastOrderId(orderId);
        }
    }


    public boolean itemExists(int productId) {
        return agreement.itemExists(productId);
    }

    public int getAgreementType() {
        if(isRoutineAgreement())
            return ROUTINE;
        else if(isByOrderAgreement())
            return BY_ORDER;
        else if(isNotTransportingAgreement())
            return NOT_TRANSPORTING;
        else
            return -1;
    }


    public void addAgreementFromDB(Agreement agreement) {
        this.agreement = agreement;
    }


    public Order getOrderObject(int orderID, OrderDAO orderDAO) throws Exception {
        return getOrderFromALlLists(orderID, orderDAO);
    }

    //I dont think this function work well!
    public ArrayList<OrderItem> checkifOrderItemAlreayExists(Order order, OrderItem orderItem, OrderDAO orderDAO) throws Exception {
        if(!order.itemExists(orderItem.getProductId())){
            return null;
        }
        int addQuantity = 0;
        ArrayList<OrderItem> result = new ArrayList<>();
        for(OrderItem item : order.getOrderItems()){
            if(item.getProductId() != orderItem.getProductId())
                result.add(item);
            else{
                addQuantity = item.getQuantity();

            }
        }
        //if I do this line there is no bug!
        addQuantity += order.getQuantityOfItem(orderItem.getProductId());

        //addQuantity += orderItem.getQuantity();
        int itemID = orderItem.getProductId();
        orderItem.setQuantity(addQuantity);
        orderItem.setDiscount(agreement.getItem(itemID).getDiscount(addQuantity));
        orderItem.setFinalPrice(agreement.getOrderPrice(itemID, addQuantity));
        result.add(orderItem);
        return result;
    }
}
