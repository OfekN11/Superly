package BusinessLayer;

import java.util.Date;

public class DefectiveItems {
    private Date date;
    private int storeID;
    private int amount;
    private String description;
    private Defect defect;
    enum Defect {Expired, Damaged};
    public DefectiveItems(Defect defect ,Date date, int storeID, int amount, String description) {
        this.date = date;
        this.storeID = storeID;
        this.amount = amount;
        this.description = description;
        this.defect = defect;
    }

    public int getStoreID() {
        return storeID;
    }

    public boolean inDates(Date start, Date end) {
        Date today = new Date();
        return (!(start.after(today) || end.before(today)));
    }
}
