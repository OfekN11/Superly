package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DEveningShift;

import java.util.Set;

public class DEveningShiftController extends DalController<DEveningShift> {
    public DEveningShiftController() {
        super("Place holder");
    }

    @Override
    public Set<DEveningShift> loadData() {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
