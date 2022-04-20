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
        //add stores
        for (int i = 1; i <= 10; i++)
            storeIds.add(i);
        addCategoriesForTests();
        addProductsForTests();
        addSalesForTests();

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

    public List<Product> getProducts() {
        return null;
    }

    public List<Category> getCategories() {
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

    private void addCategoriesForTests() {
        Category cSmall1 = new Category(1,"Small", new ArrayList<>(), new ArrayList<>());
        categories.put(categories.size()+1, cSmall1);
        Category cSmall2 = new Category(1,"Small", new ArrayList<>(), new ArrayList<>());
        categories.put(categories.size()+1, cSmall1);
        Category cLarge1 = new Category(1,"Large", new ArrayList<>(), new ArrayList<>());
        categories.put(categories.size()+1, cSmall1);
        Category cLarge2 = new Category(1,"Large", new ArrayList<>(), new ArrayList<>());
        categories.put(categories.size()+1, cSmall1);
        Category cMedium = new Category(1,"Medium", new ArrayList<>(), new ArrayList<>());
        categories.put(categories.size()+1, cSmall1);
        Category cShampoo = new Category(1,"Shampoo", new ArrayList<>(), new ArrayList<>());
        categories.put(categories.size()+1, cSmall1);
        Category cToothpaste = new Category(1,"Toothpaste", new ArrayList<>(), new ArrayList<>());
        categories.put(categories.size()+1, cSmall1);
        List<Category> milk = new ArrayList(); milk.add(cSmall1); milk.add(cMedium); milk.add(cLarge1);
        List<Category> yogurt = new ArrayList(); yogurt.add(cSmall2); yogurt.add(cLarge2);
        List<Category> health = new ArrayList(); health.add(cShampoo); health.add(cToothpaste);
        List<Category> dairy = new ArrayList(); dairy.add(new Category(1, "Milk", milk, new ArrayList<>()));
        dairy.add(new Category(1, "Yogurt", yogurt, new ArrayList<>()));
        Category cdairy = new Category(1,"Dairy", dairy, new ArrayList<>());
        categories.put(categories.size()+1, cdairy);
        Category cHealth = new Category(1,"Health,", health, new ArrayList<>());
        categories.put(categories.size()+1, cHealth);
        Category cOrg1 = new Category(1,"Organic", new ArrayList<>(), new ArrayList<>());
        categories.put(categories.size()+1, cOrg1);
        Category cOrg2 = new Category(1,"Organic", new ArrayList<>(), new ArrayList<>());
        categories.put(categories.size()+1, cOrg2);
        List<Category> veggies = new ArrayList(); veggies.add(cOrg1);
        List<Category> fruits = new ArrayList(); fruits.add(cOrg2);
        Category cVeggie = new Category(1,"Vegetables", veggies, new ArrayList<>());
        categories.put(categories.size()+1, cVeggie);
        Category cFruit = new Category(1,"Fruits", veggies, new ArrayList<>());
        categories.put(categories.size()+1, cFruit);
        List<Category> produce = new ArrayList(); produce.add(cFruit); produce.add(cVeggie);
        Category cProduce = new Category(1,"Produce", produce, new ArrayList<>());
        categories.put(categories.size()+1, cProduce);
    }

    private void addProductsForTests() {
    }

    private void addSalesForTests() {
    }

}
