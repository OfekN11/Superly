package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DLogistics_Manager;
import Domain.DAL.Objects.DSorter;

import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

public class DSorterController extends DalController<DSorter> {
    public DSorterController() {
        super("tableName");
    }

    @Override
    public Set<DSorter> loadData() {
        Set<DSorter> output = new HashSet<>();
        for (int i=40; i<50; i++)
            output.add(new DSorter(i,"HR_Manager " + (i-39),"bd",i,"con" +i,  Time.valueOf("14/07/1998")));
        return output;
    }

    @Override
    public void deleteAll() {

    }
}
