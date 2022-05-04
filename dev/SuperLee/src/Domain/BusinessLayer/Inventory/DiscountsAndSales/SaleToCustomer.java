package Domain.BusinessLayer.Inventory.DiscountsAndSales;

import java.util.*;

public class SaleToCustomer {

    private int id;
    private Date startDate;
    private Date endDate;
    private int percent;
    private List<Integer> categoryIDs;
    private List<Integer> productIDs;

    public SaleToCustomer(int id, Date startDate, Date endDate, int percent, List<Integer> categoriesList, List<Integer> products) {
        if (percent>=100 || percent<=0)
            throw new IllegalArgumentException("Percent sale must be between 1 and 99. Received: " + percent);
        if (endDate.before(startDate))
            throw new IllegalArgumentException("SaleToCustomer: Constructor: end date is before the start date");
        this.id = id;
        this.percent = percent;
        this.categoryIDs = categoriesList;
        this.productIDs = products;
        this.startDate = startDate;
        //add 1 day to the endDate in order to include the endDate's day in the sale.
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();
        endDate.setSeconds(-1);
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getCategories() {
        return categoryIDs;
    }

    public List<Integer> getProducts() {
        return productIDs;
    }
    public Date getStartDate() {
        return startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public int getPercent() {
        return percent;
    }
    //"new Date()" returns the current date.
    public boolean isUpcoming() {
        return startDate.after(new Date());
    }
    public boolean isPassed() {
        return endDate.before(new Date());
    }
    public boolean isActive() {
        return !(isUpcoming() || isPassed());
    }
    public boolean wasActive(Date dateBought) { return !(startDate.after(dateBought) || endDate.before(dateBought)); }
}
