package Domain.BusinessLayer.Supplier;

import Domain.BusinessLayer.Supplier.Agreement.Agreement;
import Domain.BusinessLayer.Supplier.Agreement.ByOrderAgreement;
import Domain.BusinessLayer.Supplier.Agreement.NotTransportingAgreement;
import Domain.BusinessLayer.Supplier.Agreement.RoutineAgreement;

import java.text.SimpleDateFormat;
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

    //private HashMap<Integer, Order> orders;
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

        //this.agreement = new Agreement();
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

    public void addAgreement(int agreementType, String agreementDays) throws Exception {
        //"[0-9]+"  checks if all the agreement days chars are digits
        if(agreementType > NOT_TRANSPORTING || agreementType < ROUTINE)
            throw new Exception("Invalid agreement type!");
        /*if( !agreementDays.matches("[0-9]+"))
            throw new Exception("Invalid agreement days!");*/
        createAgreement(agreementType, agreementDays);
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateBankNumber(int bankNumber) {
        this.bankNumber = bankNumber;
    }

    /*
    public void updateId(int newId) {
        this.id = newId;
        for(Map.Entry<Integer, Order> order : ordersInTheNext24Hours.entrySet()){
            order.getValue().updateSupplierID(newId);
        }
        for(Map.Entry<Integer, Order> order : ordersToBe.entrySet()){
            order.getValue().updateSupplierID(newId);
        }
    }
     */

    public void updateName(String newName) {
        this.name = newName;
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void changePayingAgreement(String payingAgreement) {
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

    public List<String> getOrderedItems() throws Exception {
        agreementExists();
        return agreement.getItemsInMapFormat();
    }

    public void updateBulkPriceForItem(int itemID, Map<Integer, Integer> newBulkPrices) throws Exception {
        agreementExists();
        agreement.getItem(itemID).setBulkPrices(newBulkPrices);
    }

    public void updatePricePerUnitForItem(int itemID, float newPrice) throws Exception {
        agreementExists();
        agreement.getItem(itemID).setPrice(newPrice);
    }

    public void addItem(int itemId, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices) throws Exception {
        agreementExists();
        if(agreement.itemExists(itemId))
            throw new Exception("item with this ID already exists!");
        agreement.addItem(new AgreementItem(itemId, itemName, itemManu, itemPrice, bulkPrices));
        if(!manufacturers.contains(itemManu)){
            manufacturers.add(itemManu);
        }
    }

    public void deleteItem(int itemId) throws Exception {
        agreementExists();
        String manu = agreement.getItem(itemId).getManufacturer();

        agreement.removeItem(itemId);

        if(!agreement.isManufacturerRepresented(manu)){
            manufacturers.remove(manu);
        }
    }

    public boolean isTransporting() throws Exception {
        agreementExists();
        return agreement.isTransporting();
    }

    /*
    public void updateItemId(int oldItemId, int newItemId) throws Exception {
        agreement.getItem(oldItemId).setId(newItemId);
    }*/

    public void updateItemId(int oldItemId, int newItemId) throws Exception {
        agreementExists();
        agreement.setItemId(oldItemId, newItemId);
    }


    public void updateItemName(int itemId, String newName) throws Exception {
        agreementExists();
        agreement.getItem(itemId).setName(newName);
    }

    public void updateItemManufacturer(int itemId, String manufacturer) throws Exception {
        agreementExists();
        String manu = agreement.getItem(itemId).getManufacturer();

        agreement.getItem(itemId).setManufacturer(manufacturer);

        if(!agreement.isManufacturerRepresented(manu)){
            manufacturers.remove(manu);
            manufacturers.add(manufacturer);
        }
    }

    public void addAgreementItems(List<String> itemsString) throws Exception {
        agreementExists();
        agreement.setItemsFromString(itemsString);

        manufacturers = new ArrayList<>();

        for(AgreementItem item : agreement.getItems()){
            manufacturers.add(item.getManufacturer());
        }
    }

    public void updateAgreementType( int agreementType, String agreementDays) throws Exception {
        agreementExists();
        List<AgreementItem> items = agreement.getItems();
        createAgreement(agreementType, agreementDays);
        agreement.setItems(items);
    }

    private void createAgreement(int agreementType, String agreementDays) throws Exception {
        switch(agreementType){
            case ROUTINE :
                if(!RoutineAgreement.hasDays(agreementDays)){
                    throw new Exception("You provided no days!");
                }
                agreement = new RoutineAgreement(agreementDays);
                break;
            case BY_ORDER :
                agreement = new ByOrderAgreement(Integer.parseInt(agreementDays));
                break;
            case NOT_TRANSPORTING :
                agreement = new NotTransportingAgreement();
                break;
        }
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

    public boolean isRoutineAgreement() throws Exception {
        agreementExists();
        return agreement instanceof RoutineAgreement;
    }

    public boolean isByOrderAgreement() throws Exception {
        agreementExists();
        return agreement instanceof ByOrderAgreement;
    }

    public boolean isNotTransportingAgreement() throws Exception {
        agreementExists();
        return agreement instanceof NotTransportingAgreement;
    }

    public void setDaysOfDelivery(String days) throws Exception{
        agreementExists();
        if(!(agreement instanceof RoutineAgreement)){
            throw new Exception("The supplier's agreement is not Routine agreement");
        }

        ((RoutineAgreement) agreement).setDaysOfDelivery(days);
    }

    public void setDaysUntilDelivery(int days) throws Exception{
        agreementExists();
        if(!(agreement instanceof ByOrderAgreement)){
            throw new Exception("The supplier's agreement is not Routine agreement");
        }

        ((ByOrderAgreement) agreement).setDeliveryDays(days);
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

    public void removeContact(String name) throws Exception {
        for(Contact c : contacts){
            if(Objects.equals(name, c.getName())){
                contacts.remove(c);
                return;
            }
        }

        throw new Exception("No such contact Exists!");
    }

    public List<String> getManufacturers(){
        return new ArrayList<>(manufacturers);
    }

    public void removeManufacturer(String name) throws Exception {
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

        manufacturers.remove(name);
    }

    public boolean hasAgreement(){
        return agreement != null;
    }

    public int addNewOrder() throws Exception {
        agreementExists();

        Order order = new Order(agreement.daysToDelivery(), id);
        ordersToBe.put(order.getId(), order);
        return order.getId();
    }


    public void addOneItemToOrder(int orderId, int itemId, int itemQuantity) throws Exception {
        agreementExists();
        if(!agreement.itemExists(itemId))
            throw new Exception(String.format("Item with ID: %d does not Exists!", itemId));
        if(!orderExists(orderId))
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
        ordersToBe.get(orderId).addItem(itemId, agreement.getItem(itemId).getName(), itemQuantity, ppu, discount, finalPrice);

    }

    public void removeOrder(int orderId) throws Exception {
        if(!orderExists(orderId))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));

        if(ordersInTheNext24Hours.containsKey(orderId) || finishedOrders.containsKey(orderId)){
            throw new Exception("Can't change order: time exception.");
        }

        ordersToBe.remove(orderId);
    }

    public void updateOrder(int orderID,int itemID, int quantity) throws Exception {
        agreementExists();
        if(!orderExists(orderID)){
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderID));
        }

        if(ordersInTheNext24Hours.containsKey(orderID) || finishedOrders.containsKey(orderID)){
            throw new Exception("Can't change order: time exception.");
        }

        if(!agreement.itemExists(itemID)){
            throw new Exception(String.format("Item with ID: %d does not Exists!", itemID));
        }

        ordersToBe.get(orderID).updateItemQuantity(itemID, quantity, agreement.getItem(itemID).getDiscount(quantity), agreement.getOrderPrice(itemID, quantity));
    }

    public void removeItemFromOrder(int orderId, int itemId) throws Exception {
        if(!orderExists(orderId))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));
        if(ordersInTheNext24Hours.containsKey(orderId) || finishedOrders.containsKey(orderId))
            throw new Exception("Can't change order: time exception.");
        if(!ordersToBe.get(orderId).itemExists(itemId))
            throw new Exception("Item with this ID does not exist in thins order!");
        ordersToBe.get(orderId).removeItem(itemId);
    }


    public List<String> getOrder(int orderId) throws Exception {
        Order currOrder = getOrderFromALlLists(orderId);
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

    private Order getOrderFromALlLists(int orderId) throws Exception {
        if(!orderExists(orderId))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));
        if(ordersToBe.containsKey(orderId))
            return ordersToBe.get(orderId);
        else if(ordersInTheNext24Hours.containsKey(orderId))
            return ordersInTheNext24Hours.get(orderId);
        else
            return finishedOrders.get(orderId);
    }

    public boolean orderExists(int id){
        return ordersToBe.containsKey(id) || ordersInTheNext24Hours.containsKey(id) || finishedOrders.containsKey(id);
    }

    public ArrayList<Order> itemInOrderForTheNextTwoDays(int itemID) {
        ArrayList<Order> result = new ArrayList<>();
        if(!agreement.isTransporting())
            return result;
        for(Order order : ordersToBe.values()){
            int daysUntilDelivery = order.getDaysUntillOrder(Calendar.getInstance().getTime());
            if(order.itemExists(itemID) && daysUntilDelivery <= 2)
                result.add(order);
        }

        // TODO: 07/05/2022  //Need to treat this orders differently!!!!! dont allow changing the quantity, jst return the order if its the right one
        // TODO: 07/05/2022  // Maybe they delete it? they hold lost for the next 24 hours orders
        for(Order order : ordersInTheNext24Hours.values()){
            int daysUntilDelivery = order.getDaysUntillOrder(Calendar.getInstance().getTime());
            if(order.itemExists(itemID) && daysUntilDelivery <= 2)
                result.add(order);
        }
        return result;
    }

    public Double getToatalPriceForItem(int itemId, int quantity) throws Exception {
        agreementExists();
        return agreement.getOrderPrice(itemId, quantity);
    }

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

    public ArrayList<Order> getFutureOrders() {
        ArrayList<Order> result = new ArrayList<>();
        result.addAll(ordersToBe.values());    //This should be the "future" orders
        return result;
    }
}
