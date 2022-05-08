package Domain.DAL.Controllers;
import Domain.DAL.Objects.DShift;

import java.util.*;

public class  DShiftController extends DTOControllers<DShift> {
    // properties
    private EmployeeShiftMapper employeeShiftMapper;
    private DMorningShiftController dMorningShiftController;
    private DEveningShiftController dEveningShiftController;

    // constructor
    public DShiftController() {
        super("placeHolder");
        this.employeeShiftMapper = new EmployeeShiftMapper();
        this.dMorningShiftController = new DMorningShiftController();
        this.dEveningShiftController = new DEveningShiftController();
    }


    // functions
    @Override
    public Set<DShift> loadData() {
        Set<DShift> shifts= new HashSet<>();
        Set<? extends DShift> morningShifts = dMorningShiftController.loadData();
        Set<? extends DShift> eveningShifts = dEveningShiftController.loadData();
        shifts.addAll(morningShifts);
        shifts.addAll(eveningShifts);
        loadEmployees(morningShifts);
        loadEmployees(eveningShifts);
        persistAll(shifts);
        return shifts;
    }

    private void loadEmployees(Set<? extends DShift> shifts) {
        Set<String > carriers = new HashSet<>();
        Set<String > cashier = new HashSet<>();
        Set<String > hrManager = new HashSet<>();
        Set<String > logistic = new HashSet<>();
        Set<String > sorters = new HashSet<>();
        Set<String > storekeepers = new HashSet<>();
        carriers.add("2");
        carriers.add("4");
        carriers.add("6");
        carriers.add("8");
        for(int i=0; i<2;i++){
            carriers.add(""+i);
            cashier.add(""+(i+10));
            hrManager.add(""+(i+20));
            logistic.add(""+(i+30));
            sorters.add(""+(i+40));
            storekeepers.add(""+(i+50));
        }
        for (DShift shift : shifts){
            shift.setCarrierIDs(carriers);
            shift.setCashierIDs(cashier);
            shift.setHr_managerIDs(hrManager);
            shift.setLogistics_managerIDs(logistic);
            shift.setSorterIDs(sorters);
            shift.setStorekeeperIDs(storekeepers);
        }
        // here should be a code to get from each typeShiftController
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void delete(DShift toDelete) {

    }
}

