package Domain.DAL.Controllers.ShiftEmployeesLink;

import Domain.DAL.Abstract.DataMapper;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Objects.DShift;
import Globals.Enums.ShiftTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ShiftsCarriersLink extends LinkDAO<String> {

    // properties
    public ShiftsCarriersLink() {
        super("ShiftsCarriers");
    }

    @Override
    protected String buildObject(ResultSet resultSet) throws SQLException {
        return resultSet.getString(2);
    }
}
