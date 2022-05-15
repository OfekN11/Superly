package Domain.DAL.Controllers.EmployeeLinks;
import Domain.DAL.Abstract.DataMapper;
import Domain.DAL.Abstract.LinkDAO;
import Globals.Enums.Certifications;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EmployeeCertificationDAO extends LinkDAO<Certifications> {

    public EmployeeCertificationDAO() {
        super("EmployeesCertifications");
    }

    @Override
    protected Certifications buildObject(ResultSet resultSet) throws SQLException {
        return Certifications.valueOf(resultSet.getString(2));
    }
}
