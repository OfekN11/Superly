package BusinessLayer;

import java.util.*;

public class InventoryController {
    private List<Integer> storeIds;
    private Map<Integer, Category> categories;
    private List<SaleToCustomer> sales;
    private Map<Integer, Product> products;
    private long itemsAmount; //is this ok
    public InventoryController() {
        storeIds = new ArrayList<>();
        categories = new HashMap<>();
        sales = new ArrayList<>();
        products = new HashMap<>();
        itemsAmount = 0;
    }

    public void loadTestData() {
        //initialize stuff for tests
    }

    public void loadData() {

    }

    public void newProduct(int id, String name, Category category, int weight, double price) {
    }

    public void deleteProduct(int id) {
    }

    public void addSale(List<Integer> categories, List<Integer> products, int percent, Date start, Date end) {
        SaleToCustomer sale = new SaleToCustomer(sales.size(), start, end, percent, categories, products);
        sales.add(sale);
        for (Product p: this.products)
            if (products.contains(p.getId()) || p.inCategory(categories)) //HOPE IT WILL WORK PROPERLY.
                p.addSale(sale);
    public void addSale(List<Integer> categories, List<Integer> productsIDs, int percent, Date start, Date end) {
//        SaleToCustomer sale = new SaleToCustomer(sales.size(), start, end, percent, categories, productsIDs);
//        sales.add(sale);
//        for (Integer p: productsIDs)
//            if (products.get(p)!=null)
//                products.get(p).addSale(sale);
//        for (Integer c: categories)
//            if (categories.get(c)!=null)
//                products.get(p).addSale(sale);
//            if (p.inCategory(categories))
//                p.addSale(sale);
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

    public List<Product> getProducts() {
        return null;
    }

    public List<Product> getProductsFromCategory(int categoryID) {
        return null;
    }


    public void RemoveItems(int productID, int storeID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.RemoveItems(storeID, amount);
    }
    public void MoveItems(int productID, int storeID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.MoveItems(storeID, amount);
    }
    public void AddItems(int productID, int storeID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.AddItems(storeID, amount);
    }
    public void returnProduct(int productID, int storeID, int amount) {
        //find product add amount
        Product product = null;
//        for (Product p: products)
//            if (p.getId()==productID) {
//                product = p;
//                break;
//            }
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.ReturnItems(storeID, amount);
    }
}
