package Domain.DAL.Controllers.ShiftDataMappers;
import Domain.Business.Objects.EveningShift;
import Domain.Business.Objects.MorningShift;
import Domain.Business.Objects.Shift;
import Globals.Enums.ShiftTypes;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Set;

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

    //TODO
    //should delete shift with this key
    public void delete(LocalDate date, ShiftTypes type) {
    }

    //should return all shifts (of any type) between date start and date end (inclusive)
    public Set<Shift> getBetween(LocalDate start, LocalDate end) {
    }

    //should return all shifts of type 'type' between date start and date end (inclusive)
    public Object getBetween(LocalDate left, LocalDate left1, ShiftTypes type) {
    }
}

