package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DHR_Manager;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

public class DHR_ManagerController extends DalController<DHR_Manager> {
    public DHR_ManagerController() {
        super("tableName");
    }

    @Override
    public Set<DHR_Manager> loadData() {
        Set<DHR_Manager> output = new HashSet<>();
        for (int i=20; i<30; i++)
            output.add(new DHR_Manager(i,"HR_Manager " + (i-19),"bd",i,"con" +i,  Time.valueOf("14/07/1998")));
        return output;
    }

    @Override
    public void deleteAll() {

    }
}
