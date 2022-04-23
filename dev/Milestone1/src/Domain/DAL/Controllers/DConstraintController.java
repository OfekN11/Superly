package Domain.DAL.Controllers;

import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DConstraint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class DConstraintController extends DalController<DConstraint> {

    // properties


    //constructor
    public DConstraintController() {
        super("placeHolder");
    }

    //functions
    @Override
    public Set<DConstraint> loadData() {
        try {
            Set<DConstraint> output = new HashSet<>();
            output.add(new DConstraint(new SimpleDateFormat("dd-MM-yyyy").parse("14-07-2012"),"Day",10));
            return output;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
