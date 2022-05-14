package Domain.DAL.Controllers.EmployeeMappers;
import Domain.Business.Objects.Cashier;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeCertificationDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CashierDataMapper extends ObjectDateMapper<Cashier> {
    //static fields
    private final static Map<String, Cashier> CASHIER_IDENTITY_MAP = new HashMap<>();

    // fields
    private final EmployeeCertificationDAO employeeCertificationController;

    //constructor

    public CashierDataMapper() {
        super("Cashiers");
        employeeCertificationController = new EmployeeCertificationDAO();
    }


    @Override
    protected Map<String, Cashier> getMap() {
        return CASHIER_IDENTITY_MAP;
    }

    @Override
    protected  LinkDAO getLinkDTO(String setName) {
        switch (setName){
            case "certifications":
                return  employeeCertificationController;
            default:
                throw new IllegalArgumentException("no such set");
        }
    }

    @Override
    protected Cashier buildObject(ResultSet instanceResult) throws Exception {
        return new Cashier(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getString(5),instanceResult.getDate(6).toLocalDate(),employeeCertificationController.get(instanceResult.getString(1)));
    }

    @Override
    public void insert(Cashier instance) throws SQLException {
        employeeCertificationController.replaceSet(instance.getId(),instance.getCertifications());
        super.insert(Arrays.asList(instance.getId(),instance.getName(),instance.getBankDetails(),instance.getSalary(),instance.getEmploymentConditions(),instance.getStartingDate()));
    }
}
