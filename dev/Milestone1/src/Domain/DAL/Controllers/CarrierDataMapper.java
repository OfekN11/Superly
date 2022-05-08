package Domain.DAL.Controllers;
import Domain.Business.Objects.Carrier;
import Domain.DAL.Abstract.DataMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CarrierDataMapper extends DataMapper {

    private final static Map<String, Carrier> CARRIER_IDENTITY_MAP = new HashMap<>();

    private final EmployeeCertificationDAO employeeCertificationDAO;
    private final CarrierLicensesDAO carrierLicensesDAO;

    public CarrierDataMapper() {
        super("Carriers");
        employeeCertificationDAO = new EmployeeCertificationDAO();
        carrierLicensesDAO = new CarrierLicensesDAO();
    }

    public Carrier get(String id){
        Carrier output = CARRIER_IDENTITY_MAP.get(id);
        if (output != null)
            return output;

        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection,id);
            if (!instanceResult.next())
                return null;


            output = new Carrier(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4)
                    ,instanceResult.getString(5),instanceResult.getDate(6).toLocalDate(), employeeCertificationDAO.get(id),carrierLicensesDAO.getLicensesOfCarrier(id));
            CARRIER_IDENTITY_MAP.put(id,output);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("something went wrong in Cashier DataMapper");
    }
}
