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
    private HashMap<Integer, Order> orders;

    private final int ROUTINE  = 1;
    private final int BY_ORDER  = 2;
    private final int NOT_TRANSPORTING  = 3;



    public Supplier(int id, String name, int bankNumber, String address,String payingAgreement, ArrayList<Contact> contacts, ArrayList<String> manufacturers){
        this.id = id;
        this.name = name;
        this.bankNumber = bankNumber;
        this.address = address;
        this.payingAgreement = payingAgreement;
        this.contacts = contacts;
        this.manufacturers = manufacturers;
        this.orders = new HashMap<>();
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

    public void updateId(int newId) {
        this.id = newId;
    }

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

    public void addNewOrder() throws Exception {
        agreementExists();

        Order order = new Order(agreement.daysToDelivery());
        orders.put(order.getId(), order);
    }


    /*
    public void addItemsToOrder(int orderId, List<String> itemsString) throws Exception {
        if(!orders.containsKey(orderId)){
            throw new Exception("This order Id already exists!");
        }
        ArrayList<String> itemsFullInfo = new ArrayList<>();
        for(int i = 0; i < itemsString.size(); i+=3 ){

            if(!itemsString.get(i).matches("\\d+"))  //if its a number
                throw new Exception("You gave bad ID!");
            if(i >= itemsString.size())
                throw new Exception("Something is missing");

            int currId = Integer.parseInt(itemsString.get(i));
            if(!agreement.itemExists(currId))
                throw new Exception(String.format("Item with ID: %d does not Exists!", currId));

            //ID
            AgreementItem currItem = agreement.getItem(currId);
            itemsFullInfo.add(String.valueOf(currItem.getId()));

            if(!itemsString.get(i+2).trim().equals(currItem.getName()))
                throw new Exception(String.format("Item with name: %s does not match the id you gave!", itemsString.get(i+2)));

            //Name
            itemsFullInfo.add(currItem.getName());

            //Quantity
            int quantity = Integer.parseInt(itemsString.get(i+2));
            itemsFullInfo.add(itemsString.get(i+2));

            //PPU
            itemsFullInfo.add(String.valueOf(currItem.getPricePerUnit()));

            //Discount
            int discount = agreement.getItem(currId).getDiscount(quantity);
            itemsFullInfo.add(String.valueOf(discount));

            //FinalPrice
            itemsFullInfo.add(String.valueOf(agreement.getItem(currId).calculateTotalPrice(quantity)));

        }

        //Finish the first loop to make sure all the items are Ok!
        for(int i = 0; i < itemsFullInfo.size(); i+=6){
            int id = Integer.parseInt(itemsFullInfo.get(i));
            String name = itemsFullInfo.get(i+1);
            int quantity = Integer.parseInt(itemsFullInfo.get(i+2));
            float ppu = Float.parseFloat(itemsFullInfo.get(i+3));
            int discount = Integer.parseInt(itemsFullInfo.get(i+4));
            float finalPrice = Float.parseFloat(itemsFullInfo.get(i+5));
            orders.get(orderId).addItem(id, name, quantity, ppu, discount, finalPrice);
        }

    }
     */

    public void addOneItemToOrder(int orderId, int itemId, int itemQuantity) throws Exception {
        agreementExists();
        if(!agreement.itemExists(itemId))
            throw new Exception(String.format("Item with ID: %d does not Exists!", itemId));
        if(!orders.containsKey(orderId))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));

        if(itemQuantity == 0){
            throw new Exception("Can't add 0 items to the order!");
        }

        if(!orders.get(orderId).changeable()){
            throw new Exception("Can't change order: time exception.");
        }
        AgreementItem currItem = agreement.getItem(itemId);

        float ppu = currItem.getPricePerUnit();
        int discount = agreement.getItem(itemId).getDiscount(itemQuantity);
        Double finalPrice = agreement.getItem(itemId).calculateTotalPrice(itemQuantity);
        orders.get(orderId).addItem(itemId, agreement.getItem(itemId).getName(), itemQuantity, ppu, discount, finalPrice);

    }

    public void removeOrder(int orderId) throws Exception {
        if(!orders.containsKey(orderId))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));

        if(!orders.get(orderId).changeable()){
            throw new Exception("Can't change order: time exception.");
        }

        orders.remove(orderId);
    }

    public void updateOrder(int orderID,int itemID, int quantity) throws Exception {
        agreementExists();
        if(!orders.containsKey(orderID)){
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderID));
        }

        if(!orders.get(orderID).changeable()){
            throw new Exception("Can't change order: time exception.");
        }

        if(!agreement.itemExists(itemID)){
            throw new Exception(String.format("Item with ID: %d does not Exists!", itemID));
        }

        orders.get(orderID).updateItemQuantity(itemID, quantity, agreement.getItem(itemID).getDiscount(quantity), agreement.getOrderPrice(itemID, quantity));
    }

    public void removeItemFromOrder(int orderId, int itemId) throws Exception {
        if(!orders.containsKey(orderId))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));
        if(!orders.get(orderId).itemExists(itemId))
            throw new Exception("Item with this ID does not exist in thins order!");
        orders.get(orderId).removeItem(itemId);
    }


    public List<String> getOrder(int orderId) throws Exception {
        if(!orders.containsKey(orderId))
            throw new Exception(String.format("Order with ID: %d does not Exists!", orderId));
        Order currOrder = orders.get(orderId);
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

    public boolean orderExists(int id){
        return orders.containsKey(id);
    }
}
