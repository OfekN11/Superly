package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DataMapper;
import Domain.DAL.Objects.DEmployee;
import Globals.Enums.Certifications;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EmployeeCertificationDAO extends DataMapper {

    public EmployeeCertificationDAO() {
        super("EmployeesCertifications");
    }

    public void addCertification(String employeeID, Certifications certification) throws SQLException {
        insert(Arrays.asList(employeeID, certification));
    }

    public void deleteCertification(String employeeId, Certifications certification) throws SQLException {
        remove(Arrays.asList(1, 2), Arrays.asList(employeeId, certification));
    }

    public void updateCertificationOfEmployee(String employeeId, Set<Certifications> certifications) throws SQLException {
        remove(employeeId);
        for (Certifications certification : certifications)
            insert(Arrays.asList(employeeId, certification));
    }

    public Set<Certifications> get(String employeeId) throws SQLException {
        try (Connection connection = getConnection()) {
            Set<Certifications> output = new HashSet<>();
            ResultSet resultSet = select(connection, employeeId);
            while (resultSet.next())
                output.add(Certifications.valueOf(resultSet.getString(2)));
            return output;
        }

    }
}
