package Domain.DAL.Objects;

import java.util.Date;

public class DHR_Manager extends DEmployee{
    public DHR_Manager( String id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super("PlaceHolder", id, name, bankDetails, salary, employmentConditions, startingDate);
    }

    @Override
    public void save() {

    }
}
