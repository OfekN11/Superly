package Domain.DAL.Controllers;

import Domain.DAL.Objects.DMorningShift;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DMorningShiftController extends DTOControllers<DMorningShift> {
    public DMorningShiftController() {
        super("MorningShifts");
    }

    @Override
    public Set<DMorningShift> loadData() {
        Set<DMorningShift> morningShifts = new HashSet<>();
        try {
            morningShifts.add(new DMorningShift(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-2022"),""+6,12,12,12,12,12,12));
            morningShifts.add(new DMorningShift(new SimpleDateFormat("dd-MM-yyyy").parse("28-07-2022"),""+4,12,7,12,12,12,12));
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
