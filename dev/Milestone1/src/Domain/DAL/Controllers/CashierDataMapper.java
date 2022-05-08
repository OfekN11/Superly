package Domain.DAL.Controllers;
import Domain.Business.Objects.Cashier;
import Domain.DAL.Abstract.DataMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CashierDataMapper extends DataMapper {
    //static fields
    private final static Map<String, Cashier> CASHIER_IDENTITY_MAP = new HashMap<>();

    // fields
    private final EmployeeCertificationDAO employeeCertificationController;

    //constructor

    public CashierDataMapper() {
        super("Cashiers");
        employeeCertificationController = new EmployeeCertificationDAO();
    }

    public Cashier get(String id){
        Cashier output = CASHIER_IDENTITY_MAP.get(id);
        if (output != null)
            return output;

        try(Connection connection = getConnection()) {
            ResultSet instanceResult = select(connection,id);
            if (!instanceResult.next())
                return null;


            output = new Cashier(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getString(5),instanceResult.getDate(6).toLocalDate(),employeeCertificationController.get(id));
            CASHIER_IDENTITY_MAP.put(id,output);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("something went wrong in Cashier DataMapper");
    }
}
