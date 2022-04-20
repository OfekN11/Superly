package Backend.BusinessLayer.Objects;

import Backend.Globals.Enums.LicenseTypes;

public class Driver {
    private String name;
    private LicenseTypes licenseTypes;

    public Driver(String name, LicenseTypes licenseTypes) {
        this.name = name;
        this.licenseTypes = licenseTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LicenseTypes getLicenseTypes() {
        return licenseTypes;
    }

    public void setLicenseTypes(LicenseTypes licenseTypes) {
        this.licenseTypes = licenseTypes;
    }
}
