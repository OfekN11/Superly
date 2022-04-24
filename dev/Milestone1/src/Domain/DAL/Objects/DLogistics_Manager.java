package Domain.DAL.Objects;

import java.util.Date;

public class DLogistics_Manager extends DEmployee{
    public DLogistics_Manager(String id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super("placeHolder", id, name, bankDetails, salary, employmentConditions, startingDate);
    }

    @Override
    public void save() {

    }
}
