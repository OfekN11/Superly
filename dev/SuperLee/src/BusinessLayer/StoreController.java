package BusinessLayer;

import java.util.*;

public class StoreController {
    private List<Integer> storeIds;
    private List<Integer> categoryIds;
    private List<SaleToCustomer> sales;
    private List<Product> products;
    private long itemsAmount;
    public StoreController() {
        storeIds = new ArrayList<>();
        categoryIds = new ArrayList<>();
        sales = new ArrayList<>();
        products = new ArrayList<>();
        itemsAmount = 0;
    }

    public void init() {
        //initialize stuff for tests
    }

    public void addSale(List<Integer> categories, List<Integer> products, int percent, Date start, Date end) {
        SaleToCustomer sale = new SaleToCustomer(sales.size(), start, end, percent, categories, products);
        sales.add(sale);
        for (Product p: this.products)
            if (products.contains(p.getId()) || p.inCategory(categories)) //HOPE IT WILL WORK PROPERLY.
                p.addSale(sale);
    }
    public List<DiscountFromSupplier> getDiscountFromSupplierHistory(int productID) {
        return null;
    }

    public List<SaleToCustomer> getSaleHistoryByProduct(int productID) {
        return null;
    }

    public List<SaleToCustomer> getSaleHistoryByCategory(int categoryID) {
        return null;
    }

    public List<Product> getDamagedItems() {
        return null;
    }

    public List<Product> getItems() {
        return null;
    }

    public List<Product> getItemsFromCategory(int categoryID) {
        return null;
    }


    public void removeProduct(int productID, List<Item> items) {
        //find product remove amount
        Product product = null;
        for (Product p: products)
            if (p.getId()==productID) {
                product = p;
                break;
            }
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.RemoveItems(items);
        itemsAmount -= items.size();
    }
    public void move(int productID, List<Item> items) {
        //find product move amount
        Product product = null;
        for (Product p: products)
            if (p.getId()==productID) {
                product = p;
                break;
            }
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.MoveItems(items);
    }
    public void AddItems(int storeID, int productID, Map<Date, Integer> expiryDates) {
        //find product add amount
        Product product = null;
        for (Product p: products)
            if (p.getId()==productID) {
                product = p;
                break;
            }
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        itemsAmount += product.AddItems(storeID, expiryDates, itemsAmount);
    }
    public void returnProduct(int storeID, int productID, Map<Date, Integer> expiryDates) {
        //find product add amount
        Product product = null;
        for (Product p: products)
            if (p.getId()==productID) {
                product = p;
                break;
            }
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        itemsAmount += product.ReturnItems(storeID, expiryDates, itemsAmount);
    }
}
