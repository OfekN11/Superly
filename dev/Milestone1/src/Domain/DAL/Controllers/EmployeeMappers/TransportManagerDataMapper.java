package Domain.DAL.Controllers.EmployeeMappers;

import Domain.Business.Objects.Employee.Transport_Manager;
import Domain.DAL.Abstract.EmployeeTypeDataMapper;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeCertificationDAO;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TransportManagerDataMapper extends EmployeeTypeDataMapper<Transport_Manager> {
    private static Map<String, Transport_Manager> TRANSPORT_MANAGER_IDENTITY_MAP = new HashMap<>();
    EmployeeCertificationDAO employeeCertificationController ;


    public TransportManagerDataMapper() {
        super("TransportManagers");
    }



    @Override
    protected Map<String, Transport_Manager> getMap() {
        return TRANSPORT_MANAGER_IDENTITY_MAP;
    }

    @Override
    protected Transport_Manager buildObject(ResultSet instanceResult) throws Exception {
        return new Transport_Manager(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getString(5),instanceResult.getDate(6).toLocalDate(),employeeCertificationController.get(instanceResult.getString(1)));
    }
}
