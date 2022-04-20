package BusinessLayer;

import ServiceLayer.Objects.Sale;

import java.util.*;

public class InventoryController {
    private List<Integer> storeIds;
    private Map<Integer, Category> categories;
    private List<SaleToCustomer> sales;
    private Map<Integer, Product> products;
    public InventoryController() {
        storeIds = new ArrayList<>();
        categories = new HashMap<>();
        sales = new ArrayList<>();
        products = new HashMap<>();
    }

    public void loadTestData() {
        //initialize stuff for tests
    }


    public SaleToCustomer addSale(List<Category> categoriesList, List<Integer> productIDs, int percent, Date start, Date end) {
        SaleToCustomer sale = new SaleToCustomer(sales.size(), start, end, percent, categoriesList, productIDs);
        sales.add(sale);
        for (Category c: categoriesList) {
            if (c!=null)
                productIDs = c.findProductsIDs(productIDs);
        }
        Product product = null;
        for (Integer pID: productIDs) {
            product = products.get(pID);
            if (product!=null)
                product.addSale(sale);
        }
        for (Integer pID: productIDs) {
            if (products.get(pID)!=null)
                products.get(pID).addSale(sale);
        }
        return sale;
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

    public void removeItems(int productID, int storeID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.removeItems(storeID, amount);
    }
    public void moveItems(int productID, int storeID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.moveItems(storeID, amount);
    }
    public void addItems(int productID, int storeID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.addItems(storeID, amount);
    }
    public void ReturnItems(int productID, int storeID, int amount) {
        //find product add amount
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.returnItems(storeID, amount);
    }

    public void loadData() {

    }

    public Product newProduct(int id, String name, Category category, int weight, double price, List<Supplier> suppliers) {
        List<SaleToCustomer> salesToCustomers = findProductSales(category);
        Product product = new Product(id, name, category, weight, price, suppliers, sales);
        products.put(id, product);
        return product;
    }
    private List<SaleToCustomer> findProductSales(Category category) {
        List<SaleToCustomer> salesToCustomers = new ArrayList<>();
        for (SaleToCustomer sale: sales) {
            if (!sale.isPassed() && sale.appliedForProduct(category))
                salesToCustomers.add(sale);
        }
        return salesToCustomers;
    }
    public void deleteProduct(int id) {
        products.remove(id);
        //remove sales? remove empty categories?
    }
}
