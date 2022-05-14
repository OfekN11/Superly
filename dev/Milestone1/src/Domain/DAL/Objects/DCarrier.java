package Domain.DAL.Objects;

import Domain.Business.BusinessEmployeeFactory;
import Domain.Business.Objects.Employee;
import Domain.DAL.Controllers.EmployeeLinks.CarrierLicensesDAO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DCarrier extends DEmployee{
    private Set<String> licenses;
    private CarrierLicensesDAO carrierLicensesDAO;

    public DCarrier(String id, String name, String bankDetails, int salary, String employmentConditions, Date startingDate, Set<String> licenses) {
        super("Place HOlder", id, name, bankDetails, salary, employmentConditions, startingDate);
        this.licenses = licenses;
        this.carrierLicensesDAO = new CarrierLicensesDAO();
        this.licenses = new HashSet<>();
    }

    @Override
    public void save() {

    }
    @Override
    public Employee accept(BusinessEmployeeFactory businessEmployeeFactory) {
        return businessEmployeeFactory.createBusinessEmployee(this);
    }

    public void setLicenses(Set<String> licenses) {
        this.licenses = licenses;
    }

    public Set<String> getLicenses() {
        return licenses;
    }
}
