package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Supplier;
import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class BulkPricesDAO extends DAO {


    public BulkPricesDAO() {
        super("BulkPrices");
    }



    public void addBulkPrices(int productId, Map<Integer, Integer> bulkPrices) throws SQLException {

        for(Map.Entry<Integer, Integer> enrty : bulkPrices.entrySet()){
            insert(Arrays.asList(String.valueOf(productId) , String.valueOf(enrty.getKey()) , String.valueOf(enrty.getValue())));
        }

    }

    public void updateBulkPrice(int itemID, Map<Integer, Integer> newBulkPrices) throws SQLException {
        remove(itemID);
        addBulkPrices(itemID, newBulkPrices);
    }


    public Map<Integer, Integer> getAllBulkPrices(int itemId) {
        Map<Integer, Integer> output = new HashMap<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection, itemId);
            while (instanceResult.next()) {
                output.put(instanceResult.getInt(2), instanceResult.getInt(3));
            }
        } catch (Exception throwables) {
            System.out.println(throwables.getMessage());
        }

        return output;
    }
}
