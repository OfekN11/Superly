package Domain.Business.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Controllers.DConstraintController;
import Domain.DAL.Objects.DConstraint;
import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class ConstraintController {
    private final Map<LocalDate, Map<ShiftTypes, Constraint>> constraints = new HashMap<>();
    private final DConstraintController dConstraintController = new DConstraintController();

    public void loadData() {
        Set<DConstraint> dConstraints = dConstraintController.loadData();
        for(DConstraint dConstraint: dConstraints){
            if (!constraints.containsKey(dConstraint.getDate()))
                constraints.put(dConstraint.getDate(),new HashMap<>());
            constraints.get(dConstraint.getDate()).put(dConstraint.getShiftType(),new Constraint(dConstraint));
        }

    }

    public void deleteData() {
    }

    public Constraint getConstraint(LocalDate workday, ShiftTypes shift){
        if (!constraints.containsKey(workday))
            constraints.put(workday, new HashMap<>());
        if (!constraints.get(workday).containsKey(shift))
            constraints.get(workday).put(shift, new Constraint(workday, shift, new HashSet<>()));
        return constraints.get(workday).get(shift);
    }

    public void registerToConstraint(String id, LocalDate workday, ShiftTypes shift) {
        Constraint constraint = getConstraint(workday, shift);
        constraint.register(id);
    }

    public void unregisterFromConstraint(String id, LocalDate workday, ShiftTypes shift) {
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

    public Set<String> getConstraintEmployees(LocalDate workday, ShiftTypes shift){
        if (!constraints.containsKey(workday) || !constraints.get(workday).containsKey(shift))
            return new HashSet<>();
        return constraints.get(workday).get(shift).getEmployees();
    }


    public Set<Constraint> getEmployeeConstraintsBetween(String id, LocalDate today, LocalDate nextMonth) {
        Set<LocalDate> dates = getDatesBetween(today,nextMonth);
        Set<Constraint> output = new HashSet<>();
        for(LocalDate date : dates){
            if (constraints.containsKey(date)){
                for(Constraint constraint: constraints.get(date).values())
                    if (constraint.getEmployees().contains(id))
                        output.add(constraint);
            }
        }
        return output;
    }

    private Set<LocalDate> getDatesBetween(LocalDate today,LocalDate nextMonth){
        return constraints.keySet().stream()
                .filter((d) -> d.isAfter(today) || d.isEqual(today))
                .filter((d) -> d.isBefore(nextMonth) || d.isEqual(nextMonth)).collect(Collectors.toSet());
    }
}
