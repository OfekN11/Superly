package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DTOControllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DCashier;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DCashierController extends DTOControllers<DCashier> {

    public DCashierController() {
        super("tableName");
    }

    @Override
    public Set<DCashier> loadData() {
        Set<DCashier> output = new HashSet<>();
        for (int i=10; i<20; i++) {
            try {
                output.add(new DCashier(""+i,"Cashier " + (i-9),"bd",i,"con" +i, new SimpleDateFormat("dd-MM-yyyy").parse("15-06-1198")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    @Override
    public void deleteAll() {

    }
}
