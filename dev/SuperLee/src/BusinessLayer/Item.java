package BusinessLayer;

import java.util.Date;

public class Item {
    public Item(Location location, String name, Date expiryDate) {
        this.location = location;
        this.name = name;
        this.expiryDate = expiryDate;
    }

    private Location location;
    private String name; //Do we need this field?
    private final Date expiryDate;

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) { //Move from warehouse to store
        this.location = location;
    }
    public Date getExpiryDate() {
        return expiryDate;
    }

}
