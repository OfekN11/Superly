package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Supplier;
import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuppliersDAO extends DAO {


    // TODO: 13/05/2022 WHY THE KEY IS STRING ???
    private final static Map<String, Supplier> SUPPLIER_IDENTITY_MAP = new HashMap<>();

    private final OrderDAO orderDAO;
    private final ContactDAO contactDAO;

    public SuppliersDAO(){
        super("Supplier");
        orderDAO = new OrderDAO();
        contactDAO = new ContactDAO();
    }



    public Supplier getSupplier(int id){
        Supplier output = SUPPLIER_IDENTITY_MAP.get(String.valueOf(id));
        if (output != null)
            return output;

        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection,id);
            if (!instanceResult.next())
                return null;

            //    (int id, int bankNumber, String address, String name, String payingAgreement)
            output = new Supplier(instanceResult.getInt(1),instanceResult.getInt(2),instanceResult.getString(3),instanceResult.getString(4)
                    ,instanceResult.getString(5));
            SUPPLIER_IDENTITY_MAP.put( String.valueOf(id) ,output);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("something went wrong in SupplierDAO");

    }

    public ArrayList<Supplier> getAllSuppliers(){
        /*
        ArrayList<Supplier> result = new ArrayList<>();
        for(Map.Entry<Integer, Supplier> curr : suppliers.entrySet()){
            result.add(curr.getValue());
        }
        return result;

         */
        return null;
    }

    public void addSupplier(int id, Supplier supplier) throws SQLException {
        //insert(Arrays.asList(String.valueOf(id), ));
    }

    public void removeSupplier(int id){
        //suppliers.remove(id);
    }

    public boolean supplierExist(int id){
        //return suppliers.containsKey(id);
        return false;
    }

    public boolean isEmpty() {
        //return suppliers.isEmpty();
        return false;
    }
}
