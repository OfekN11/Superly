package SuperLee.BusinessLayer;

import SuperLee.BusinessLayer.Agreement.Agreement;

import java.util.ArrayList;
import java.util.Map;

public class Supplier {

    private int id;
    private int bankNumber;
    private String address;
    private String name;
    private ArrayList<Contact> contacts;
    private String payingAgreement;
    private Agreement agreement;
    private ArrayList<String> manufacturers;

    public Supplier(int id, String name, int bankNumber, String address,String payingAgreement, Contact contact  /*,  ArrayList<String> manufacturers*/){
        this.id = id;
        this.name = name;
        this.bankNumber = bankNumber;
        this.address = address;
        this.payingAgreement = payingAgreement;
        this.contacts = new ArrayList<>();
        contacts.add(contact);

        manufacturers = new ArrayList<>();
        //this.agreement = new Agreement();
        //this.manufacturers = manufacturers;
    }

    public int getId() {
        return id;
    }

    public int getBankNumber() {
        return bankNumber;
    }

    public boolean removeContact(Contact contact) {
        return contacts.remove(contact);
    }

    public void addAgreement(ArrayList<AgreementItem> items /*,payingAgreement  */) {

        // TODO: 14/04/2022 SAGI
        //agreement = new Agreement(items /*,payingAgreement  */);

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

    public void addManufacturer(String manufacturer) {
        manufacturers.add(manufacturer);
    }


    public String getName() {
        return name;
    }

    public ArrayList<AgreementItem> getOrderedItems() {
        return agreement.getItems();
    }

    public void updateBulkPriceForItem(String itemName, Map<Integer, Integer> newBulkPrices) {
        // TODO: 14/04/2022 SAGI
        //agreement.updateBulkPriceForItem(itemName, newBulkPrices);
    }

    public void updatePricePerUnitForItem(String itemName, int newPrice) {
        // TODO: 14/04/2022 SAGI
        //agreement.updatePricePerUnitForItem(itemName, newPrice);
    }

    public void addItem(int itemId, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices) {
        //agreement.addItem(new AgreementItem(itemId, itemName, itemManu, itemPrice, bulkPrices));
        // TODO: 15/04/2022 YONE EROR NOT HANDLED
    }

    public void deleteItem(int itemId) {
        //agreement.removeItem(itemId);
        // TODO: 15/04/2022 YONE EROR NOT HANDLED
    }


    public boolean isTransporting() {
        return agreement.isTransporting();
    }

    public void updateItemId(int oldItemId, int newItemId) {
        // TODO: 14/04/2022 SAGI
        //agreement.updateItemId(int oldItemId, int newItemId);
    }

    public void updateItemName(int itemId, String newName) {
        // TODO: 14/04/2022 SAGI
        //agreement.updateItemName(itemId, newName);
    }

    public void updateItemManufacturer(int itemId, String manufacturer) {
        // TODO: 14/04/2022 SAGI
        //agreement.updateItemManufacturer(itemId, manufacturer);
    }
}
