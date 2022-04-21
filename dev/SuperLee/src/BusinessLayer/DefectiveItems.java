package BusinessLayer;

import Globals.Defect;

import java.util.Date;

public class DefectiveItems {
    private Date date;
    private int storeID;
    private int productID;
    private int amount;
    private String description;
    private Defect defect;
    public DefectiveItems(Defect defect ,Date date, int storeID, int productID, int amount, String description) {
        this.date = date;
        this.storeID = storeID;
        this.productID = productID;
        this.amount = amount;
        this.description = description;
        this.defect = defect;
    }

    public int getProductID() {
        return productID;
    }

    public Date getDate() {
        return date;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public Defect getDefect() {
        return defect;
    }

    public int getStoreID() {
        return storeID;
    }

    public boolean inDates(Date start, Date end) {
        Date today = new Date();
        return (!(start.after(today) || end.before(today)));
    }
}
