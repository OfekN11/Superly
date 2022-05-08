package Domain.DAL.Controllers;
import Domain.Business.Objects.Cashier;
import Domain.DAL.Abstract.DataMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CashierDataMapper extends DataMapper {
    private static Map<String, Cashier> CASHIER_IDENTITY_MAP = new HashMap<>();

    public CashierDataMapper() {
        super("tableName");
    }

    public Cashier get(String id){
        Cashier output = CASHIER_IDENTITY_MAP.get(id);
        if (output != null)
            return output;

        try(Connection connection = getConnection()) {
            ResultSet resultSet = select(connection,id);
            resultSet.next();
            output = new Cashier(resultSet.getString(1),resultSet.ge)
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
