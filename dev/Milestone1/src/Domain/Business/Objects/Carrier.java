package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;

import java.util.Date;

public class Carrier extends Employee {

    public Carrier(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super(id, name, bankDetails, salary, employmentConditions, startingDate, "Carrier");
    }

    public Carrier(DEmployee dEmployee) {
        super(dEmployee);
    }
}
