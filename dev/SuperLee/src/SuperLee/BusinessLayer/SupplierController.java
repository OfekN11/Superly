package SuperLee.BusinessLayer;

import SuperLee.ServiceLayer.ServiceItemObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SupplierController {

    private HashMap<Integer ,Supplier> suppliers;



    public SupplierController(){
        suppliers = new HashMap<>();
    }



    public void addSupplier(int id, String name, int bankNumber, String address, String payingAgreement, String contactName, String contactPhone){
        if(supplierExist(id) || validPhoneNumber(contactPhone))
            throw new IllegalArgumentException("Supplier with same Id already exists");
        Contact contact = new Contact(contactName, contactPhone);
        suppliers.put(id, new Supplier(id, name, bankNumber, address, payingAgreement, contact) );
    }

    public void removeSupplier(int id){
        if(!supplierExist(id))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.remove(id);
    }




    public void updateSupplierAddress(int id, String address){
        if(!supplierExist(id))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(id).updateAddress(address);
    }

    public void updateSupplierBankNumber(int id, int bankNumber){
        if(!supplierExist(id))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(id).updateBankNumber(bankNumber);
    }

    public void updateSupplierID(int id, int newId){
        if(!supplierExist(id) || supplierExist(newId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        Supplier temp = suppliers.get(id);
        temp.updateId(newId);
        suppliers.remove(id);
        suppliers.put(newId, temp);
    }

    public void updateSupplierName(int id, String newName){
        if(!supplierExist(id))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(id).updateName(newName);
    }

    public void addSupplierContact(int id, String contactName, String contactPhone){
        if(!supplierExist(id) || validPhoneNumber(contactPhone))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        Contact contact = new Contact(contactName, contactPhone);
        suppliers.get(id).addContact(contact);
    }

    public void updateSupplierPayingAgreement(int id, String payingAgreement){
        if(!supplierExist(id))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(id).changePayingAgreement(payingAgreement);
    }

    public void addSupplierManufacturer(int id, String manufacturer){
        if(!supplierExist(id))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(id).addManufacturer(manufacturer);
    }


    public void addAgreement(int supplierId, ArrayList<ArrayList<String>> itemsInLists   /* should be dictionary of all the items and in here we convert them to business items */){
        if(!supplierExist(supplierId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        //convert itemsInLists to business items.
        ArrayList<AgreementItem> items = new ArrayList<>();
        //for(int i = 0; i < itemsInLists.size(); i++){
        // TODO: 14/04/2022 SAGI YONE
        //How can I store the bulkprices with dictionary in an ArrayList?
        //}
        suppliers.get(supplierId).addAgreement(items);
    }


    public Map itemsFromAllSuppliers(){
        HashMap<String, ArrayList<AgreementItem>> items = new HashMap<>();
         for (Supplier supplier : suppliers.values())
             items.put(supplier.getName(), supplier.getOrderedItems());
        return items;
    }

    public ArrayList<AgreementItem> itemsFromOneSupplier(int id){
        if(!supplierExist(id))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        return suppliers.get(id).getOrderedItems();
    }

    //SHOULD BE PRIVATE
    // TODO: 15/04/2022 YONE
    public boolean supplierExist(int id){
        return suppliers.containsKey(id);
    }

    //SHOULD BE PRIVATE
    // TODO: 15/04/2022 YONE
    public boolean validPhoneNumber(String phoneNumber){
        //MAYBE THROW THE REGEX?? JUST CHECK NO LETTER INVOLVED
        // TODO: 15/04/2022 YONE 
        return phoneNumber.matches("^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");

        //"/^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$/im\n"  not working
        //^([+]?[\s0-9]+)?(\d{3}|[(]?[0-9]+[)])?([-]?[\s]?[0-9])+$  half working
        //"/\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/"  not working
        ///^(([+]{0,1}\d{2})|\d?)[\s-]?[0-9]{2}[\s-]?[0-9]{3}[\s-]?[0-9]{4}$/gm not working
        //"^(\\d{3}[- .]?){2}\\d{4}$" working for israeli numbers!  not for home numbers
        //"^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" not for home numbers
    }

    public void updatebulkPriceForItem(int supplierId, String itemName, Map<Integer, Integer> newBulkPrices){
        if(!supplierExist(supplierId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(supplierId).updateBulkPriceForItem(itemName, newBulkPrices);
    }

    public void updatePricePerUnitForItem(int supplierId, String itemName, int newPrice){
        if(!supplierExist(supplierId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(supplierId).updatePricePerUnitForItem(itemName, newPrice);
    }

    public void updateItemId(int supplierId, int olditemId, int newItemId){
        if(!supplierExist(supplierId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(supplierId).updateItemId(olditemId, newItemId);
    }

    public void updateItemName(int supplierId, int itemId, String newName){
        if(!supplierExist(supplierId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(supplierId).updateItemName(itemId, newName);
    }

    public void updateItemManufacturer(int supplierId, int itemId, String manufacturer){
        if(!supplierExist(supplierId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(supplierId).updateItemManufacturer(itemId, manufacturer);
    }


    public void addItemToAgreement(int supplierId, int itemId, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices){
        if(!supplierExist(supplierId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(supplierId).addItem(itemId, itemName, itemManu, itemPrice, bulkPrices);
    }

    public void deleteItemFromAgreement(int supplierId, int itemId){
        if(!supplierExist(supplierId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        suppliers.get(supplierId).deleteItem(itemId);
    }

    //change supplying days for by order and for routine
    public void changeAgreement(){
        //Do we need this now?
        // TODO: 14/04/2022 YONE WE WANT THYS?
    }


    public boolean isTransporting(int supplierId){
        if(!supplierExist(supplierId))
            throw new IllegalArgumentException("There is no supplier with this ID!");
        return suppliers.get(supplierId).isTransporting();
    }



    // TODO: 14/04/2022 YONE

}
