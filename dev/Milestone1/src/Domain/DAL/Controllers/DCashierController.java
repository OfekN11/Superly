package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DCashier;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

public class DCashierController extends DalController<DCashier> {

    public DCashierController() {
        super("tableName");
    }

    @Override
    public Set<DCashier> loadData() {
        Set<DCashier> output = new HashSet<>();
        for (int i=10; i<20; i++)
            output.add(new DCashier(i,"Cashier " + (i-9),"bd",i,"con" +i,  Time.valueOf("14/07/1998")));
        return output;
    }

    @Override
    public void deleteAll() {

    }
}
