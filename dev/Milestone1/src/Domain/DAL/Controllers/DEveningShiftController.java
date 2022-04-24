package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DTOControllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DEveningShift;
import Domain.DAL.Objects.DMorningShift;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DEveningShiftController extends DTOControllers<DEveningShift> {
    public DEveningShiftController() {
        super("Place holder");
    }

    @Override
    public Set<DEveningShift> loadData() {
        Set<DEveningShift> morningShifts = new HashSet<>();
        try {
            morningShifts.add(new DEveningShift(new SimpleDateFormat("dd-MM-yyyy").parse("19-06-1198"),""+8,15,12,12,12,12,12));
            morningShifts.add(new DEveningShift(new SimpleDateFormat("dd-MM-yyyy").parse("23-07-1198"),""+2,12,7,12,12,12,12));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return morningShifts;

    }

    @Override
    public void deleteAll() {

    }
}
