package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;

import java.util.Date;

public class Storekeeper extends Employee {

    public Storekeeper(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, "Storekeeper");
    }

    public Storekeeper(DEmployee dEmployee) {
        super(dEmployee);
    }
}
