package Domain.DAL.Controllers.InventoryAndSuppliers;

import Domain.Business.Objects.Supplier.AgreementItem;
import Domain.DAL.Abstract.DataMapper;
import Domain.DAL.Abstract.LinkDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AgreementItemDAO extends DataMapper<AgreementItem> {

    private final static Map<String, AgreementItem> AGREEMENT_ITEM_IDENTITY_MAP = new HashMap<>();


    private final static int SUPPLIER_ID_COLUMN = 1;
    private final static int PRODUCT_ID_COLUMN = 2;
    private final static int MANUFACTURER_COLUMN = 3;
    private final static int PPU_COLUMN = 4;
    private final static int ID_BY_SUPPLIER = 5;

    private final BulkPricesDAO bulkPricesDAO;

    public AgreementItemDAO() {
        super("AgreementItem");
        bulkPricesDAO = new BulkPricesDAO();
    }

    @Override
    protected Map<String, AgreementItem> getMap() {
        return AGREEMENT_ITEM_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    @Override
    //(int _productId, int _idBySupplier,  String _name, String _manu, float _price, Map<Integer, Integer> _bulkPrices){
    protected AgreementItem buildObject(ResultSet instanceResult) throws Exception {
        return new AgreementItem(instanceResult.getInt(PRODUCT_ID_COLUMN),
                instanceResult.getInt(ID_BY_SUPPLIER),
                instanceResult.getString(MANUFACTURER_COLUMN),
                instanceResult.getFloat(PPU_COLUMN),
                getBulkMap(instanceResult.getInt(SUPPLIER_ID_COLUMN), instanceResult.getInt(PRODUCT_ID_COLUMN)));
    }


    private Map<Integer, Integer> getBulkMap(int supId, int itemId) {
        return bulkPricesDAO.getAllBulkPrices(supId, itemId);
    }

    @Override
    public void insert(AgreementItem item) throws SQLException {
            //we don't use this, WE CAN'T USE SAVE!
    }

    public Map<Integer, AgreementItem> getAllAgreementItemFromSupplier(int supplierId){
        Map<Integer, AgreementItem> output = new HashMap<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, supplierId);
            while (instanceResult.next()) {
                AgreementItem currItem = buildObject(instanceResult);
                output.put(currItem.getProductId(), currItem);
                AGREEMENT_ITEM_IDENTITY_MAP.put(String.valueOf(currItem.getProductId()), currItem);
            }
        } catch (Exception throwables) {
            System.out.println(throwables.getMessage());
        }

        return output;
    }



    public void addItemstoAgreement(int supplierId, List<AgreementItem> items) throws Exception {
        for(AgreementItem item : items){
            insertOneItem(supplierId, item);
        }
    }

    public void insertOneItem(int supplierId, AgreementItem item) throws SQLException {
        insert(Arrays.asList(String.valueOf(supplierId), String.valueOf(item.getProductId()),
                item.getManufacturer(),String.valueOf(item.getPricePerUnit()), String.valueOf(item.getIdBySupplier())));

        bulkPricesDAO.addBulkPrices( supplierId, item.getProductId(), item.getBulkPrices());

        AGREEMENT_ITEM_IDENTITY_MAP.put(String.valueOf(item.getProductId()), item);
    }


    public void addItemToAgreement(int supplierId, int itemId, int idBySupplier, String itemName, String itemManu, float itemPrice, double weight, Map<Integer, Integer> bulkPrices) throws Exception {

        if(AGREEMENT_ITEM_IDENTITY_MAP.containsKey(String.valueOf(itemId)))
            throw new Exception("item with this ID already exists!");
        AgreementItem item = new AgreementItem(itemId, idBySupplier, itemManu, itemPrice, bulkPrices);
        insertOneItem(supplierId, item);

    }


    public void removeSupplier(int id) throws SQLException {
        for( AgreementItem item : AGREEMENT_ITEM_IDENTITY_MAP.values()){
            bulkPricesDAO.remove(item.getProductId());
            remove(item.getProductId());
        }
        remove(id);
    }

    public void updateBulkPrice(int supId, int itemID, Map<Integer, Integer> newBulkPrices) throws SQLException {
        bulkPricesDAO.updateBulkPrice(supId, itemID, newBulkPrices);
    }

    public void updatePPU(int itemID, float newPrice) throws SQLException {
        update(Arrays.asList(PPU_COLUMN), Arrays.asList(newPrice), Arrays.asList(PRODUCT_ID_COLUMN), Arrays.asList(itemID) );
    }

    public void updateItemId(int oldItemId, int newItemId) throws SQLException {
        update(Arrays.asList(PRODUCT_ID_COLUMN), Arrays.asList(newItemId), Arrays.asList(PRODUCT_ID_COLUMN), Arrays.asList(oldItemId) );
    }

    /*
    public void updateItemName(int itemId, String newName) throws SQLException {
        update(Arrays.asList(NAME_COLUMN), Arrays.asList(newName), Arrays.asList(PRODUCT_ID_COLUMN), Arrays.asList(itemId) );
    }

     */

    public void updateManufacturer(int itemId, String manufacturer) throws SQLException {
        update(Arrays.asList(MANUFACTURER_COLUMN), Arrays.asList(manufacturer), Arrays.asList(PRODUCT_ID_COLUMN), Arrays.asList(itemId) );
    }

    /*
    public void updateWeight(int itemId, double weight) throws SQLException {
        update(Arrays.asList(WEIGHT), Arrays.asList(weight), Arrays.asList(PRODUCT_ID_COLUMN), Arrays.asList(itemId) );
    }
     */

    public void removeItem(int id, int itemId) throws SQLException {
        remove(Arrays.asList(1,2), Arrays.asList(id, itemId));
    }


    public String getNameOfItem(int itemId) {
        return AGREEMENT_ITEM_IDENTITY_MAP.get(String.valueOf(itemId)).getName();
    }


}
