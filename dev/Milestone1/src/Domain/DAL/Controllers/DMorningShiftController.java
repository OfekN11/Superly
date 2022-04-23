package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DMorningShift;

import java.util.Set;

public class DMorningShiftController extends DalController<DMorningShift> {
    public DMorningShiftController() {
        super("placeHolder");
    }

    @Override
    public Set<DMorningShift> loadData() {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
