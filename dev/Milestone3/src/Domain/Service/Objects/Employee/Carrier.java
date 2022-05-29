package Domain.Service.Objects.Employee;

import Globals.Enums.JobTitles;
import Globals.Enums.LicenseTypes;
import Presentation.Screens.ScreenEmployeeFactory;

import java.util.Collections;
import java.util.Set;

/**
 * Service model of the Carrier
 */
public class Carrier extends Employee {
    public final Set<LicenseTypes> licenses;

    public Carrier(Domain.Business.Objects.Employee.Carrier bCarrier){
        super(bCarrier);
        licenses = Collections.unmodifiableSet(bCarrier.getLicenses());
    }

    @Override
    public Presentation.Screens.Employee accept(ScreenEmployeeFactory screenEmployeeFactory) {
        return screenEmployeeFactory.createScreenEmployee(this);
    }

    @Override
    public JobTitles getType() {
        return JobTitles.Carrier;
    }
}
