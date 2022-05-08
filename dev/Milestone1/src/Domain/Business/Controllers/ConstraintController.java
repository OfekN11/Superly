package Domain.Business.Controllers;

import Domain.Business.Objects.Constraint;
import Domain.DAL.Controllers.ConstraintDataMap;
import Domain.DAL.Objects.DConstraint;
import Globals.Enums.ShiftTypes;

import java.util.*;

public class ConstraintController {
    private final Map<Date, Map<ShiftTypes, Constraint>> constraints = new HashMap<>();
    private final ConstraintDataMap constraintDataMap = new ConstraintDataMap();

    public void loadData() {
        Set<DConstraint> dConstraints = constraintDataMap.loadData();
        for(DConstraint dConstraint: dConstraints){
            if (!constraints.containsKey(dConstraint.getDate()))
                constraints.put(dConstraint.getDate(),new HashMap<>());
            constraints.get(dConstraint.getDate()).put(dConstraint.getShiftType(),new Constraint(dConstraint));
        }

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

    public void unregisterFromConstraint(String id, Date workday, ShiftTypes shift) {
        if (!constraints.containsKey(workday) || !constraints.get(workday).containsKey(shift))
            return;
        Constraint constraint = getConstraint(workday, shift);
        constraint.unregister(id);
        if (constraint.getEmployees().isEmpty()) {
            constraintDataMap.delete(constraint.getdConstraint());
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

    public Set<Constraint> getEmployeeConstraintsBetween(String id, Date today, Date nextMonth) {
        Set<Date> dates = getDatesBetween(today,nextMonth);
        Set<Constraint> output = new HashSet<>();
        for(Date date : dates){
            if (constraints.containsKey(date)){
                for(Constraint constraint: constraints.get(date).values())
                    if (constraint.getEmployees().contains(id))
                        output.add(constraint);
            }
        }
        return output;
    }

    private Set<Date> getDatesBetween(Date today,Date nextMonth){
        Set<Date> datesInRange = new HashSet<>();
        Calendar calendar = getCalendarWithoutTime(today);
        Calendar endCalendar = getCalendarWithoutTime(nextMonth);
        endCalendar.add(Calendar.DATE, 1);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        return datesInRange;
    }

    private Calendar getCalendarWithoutTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

}
