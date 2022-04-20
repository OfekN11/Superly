package Backend.BusinessLayer.Objects;

abstract public class Site {
    private Address address;

    public Site(Address address) {
        this.address = address;
    }
}
