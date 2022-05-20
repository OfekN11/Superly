package Domain.DAL.Controllers.EmployeeMappers;
import Domain.Business.Objects.Employee.Carrier;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Controllers.EmployeeLinks.CarrierLicensesDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CarrierDAO extends AbstractEmployeeDAO<Carrier> {

    private final static Map<String, Carrier> CARRIER_IDENTITY_MAP = new HashMap<>();
    private final CarrierLicensesDAO carrierLicensesDAO;

    public CarrierDAO() {
        super("Carriers");
        carrierLicensesDAO = new CarrierLicensesDAO();
    }


    @Override
    protected Map<String, Carrier> getMap() {
        return CARRIER_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        switch (setName){
            case "licenses":
                return carrierLicensesDAO;
            default:
                return super.getLinkDTO(setName);
        }
    }

    @Override
    protected Carrier buildObject(ResultSet instanceResult) throws Exception {
        return new Carrier(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getDate(6).toLocalDate(),getEmployeeCertificationController().get(instanceResult.getString(1)),carrierLicensesDAO.get(instanceResult.getNString(1)));
    }

    @Override
    public void insert(Carrier instance) throws SQLException {
        carrierLicensesDAO.replaceSet(instance.getId(),instance.getLicenses());
        super.insert(instance);
    }
}
