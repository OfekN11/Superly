package SuperLee.ServiceLayer;

import SuperLee.BusinessLayer.Agreement.Agreement;
import SuperLee.BusinessLayer.Contact;

import java.util.ArrayList;

public class ServiceSupplierObject {

    private int id;
    private int bankNumber;
    private String address;
    private String name;
    private String payingAgreement;
    private ArrayList<ServiceContactObject> contacts;




    public ServiceSupplierObject(int id, String name, int bankNumber, String address ,String payingAgreement, ArrayList<ServiceContactObject> contacts){
        this.id = id;
        this.name = name;
        this.bankNumber = bankNumber;
        this.address = address;
        this.payingAgreement = payingAgreement;
        this.contacts = new ArrayList<>();
        this.contacts = contacts;

        //this.agreement = new Agreement();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getBankNumber() {
        return bankNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getPayingAgreement() {
        return payingAgreement;
    }

    public ArrayList<ServiceContactObject> getContacts() {
        return contacts;
    }

    public String toString(){
        return "ID: " + id + "\nName: " + name + "\nAddress: " + address + "\nBank number: " + bankNumber + "\nPaying agreement: " + payingAgreement;
    }


}
