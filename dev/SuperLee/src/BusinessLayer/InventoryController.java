package BusinessLayer;

import java.util.*;

public class InventoryController {
    private List<Integer> storeIds;
    private List<Integer> categoryIds;
    private List<SaleToCustomer> sales;
    private List<Product> products;
    public InventoryController() {
        storeIds = new ArrayList<>();
        categoryIds = new ArrayList<>();
        sales = new ArrayList<>();
        products = new ArrayList<>();
    }

    public void testInit() {
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
        for (Product p: products)
            if (p.getId()==productID) {
                product = p;
                break;
            }
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.ReturnItems(storeID, amount);
    }

    public void loadData() {

    }

    public void newProduct(int id, String name, Category category, int weight, double price) {
    }

    public void deleteProduct(int id) {
    }
}
