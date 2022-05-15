package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Contact;
import Domain.BusinessLayer.Supplier.Supplier;
import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContactDAO extends DAO {

    private static final ArrayList<Contact> contacts = new ArrayList<>();


    private final static int SUPPLIER_ID_COLUMN = 1;
    private final static int NAME_COLUMN = 2;
    private final static int PHONE_COLUMN = 3;


    public ContactDAO() {
        super("SupplierContacts");
    }


    public void addContact(int supplierId, Contact contact) {
        try {
            insert(Arrays.asList( String.valueOf(supplierId) , contact.getName() ,contact.getPhone()));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public void removeContact(int supplierId, String name) {
        try {
            remove(Arrays.asList(1 , 2 ) ,Arrays.asList(String.valueOf(supplierId) , name) );
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    // TODO: 13/05/2022  How to check if contact already exists in the list??, in this case we don't need to upload him
    public ArrayList<Contact> getAllSupplierContact(int supID) {
        ArrayList<Contact> output = new ArrayList<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, String.valueOf(supID));
            while (instanceResult.next()) {
                Contact contact = new Contact(instanceResult.getString(2), instanceResult.getString(3));
                output.add(contact);
                contacts.add(contact);
            }
        } catch (Exception throwables) {
            System.out.println(throwables.getMessage());
        }
        return output;
    }


}
