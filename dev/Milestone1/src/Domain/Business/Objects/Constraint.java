package Domain.Business.Objects;
import Domain.DAL.Objects.DConstraint;
import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Constraint {
    // properties
    private LocalDate date;
    private ShiftTypes type; // morning evening
    private Set<String> employees;

    public Constraint(LocalDate date, ShiftTypes type, Set<String> employees) {
        this.date = date;
        this.type = type;
        this.employees = new HashSet<>(employees);
    }

    public Constraint(DConstraint dConstraint){
        type = dConstraint.getShiftType();
        employees = new HashSet<>(dConstraint.getEmployees());
    }

    public LocalDate getDate() {
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
        employees.add(id);
    }

    public void unregister(String id) {
        employees.remove(id);
    }
}
