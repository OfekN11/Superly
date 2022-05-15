package Domain.DAL.Controllers.ShiftDataMappers;
import Domain.Business.Objects.MorningShift;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Domain.DAL.Controllers.ShiftEmployeesLink.*;
import Globals.Enums.ShiftTypes;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MorningShiftDataMapper extends ObjectDateMapper<MorningShift> {
    private static Map<String, MorningShift> MORNING_SHIFTS_IDENTITY_MAP = new HashMap<>();

    private ShiftsCarriersLink shiftsCarriersLink;
    private ShiftsCashiersLink shiftsCashiersLink;
    private ShiftsHRManagers shiftsHRManagers;
    private ShiftsLogisticManagersLink shiftsLogisticManagersLink;
    private ShiftsSortersLink shiftsSortersLink;
    private ShiftsStorekeepersLink shiftsStorekeepersLink;
    private ShiftsTransportManagers shiftsTransportManagers;

    public MorningShiftDataMapper() {
        super("MorningShifts");
        shiftsCarriersLink = new ShiftsCarriersLink();
        shiftsCashiersLink = new ShiftsCashiersLink();
        shiftsHRManagers = new ShiftsHRManagers();
        shiftsLogisticManagersLink = new ShiftsLogisticManagersLink();
        shiftsSortersLink= new ShiftsSortersLink();
        shiftsStorekeepersLink = new ShiftsStorekeepersLink();
        shiftsTransportManagers = new ShiftsTransportManagers();
    }

    @Override
    protected Map<String, MorningShift> getMap() {
        return MORNING_SHIFTS_IDENTITY_MAP;
    }

    @Override
    protected LinkDAO getLinkDTO(String setName) {
        switch (setName){
            case "carriers":
                return shiftsCarriersLink;
            case "cashiers":
                return shiftsCashiersLink;
            case "HRManagers":
                return shiftsHRManagers;
            case "logisticsManagers":
                return shiftsLogisticManagersLink;
            case "sorters":
                return shiftsSortersLink;
            case "storekeepers":
                return shiftsStorekeepersLink;
            case "transportManagers":
                return shiftsTransportManagers;
            default:
                throw new IllegalArgumentException("illegal set name");
        }
    }

    @Override
    protected MorningShift buildObject(ResultSet instanceResult) throws Exception {
        String id = instanceResult.getString(1);
        return new MorningShift(instanceResult.getDate(2).toLocalDate(), instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getInt(5),instanceResult.getInt(6),instanceResult.getInt(7),instanceResult.getInt(8),instanceResult.getInt(9),instanceResult.getInt(10),
                shiftsCarriersLink.get(id),shiftsCashiersLink.get(id),shiftsStorekeepersLink.get(id),shiftsSortersLink.get(id),shiftsHRManagers.get(id),shiftsLogisticManagersLink.get(id),shiftsTransportManagers.get(id));
    }

    @Override
    public void insert(MorningShift instance) throws SQLException {
        String id = instance.getWorkday().toString()+ ShiftTypes.Morning.toString();
        shiftsCarriersLink.replaceSet(id,instance.getCarrierIDs());
        shiftsCashiersLink.replaceSet(id,instance.getCashierIDs());
        shiftsHRManagers.replaceSet(id,instance.getHr_managerIDs());
        shiftsLogisticManagersLink.replaceSet(id,instance.getLogistics_managerIDs());
        shiftsSortersLink.replaceSet(id,instance.getSorterIDs());
        shiftsStorekeepersLink.replaceSet(id,instance.getStorekeeperIDs());
        shiftsTransportManagers.replaceSet(id,instance.getTransportManagersIDs());
        super.remove(instance.getWorkday().toString()+ShiftTypes.Morning.toString());
        super.insert(Arrays.asList(id,instance.getWorkday(),instance.getShiftManagerId(),instance.getCarrierCount(),instance.getCashierCount(),instance.getStorekeeperCount(),instance.getSorterCount(),instance.getHr_managersCount(),instance.getLogistics_managersCount(),instance.getTransportManagersCount()));
    }
}
