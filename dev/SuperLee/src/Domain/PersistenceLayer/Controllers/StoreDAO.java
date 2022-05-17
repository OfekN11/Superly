package Domain.PersistenceLayer.Controllers;

import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class StoreDAO extends DAO {

    public StoreDAO(){
        super("Stores");
    }

    public Collection<Integer> getAll() {
        Collection<Integer> stores = new ArrayList<>();
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection);
            while (instanceResult.next()) {
                stores.add(instanceResult.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stores;
    }

    public void addStore(int id) {
        try {
            insert(Arrays.asList(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeStore(int id) {
        try {
            remove(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getIDCount() {
        try(Connection connection = getConnection()) {
            ResultSet instanceResult = getMax(connection, 1);
            while (instanceResult.next()) {
                return instanceResult.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
