package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DalController;
import Globals.Pair;
import java.util.HashSet;
import java.util.Set;

public class DCarrierLicensesController extends DalController<Pair<Integer, Set<String>>> {
    // dict of employeeID and its Licenses
    public DCarrierLicensesController() {
        super("tableName");
    }


    @Override
    public Set<Pair<Integer, Set<String>>> loadData() {
        Set<Pair<Integer, Set<String>>> output = new HashSet<>();
        for(int i=0; i<10; i++){
            Set<String> licenses = new HashSet<>();
            licenses.add("licens "+ i);
            output.add(new Pair<>(i,licenses));
        }
        return output;
    }

    @Override
    public void deleteAll() {

    }
}
