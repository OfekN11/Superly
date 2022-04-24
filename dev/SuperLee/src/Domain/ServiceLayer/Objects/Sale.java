package Domain.ServiceLayer.Objects;

import Domain.BusinessLayer.DiscountsAndSales.SaleToCustomer;

import java.util.Date;
import java.util.List;

public class Sale {
    private final int id;
    private final int percent;
    private final Date startDate;
    private final Date endDate;
    private final List<Integer> categories;
    private final List<Integer> products;
    public Sale(SaleToCustomer s) {
        this.id = s.getId();
        this.percent = s.getPercent();
        this.startDate = s.getStartDate();
        this.endDate = s.getEndDate();
        this.categories = s.getCategories();
        this.products = s.getProducts();
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", percent=" + percent +
                ", start date=" + startDate +
                ", end date=" + endDate +
                ", categories: " + categories.toString() +
                ", products: " + products.toString() +
                '}';
    }
}
