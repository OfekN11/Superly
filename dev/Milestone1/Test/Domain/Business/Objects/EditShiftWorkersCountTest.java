package Domain.Business.Objects;

import Domain.Business.Controllers.ShiftController;
import Globals.Enums.ShiftTypes;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class EditShiftWorkersCountTest {
    @org.junit.Test
    public void EditCarriersCount() {
        ShiftController shiftController = new ShiftController();
        shiftController.loadData();
        try {
            Shift shift = shiftController.getShift(new SimpleDateFormat("dd-MM-yyyy").parse("19-06-1198"), ShiftTypes.Evening);
            int prev = shift.getCarrierCount();
            shift.setCarrierCount(prev+3);
            assertEquals(prev+3, shift.getCarrierCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EditCashiersCount() {
        ShiftController shiftController = new ShiftController();
        shiftController.loadData();
        try {
            Shift shift = shiftController.getShift(new SimpleDateFormat("dd-MM-yyyy").parse("19-06-1198"), ShiftTypes.Evening);
            int prev = shift.getCarrierCount();
            shift.setCashierCount(prev+3);
            assertEquals(prev+3, shift.getCashierCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}