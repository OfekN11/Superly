package Domain.Business.Objects;

import Domain.Business.Controllers.EmployeeController;
import Domain.Business.Controllers.ShiftController;
import Domain.DAL.Controllers.EmployeeMappers.EmployeeDataMapper;
import Domain.DAL.Controllers.ShiftDataMappers.ShiftDataMapper;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class EditShiftWorkersCountTest {
    static LocalDate date=LocalDate.parse("2021-06-19");


    @org.junit.Test
    public void EditCarriersCount() {
        try {
            Shift shift = new MorningShift(date,"206618175",2,2,2,2,2,2,2);
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
            Shift shift = new MorningShift(date,"206618175",2,2,2,2,2,2,2);
            int prev = shift.getCarrierCount();
            shift.setCashierCount(prev+3);
            assertEquals(prev+3, shift.getCashierCount());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}