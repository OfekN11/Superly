package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Inventory.DefectiveItems;
import Domain.BusinessLayer.Inventory.StockReport;
import Domain.PersistenceLayer.Abstract.DataMapper;
import Domain.PersistenceLayer.Abstract.LinkDAO;
import Globals.Defect;
import Globals.Pair;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DefectiveItemsDataMapper extends DataMapper<DefectiveItems> {
    private final static int ID_COLUMN = 1;
    private final static int STORE_COLUMN = 2;
    private final static int PRODUCT_COLUMN = 3;
    private final static int DATE_COLUMN = 4;
    private final static int AMOUNT_COLUMN = 5;
    private final static int EMPLOYEE_ID_COLUMN = 6;
    private final static int DESCRIPTION_COLUMN = 7;
    private final static int DEFECT_COLUMN = 8;

    private final static Map<String, DefectiveItems> IDENTITY_MAP = new HashMap<>();

    public DefectiveItemsDataMapper() {
        super("DefectiveItems");
    }


    @Override
    protected Map<String, DefectiveItems> getMap() {
        return IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        return null;
    }

    protected DefectiveItems buildObject(ResultSet resultSet){
        try {
            Defect defect;
            String d = resultSet.getString(DEFECT_COLUMN);
            switch (d) {
                case ("expired"):
                    defect = Defect.Expired;
                    break;
                case ("damaged"):
                    defect = Defect.Damaged;
                    break;
                default:
                    throw new Exception("Illegal defect");
            }
            return new DefectiveItems(resultSet.getInt(ID_COLUMN),
                    defect,
                    resultSet.getDate(DATE_COLUMN),
                    resultSet.getInt(STORE_COLUMN),
                    resultSet.getInt(PRODUCT_COLUMN),
                    resultSet.getInt(AMOUNT_COLUMN),
                    resultSet.getInt(EMPLOYEE_ID_COLUMN),
                    resultSet.getString(DESCRIPTION_COLUMN),
                    resultSet.getInt(EMPLOYEE_ID_COLUMN)==1);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(DefectiveItems instance) throws SQLException {
        try {
            insert(Arrays.asList(instance.getId(),
                    instance.getStoreID(),
                    instance.getProductID(),
                    instance.getDate(),
                    instance.getAmount(),
                    instance.getEmployeeID(),
                    instance.getDescription(),
                    instance.getDefect()));
            IDENTITY_MAP.put(Integer.toString(instance.getId()), instance);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Collection<DefectiveItems> getAll() {
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection);
            while (instanceResult.next()) {
                IDENTITY_MAP.put(Integer.toString(instanceResult.getInt(ID_COLUMN)), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IDENTITY_MAP.values();
    }

    public Collection<DefectiveItems> getByProduct(int productID) {
        List<DefectiveItems> output = new ArrayList<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, Arrays.asList(PRODUCT_COLUMN), Arrays.asList(productID));
            while (instanceResult.next()) {
                DefectiveItems curr = buildObject(instanceResult);
                output.add(curr);
                IDENTITY_MAP.put(Integer.toString(instanceResult.getInt(ID_COLUMN)), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public Collection<DefectiveItems> getByStore(int storeID) {
        List<DefectiveItems> output = new ArrayList<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, Arrays.asList(STORE_COLUMN), Arrays.asList(storeID));
            while (instanceResult.next()) {
                DefectiveItems curr = buildObject(instanceResult);
                output.add(curr);
                IDENTITY_MAP.put(Integer.toString(instanceResult.getInt(ID_COLUMN)), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public Collection<DefectiveItems> getByEmployee(int employeeID) {
        List<DefectiveItems> output = new ArrayList<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, Arrays.asList(EMPLOYEE_ID_COLUMN), Arrays.asList(employeeID));
            while (instanceResult.next()) {
                DefectiveItems curr = buildObject(instanceResult);
                output.add(curr);
                IDENTITY_MAP.put(Integer.toString(instanceResult.getInt(ID_COLUMN)), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public Collection<DefectiveItems> getByDate(Date date) {
        List<DefectiveItems> output = new ArrayList<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, Arrays.asList(DATE_COLUMN), Arrays.asList(date));
            while (instanceResult.next()) {
                DefectiveItems curr = buildObject(instanceResult);
                output.add(curr);
                IDENTITY_MAP.put(Integer.toString(instanceResult.getInt(ID_COLUMN)), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public Collection<DefectiveItems> getByDefect(Defect defect) {
        List<DefectiveItems> output = new ArrayList<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, Arrays.asList(DEFECT_COLUMN), Arrays.asList(defect));
            while (instanceResult.next()) {
                DefectiveItems curr = buildObject(instanceResult);
                output.add(curr);
                IDENTITY_MAP.put(Integer.toString(instanceResult.getInt(ID_COLUMN)), buildObject(instanceResult));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
