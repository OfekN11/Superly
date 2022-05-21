package Domain.DAL.Controllers.ShiftDataMappers;

import Domain.Business.Objects.Shift.EveningShift;
import Globals.Enums.ShiftTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.*;

public class EveningShiftDAO extends AbstractShiftDAO<EveningShift> {
    private static final Map<String,EveningShift> EVENING_SHIFTS_MAP = new HashMap<>();

    public EveningShiftDAO() {
        super("EveningShifts");
    }


    @Override
    protected Map<String, EveningShift> getMap() {
        return EVENING_SHIFTS_MAP;
    }


    @Override
    protected EveningShift buildObject(String id) throws Exception {
        Set<String> carriers=shiftsCarriersLink.get(id);
        Set<String> cashiers=shiftsCashiersLink.get(id);
        Set<String> storekeepers=shiftsStorekeepersLink.get(id);
        Set<String> sorters=shiftsSortersLink.get(id);
        Set<String> hrs=shiftsHRManagers.get(id);
        Set<String> logistic=shiftsLogisticManagersLink.get(id);
        Set<String> transport=shiftsTransportManagers.get(id);
        Set<String> constraints=constraintsEmployeesLink.get(id);
        try (Connection connection= getConnection()) {
            ResultSet instanceResult = select(connection,id);
            return new EveningShift(instanceResult.getDate(2).toLocalDate(), instanceResult.getString(3),instanceResult.getInt(4),instanceResult.getInt(5),instanceResult.getInt(6),instanceResult.getInt(7),instanceResult.getInt(8),instanceResult.getInt(9),instanceResult.getInt(10),
                carriers,cashiers,storekeepers,sorters,hrs,logistic,transport,constraints);
        }
    }

    @Override
    protected String getType() {
        return ShiftTypes.Evening.toString();
    }
}
