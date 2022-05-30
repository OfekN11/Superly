package Domain.DAL.Controllers.ShiftEmployeesLink;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShiftsHRManagers extends LinkDAO<String> {
    public ShiftsHRManagers() {
        super("ShiftsHRManagers");
    }

    @Override
    protected String buildObject(ResultSet resultSet) throws SQLException {
        return resultSet.getString(2);
    }
}
