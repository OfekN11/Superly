package BusinessLayer;

import java.util.Date;

public class ExpiredItemReport {
    private Date date;
    private int storeID;
    private int amount;

    public ExpiredItemReport(Date date, int storeID, int amount) {
        this.date = date;
        this.storeID = storeID;
        this. amount = amount;
    }

    public int getStoreID() {
        return storeID;
    }

    public boolean inDates(Date start, Date end) {
        Date today = new Date();
        return (!(start.after(today) || end.before(today)));
    }
}
