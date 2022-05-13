package Domain.PersistenceLayer.Controllers;

import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.SQLException;
import java.util.Arrays;

public class ByOrderDAO extends DAO {


    public ByOrderDAO() {
        super("ByOrder");
    }

    public void addTime(int id, String agreementDays) {

        try {
            insert(Arrays.asList(id, Integer.parseInt(agreementDays)));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }
}
