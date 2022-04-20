package BusinessLayer;

import ServiceLayer.Objects.Sale;
import java.util.*;
import java.lang.Object.*;

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
        //add stores
        for (int i = 1; i <= 10; i++)
            storeIds.add(i);
        addCategoriesForTests();
        addProductsForTests();
        addSalesForTests();
    }

    public SaleToCustomer addSale(List<Integer> categoriesList, List<Integer> productIDs, int percent, Date start, Date end) {
        SaleToCustomer sale = new SaleToCustomer(sales.size(), start, end, percent, categoriesList, productIDs);
        sales.add(sale);
        Product product = null;
        for (Integer pID: productIDs) {
            product = products.get(pID);
            if (product!=null)
                product.addSale(sale);
        }
        Category category = null;
        for (Integer cID: categoriesList) {
            category = categories.get(cID);
            if (category!=null)
                category.addSale(sale);        }
        return sale;
    }

    public List<DiscountFromSupplier> getDiscountFromSupplierHistory(int productID) {
        return null;
    }

    public List<SaleToCustomer> getSaleHistoryByProduct(int productID) {
        return products.get(productID).getSaleHistory();
    }

    public List<SaleToCustomer> getSaleHistoryByCategory(int categoryID) {
        return categories.get(categoryID).getSaleHistory();
    }

    public List<Product> getDamagedItems() {
        return null;
    }

    public Collection<Product> getProducts() {
        return products.values();
    }

    public Collection<Category> getCategories() {
        return categories.values();
    }

    public List<Product> getProductsFromCategory(int categoryID) {
        return categories.get(categoryID).getProducts();
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

    public Product newProduct(String name, int categoryID, int weight, double price, List<Supplier> suppliers) {
        Category category = categories.get(categoryID);
        int id = products.size() + 1;
        Product product = new Product(id, name, category, weight, price, suppliers);
        products.put(id, product);
        return product;
    }

    public void deleteProduct(int id){
        products.remove(id);
        //remove sales? remove empty categories?
    }

    public void reportDamaged(int storeID, int productID, int amount, String description) {

    }

    public void reportExpired(int storeID, int productID, int amount) {

    }


    private void addCategoriesForTests () {
        Category cSmall1 = new Category(1, "Small", new HashSet<>(), new ArrayList<>(), null);
        categories.put(categories.size() + 1, cSmall1);
        Category cSmall2 = new Category(1, "Small", new HashSet<>(), new ArrayList<>(), null);
        categories.put(categories.size() + 1, cSmall2);
        Category cLarge1 = new Category(1, "Large", new HashSet<>(), new ArrayList<>(), null);
        categories.put(categories.size() + 1, cLarge1);
        Category cLarge2 = new Category(1, "Large", new HashSet<>(), new ArrayList<>(), null);
        categories.put(categories.size() + 1, cLarge2);
        Category cMedium = new Category(1, "Medium", new HashSet<>(), new ArrayList<>(), null);
        categories.put(categories.size() + 1, cMedium);
        Category cShampoo = new Category(1, "Shampoo", new HashSet<>(), new ArrayList<>(), null);
        Set<Category> milk = new HashSet<>();
        milk.add(cSmall1);
        milk.add(cMedium);
        milk.add(cLarge1);
        Set<Category> yogurt = new HashSet<>();
        yogurt.add(cSmall2);
        yogurt.add(cLarge2);
        Set<Category> dairy = new HashSet<>();
        Category cMilk = new Category(1, "Milk", milk, new ArrayList<>(), null);
        Category cYogurt = new Category(1, "Yogurt", yogurt, new ArrayList<>(), null);
        dairy.add(cMilk);
        dairy.add(cYogurt);
        Category cdairy = new Category(1, "Dairy", dairy, new ArrayList<>(), null);
        categories.put(categories.size() + 1, cdairy);
        categories.put(categories.size() + 1, cShampoo);
        Category cToothpaste = new Category(1, "Toothpaste", new HashSet<>(), new ArrayList<>(), null);
        categories.put(categories.size() + 1, cToothpaste);
        Set<Category> health = new HashSet<>();
        health.add(cShampoo);
        health.add(cToothpaste);
        Category cHealth = new Category(1, "Health,", health, new ArrayList<>(), null);
        categories.put(categories.size() + 1, cHealth);
        Category cOrg1 = new Category(1, "Organic", new HashSet<>(), new ArrayList<>(), null);
        categories.put(categories.size() + 1, cOrg1);
        Category cOrg2 = new Category(1, "Organic", new HashSet<>(), new ArrayList<>(), null);
        categories.put(categories.size() + 1, cOrg2);
        Set<Category> veggies = new HashSet();
        veggies.add(cOrg1);
        Set<Category> fruits = new HashSet();
        fruits.add(cOrg2);
        Category cVeggie = new Category(1, "Vegetables", veggies, new ArrayList<>(), null);
        //12
        categories.put(categories.size() + 1, cVeggie);
        Category cFruit = new Category(1, "Fruits", veggies, new ArrayList<>(), null);
        categories.put(categories.size() + 1, cFruit);
        Set<Category> produce = new HashSet<>();
        produce.add(cFruit);
        produce.add(cVeggie);
        Category cProduce = new Category(1, "Produce", produce, new ArrayList<>(), null);
        categories.put(categories.size() + 1, cProduce);
    }

    private void addProductsForTests () {
        products.put(products.size()+1, new Product(products.size()+1, "tomato", categories.get(12), -1, 7.2, new ArrayList<>()));
        products.put(products.size()+1, new Product(products.size()+1, "tomato", categories.get(10), -1, 9.2, new ArrayList<>()));
        products.put(products.size()+1, new Product(products.size()+1, "strawberry", categories.get(11), -1, 7.2, new ArrayList<>()));
        products.put(products.size()+1, new Product(products.size()+1, "melon", categories.get(13), -1, 7.2, new ArrayList<>()));
        products.put(products.size()+1, new Product(products.size()+1, "Hawaii", categories.get(6), 1.2, 13, new ArrayList<>()));
        products.put(products.size()+1, new Product(products.size()+1, "Crest", categories.get(9), 0.7, 7.2, new ArrayList<>()));
        products.put(products.size()+1, new Product(products.size()+1, "Tara 1L", categories.get(5), 1.2, 8.6, new ArrayList<>()));
        products.put(products.size()+1, new Product(products.size()+1, "Tnuva 1L", categories.get(5), 1.2, 8, new ArrayList<>()));
        products.put(products.size()+1, new Product(products.size()+1, "yoplait strawberry", categories.get(2), 0.5, 5.3, new ArrayList<>()));
        products.put(products.size()+1, new Product(products.size()+1, "yoplait vanilla", categories.get(2), 0.5, 5.3, new ArrayList<>()));

    }

    private void addSalesForTests () {
    }

    public List<Integer> getStoreIDs() {
        return storeIds;
    }

    public Double buyItems(int productID, int storeID, int amount) {
        double price = products.get(productID).getCurrentPrice()*amount;
        removeItems(productID, storeID, amount);
        return price;
    }
}
