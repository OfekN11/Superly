package Domain.DAL.Controllers;

import Domain.DAL.Objects.DSorter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DSorterController extends DTOControllers<DSorter> {
    public DSorterController() {
        super("tableName");
    }

    @Override
    public Set<DSorter> loadData() {
        Set<DSorter> output = new HashSet<>();
        for (int i=40; i<50; i++) {
            try {
                output.add(new DSorter(""+i,"Sorter " + i,"bd",i,"con" +i,  new SimpleDateFormat("dd-MM-yyyy").parse("15-06-1198")));
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
    public void delete(DSorter toDelete) {

    }
}
