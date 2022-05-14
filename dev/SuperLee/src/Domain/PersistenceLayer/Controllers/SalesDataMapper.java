package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Inventory.DiscountsAndSales.SaleToCustomer;
import Domain.BusinessLayer.Inventory.Product;
import Domain.PersistenceLayer.Abstract.DataMapper;
import Domain.PersistenceLayer.Abstract.LinkDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

public class SalesDataMapper extends DataMapper<SaleToCustomer> {

    private final static Map<String, SaleToCustomer> IDENTITY_MAP = new HashMap<>();
    private final static CategoryDataMapper categoryDataMapper = new CategoryDataMapper();
    private final static ProductDataMapper productDataMapper = new ProductDataMapper();
    private final static SalesToProductDAO salesToProductDAO = new SalesToProductDAO();
    private final static SalesToCategoryDAO salesToCategoryDAO = new SalesToCategoryDAO();

    private final static int ID_COLUMN = 1;
    private final static int START_DATE_COLUMN = 2;
    private final static int END_DATE_COLUMN = 3;
    private final static int PERCENT_COLUMN = 4;

    public SalesDataMapper(){
        super("Sales");
    }

    @Override
    public Map getMap() {
        return IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    @Override
    protected SaleToCustomer buildObject(ResultSet resultSet) {
        try {
            return new SaleToCustomer(resultSet.getInt(ID_COLUMN),
                    resultSet.getDate(START_DATE_COLUMN),
                    resultSet.getDate(END_DATE_COLUMN),
                    resultSet.getInt(PERCENT_COLUMN),
                    getCategories(resultSet.getInt(ID_COLUMN)),
                    getProducts(resultSet.getInt(ID_COLUMN))
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Integer> getProducts(int sale) {
        return salesToProductDAO.getProductsOfSale(sale);
    }

    private List<Integer> getCategories(int sale) {
        return salesToCategoryDAO.getCategoriesOfSale(sale);
    }

    public int remove(Object id) {
        return remove(id);
    }

    @Override
    public void insert(SaleToCustomer instance){
        try {
            insert(Arrays.asList(instance.getId(),
                    instance.getStartDate(),
                    instance.getEndDate(),
                    instance.getPercent()));
            IDENTITY_MAP.put(Integer.toString(instance.getId()), instance);
            for (int c : instance.getCategories())
                salesToProductDAO.insert(Arrays.asList(instance.getId(), c));
            for (int p : instance.getProducts())
                salesToProductDAO.insert(Arrays.asList(instance.getId(), p));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<SaleToCustomer> getAll() {
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection);
            while (instanceResult.next()) {
                IDENTITY_MAP.put(instanceResult.getString(ID_COLUMN), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IDENTITY_MAP.values();
    }

    public Collection<SaleToCustomer> getSalesByCategory(int category) {
        List<Integer> saleIDs = salesToCategoryDAO.getSales(category);
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, ID_COLUMN, Collections.singletonList(saleIDs));
            while (instanceResult.next()) {
                IDENTITY_MAP.put(instanceResult.getString(ID_COLUMN), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IDENTITY_MAP.values();
    }

    public Collection<SaleToCustomer> getSalesByProduct(int product) {
        List<Integer> saleIDs = salesToProductDAO.getSales(product);
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, ID_COLUMN, Collections.singletonList(saleIDs));
            while (instanceResult.next()) {
                IDENTITY_MAP.put(instanceResult.getString(ID_COLUMN), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IDENTITY_MAP.values();
    }
}
