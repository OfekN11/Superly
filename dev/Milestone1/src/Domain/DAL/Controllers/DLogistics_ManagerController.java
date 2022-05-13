package Domain.DAL.Controllers;
import Domain.DAL.Objects.DLogistics_Manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DLogistics_ManagerController extends DTOControllers<DLogistics_Manager> {
    public DLogistics_ManagerController() {
        super("Logistics_Manager");
    }

    @Override
    public Set<DLogistics_Manager> loadData() {
        Set<DLogistics_Manager> output = new HashSet<>();
        for (int i=30; i<40; i++) {
            try {
                output.add(new DLogistics_Manager(""+i,"Logistics_Manager " + i,"bd",i,"con" +i,  new SimpleDateFormat("dd-MM-yyyy").parse("25-07-2022")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void delete(DLogistics_Manager toDelete) {

    }
}
