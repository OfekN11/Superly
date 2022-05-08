package Domain.Business.Objects;

public abstract class Site {
    private static int incID = 0;
    private int id;
    private Address address;
    private String contactName;
    private String phoneNumber;

    public Site(Address address, String contactName, String phoneNumber) {
        id = incID++;
        this.address = address;
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
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
