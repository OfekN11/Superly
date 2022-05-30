package Domain.DAL.Controllers.ShiftEmployeesLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShiftsCashiersLink extends LinkDAO<String> {
    public ShiftsCashiersLink() {
        super("ShiftsCashiers");
    }

    @Override
    protected String buildObject(ResultSet resultSet) throws SQLException {
        return resultSet.getString(2);
    }
}
