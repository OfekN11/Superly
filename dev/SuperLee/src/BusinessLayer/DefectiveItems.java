package BusinessLayer;

import Globals.Defect;

import java.util.Calendar;

import java.util.Date;

public class DefectiveItems {
    private Date date;
    private int storeID;
    private int productID;
    private int amount;
    private int employeeID;
    private String description;
    private Defect defect;

    public DefectiveItems(Defect defect ,Date date, int storeID, int productID, int amount, int employeeID, String description) {
        this.date = date;
        this.storeID = storeID;
        this.productID = productID;
        this.amount = amount;
        this.employeeID = employeeID;
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

    public int getEmployeeID() {
        return employeeID;
    }


    public boolean inDates(Date startDate, Date endDate) {
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();
        endDate.setSeconds(-1);
        return (!(startDate.after(today) || endDate.before(today)));
    }
}
