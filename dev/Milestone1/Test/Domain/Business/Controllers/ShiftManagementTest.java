package Domain.Business.Controllers;

import Domain.Business.Objects.Shift;
import Globals.Enums.ShiftTypes;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class ShiftManagementTest {
    @org.junit.Test
    public void EditShiftCarriers() {
        ShiftController shiftController = new ShiftController();
        shiftController.loadData();
        try {
            Shift shift = shiftController.getShift(new SimpleDateFormat("dd-MM-yyyy").parse("19-06-1198"), ShiftTypes.Evening);
            Set<String> prevCarrierIDs = shift.getCarrierIDs();
            Set<String> newCarrierIds = new HashSet<>(prevCarrierIDs);
            newCarrierIds.add("28");
            shiftController.editShiftCarrierIDs(new SimpleDateFormat("dd-MM-yyyy").parse("19-06-1198"), ShiftTypes.Evening, newCarrierIds);
            assertNotEquals(prevCarrierIDs, newCarrierIds);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void RegisterWorkDay() {
        ShiftController shiftController = new ShiftController();
        shiftController.loadData();
        try {
            shiftController.createShift(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-1998"),ShiftTypes.Morning,"5",6,6,6,4,4,4);
            assertNotNull(shiftController.getShift(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-1998"),ShiftTypes.Morning));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @org.junit.Test
    public void RemoveWorkDay() {
        ShiftController shiftController = new ShiftController();
        shiftController.loadData();
        try {
            shiftController.createShift(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-1998"),ShiftTypes.Morning,"5",6,6,6,4,4,4);
            shiftController.removeShift(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-1998"),ShiftTypes.Morning);
            shiftController.getShift(new SimpleDateFormat("dd-MM-yyyy").parse("25-07-1998"),ShiftTypes.Morning);
            fail();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

}