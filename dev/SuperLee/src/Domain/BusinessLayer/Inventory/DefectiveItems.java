package Domain.BusinessLayer.Inventory;

import Globals.Defect;

import java.util.Calendar;

import java.util.Date;

public class DefectiveItems {
    private int id;
    private Date date;
    private int storeID;
    private int productID;
    private int amount;
    private int employeeID;
    private boolean inWarehouse;
    private String description;
    private Defect defect;

    public DefectiveItems(int id, Defect defect, Date date, int storeID, int productID, int amount, int employeeID, String description, boolean inWarehouse) {
        this.id = id;
        this.date = date;
        this.storeID = storeID;
        this.productID = productID;
        this.amount = amount;
        this.employeeID = employeeID;
        this.inWarehouse = inWarehouse;
        this.description = description;
        this.defect = defect;
    }

    public int getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public int getStoreID() {
        return storeID;
    }
    public int getProductID() {
        return productID;
    }
    public int getAmount() {
        return amount;
    }
    public int getEmployeeID() {
        return employeeID;
    }
    public boolean getInWarehouse() { return inWarehouse; }
    public String getDescription() {
        return description;
    }
    public Defect getDefect() {
        return defect;
    }

    public boolean inDates(Date startDate, Date endDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();
        endDate.setSeconds(-1);
        return (!(startDate.after(date) || endDate.before(date)));
    }
}
