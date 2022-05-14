package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Inventory.Product;
import Domain.BusinessLayer.Inventory.StockReport;
import Domain.PersistenceLayer.Abstract.DAO;
import Domain.PersistenceLayer.Abstract.DataMapper;
import Domain.PersistenceLayer.Abstract.LinkDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StockReportDAO<StockReport> extends DAO {

    private final static int STORE_COLUMN = 1;
    private final static int PRODUCT_COLUMN = 2;
    private final static int AMOUNT_IN_STORE_COLUMN = 3;
    private final static int AMOUNT_IN_WAREHOUSE_COLUMN = 4;
    private final static int MIN_COLUMN = 5;
    private final static int TARGET_COLUMN = 6;

    public StockReportDAO() {
        super("StockReport");
    }


    protected StockReport buildObject(ResultSet instanceResult) throws Exception {
        return null;
    }

    public void insert(StockReport instance) throws SQLException {

    }

    public void updateMin(int productID, int storeID, int min) {
        try {
            update(Arrays.asList(MIN_COLUMN),
                    Arrays.asList(min),
                    Arrays.asList(PRODUCT_COLUMN, STORE_COLUMN),
                    Arrays.asList(productID, storeID));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTarget(int productID, int storeID, int target) {
        try {
            update(Arrays.asList(TARGET_COLUMN),
                    Arrays.asList(target),
                    Arrays.asList(PRODUCT_COLUMN, STORE_COLUMN),
                    Arrays.asList(productID, storeID));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateInStore(int productID, int storeID, int amount) {
        try {
            update(Arrays.asList(AMOUNT_IN_STORE_COLUMN),
                    Arrays.asList(amount),
                    Arrays.asList(PRODUCT_COLUMN, STORE_COLUMN),
                    Arrays.asList(productID, storeID));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateInWarehouse(int productID, int storeID, int amount) {
        try {
            update(Arrays.asList(AMOUNT_IN_WAREHOUSE_COLUMN),
                    Arrays.asList(amount),
                    Arrays.asList(PRODUCT_COLUMN, STORE_COLUMN),
                    Arrays.asList(productID, storeID));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
