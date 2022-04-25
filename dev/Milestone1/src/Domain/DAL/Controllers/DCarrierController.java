package Domain.DAL.Controllers;
import Domain.DAL.Abstract.DTOControllers;
import Domain.DAL.Objects.DCarrier;
import Globals.Pair;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

public class DCarrierController extends DTOControllers<DCarrier> {
    private DCarrierLicensesController dCarrierLicensesController;

    public DCarrierController() {
        super("tableName");
        dCarrierLicensesController = new DCarrierLicensesController();
    }

    @Override
    public Set<DCarrier> loadData() {
        Set<DCarrier> output = new HashSet<>();
        for (int i=0; i<10; i++) {
            try {
                Set<String> licenses = new HashSet<>();
                licenses.add(String.valueOf(i));
                output.add(new DCarrier(""+i,"Carrier "+ i+1,"bd",i,"con" +i,  new SimpleDateFormat("dd-MM-yyyy").parse("15-06-1198"), licenses));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        updateLicenses(output);
        return output;
    }

    private void updateLicenses(Set<DCarrier> dCarriers) {
        Dictionary <String,DCarrier> dCarrierDictionary = new Hashtable<>();
        for(DCarrier dCarrier : dCarriers)
            dCarrierDictionary.put(dCarrier.getId(),dCarrier);
        for(Pair<String, Set<String>> pair :dCarrierLicensesController.loadData())
            dCarrierDictionary.get(pair.getLeft()).setLicenses(pair.getRight());
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public void delete(DCarrier toDelete) {

    }
}
