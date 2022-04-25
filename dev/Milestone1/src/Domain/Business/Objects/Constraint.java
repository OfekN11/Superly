package Domain.Business.Objects;
import Domain.DAL.Objects.DConstraint;
import Globals.Enums.ShiftTypes;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Constraint {
    private final DConstraint dConstraint;
    // properties
    private Date date;
    private ShiftTypes type; // morning evening
    private Set<String> employees;

    public Constraint(Date date, ShiftTypes type, Set<String> employees) {
        this.date = date;
        this.type = type;
        this.employees = new HashSet<>(employees);
        dConstraint = new DConstraint(date,type,employees);
        dConstraint.save();
    }

    public Constraint(DConstraint dConstraint){
        this.dConstraint = dConstraint;
        date = dConstraint.getDate();
        type = dConstraint.getShiftType();
        employees = new HashSet<>(dConstraint.getEmployees());
    }

    public Date getDate() {
        return date;
    }

    public ShiftTypes getType() {
        return type;
    }

    public Set<String> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<String> employees) {
        this.employees = new HashSet<>(employees);
    }

    public void register(String id) {
        dConstraint.register(id);
        employees.add(id);
    }

    public void unregister(String id) {
        dConstraint.unregister(id);
        employees.remove(id);
    }

    public DConstraint getdConstraint() {
        return dConstraint;
    }
}
