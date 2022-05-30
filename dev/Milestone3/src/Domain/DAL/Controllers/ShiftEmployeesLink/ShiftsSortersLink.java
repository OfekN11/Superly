package Domain.DAL.Controllers.ShiftEmployeesLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShiftsSortersLink extends LinkDAO<String> {
    public ShiftsSortersLink() {
        super("ShiftsSorters");
    }

    @Override
    protected String buildObject(ResultSet resultSet) throws SQLException {
        return resultSet.getString(2);
    }
}
