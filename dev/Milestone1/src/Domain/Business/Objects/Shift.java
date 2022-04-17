package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Shift {
    // properties
    private Date date;
    private Set<Employee> employees;
    private int shiftManagerId;
    private DShift dShift; // represent of this object in the DAL

    // constructors
    public Shift(Date date, Set<Employee> employees, String type,int shiftManagerId) {
        this.date = date;
        Set<Integer> dEmployeesId = new HashSet<>();
        for (Employee employee: employees) {
            dEmployeesId.add(employee.getId());
        }
        this.dShift = new DShift(date,type,dEmployeesId,shiftManagerId);
        this.shiftManagerId =shiftManagerId;
    }

    public Shift(DShift dShift, Set<Employee> employees,int shiftManagerId) {
        this.dShift = dShift;
        this.date = dShift.date;
        this.employees =employees;
        this.shiftManagerId = shiftManagerId;
    }
}
