package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Agreement.RoutineAgreement;
import Domain.BusinessLayer.Supplier.Contact;
import Domain.BusinessLayer.Supplier.Supplier;
import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SuppliersDAO extends DAO {


    private final static Map<String, Supplier> SUPPLIER_IDENTITY_MAP = new HashMap<>();

    private final ContactDAO contactDAO;
    private final ManufacturerDAO manufacturerDAO;
    private final RoutineDAO routineDAO;
    private final ByOrderDAO byOrderDAO;
    private final SelfTransportingDAO selfTransportDAO;
    private final AgreementItemDAO agreementItemDAO;

    private final int ROUTINE  = 1;
    private final int BY_ORDER  = 2;
    private final int NOT_TRANSPORTING  = 3;

    public SuppliersDAO(){
        super("Supplier");
        contactDAO = new ContactDAO();
        manufacturerDAO = new ManufacturerDAO();
        routineDAO = new RoutineDAO();
        byOrderDAO = new ByOrderDAO();
        selfTransportDAO = new SelfTransportingDAO();
        agreementItemDAO = new AgreementItemDAO();
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
            System.out.println(e.getMessage());
        }
        return output;
    }


    // TODO: 13/05/2022  How to check if supplier already exists in the Suppliers_ID_Map??, in this case we don't need to upload him
    public ArrayList<Supplier> getAllSuppliers(){

        ArrayList<Supplier> output = new ArrayList<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection);
            while (instanceResult.next()) {
                Supplier currSupplier = new Supplier(instanceResult.getInt(1), instanceResult.getInt(2),
                        instanceResult.getString(3), instanceResult.getString(4), instanceResult.getString(5));
                SUPPLIER_IDENTITY_MAP.put(String.valueOf(currSupplier.getId()), currSupplier);
            }
        } catch (Exception throwables) {
            System.out.println(throwables.getMessage());
        }

        return output;
    }

    public void addSupplier(Supplier supplier, ArrayList<Contact> contacts, ArrayList<String> manufacturers) throws SQLException {
        insert(Arrays.asList(String.valueOf(supplier.getId()), String.valueOf(supplier.getBankNumber()),
                String.valueOf(supplier.getAddress()), String.valueOf(supplier.getName()), String.valueOf(supplier.getPayingAgreement()), String.valueOf(supplier.getAgreementType())));


        if(contacts != null && contacts.size()> 0){
            for(Contact contact : contacts){
                contactDAO.addContact(supplier.getId(), contact);
            }
        }

        if(manufacturers != null && manufacturers.size() > 0){
            for(String manufacturer : manufacturers){
                manufacturerDAO.addManufacturer(supplier.getId(), manufacturer);
            }
        }

        SUPPLIER_IDENTITY_MAP.put(String.valueOf(supplier.getId()) , supplier);
    }

    public void removeSupplier(int id){
        try {

            manufacturerDAO.remove(id);
            contactDAO.remove(id);
            selfTransportDAO.remove(id);
            byOrderDAO.remove(id);
            routineDAO.remove(id);

            remove(id);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        SUPPLIER_IDENTITY_MAP.remove(id);
    }

    public boolean supplierExist(int id){
        if(SUPPLIER_IDENTITY_MAP.containsKey(String.valueOf(id)))
            return true;
        try {
            ResultSet result = select(getConnection(), String.valueOf(id));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
        return false;
    }

    public boolean isEmpty() {
        return (getAllSuppliers().size() == 0);

    }

    public void addSupplierContact(int id, Contact contact) {
        contactDAO.addContact(id, contact);
    }

    public void removeSupplierContact(int supID, String name) {
        contactDAO.removeContact(supID, name);
    }

    public ArrayList<Contact> getAllSupplierContacts(int supID) {
        return contactDAO.getAllSupplierContact(supID);
    }

    public void addSupplierManufacturer(int id, String name){
        manufacturerDAO.addManufacturer(id, name);
    }

    public void removeSupplierManufacturer(int id, String name){
        manufacturerDAO.removeManufacturer(id, name);
    }

    public ArrayList<String> getAllSupplierManufacturers(int id){
        return manufacturerDAO.getAllSupplierManufacturer(id);
    }


    //    public int update(columnsLocationToUpdate,valuesToUpdate, conditionColumnLocation, conditionValues) throws SQLException {
    public void updateSupplierAddress(int id, String address) {

        Supplier supplier = getSupplier(id);
        supplier.updateAddress(address);
        try {
            update(Arrays.asList(3), Arrays.asList(address), Arrays.asList(1), Arrays.asList(id));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public void updateSupplierBankNumber(int id, int bankNumber) {
        Supplier supplier = getSupplier(id);
        supplier.updateBankNumber(bankNumber);
        try {
            update(Arrays.asList(2), Arrays.asList(bankNumber), Arrays.asList(1), Arrays.asList(id));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public void updateSupplierName(int id, String newName) {
        Supplier supplier = getSupplier(id);
        supplier.updateName(newName);
        try {
            update(Arrays.asList(4), Arrays.asList(newName), Arrays.asList(1), Arrays.asList(id));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    public void updateSupplierPayingAgreement(int id, String payingAgreement) {
        Supplier supplier = getSupplier(id);
        supplier.changePayingAgreement(payingAgreement);
        try {
            update(Arrays.asList(5), Arrays.asList(payingAgreement), Arrays.asList(1), Arrays.asList(id));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }

    private void updateAgreementType(int id, int agreementType) {
        try {
            update(Arrays.asList(6), Arrays.asList(agreementType), Arrays.asList(1), Arrays.asList(id));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }
    }


    // TODO: 13/05/2022 From here It's Agreement functions, maybe move them from here?

    public void addAgreement(int supplierId, int agreementType, String agreementDays) throws Exception {
        if(agreementType > NOT_TRANSPORTING || agreementType < ROUTINE)
            throw new Exception("Invalid agreement type!");
        Supplier supplier = getSupplier(supplierId);
        createAgreement(supplier, agreementType, agreementDays);
    }

    private void createAgreement(Supplier supplier, int agreementType, String agreementDays) throws Exception {

        updateAgreementType(supplier.getId(), agreementType);

        switch(agreementType){
            case ROUTINE :
                if(!RoutineAgreement.hasDays(agreementDays)){
                    throw new Exception("You provided no days!");
                }
                routineDAO.addDaysOfDelivery(supplier.getId(), agreementDays);
                break;
            case BY_ORDER :
                byOrderDAO.addTime(supplier.getId(), agreementDays);
                break;
            case NOT_TRANSPORTING :
                selfTransportDAO.updateSupplier(supplier.getId());
                break;
        }
    }


    public boolean isTransporting(int supplierId) {
        try {
            ResultSet result = select(getConnection(),Arrays.asList(6), Arrays.asList(1), Arrays.asList(supplierId) );
            if(result.next()){
                int resultInt = result.getInt(1);
                return resultInt == BY_ORDER || resultInt == ROUTINE;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean hasAgreement(int supID) {
        try {
            ResultSet result = select(getConnection(),Arrays.asList(6), Arrays.asList(1), Arrays.asList(supID) );
            if(result.next()){
                int resultInt = result.getInt(1);
                return resultInt != -1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public void addItemsToAgreement(int supplierId, List<String> itemsString) throws Exception {

        ArrayList<String> newManufacturers = agreementItemDAO.addItemstoAgreement(supplierId, itemsString);

        for(String manufacturer: newManufacturers){
            //How can I check if manufacturer already exists?
            manufacturerDAO.addManufacturer(supplierId, manufacturer);
        }

    }

    public void addItemToAgreement(int supplierId, int itemId, int idBySupplier, String itemName, String itemManu, float itemPrice, Map<Integer, Integer> bulkPrices) throws Exception {
        agreementItemDAO.addItemToAgreement(itemId, idBySupplier, itemName, itemManu, itemPrice, bulkPrices);

        //How can I check if manufacturer already exists?
        manufacturerDAO.addManufacturer( supplierId, itemManu );
    }
}
