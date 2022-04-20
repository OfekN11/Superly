package Backend.BusinessLayer.Objects;

public class Source extends Site{
    private String contactName;
    private String phoneNumber;

    public Source(Address address, String contactName, String phoneNumber) {
        super(address);
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
    }
}
