package Domain.DAL.Controllers.ShiftDataMappers;

import Domain.Business.Objects.EveningShift;
import Domain.DAL.Abstract.LinkDAO;
import Domain.DAL.Abstract.ObjectDateMapper;
import Domain.DAL.Controllers.ShiftEmployeesLink.*;
import Globals.Enums.ShiftTypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class EveningShiftDataMapper extends ObjectDateMapper<EveningShift> {
    private static final Map<String,EveningShift> EVENING_SHIFTS_MAP = new HashMap<>();

    private ShiftsCarriersLink shiftsCarriersLink;
    private ShiftsCashiersLink shiftsCashiersLink;
    private ShiftsHRManagers shiftsHRManagers;
    private ShiftsLogisticManagersLink shiftsLogisticManagersLink;
    private ShiftsSortersLink shiftsSortersLink;
    private ShiftsStorekeepersLink shiftsStorekeepersLink;
    private ShiftsTransportManagers shiftsTransportManagers;
    public EveningShiftDataMapper() {
        super("EveningShifts");
        shiftsCarriersLink = new ShiftsCarriersLink();
        shiftsCashiersLink = new ShiftsCashiersLink();
        shiftsHRManagers = new ShiftsHRManagers();
        shiftsLogisticManagersLink = new ShiftsLogisticManagersLink();
        shiftsSortersLink= new ShiftsSortersLink();
        shiftsStorekeepersLink = new ShiftsStorekeepersLink();
        shiftsTransportManagers = new ShiftsTransportManagers();
    }


    @Override
    protected Map<String, EveningShift> getMap() {
        return EVENING_SHIFTS_MAP;
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
    protected EveningShift buildObject(ResultSet instanceResult) throws Exception {
        String id = instanceResult.getString(1);
        return new EveningShift(instanceResult.getDate(2).toLocalDate(), instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getInt(5),instanceResult.getInt(6),instanceResult.getInt(7),instanceResult.getInt(8),instanceResult.getInt(9),instanceResult.getInt(10),
                shiftsCarriersLink.get(id),shiftsCashiersLink.get(id),shiftsStorekeepersLink.get(id),shiftsSortersLink.get(id),shiftsHRManagers.get(id),shiftsLogisticManagersLink.get(id),shiftsTransportManagers.get(id));
    }

    @Override
    public void insert(EveningShift instance) throws SQLException {
        String id = instance.getWorkday().toString()+ ShiftTypes.Evening.toString();
        shiftsCarriersLink.replaceSet(id,instance.getCarrierIDs());
        shiftsCashiersLink.replaceSet(id,instance.getCashierIDs());
        shiftsHRManagers.replaceSet(id,instance.getHr_managerIDs());
        shiftsLogisticManagersLink.replaceSet(id,instance.getLogistics_managerIDs());
        shiftsSortersLink.replaceSet(id,instance.getSorterIDs());
        shiftsStorekeepersLink.replaceSet(id,instance.getStorekeeperIDs());
        shiftsTransportManagers.replaceSet(id,instance.getTransportManagersIDs());
        super.remove(instance.getWorkday().toString()+ShiftTypes.Evening.toString());
        super.insert(Arrays.asList(id,instance.getWorkday(),instance.getShiftManagerId(),instance.getCarrierCount(),instance.getCashierCount(),instance.getStorekeeperCount(),instance.getSorterCount(),instance.getHr_managersCount(),instance.getLogistics_managersCount(),instance.getTransportManagersCount()));
    }

    @Override
    protected Set<LinkDAO> getAllLinkDTOs() {
        Set<LinkDAO> linkDAOS = new HashSet<>();
        linkDAOS.add(shiftsCarriersLink);
        linkDAOS.add(shiftsCashiersLink);
        linkDAOS.add(shiftsHRManagers);
        linkDAOS.add(shiftsLogisticManagersLink);
        linkDAOS.add(shiftsSortersLink);
        linkDAOS.add(shiftsStorekeepersLink);
        linkDAOS.add(shiftsTransportManagers);
        return linkDAOS;
    }
}
