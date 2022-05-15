package Domain.DAL.Controllers.EmployeeMappers;

import Domain.Business.Objects.Logistics_Manager;
import Domain.Business.Objects.Sorter;
import Domain.DAL.Abstract.EmployeeTypeDataMapper;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeCertificationDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SorterDataMapper extends EmployeeTypeDataMapper<Sorter> {
    private static Map<String, Sorter> SORTER_IDENTITY_MAP = new HashMap<>();
    EmployeeCertificationDAO employeeCertificationController ;


    public SorterDataMapper() {
        super("Sorters");
        employeeCertificationController =new EmployeeCertificationDAO();
    }



    @Override
    protected Map<String, Sorter> getMap() {
        return SORTER_IDENTITY_MAP;
    }


    @Override
    protected Sorter buildObject(ResultSet instanceResult) throws Exception {
        return new Sorter(instanceResult.getString(1),instanceResult.getString(2),instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getString(5),instanceResult.getDate(6).toLocalDate(),employeeCertificationController.get(instanceResult.getString(1)));
    }

}
