package Domain.DAL.Objects;

import java.util.Date;
import java.util.Set;

public class DMorningShift extends DShift{
    public DMorningShift(Date date, String type, Set<Integer> workers, int shiftManagerId) {
        super("PlaceHolder", date, type, workers, shiftManagerId);
    }

    @Override
    public void save() {

    }
}
