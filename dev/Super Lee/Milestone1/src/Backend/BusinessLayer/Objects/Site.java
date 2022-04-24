package Backend.BusinessLayer.Objects;

abstract public class Site {
    private static int incID = 0;
    private int id;
    private Address address;

    public Site(Address address) {
        id = incID;
        incID++;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
