package Domain.DAL.Objects;

import Domain.DAL.Abstract.DTO;
import Domain.DAL.Controllers.DEmployeeShiftController;

import java.util.Date;
import java.util.Set;

public class DShift extends DTO {

    // properties
    public Date date;
    public String type;
    public Set<Integer> workersId;
    public int shiftManagerId;
    private DEmployeeShiftController employeeShiftController;

    // constructor
    public DShift(Date date, String type, Set<Integer> workers,int shiftManagerId) {
        this.date = date;
        this.type = type;
        this.workersId = workers;
        this.employeeShiftController = new DEmployeeShiftController();
        this.shiftManagerId = shiftManagerId;
    }
}
