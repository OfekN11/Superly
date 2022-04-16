package Domain.DAL.Objects;

import Domain.DAL.Abstract.DTO;
import Domain.DAL.Controllers.DEmployeeShiftController;

import java.util.Date;
import java.util.Set;

public class DShift extends DTO {

    // properties
    private Date date;
    private String type;
    private Set<DEmployee> workers;
    private DEmployeeShiftController employeeShiftController;

    // constructor
    public DShift(Date date, String type, Set<DEmployee> workers) {
        this.date = date;
        this.type = type;
        this.workers = workers;
        this.employeeShiftController = new DEmployeeShiftController();
    }
}
