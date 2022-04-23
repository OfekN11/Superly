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
    private Set<Integer> employees;

    public Constraint(Date date, ShiftTypes type, Set<Integer> employees) {
        this.date = date;
        this.type = type;
        this.employees = new HashSet<>(employees);
        dConstraint = new DConstraint();
    }

    public Constraint(DConstraint dConstraint){
        this.dConstraint = dConstraint;
        date = dConstraint.getDate();
        type = dConstraint.getShiftType();
        employees = new HashSet<>(dConstraint.getEmployees())
    }

    public Date getDate() {
        return date;
    }

    public ShiftTypes getType() {
        return type;
    }

    public Set<Integer> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Integer> employees) {
        this.employees = new HashSet<>(employees);
    }

    public void register(int id) {
        employees.add(id);
    }

    public void unregister(int id) {
        employees.remove(id);
    }
}
