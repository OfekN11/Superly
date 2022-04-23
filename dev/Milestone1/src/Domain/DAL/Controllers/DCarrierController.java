package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DalController;
import Domain.DAL.Objects.DCarrier;
import javafx.util.Pair;
import java.sql.Time;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class DCarrierController extends DalController<DCarrier> {
    private DCarrierLicensesController dCarrierLicensesController;

    public DCarrierController() {
        super("tableName");
        dCarrierLicensesController = new DCarrierLicensesController();
    }

    @Override
    public Set<DCarrier> loadData() {
        Set<DCarrier> output = new HashSet<>();
        for (int i=0; i<10; i++)
            output.add(new DCarrier(i,"Carrier "+ i+1,"bd",i,"con" +i,  Time.valueOf("14/07/1998")));
        updateLicenses(output);
        return output;
    }

    private void updateLicenses(Set<DCarrier> dCarriers) {
        Dictionary <Integer,DCarrier> dCarrierDictionary = new Hashtable<>();
        for(DCarrier dCarrier : dCarriers)
            dCarrierDictionary.put(dCarrier.getId(),dCarrier);
        for(Pair<Integer, Set<String>> pair :dCarrierLicensesController.loadData())
            dCarrierDictionary.get(pair.getKey()).setLicenses(pair.getValue());
    }

    @Override
    public void deleteAll() {
    }
}
