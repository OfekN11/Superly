package BusinessLayer;

import java.util.*;

public class Price {
    private double originalPrice;
    private List<SaleToCustomer> sales;

    public Price(double originalPrice, List<SaleToCustomer> sales) {
        this.originalPrice = originalPrice;
        this.sales = new ArrayList<>(sales);
    }
    public void addSale(SaleToCustomer sale) {
        sales.add(sale);
    }
    public double getOriginalPrice() {
        return originalPrice;
    }
    public double getCurrentPrice() {
        SaleToCustomer sale = findCurrentSale();
        if (sale==null)
            return originalPrice;
        else
            return originalPrice*sale.getPercent()/100;
    }
    private SaleToCustomer findCurrentSale() { //returns active sale with the highest percentage.
        SaleToCustomer currentSale = null;
        for (SaleToCustomer sale: sales)
            if ((sale.isActive() && currentSale==null) || (sale.isActive() && currentSale.getPercent()<sale.getPercent()))
                currentSale = sale;
        return currentSale;
    }
}
