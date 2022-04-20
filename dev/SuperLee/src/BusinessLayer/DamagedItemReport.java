package BusinessLayer;
import java.util.*;
public class DamagedItemReport {
    private Date date;
    private int storeID;
    private int amount;
    private String description;

    public DamagedItemReport(Date date, int storeID, int amount, String description) {
        this.date = date;
        this.storeID = storeID;
        this. amount = amount;
        this. description = description;
    }

    public int getStoreID() {
        return storeID;
    }

    public boolean inDates(Date start, Date end) {
        Date today = new Date();
        return (!(start.after(today) || end.before(today)));
    }
}
