package Domain.Business.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Controllers.ConstraintDAO;
import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class ConstraintController {

    private final ConstraintDAO constraintDataMapper = new ConstraintDAO();

    //CREATE

    public void addConstraint(LocalDate workday, ShiftTypes shiftType) throws Exception{
        constraintDataMapper.save(new Constraint(workday, shiftType, new HashSet<>()));
    }

    //READ

    public Constraint getConstraint(LocalDate workday, ShiftTypes shiftType) throws Exception {
        Constraint constraint = constraintDataMapper.get(workday, shiftType);
        if (constraint != null)
            return constraint;
        addConstraint(workday, shiftType);
        constraint = constraintDataMapper.get(workday, shiftType);
        return constraint;
    }

    public Set<String> getConstraintEmployeeIDs(LocalDate workday, ShiftTypes shift) throws Exception{
        Constraint constraint = getConstraint(workday, shift);
        if (constraint == null)
            return new HashSet<>();
        return constraint.getEmployees();
    }

    public Set<Constraint> getEmployeeConstraintsBetween(String id, LocalDate start, LocalDate end) {
        Set<Constraint> constraints = constraintDataMapper.getConstraintsBetween(start, end);
        constraints = constraints.stream().filter(c -> c.getEmployees().contains(id)).collect(Collectors.toSet());
        return constraints;
    }

    //UPDATE

    public void registerToConstraint(String id, LocalDate workday, ShiftTypes shift) throws Exception{
        Constraint constraint = getConstraint(workday, shift);
        constraint.register(id);
        constraintDataMapper.save(constraint);
    }

    public void unregisterFromConstraint(String id, LocalDate workday, ShiftTypes shift) throws Exception{
        Constraint constraint = getConstraint(workday, shift);
        if (constraint == null)
            return;
        constraint.unregister(id);
        if (constraint.isEmpty())
            removeConstraint(constraint);
    }

    //DELETE

    public void removeConstraint(LocalDate workday, ShiftTypes shiftTypes) throws Exception{
        Constraint constraint = getConstraint(workday, shiftTypes);
        if (constraint != null)
            constraintDataMapper.delete(constraint);
    }

    public void removeConstraint(Constraint constraint) throws Exception{
        constraintDataMapper.delete(constraint);
    }

    //MISC
}
