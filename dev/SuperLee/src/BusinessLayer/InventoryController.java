package BusinessLayer;

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


    public SaleToCustomer addSale(List<Integer> categoryIDs, List<Integer> productIDs, int percent, Date start, Date end) {
        SaleToCustomer sale = new SaleToCustomer(sales.size(), start, end, percent, categoryIDs, productIDs);
        sales.add(sale);
        for (Integer pID: productIDs) {
            if (products.get(pID)!=null)
                products.get(pID).addSale(sale);
        }
//        for (Integer cID: categoryIDs) {
//            if (categories.get(cID)!=null)
//                //add sale for each product in this categories
//        }
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
    public void ReturnItems(int productID, int storeID, int amount) {
        //find product add amount
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("StoreController: returnProduct: no such product found");
        product.ReturnItems(storeID, amount);
    }

    public void loadData() {

    }

    public Product newProduct(int id, String name, Category category, int weight, double price) {
        return null;
    }

    public void deleteProduct(int id) {
    }
}
