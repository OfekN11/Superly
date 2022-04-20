package BusinessLayer;

import java.util.*;

public class InventoryController {
    private List<Integer> storeIds;
    private List<Integer> categoryIds;
    private List<SaleToCustomer> sales;
    private Map<Integer, Product> products;
    private long itemsAmount;
    public InventoryController() {
        storeIds = new ArrayList<>();
        categoryIds = new ArrayList<>();
        sales = new ArrayList<>();
        products = new HashMap<>();
        itemsAmount = 0;
    }

    public void testInit() {
        //initialize stuff for tests
    }


    public void addSale(List<Integer> categories, List<Integer> productsIDs, int percent, Date start, Date end) {
        SaleToCustomer sale = new SaleToCustomer(sales.size(), start, end, percent, categories, productsIDs);
        sales.add(sale);
        for (Integer p: productsIDs)
            if (products.get(p)!=null)
                products.get(p).addSale(sale);
        for (Integer c: categories)
            if (p.inCategory(categories))
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

    public void loadData() {

    }

    public void newProduct(int id, String name, Category category, int weight, double price) {
    }

    public void deleteProduct(int id) {
    }
}
