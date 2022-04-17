package Domain.Business.Objects;
import Domain.DAL.Objects.DShift;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Shift {
    private static final String employeeAlreadyIn = "this employee already in this shift";
    // properties
    protected Date date;
    protected Set<Employee> employees;
    protected int shiftManagerId;
    protected DShift dShift; // represent of this object in the DAL
    protected String type;
    // constructors
    public Shift(Date date,String type,Employee shiftManager) {
        this.employees = new HashSet<>();
        this.date = date;
        this.employees.add(shiftManager);
        Set<Integer> employeesId = new HashSet<>();
        for (Employee employee: employees) {
            employeesId.add(employee.getId());
        }
        this.dShift = new DShift(date,type,employeesId,shiftManager.getId());
        this.shiftManagerId =shiftManager.getId();
        this.type = type;
    }

    public Shift(DShift dShift, Set<Employee> employees) {
        this.dShift = dShift;
        this.date = dShift.date;
        this.employees =employees;
        this.shiftManagerId = dShift.shiftManagerId;
    }

    public void AddEmployee(Employee employee){
        if(employees.contains(employee))
            throw new RuntimeException(employeeAlreadyIn);
        employees.add(employee);
    }
}
