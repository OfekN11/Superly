package ServiceLayer.Objects;

import BusinessLayer.DiscountsAndSales.SaleToCustomer;

import java.util.Date;

public class Sale {
    private final int percent;
    private final Date startDate;
    private final Date endDate;
    public Sale(SaleToCustomer s) {
        this.percent = s.getPercent();
        this.startDate = s.getStartDate();
        this.endDate = s.getEndDate();
    }

    @Override
    public String toString() {
        return "Sale{" +
                "percent=" + percent +
                ", start date=" + startDate +
                ", end date=" + endDate +
                '}';
    }
}
