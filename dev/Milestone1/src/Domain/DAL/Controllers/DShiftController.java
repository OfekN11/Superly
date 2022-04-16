package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DShift;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

public class DShiftController extends DalController<DShift> {
    // properties
    private DEmployeeController dEmployeeController;
    private DEmployeeShiftController dEmployeeShiftController;

    // functions
    @Override
    public Set<DShift> createFakeDTOs() {
        Set<DShift> employees= new HashSet<>();
        employees.add(new DShift(Time.valueOf("14/07/1998"),"Day", dEmployeeController.createFakeDTOs()));
        employees.add(new DShift(Time.valueOf("14/07/1998"),"night", dEmployeeController.createFakeDTOs()));
        return employees;
    }

    public DShiftController() {
        this.dEmployeeController = new DEmployeeController();
        this.dEmployeeShiftController = new DEmployeeShiftController();
    }
}

