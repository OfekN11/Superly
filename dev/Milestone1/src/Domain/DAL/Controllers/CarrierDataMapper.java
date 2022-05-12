package Domain.DAL.Controllers;
import Domain.Business.Objects.Carrier;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CarrierDataMapper extends ObjectDateMapper<Carrier> {

    private final static Map<String, Carrier> CARRIER_IDENTITY_MAP = new HashMap<>();

    private final EmployeeCertificationDAO employeeCertificationDAO;
    private final CarrierLicensesDAO carrierLicensesDAO;

    public CarrierDataMapper() {
        super("Carriers");
        employeeCertificationDAO = new EmployeeCertificationDAO();
        carrierLicensesDAO = new CarrierLicensesDAO();
    }


    @Override
    protected Map<String, Carrier> getMap() {
        return CARRIER_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        switch (setName){
            case "certifications":
                return  employeeCertificationDAO;
            case "licenses":
                return carrierLicensesDAO;
            default:
                throw new IllegalArgumentException("no such set");
        }
    }

    @Override
    protected Carrier buildObject(ResultSet instanceResult) throws Exception {
        return new Carrier(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getString(5),instanceResult.getDate(6).toLocalDate(),employeeCertificationDAO.get(instanceResult.getString(1)),carrierLicensesDAO.get(instanceResult.getNString(1)));
    }

    @Override
    public void insert(Carrier instance) throws SQLException {
        employeeCertificationDAO.replaceSet(instance.getId(),instance.getCertifications());
        carrierLicensesDAO.replaceSet(instance.getId(),instance.getLicenses());
        super.insert(Arrays.asList(instance.getId(),instance.getName(),instance.getBankDetails(),instance.getSalary(),instance.getEmploymentConditions(),instance.getStartingDate()));
    }
}
