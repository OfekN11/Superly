package Domain.DAL.Controllers.ShiftDataMappers;

import Domain.Business.Objects.EveningShift;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Domain.DAL.Controllers.EmployeeLinks.EmployeeTypeLink;
import Domain.DAL.Controllers.ShiftEmployeesLink.ShiftsCarriersLink;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DEveningShiftController extends ObjectDateMapper<EveningShift> {
    private static Map<String,EveningShift> EVENING_SHIFTS_MAP = new HashMap<>();

    private ShiftsCarriersLink shiftsCarriersLink;
    private EmployeeTypeLink employeeTypeLink;
    public DEveningShiftController() {
        super("EveningShifts");
        shiftsCarriersLink = new ShiftsCarriersLink();
        employeeTypeLink = new EmployeeTypeLink();
    }


    @Override
    protected Map<String, EveningShift> getMap() {
        return EVENING_SHIFTS_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        switch (setName){
            case "carriers":
            case "cashiers":
            case "HRManagers":
            case "logisticsManagers":
            case "sorters":
            case "storekeepers":
                return shiftsCarriersLink;
            default:
                throw new IllegalArgumentException("illegal set name");
        }
    }

    @Override
    protected EveningShift buildObject(ResultSet instanceResult) throws Exception {
        Set<String> employeeIds = shiftsCarriersLink.get(instanceResult.getString(1));
        Set<String> carriers= new HashSet<>();
        Set<String> cashiers= new HashSet<>();
        Set<String> HRManagers= new HashSet<>();
        Set<String> logisticsManagers= new HashSet<>();
        Set<String> sorters= new HashSet<>();
        Set<String> storekeepers= new HashSet<>();
        Set<String> transportManagers= new HashSet<>();
        for(String eID :employeeIds){
            Set<String> typeSet= employeeTypeLink.get(eID);
            for(String type:typeSet){
                switch (type){
                    case "carrier":
                        carriers.add(eID);
                        break;
                    case "cashier":
                        cashiers.add(eID);
                        break;
                    case "HRManager":
                        HRManagers.add(eID);
                        break;
                    case "logisticsManager":
                        logisticsManagers.add(eID);
                        break;
                    case "sorter":
                        sorters.add(eID);
                        break;
                    case "storekeeper":
                        storekeepers.add(eID);
                        break;
                    case "transportManager":
                        transportManagers.add(eID);
                        break;

                    default:
                        throw new IllegalArgumentException("type is not declared");
                }
            }
        }
        // this line is red cause we need to up to Shifts Constructor the transport Manager Set (do it in the end of the constructor)
        return new EveningShift(instanceResult.getDate(2), instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getInt(5),instanceResult.getInt(6),instanceResult.getInt(7),instanceResult.getInt(8),instanceResult.getInt(9),instanceResult.getInt(10))
    }

    @Override
    public void insert(EveningShift instance) throws SQLException {

    }
}
