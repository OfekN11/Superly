package Domain.BusinessLayer;

import java.util.*;

public class SupplierController {

    private HashMap<Integer ,Supplier> suppliers;



    public SupplierController(){
        suppliers = new HashMap<>();
    }



    public void addSupplier(int id, String name, int bankNumber, String address, String payingAgreement, ArrayList<Pair<String,String>> contactPairs, ArrayList<String> manufacturers) throws Exception {
        if(supplierExist(id))
            throw new Exception("Supplier with same Id already exists");
        ArrayList<Contact> contacts = new ArrayList<>();
        for(Pair<String,String> curr : contactPairs){
            /*if(!validPhoneNumber(curr.getSecond()))
                throw new Exception("Invalid phone number!");*/
            contacts.add(new Contact( curr.getFirst(), curr.getSecond()));
        }
        suppliers.put(id, new Supplier(id, name, bankNumber, address, payingAgreement, contacts, manufacturers) );
    }

    public void removeSupplier(int id) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliers.remove(id);
    }

    public void updateSupplierAddress(int id, String address) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(id).updateAddress(address);
    }

    public void updateSupplierBankNumber(int id, int bankNumber) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(id).updateBankNumber(bankNumber);
    }

    public void updateSupplierID(int id, int newId) throws Exception {
        if(!supplierExist(id) || supplierExist(newId))
            throw new Exception("There is no supplier with this ID!");
        Supplier temp = suppliers.get(id);
        temp.updateId(newId);
        suppliers.remove(id);
        suppliers.put(newId, temp);
    }

    public void updateSupplierName(int id, String newName) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(id).updateName(newName);
    }

    public void addSupplierContact(int id, String contactName, String contactPhone) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        /*if(!validPhoneNumber(contactPhone))
            throw new Exception("Phone number is Illegal");*/
        Contact contact = new Contact(contactName, contactPhone);
        suppliers.get(id).addContact(contact);
    }

    public void updateSupplierPayingAgreement(int id, String payingAgreement) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(id).changePayingAgreement(payingAgreement);
    }

    public void addSupplierManufacturer(int id, String manufacturer) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(id).addManufacturer(manufacturer);
    }

    public void addAgreement(int supplierId, int agreementType, String agreementDays) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");

        suppliers.get(supplierId).addAgreement(agreementType, agreementDays);
    }

    public void addItemsToAgreement(int supplierId, List<String> itemsString) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).addAgreementItems(itemsString);
    }


    // TODO: YONE
    //SHOULD BE PRIVATE, public for testing
    public boolean supplierExist(int id){
        return suppliers.containsKey(id);
    }

    // TODO: YONE
    //SHOULD BE PRIVATE, public for testing
    public boolean validPhoneNumber(String phoneNumber){
        //MAYBE THROW THE REGEX?? JUST CHECK NO LETTER INVOLVED
        // TODO: 15/04/2022 YONE
        return !phoneNumber.matches("[a-zA-Z]+") && phoneNumber.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");

        //"/^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$/im\n"  not working
        //^([+]?[\s0-9]+)?(\d{3}|[(]?[0-9]+[)])?([-]?[\s]?[0-9])+$  half working
        //"/\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/"  not working
        ///^(([+]{0,1}\d{2})|\d?)[\s-]?[0-9]{2}[\s-]?[0-9]{3}[\s-]?[0-9]{4}$/gm not working
        //"^(\\d{3}[- .]?){2}\\d{4}$" working for israeli numbers!  not for home numbers
        //"^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" not for home numbers
    }


    public Map<String, List<String>> itemsFromAllSuppliers(){
        HashMap<String, List<String>> items = new HashMap<>();
        for (Supplier supplier : suppliers.values())
            items.put(supplier.getName(), supplier.getOrderedItems());
        return items;
    }

    public List<String> itemsFromOneSupplier(int id) throws Exception {
        if(!supplierExist(id))
            throw new Exception("There is no supplier with this ID!");
        return suppliers.get(id).getOrderedItems();
    }

    public void updateBulkPriceForItem(int supplierId, int itemID, Map<Integer, Integer> newBulkPrices) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).updateBulkPriceForItem(itemID, newBulkPrices);
    }

    public void updatePricePerUnitForItem(int supplierId, int itemID, float newPrice) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).updatePricePerUnitForItem(itemID, newPrice);
    }

    public void updateItemId(int supplierId, int olditemId, int newItemId) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).updateItemId(olditemId, newItemId);
    }

    public void updateItemName(int supplierId, int itemId, String newName) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).updateItemName(itemId, newName);
    }

    public void updateItemManufacturer(int supplierId, int itemId, String manufacturer) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).updateItemManufacturer(itemId, manufacturer);
    }

    public void addItemToAgreement(int supplierId, int itemId, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).addItem(itemId, itemName, itemManu, itemPrice, bulkPrices);
    }

    public void deleteItemFromAgreement(int supplierId, int itemId) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).deleteItem(itemId);
    }

    public boolean isTransporting(int supplierId) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        return suppliers.get(supplierId).isTransporting();
    }

    public void updateAgreementType(int supplierId,  int agreementType, String agreementDays) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).updateAgreementType(agreementType, agreementDays);
    }

    public void setAgreement(int supplierId, int agreementType, String agreementDays) throws Exception {
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        suppliers.get(supplierId).addAgreement(agreementType, agreementDays);
    }

    public List<Integer> getDaysOfDelivery(int supplierId) throws Exception{
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        return suppliers.get(supplierId).getDaysOfDelivery();
    }

    public int getDeliveryDays(int supplierId) throws Exception{
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        return suppliers.get(supplierId).getDeliveryDays();
    }

    public int daysToDelivery(int supplierId) throws Exception {
        if(!supplierExist(supplierId)){
            throw new Exception("There is no supplier with this ID!");
        }
        return suppliers.get(supplierId).daysToDelivery();

    }

    public ArrayList<String> getSupplierInfo(int supplierId) throws Exception{
        if(!supplierExist(supplierId))
            throw new Exception("There is no supplier with this ID!");
        return suppliers.get(supplierId).getSupplierInfo();
    }

    public boolean isRoutineAgreement(int supplierId) throws Exception {
        if(!supplierExist(supplierId)){
            throw new Exception("There is no supplier with this ID!");
        }
        return suppliers.get(supplierId).isRoutineAgreement();
    }

    public boolean isByOrderAgreement(int supplierId) throws Exception {
        if(!supplierExist(supplierId)){
            throw new Exception("There is no supplier with this ID!");
        }
        return suppliers.get(supplierId).isByOrderAgreement();
    }

    public boolean isNotTransportingAgreement(int supplierId) throws Exception {
        if(!supplierExist(supplierId)){
            throw new Exception("There is no supplier with this ID!");
        }
        return suppliers.get(supplierId).isNotTransportingAgreement();
    }

    public void setDaysOfDelivery(int supplierID, String days) throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliers.get(supplierID).setDaysOfDelivery(days);
    }

    public void addDaysOfDelivery(int supplierID, String days) throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliers.get(supplierID).addDaysOfDelivery(days);
    }

    public void removeDayOfDelivery(int supplierID, int day) throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliers.get(supplierID).removeDayOfDelivery(day);
    }

    public String getItem(int supplierID, int itemId) throws Exception {
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliers.get(supplierID).getItem(itemId).toString();
    }

    public void editBulkPrice(int supplierID, int itemId, int quantity, int discount)throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliers.get(supplierID).getItem(itemId).editBulkPrice(quantity, discount);
    }

    public void addBulkPrice(int supplierID, int itemId, int quantity, int discount)throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliers.get(supplierID).getItem(itemId).addBulkPrice(quantity, discount);
    }

    public void removeBulkPrice(int supplierID, int itemId, int quantity)throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliers.get(supplierID).getItem(itemId).removeBulkPrice(quantity);
    }

    public double calculatePriceForItemOrder(int supplierID, int itemId, int quantity) throws Exception {
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliers.get(supplierID).getItem(itemId).calculateTotalPrice(quantity);
    }

    public void changeDaysUntilDelivery(int supplierID, int days)throws Exception{
        if(!supplierExist(supplierID)){
            throw new Exception("The supplier does not exists!");
        }

        if(suppliers.get(supplierID).isByOrderAgreement()){
            suppliers.get(supplierID).setDaysUntilDelivery(days);
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

        List<Contact> list = suppliers.get(supID).getAllContact();

        for(Contact c : list){
            contacts.add(c.toString());
        }

        return contacts;
    }

    public void removeContact(int supID, String name) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliers.get(supID).removeContact(name);
    }

    public List<String> getManufacturers(int supID) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliers.get(supID).getManufacturers();
    }

    public void removeManufacturer(int supID, String name) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        suppliers.get(supID).removeManufacturer(name);
    }

    public boolean isSuppliersEmpty(){
        return suppliers.isEmpty();
    }

    public boolean hasAgreement(int supID) throws Exception {
        if(!supplierExist(supID)){
            throw new Exception("The supplier does not exists!");
        }

        return suppliers.get(supID).hasAgreement();
    }
}
