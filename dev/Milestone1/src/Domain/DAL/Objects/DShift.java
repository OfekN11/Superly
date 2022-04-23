package Domain.DAL.Objects;

import Domain.DAL.Abstract.DTO;
import Domain.DAL.Controllers.DEmployeeShiftController;

import java.util.Date;
import java.util.Set;

public abstract class DShift extends DTO {

    // properties
    private Date date;
    private String type;
    private Set<Integer> employeesId;
    private int shiftManagerId;
    private DEmployeeShiftController employeeShiftController;

    // constructor
    public DShift(String tableName,Date date, String type, Set<Integer> workers,int shiftManagerId) {
        super(0, tableName); // no id to Shifts
        this.date = date;
        this.type = type;
        this.employeesId = workers;
        this.employeeShiftController = new DEmployeeShiftController();
        this.shiftManagerId = shiftManagerId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setShiftManagerId(int shiftManagerId) {
        this.shiftManagerId = shiftManagerId;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public Set<Integer> getEmployeesId() {
        return employeesId;
    }

    public int getShiftManagerId() {
        return shiftManagerId;
    }

    public void addEmployee(int employeeId){
        if (isPersist()) {
            employeeShiftController.addEmployee(getDate(), getType(), employeeId);
        }
        employeesId.add(employeeId);
    }

    public void removeEmployee(int employeeId){
        if (isPersist()) {
            employeeShiftController.removeEmployee(getDate(), getType(), employeeId);
        }
        employeesId.remove(employeeId);
    }


    public void delete(){
        //need to overwrite the delete because there is no id
    }
}
