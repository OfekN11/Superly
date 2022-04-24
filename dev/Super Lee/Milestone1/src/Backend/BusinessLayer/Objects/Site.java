package Backend.BusinessLayer.Objects;

abstract public class Site {
    private Address address;

    public Site(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
