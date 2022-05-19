package Domain.Business.Objects.Site;

import Domain.Business.Objects.Site.Source;

public class Destination extends Source {
    public Destination(Address address, String contactName, String phoneNumber) {
        super(address, contactName, phoneNumber);
    }
}
