package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Inventory.Product;
import Domain.BusinessLayer.Supplier.Supplier;
import Domain.PersistenceLayer.Abstract.DAO;
import Domain.PersistenceLayer.Abstract.DataMapper;
import Domain.PersistenceLayer.Abstract.LinkDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProductDataMapper extends DataMapper<Product> {


    private final static Map<String, Product> Product_IDENTITY_MAP = new HashMap<>();
    private final CategoryDAO categoryDAO;

    private final static int ID_COLUMN = 1;
    private final static int CATEGORY_COLUMN = 2;
    private final static int NAME_COLUMN = 3;
    private final static int WEIGHT_COLUMN = 4;
    private final static int MANUFACTURER_COLUMN = 5;
    private final static int PRICE_COLUMN = 6;

    public ProductDataMapper(){
        super("Product");
        categoryDAO=new CategoryDAO();
    }

    @Override
    protected Map getMap() {
        return Product_IDENTITY_MAP;
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
                    categoryDAO.get(Integer.toString(resultSet.getInt(CATEGORY_COLUMN))),
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

}
