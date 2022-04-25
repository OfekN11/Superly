package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DTOControllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DMorningShift;
import Domain.Service.Objects.MorningShift;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DMorningShiftController extends DTOControllers<DMorningShift> {
    public DMorningShiftController() {
        super("placeHolder");
    }

    @Override
    public Set<DMorningShift> loadData() {
        Set<DMorningShift> morningShifts = new HashSet<>();
        try {
            morningShifts.add(new DMorningShift(new SimpleDateFormat("dd-MM-yyyy").parse("15-06-2021"),""+6,12,12,12,12,12,12));
            morningShifts.add(new DMorningShift(new SimpleDateFormat("dd-MM-yyyy").parse("18-06-2021"),""+4,12,7,12,12,12,12));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return morningShifts;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void delete(DMorningShift toDelete) {

    }
}
