package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Agreement.Agreement;
import Domain.BusinessLayer.Supplier.Agreement.ByOrderAgreement;
import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ByOrderDAO extends DAO {


    public ByOrderDAO() {
        super("ByOrder");
    }

    public void addTime(int id, List<Integer> agreementDays) {

        try {
            insert(Arrays.asList(id,  agreementDays.get(0)));
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
        }

    }

    public Agreement loadAgreement(int supplierId) {
        ResultSet resultSet = null;
        int result = 2;
        try (Connection connection = getConnection()){
            resultSet = select(connection, supplierId);
            while(resultSet.next()){
                result = resultSet.getInt(2);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return new ByOrderAgreement(result);
    }
}
