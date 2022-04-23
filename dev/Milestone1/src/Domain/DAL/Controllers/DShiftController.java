package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DShift;
import java.util.HashSet;
import java.util.Set;

public class  DShiftController extends DalController<DShift> {
    // properties
    private DEmployeeController dEmployeeController;
    private DEmployeeShiftController dEmployeeShiftController;
    private DMorningShiftController dMorningShiftController;
    private DEveningShiftController dEveningShiftController;

    // constructor
    public DShiftController() {
        super("placeHolder");
        this.dEmployeeController = new DEmployeeController();
        this.dEmployeeShiftController = new DEmployeeShiftController();
        this.dMorningShiftController = new DMorningShiftController();
        this.dEveningShiftController = new DEveningShiftController();
    }


    // functions
    @Override
    public Set<DShift> loadData() {
        Set<DShift> shifts= new HashSet<>();
        Set<? extends DShift> morningShifts = dMorningShiftController.loadData();
        Set<? extends DShift> eveningShifts = dEveningShiftController.loadData();
        shifts.addAll(morningShifts);
        shifts.addAll(eveningShifts);
        return shifts;
    }

    @Override
    public void deleteAll() {

    }
}

