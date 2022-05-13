package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Contact;
import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.SQLException;
import java.util.Arrays;

public class ContactDAO extends DAO {



    public ContactDAO() {
        super("SupplierContacts");
    }


    public void addContact(int supplierId, Contact contact) throws SQLException {
        insert(Arrays.asList( String.valueOf(supplierId) ,contact.getPhone(), contact.getName()));
    }

}
