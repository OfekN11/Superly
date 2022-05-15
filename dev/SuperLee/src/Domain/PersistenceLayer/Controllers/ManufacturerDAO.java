package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Contact;
import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManufacturerDAO extends DAO {


    private static final ArrayList<String> manufacturers = new ArrayList<>();

    private final static int SUPPLIER_ID_COLUMN = 1;
    private final static int MANUFACTURER_COLUMN = 2;


    public ManufacturerDAO() {
        super("SupplierToManufacturer");
    }




    public void addManufacturer(int id, String manufacturer) {
        try {
            insert(Arrays.asList( String.valueOf(id) , manufacturer));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }


    public void removeManufacturer(int supplierId, String name) {
        try {
            remove(Arrays.asList(1 , 2 ) ,Arrays.asList(String.valueOf(supplierId) , name) );
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    // TODO: 13/05/2022  How to check if manu already exists in the list??, in this case we don't need to upload him
    public ArrayList<String> getAllSupplierManufacturer(int supID) {
        ArrayList<String> output = new ArrayList<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, String.valueOf(supID));
            while (instanceResult.next()) {
                String manufacturer = instanceResult.getString(2);
                output.add(manufacturer);
                manufacturers.add(manufacturer);
            }
        } catch (Exception throwables) {
            System.out.println(throwables.getMessage());
        }
        return output;
    }
}
