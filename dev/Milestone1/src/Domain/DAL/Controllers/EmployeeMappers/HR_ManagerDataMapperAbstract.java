package Domain.DAL.Controllers.EmployeeMappers;
import Domain.Business.Objects.HR_Manager;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeCertificationDAO;

import java.sql.ResultSet;
import java.util.*;

public class HR_ManagerDataMapperAbstract extends AbstractEmployeeDataMapper<HR_Manager> {
    private static Map<String, HR_Manager> HR_MANAGERS_MAP = new HashMap<>();
    EmployeeCertificationDAO employeeCertificationController ;


    public HR_ManagerDataMapperAbstract() {
        super("HRManagers");
        employeeCertificationController = new EmployeeCertificationDAO();
    }

    @Override
    protected Map<String, HR_Manager> getMap() {
        return HR_MANAGERS_MAP;
    }

    @Override
    protected HR_Manager buildObject(ResultSet instanceResult) throws Exception {
        return new HR_Manager(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getString(5),instanceResult.getDate(6).toLocalDate(),employeeCertificationController.get(instanceResult.getString(1)));
    }
}
