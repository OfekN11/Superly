package ServiceLayer.Objects;

import BusinessLayer.DiscountsAndSales.SaleToCustomer;

public class Sale {
    private final int percent;
    public Sale(SaleToCustomer s) {
        this.percent = s.getPercent();
    }

    @Override
    public String toString() {
        return "Sale{" +
                "percent=" + percent +
                '}';
    }
}
