package Domain.DAL.Controllers.ShiftDataMappers;


import Domain.Business.Objects.Shift.EveningShift;
import Domain.Business.Objects.Shift.MorningShift;
import Domain.Business.Objects.Shift.Shift;
import Globals.Enums.ShiftTypes;

import java.sql.SQLException;
import java.time.LocalDate;

public class ShiftDataMapper {
    // properties
    private MorningShiftDataMapper morningShiftDataMapper;
    private EveningShiftDataMapper eveningShiftDataMapper;

    // constructor
    public ShiftDataMapper() {
        this.morningShiftDataMapper = new MorningShiftDataMapper();
        this.eveningShiftDataMapper = new EveningShiftDataMapper();
    }


    // functions
   public Shift get(LocalDate date, ShiftTypes shiftType){
       try {
            switch (shiftType){
                case Morning:
                    return morningShiftDataMapper.get(date.toString()+shiftType.toString());
                case Evening:
                    return eveningShiftDataMapper.get(date.toString()+shiftType.toString());
                default:
                    throw new IllegalArgumentException("no case for this ShiftType");
            }
        } catch (Exception e) {
           e.printStackTrace();
           throw new RuntimeException("FATAL ERROR WITH DB CONNECTION. STOP WORK IMMEDIATELY!");
       }
   }



   public void save(Shift shift) throws SQLException {
       shift.save(this);
   }

   public void save(MorningShift morningShift) throws SQLException {
        morningShiftDataMapper.save(morningShift.getWorkday()+ShiftTypes.Morning.toString(),morningShift);
   }

    public void save(EveningShift eveningShift) throws SQLException {
        eveningShiftDataMapper.save(eveningShift.getWorkday()+ShiftTypes.Evening.toString(),eveningShift);
    }

    public void update(Shift shift) throws SQLException {
        shift.update(this);
    }

    public void update(MorningShift shift) throws SQLException {
        morningShiftDataMapper.insert(shift);
    }

    public void update(EveningShift shift) throws SQLException {
        eveningShiftDataMapper.insert(shift);
    }
}

