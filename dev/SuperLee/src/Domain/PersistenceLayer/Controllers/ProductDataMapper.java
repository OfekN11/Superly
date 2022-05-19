package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Inventory.Category;
import Domain.BusinessLayer.Inventory.Product;
import Domain.BusinessLayer.Inventory.SaleToCustomer;
import Domain.PersistenceLayer.Abstract.DataMapper;
import Domain.PersistenceLayer.Abstract.LinkDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

public class ProductDataMapper extends DataMapper<Product> {


    private final static Map<String, Product> PRODUCT_IDENTITY_MAP = new HashMap<>();
    private final static CategoryDataMapper CATEGORY_DATA_MAPPER = Category.CATEGORY_DATA_MAPPER;

    private final static int ID_COLUMN = 1;
    private final static int CATEGORY_COLUMN = 2;
    private final static int NAME_COLUMN = 3;
    private final static int WEIGHT_COLUMN = 4;
    private final static int MANUFACTURER_COLUMN = 5;
    private final static int PRICE_COLUMN = 6;

    public ProductDataMapper(){
        super("Product");
    }

    public Map<Integer, Product> getIntegerMap() {
        Map<Integer, Product> output = new HashMap<>();
        for (Map.Entry<String, Product> entry: PRODUCT_IDENTITY_MAP.entrySet()) {
            output.put(Integer.parseInt(entry.getKey()), entry.getValue());
        }
        return output;
    }

    @Override
    public Map getMap() {
        return PRODUCT_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    @Override
    protected Product buildObject(ResultSet resultSet) {
        try {
            return new Product(resultSet.getInt(ID_COLUMN),
                    resultSet.getString(NAME_COLUMN),
                    CATEGORY_DATA_MAPPER.get(Integer.toString(resultSet.getInt(CATEGORY_COLUMN))),
                    resultSet.getDouble(WEIGHT_COLUMN),
                    resultSet.getDouble(PRICE_COLUMN),
                    resultSet.getString(MANUFACTURER_COLUMN)
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int remove(Object id) {
<<<<<<< HEAD
        try {
            PRODUCT_IDENTITY_MAP.remove(id);
            super.remove(id);
        }
        catch (Exception e) {
=======
        try{
            List<Integer> products = Arrays.asList((Integer)id);
            LocationDataMapper locationDataMapper = new LocationDataMapper();
            for (Integer product : products)
                locationDataMapper.removeByProduct(product);
            StockReportDataMapper stockReportDataMapper = new StockReportDataMapper();

            for (Integer product : products)
                stockReportDataMapper.removeProduct(product);
            executeNonQuery(String.format("DELETE FROM %s WHERE %s LIKE \"%s\"",tableName, getColumnName(NAME_COLUMN), "Test%"));
            return super.remove(id);
        } catch (Exception e) {
>>>>>>> d29c3a4a656113f71a606e96b590ef0c288d16b1
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void insert(Product instance){
        try {
            insert(Arrays.asList(instance.getId(),
                    instance.getCategoryID(),
                    instance.getName(),
                    instance.getWeight(),
                    instance.getManufacturer(),
                    instance.getOriginalPrice()));
            PRODUCT_IDENTITY_MAP.put(Integer.toString(instance.getId()), instance);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(int productID, int category) {
        try {
            updateProperty(Integer.toString(productID), CATEGORY_COLUMN, category);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePrice(int productID, double price) {
        try {
            updateProperty(Integer.toString(productID), PRICE_COLUMN, price);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateName(int productID, String name) {
        try {
            updateProperty(Integer.toString(productID), NAME_COLUMN, name);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<Product> getAll() {
        CATEGORY_DATA_MAPPER.getAll();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection);
            while (instanceResult.next()) {
                PRODUCT_IDENTITY_MAP.put(instanceResult.getString(ID_COLUMN), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return PRODUCT_IDENTITY_MAP.values();
    }

    public List<Product> getProductsFromCategory(int id) {
        try(Connection connection = getConnection()) {
            List<Integer> productIDs = new ArrayList<>();
            ResultSet resultSet = executeQuery(connection, String.format("Select %s from %s where %s=" + id,
                    getColumnName(ID_COLUMN),
                    tableName,
                    getColumnName(CATEGORY_COLUMN)));
            while (resultSet.next()) {
                productIDs.add(resultSet.getInt(1));
            }
            List<Product> products = new ArrayList<>();
            for (Integer productID: productIDs) {
                products.add(get(Integer.toString(productID)));
            }
            return products;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getIDCount() {
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = getMax(connection, ID_COLUMN);
            while (instanceResult.next()) {
                return instanceResult.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeTestProducts() {
        try(Connection connection = getConnection()){
            ResultSet resultSet = executeQuery(connection,String.format("Select * FROM %s WHERE %s LIKE \"%s\"",tableName, getColumnName(NAME_COLUMN), "Test%"));
            List<Integer> products = new ArrayList<>();
            while (resultSet.next()) {
                products.add(resultSet.getInt(ID_COLUMN));
            }
            LocationDataMapper locationDataMapper = new LocationDataMapper();
            for (Integer product : products)
                locationDataMapper.removeByProduct(product);
            StockReportDataMapper stockReportDataMapper = new StockReportDataMapper();
            for (Integer product : products)
                stockReportDataMapper.removeProduct(product);
            executeNonQuery(String.format("DELETE FROM %s WHERE %s LIKE \"%s\"",tableName, getColumnName(NAME_COLUMN), "Test%"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
