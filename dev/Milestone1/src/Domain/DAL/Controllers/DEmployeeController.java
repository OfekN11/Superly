package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DEmployee;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

public class DEmployeeController extends DalController<DEmployee> {
    @Override
    public Set<DEmployee> createFakeDTOs() {
        Set<DEmployee> employees= new HashSet<>();
        for (int i = 0; i < 10; i++)
            employees.add(new DEmployee(i,"number" +i,"i wish i had money",0,"i am free, no conditions",Time.valueOf("14/07/1998"),"Carrier"
            ));

        employees.add(new DEmployee(12,"number" +12,"i wish i had money",0,"i am free, no conditions",Time.valueOf("14/07/1998"),"Cashier"
        ));
        employees.add(new DEmployee(13,"number" +13,"i wish i had money",0,"i am free, no conditions",Time.valueOf("14/07/1998"),"Storekeeper"
        ));
        return employees;
    }
}
