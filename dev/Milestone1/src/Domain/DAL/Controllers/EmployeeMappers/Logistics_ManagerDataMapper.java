package Domain.DAL.Controllers.EmployeeMappers;
import Domain.Business.Objects.Logistics_Manager;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeCertificationDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Logistics_ManagerDataMapper extends ObjectDateMapper<Logistics_Manager> {
    private static Map<String, Logistics_Manager> LOGISTIC_MANAGERS_MAP = new HashMap<>();
    EmployeeCertificationDAO employeeCertificationController ;


    public Logistics_ManagerDataMapper() {
        super("Logistics_Manager");
        employeeCertificationController = new EmployeeCertificationDAO();
    }



    @Override
    protected Map<String, Logistics_Manager> getMap() {
        return LOGISTIC_MANAGERS_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        switch (setName){
            case "certifications":
                return  employeeCertificationController;
            default:
                throw new IllegalArgumentException("no such set");
        }
    }

    @Override
    protected Logistics_Manager buildObject(ResultSet instanceResult) throws Exception {
        return new Logistics_Manager(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getString(5),instanceResult.getDate(6).toLocalDate(),employeeCertificationController.get(instanceResult.getString(1)));
    }

    @Override
    public void insert(Logistics_Manager instance) throws SQLException {
        employeeCertificationController.replaceSet(instance.getId(),instance.getCertifications());
        super.remove(instance.getId());
        super.insert(Arrays.asList(instance.getId(),instance.getName(),instance.getBankDetails(),instance.getSalary(),instance.getEmploymentConditions(),instance.getStartingDate()));
    }
}
