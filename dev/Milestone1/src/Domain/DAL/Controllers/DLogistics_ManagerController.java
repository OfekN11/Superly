package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DLogistics_Manager;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

public class DLogistics_ManagerController extends DalController<DLogistics_Manager> {
    public DLogistics_ManagerController() {
        super("tableName");
    }

    @Override
    public Set<DLogistics_Manager> loadData() {
        Set<DLogistics_Manager> output = new HashSet<>();
        for (int i=30; i<40; i++)
            output.add(new DLogistics_Manager(i,"HR_Manager " + (i-29),"bd",i,"con" +i,  Time.valueOf("14/07/1998")));
        return output;
    }

    @Override
    public void deleteAll() {

    }
}
