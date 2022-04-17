package BusinessLayer;

import java.util.Date;
import java.util.Dictionary;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private Dictionary<Location, Integer> minAmount;
    private Dictionary<Location, Integer> inStock;
    private Dictionary<Location, Integer> maxAmount;
    private int weight;
    private List<Supplier> suppliers;
    private double price;
    private List<SaleToCustomer> discountHistory;
    private Date expiration;
}
