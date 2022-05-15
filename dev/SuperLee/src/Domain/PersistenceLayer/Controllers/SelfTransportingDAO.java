package Domain.PersistenceLayer.Controllers;

import Domain.BusinessLayer.Supplier.Agreement.Agreement;
import Domain.BusinessLayer.Supplier.Agreement.NotTransportingAgreement;
import Domain.PersistenceLayer.Abstract.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public Agreement loadAgreement(int supplierId) {
        return new NotTransportingAgreement();
    }
}
