package Domain.Business.Objects;
import Domain.Business.Objects.Enums.ShiftType;
import Domain.DAL.Objects.DShift;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Shift {
    private static final String employeeAlreadyInShiftErrorMsg = "this employee already in this shift";
    private static final String employeeNotInShiftErrorMsg = "Employee %o was not in the shift";
    // properties
    private Date date;
    private Set<Employee> employees;
    private int shiftManagerId;
    private DShift dShift; // represent of this object in the DAL

    // constructors
    public Shift(Date date,Cashier shiftManager) {
        this.employees = new HashSet<>();
        this.date = date;
        this.employees.add(shiftManager);
        this.shiftManagerId =shiftManager.getId();
        Set<Integer> employeesId = new HashSet<>();
        employeesId.add(shiftManagerId);
        this.dShift = new DShift(date,getType().toString(),employeesId,shiftManager.getId());
    }

    public Shift(DShift dShift, Set<Employee> employees) {
        this.dShift = dShift;
        this.date = dShift.date;
        this.employees =employees;
        this.shiftManagerId = dShift.shiftManagerId;
    }

    public void AddEmployee(Employee employee){
        if(employees.contains(employee))
            throw new RuntimeException(employeeAlreadyInShiftErrorMsg);
        employees.add(employee);
    }

    public void replaceShiftManager(Cashier newManager){
        if(employees.contains(newManager))
            shiftManagerId = newManager.getId();
    }

    public static String getEmployeeAlreadyInShiftErrorMsg() {
        return employeeAlreadyInShiftErrorMsg;
    }

    public Date getDate() {
        return date;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public int getShiftManagerId() {
        return shiftManagerId;
    }

    public DShift getdShift() {
        return dShift;
    }

    public abstract ShiftType getType();

    public void removeEmployee(Employee employee){
        if (!employees.remove(employee))
            throw new RuntimeException(String.format(employeeNotInShiftErrorMsg,employee.getId()));
    }
}
