package Domain.Business.Objects;

import Domain.DAL.Objects.DEmployee;
import Domain.DAL.Objects.DShift;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public abstract class Shift {
    // properties
    Date date;
    Set<Carrier> carriers;
    Set<Cashier> cashiers;
    Set<Storekeeper> storekeepers;
    DShift dShift; // represent of this object in the DAL

    public Shift(Date date, Set<Carrier> carriers, Set<Cashier> cashiers, Set<Storekeeper> storekeepers, String type) {
        this.date = date;
        this.carriers = carriers;
        this.cashiers = cashiers;
        this.storekeepers = storekeepers;
        Set<DEmployee> dEmployees = new HashSet<>();

        for(Employee employee: carriers)
            dEmployees.add(employee.getdEmployee());
        for(Employee employee: cashiers)
            dEmployees.add(employee.getdEmployee());
        for(Employee employee: storekeepers)
            dEmployees.add(employee.getdEmployee());
        this.dShift = new DShift(date,type,dEmployees);
    }

    public Shift(DShift dShift,Set<Carrier> carriers, Set<Cashier> cashiers, Set<Storekeeper> storekeepers) {
        this.dShift = dShift;
        this.date = dShift.date;
        this.carriers = carriers;
        this.cashiers =cashiers;
        this.storekeepers =storekeepers;
    }
}
