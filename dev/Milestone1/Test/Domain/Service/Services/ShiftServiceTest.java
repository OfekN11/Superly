package Domain.Service.Services;

import Domain.Service.Objects.Result;
import Domain.Service.Objects.Shift;
import Globals.Enums.ShiftTypes;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ShiftServiceTest {

    @Test
    public void shiftManagerTest() {
        ShiftService shiftService = new ShiftService();
        shiftService.loadData();
        try {
            assertTrue(shiftService.createShift(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-1998"), ShiftTypes.Morning,"5",6,6,6,4,4,4).isOk());
            Result<Shift> result = shiftService.getShift(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-1998"), ShiftTypes.Morning);
            if (result.isError())
                fail(result.getError());
            Shift shift = result.getValue();
            assertTrue(shift.cashierIDs.size()>0);
            assertNotNull(shift.shiftManagerId);
            assertNotEquals("",shift.shiftManagerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createAndManageShift() {
        ShiftService shiftService = new ShiftService();
        try {
            if (shiftService.createShift(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-2021"), ShiftTypes.Morning,"5",6,6,6,4,4,4).isError())
                fail();
            Set<String> ids = new HashSet<>();
            ids.add("6");
            ids.add("20");
            if(shiftService.editShiftCarrierIDs(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-2021"),ShiftTypes.Morning,ids).isError())
                fail();

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}