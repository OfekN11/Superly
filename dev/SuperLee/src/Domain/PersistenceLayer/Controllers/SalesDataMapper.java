package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Inventory.SaleToCustomer;
import Domain.PersistenceLayer.Abstract.DataMapper;
import Domain.PersistenceLayer.Abstract.LinkDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.*;

public class SalesDataMapper extends DataMapper<SaleToCustomer> {

    private final static Map<String, SaleToCustomer> IDENTITY_MAP = new HashMap<>();
//    private final static CategoryDataMapper categoryDataMapper = new CategoryDataMapper();
//    private final static ProductDataMapper productDataMapper = new ProductDataMapper();
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
                    resultSet.getDate(START_DATE_COLUMN).toLocalDate(),
                    resultSet.getDate(END_DATE_COLUMN).toLocalDate(),
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
        salesToProductDAO.removeBySale(id);
        salesToCategoryDAO.removeBySale(id);
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
                salesToCategoryDAO.insert(Arrays.asList(instance.getId(), c));
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
        List<SaleToCustomer> output = new ArrayList<>();
        List<Integer> saleIDs = salesToCategoryDAO.getSales(category);
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, ID_COLUMN, saleIDs);
            while (instanceResult.next()) {
                SaleToCustomer curr = buildObject(instanceResult);
                output.add(curr);
                IDENTITY_MAP.put(Integer.toString(curr.getId()), curr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public Collection<SaleToCustomer> getSalesByProduct(int product) {
        List<SaleToCustomer> output = new ArrayList<>();
        List<Integer> saleIDs = salesToProductDAO.getSales(product);
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, ID_COLUMN, saleIDs);
            while (instanceResult.next()) {
                SaleToCustomer curr = buildObject(instanceResult);
                output.add(curr);
                IDENTITY_MAP.put(Integer.toString(curr.getId()), curr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IDENTITY_MAP.values();
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

    public void updateEndDate(int saleID, LocalDate date) {
        try {
            updateProperty(Integer.toString(saleID), END_DATE_COLUMN, date);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
