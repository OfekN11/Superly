package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DataMapper;
import Globals.Enums.LicenseTypes;
import Globals.Enums.ShiftTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CarrierLicensesDAO extends DataMapper {
    // dict of employeeID and its Licenses
    public CarrierLicensesDAO() {
        super("CarriersLicenses");
    }

    public Set<LicenseTypes> getLicensesOfCarrier(String carrierId) throws SQLException {
        Set<LicenseTypes> output = new HashSet<>();
        try(Connection connection = getConnection()){
            ResultSet resultSet = select(connection, carrierId);
            while (resultSet.next())
                output.add(LicenseTypes.valueOf(resultSet.getString(2)));
        }
        return output;
    }

    public void updateSetOfLicenses(String carrierId, Set<LicenseTypes> newLicenseSet) throws SQLException {
        remove(carrierId);
        for(LicenseTypes license: newLicenseSet)
            insert(Arrays.asList(carrierId,license));
    }


}
