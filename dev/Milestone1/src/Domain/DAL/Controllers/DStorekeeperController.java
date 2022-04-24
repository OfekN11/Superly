package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DSorter;
import Domain.DAL.Objects.DStorekeeper;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DStorekeeperController extends DalController<DStorekeeper> {
    public DStorekeeperController() {
        super("tableName");
    }

    @Override
    public Set<DStorekeeper> loadData() {
        Set<DStorekeeper> output = new HashSet<>();
        for (int i=50; i<60; i++) {
            try {
                output.add(new DStorekeeper(""+i,"HR_Manager " + (i-39),"bd",i,"con" +i,  new SimpleDateFormat("dd-MM-yyyy").parse("15-06-1198")));
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
