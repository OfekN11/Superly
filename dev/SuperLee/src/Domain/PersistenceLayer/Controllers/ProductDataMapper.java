package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Inventory.Category;
import Domain.BusinessLayer.Inventory.Product;
import Domain.PersistenceLayer.Abstract.DataMapper;
import Domain.PersistenceLayer.Abstract.LinkDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

public class ProductDataMapper extends DataMapper<Product> {


    private final static Map<String, Product> Product_IDENTITY_MAP = new HashMap<>();
    private final static CategoryDataMapper categoryDataMapper = new CategoryDataMapper();

    private final static int ID_COLUMN = 1;
    private final static int CATEGORY_COLUMN = 2;
    private final static int NAME_COLUMN = 3;
    private final static int WEIGHT_COLUMN = 4;
    private final static int MANUFACTURER_COLUMN = 5;
    private final static int PRICE_COLUMN = 6;

    public ProductDataMapper(){
        super("Product");
    }

    @Override
    public Map getMap() {
        return Product_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    @Override
    protected Product buildObject(ResultSet resultSet) {
        try {
            Category c = categoryDataMapper.get(Integer.toString(resultSet.getInt(CATEGORY_COLUMN)));
            return new Product(resultSet.getInt(ID_COLUMN),
                    resultSet.getString(NAME_COLUMN),
                    c,
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
        return remove(id);
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
            Product_IDENTITY_MAP.put(Integer.toString(instance.getId()), instance);
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
        try(Connection connection = getConnection()) {
            categoryDataMapper.getAll();
            ResultSet instanceResult = select(connection);
            while (instanceResult.next()) {
                Product_IDENTITY_MAP.put(instanceResult.getString(ID_COLUMN), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Product_IDENTITY_MAP.values();
    }
}
