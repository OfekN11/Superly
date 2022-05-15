package Domain.Business.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Controllers.ConstraintDataMapper;
import Globals.Enums.ShiftTypes;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class ConstraintController {

    private ConstraintDataMapper constraintDataMapper;

    public ConstraintController() {
        this.constraintDataMapper = new ConstraintDataMapper();
    }

    public Constraint getConstraint(LocalDate workday, ShiftTypes shiftType){
        Constraint constraint = constraintDataMapper.get(workday,shiftType);
        if (constraint != null)
            return constraint;

        try {
            constraint =new Constraint(workday,shiftType,new HashSet<>());
            constraintDataMapper.save(workday,shiftType,constraint);
            return constraint;
        } catch (SQLException throwables) {
            throw new RuntimeException("saving constraint has failed");
        }
    }

    public void registerToConstraint(String id, LocalDate workday, ShiftTypes shift) {
        Constraint constraint = getConstraint(workday, shift);
        constraint.register(id);
        try {
            constraintDataMapper.update(constraint);
        } catch (SQLException throwables) {
            throw new RuntimeException("updating constraint has failed");
        }
    }

    public void unregisterFromConstraint(String id, LocalDate workday, ShiftTypes shift) {
        if (constraintDataMapper.get(workday.toString()+shift.toString()) == null)
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
