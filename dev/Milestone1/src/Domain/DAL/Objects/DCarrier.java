package Domain.DAL.Objects;

import Domain.DAL.Controllers.DCarrierLicensesController;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DCarrier extends DEmployee{
    private Set<String> licenses;
    private DCarrierLicensesController dCarrierLicensesController;

    public DCarrier(int id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate) {
        super("Place HOlder", id, name, bankDetails, salary, employmentConditions, startingDate);
        this.licenses = licenses;
        this.dCarrierLicensesController = new DCarrierLicensesController();
        this.licenses = new HashSet<>();
    }

    @Override
    public void save() {

    }

    public void setLicenses(Set<String> licenses) {
        this.licenses = licenses;
    }
}
