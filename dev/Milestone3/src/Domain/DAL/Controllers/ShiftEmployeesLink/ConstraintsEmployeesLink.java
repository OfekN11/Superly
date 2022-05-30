package Domain.DAL.Controllers.ShiftEmployeesLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConstraintsEmployeesLink extends LinkDAO<String> {
    public ConstraintsEmployeesLink() {
        super("ConstraintsEmployees");
    }

    @Override
    protected String buildObject(ResultSet resultSet) throws SQLException {
        return resultSet.getString(2);
    }
}
