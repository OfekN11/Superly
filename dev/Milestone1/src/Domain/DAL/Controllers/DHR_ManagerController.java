package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DTOControllers;
import Domain.DAL.Objects.DHR_Manager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DHR_ManagerController extends DTOControllers<DHR_Manager> {
    public DHR_ManagerController() {
        super("tableName");
    }

    @Override
    public Set<DHR_Manager> loadData() {
        Set<DHR_Manager> output = new HashSet<>();
        for (int i=20; i<30; i++) {
            try {
                output.add(new DHR_Manager(""+i,"HR_Manager " + i,"bd",i,"con" +i,  new SimpleDateFormat("dd-MM-yyyy").parse("25-07-2022")));
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
    public void delete(DHR_Manager toDelete) {

    }
}
