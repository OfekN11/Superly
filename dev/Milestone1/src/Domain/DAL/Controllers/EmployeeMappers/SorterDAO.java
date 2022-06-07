package Domain.DAL.Controllers.EmployeeMappers;

import Domain.Business.Objects.Employee.Sorter;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeCertificationDAO;

import java.sql.ResultSet;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SorterDAO extends AbstractEmployeeDAO<Sorter> {
    private static Map<String, Sorter> SORTER_IDENTITY_MAP = new ConcurrentHashMap<>();
    EmployeeCertificationDAO employeeCertificationController ;


    public SorterDAO() {
        super("Sorters");
        employeeCertificationController =new EmployeeCertificationDAO();
    }



    @Override
    protected Map<String, Sorter> getMap() {
        return SORTER_IDENTITY_MAP;
    }


    @Override
    protected Sorter buildObject(ResultSet instanceResult) throws Exception {
        return new Sorter(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getDate(6).toLocalDate(),employeeCertificationController.get(instanceResult.getString(1)));
    }

}
