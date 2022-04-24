package Domain.Business.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Controllers.DConstraintController;
import Globals.Enums.ShiftTypes;

import java.util.*;

public class ConstraintController {
    private final Map<Date, Map<ShiftTypes, Constraint>> constraints = new HashMap<>();
    private final DConstraintController dConstraintController = new DConstraintController();

    public void loadData() {
    }

    public void deleteData() {
    }

    public Constraint getConstraint(Date workday, ShiftTypes shift){
        if (!constraints.containsKey(workday))
            constraints.put(workday, new HashMap<>());
        if (!constraints.get(workday).containsKey(shift))
            constraints.get(workday).put(shift, new Constraint(workday, shift, new HashSet<>()));
        return constraints.get(workday).get(shift);
    }

    public void registerToConstraint(String id, Date workday, ShiftTypes shift) {
        Constraint constraint = getConstraint(workday, shift);
        constraint.register(id);
    }

    public void unregisterFromConstraint(int id, Date workday, ShiftTypes shift) {
        if (!constraints.containsKey(workday) || !constraints.get(workday).containsKey(shift))
            return;
        Constraint constraint = getConstraint(workday, shift);
        constraint.unregister(id);
        if (constraint.getEmployees().isEmpty()) {
            dConstraintController.delete(constraint.getdConstraint());
            constraints.get(workday).remove(shift);
            if (constraints.get(workday).isEmpty())
                constraints.remove(workday);
        }
    }

    public Set<String> getConstraintEmployees(Date workday, ShiftTypes shift){
        if (!constraints.containsKey(workday) || !constraints.get(workday).containsKey(shift))
            return new HashSet<>();
        return constraints.get(workday).get(shift).getEmployees();
    }
}
