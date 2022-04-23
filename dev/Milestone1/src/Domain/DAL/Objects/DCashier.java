package Domain.DAL.Objects;

import java.util.Date;

public class DCashier extends DEmployee{
    public DCashier( int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super("Place Holder", id, name, bankDetails, salary, employmentConditions, startingDate);
    }

    @Override
    public void save() {

    }
}
