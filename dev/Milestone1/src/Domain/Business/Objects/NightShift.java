package Domain.Business.Objects;

import Domain.DAL.Objects.DShift;

import java.util.Date;
import java.util.Set;

public  class NightShift extends Shift {
    public NightShift(DShift dShift, Set<Employee> employees) {
        super(dShift,employees);
    }

    public NightShift(Date date,  Employee shiftManager){
        super(date,"Night",shiftManager);
    }
}
