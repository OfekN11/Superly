package Domain.PersistenceLayer.Controllers;

import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.SQLException;
import java.util.Arrays;

public class SelfTransportingDAO extends DAO {

    public SelfTransportingDAO() {
        super("SelfTransport");
    }

    public void updateSupplier(int id) {
        try {
            insert(Arrays.asList(id));
        } catch (SQLException throwable) {
            System.out.println(throwable.getMessage());
        }
    }
}
