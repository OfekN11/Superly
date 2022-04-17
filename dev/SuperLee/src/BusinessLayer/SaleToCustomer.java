package BusinessLayer;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class SaleToCustomer {
    private int id;
    private Date startDate;
    private Date endDate;
    private int percent;
    private List<Integer> categories;
    private List<Integer> products;

    public SaleToCustomer(int id, Date startDate, Date endDate, int percent, List<Integer> categories, List<Integer> products) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.percent = percent;
        this.categories = categories;
        this.products = products;
    }
}
