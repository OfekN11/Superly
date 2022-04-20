package BusinessLayer;

import java.util.*;
import java.util.function.BiPredicate;

public class SaleToCustomer {
    private int id;
    private Date startDate;
    private Date endDate;
    private double percent;
    private List<Integer> categories;
    private List<Integer> products;

    public SaleToCustomer(int id, Date startDate, Date endDate, double percent, List<Integer> categoriesList, List<Integer> products) {
        if (percent>=100 || percent<=0)
            throw new IllegalArgumentException("SaleToCustomer: Constructor: Illegal percent value");
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.categories = categoriesList;
        this.products = products;
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
    public double getPercent() {
        return percent;
    }
}
