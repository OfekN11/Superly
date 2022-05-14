package Domain.DAL.Controllers.EmployeeMappers;
import Domain.Business.Objects.Storekeeper;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeCertificationDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StorekeeperDataMapper extends ObjectDateMapper<Storekeeper> {
    private static Map<String, Storekeeper> STOREKEEPER_IDENTITY_MAP = new HashMap<>();
    EmployeeCertificationDAO employeeCertificationController ;


    public StorekeeperDataMapper() {
        super("Storekeepers");
        employeeCertificationController= new EmployeeCertificationDAO();
    }




    @Override
    protected Map<String, Storekeeper> getMap() {
        return STOREKEEPER_IDENTITY_MAP;
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
    protected Storekeeper buildObject(ResultSet instanceResult) throws Exception {
        return new Storekeeper(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getString(5),instanceResult.getDate(6).toLocalDate(),employeeCertificationController.get(instanceResult.getString(1)));
    }

    @Override
    public void insert(Storekeeper instance) throws SQLException {
        employeeCertificationController.replaceSet(instance.getId(),instance.getCertifications());
        super.remove(instance.getId());
        super.insert(Arrays.asList(instance.getId(),instance.getName(),instance.getBankDetails(),instance.getSalary(),instance.getEmploymentConditions(),instance.getStartingDate()));
    }
}
