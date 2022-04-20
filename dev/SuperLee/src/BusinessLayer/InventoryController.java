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
        return null;
    }

    public List<SaleToCustomer> getSaleHistoryByCategory(int categoryID) {
        return null;
    }

    public List<Product> getDamagedItems() {
        return null;
    }

    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<>();
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            productList.add(entry.getValue());
        }
        return productList;
    }

    public Collection<Category> getCategories() {
        return categories.values();
    }

    public List<Product> getItemsFromCategory(int categoryID) {
        return null;
    }

    public void removeItems(int productID, int storeID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("InventoryController: removeProduct: no such product found");
        product.removeItems(storeID, amount);
    }
    public void moveItems(int productID, int storeID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("InventoryController: moveItems: no such product found");
        product.moveItems(storeID, amount);
    }
    public void addItems(int productID, int storeID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("InventoryController: addItems: no such product found");
        product.addItems(storeID, amount);
    }
    public void returnItems(int productID, int storeID, int amount) {
        //find product add amount
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("InventoryController: returnItems: no such product found");
        product.returnItems(storeID, amount);
    }

    public void addStore() {
        if (storeIds.isEmpty())
            storeIds.add(0);
        storeIds.add(storeIds.get(storeIds.size()-1)+1);
    }
    public void removeStore(int storeID) {
        storeIds.remove(storeIds.indexOf(storeID));
    }
    public void addProductToStore(int storeID, int productID, int minAmount, int maxAmount) { //affect 4 maps in product.

    }
    public void removeProductFromStore(int storeID, int productID) {

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
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("InventoryController: reportDamaged: no such product found");
        product.removeItems(storeID, amount);
        product.reportDamaged(storeID, amount, description);
    }
    public void reportExpired(int storeID, int productID, int amount) {
        Product product = products.get(productID);
        if (product==null)
            throw new IllegalArgumentException("InventoryController: reportExpired: no such product found");
        product.removeItems(storeID, amount);
        product.reportExpired(storeID, amount);
    }
    public List<DamagedItemReport> getDamagedItemReports(Date start, Date end, List<Integer> storeID) { //when storeID is empty, then no restrictions.
        List<DamagedItemReport> dirList = new ArrayList<>();
        List<Product> productList = getProducts();
        for (Product p: productList) {
            dirList.addAll(p.getDamagedItemReports(start, end, storeID));
        }
        return dirList;
    }
    public List<ExpiredItemReport> getExpiredItemReports(Date start, Date end, List<Integer> storeID) { //when storeID is empty, then no restrictions.
        List<ExpiredItemReport> eirList = new ArrayList<>();
        List<Product> productList = getProducts();
        for (Product p: productList) {
            eirList.addAll(p.getExpiredItemReports(start, end, storeID));
        }
        return eirList;
    }

        private void addCategoriesForTests () {
//        Category cSmall1 = new Category(1, "Small", new ArrayList<>(), new ArrayList<>());
//        categories.put(categories.size() + 1, cSmall1);
//        Category cSmall2 = new Category(1, "Small", new ArrayList<>(), new ArrayList<>());
//        categories.put(categories.size() + 1, cSmall1);
//        Category cLarge1 = new Category(1, "Large", new ArrayList<>(), new ArrayList<>());
//        categories.put(categories.size() + 1, cSmall1);
//        Category cLarge2 = new Category(1, "Large", new ArrayList<>(), new ArrayList<>());
//        categories.put(categories.size() + 1, cSmall1);
//        Category cMedium = new Category(1, "Medium", new ArrayList<>(), new ArrayList<>());
//        categories.put(categories.size() + 1, cSmall1);
//        Category cShampoo = new Category(1, "Shampoo", new ArrayList<>(), new ArrayList<>());
//        categories.put(categories.size() + 1, cSmall1);
//        Category cToothpaste = new Category(1, "Toothpaste", new ArrayList<>(), new ArrayList<>());
//        categories.put(categories.size() + 1, cSmall1);
//        List<Category> milk = new ArrayList();
//        milk.add(cSmall1);
//        milk.add(cMedium);
//        milk.add(cLarge1);
//        List<Category> yogurt = new ArrayList();
//        yogurt.add(cSmall2);
//        yogurt.add(cLarge2);
//        List<Category> health = new ArrayList();
//        health.add(cShampoo);
//        health.add(cToothpaste);
//        List<Category> dairy = new ArrayList();
//        dairy.add(new Category(1, "Milk", milk, new ArrayList<>()));
//        dairy.add(new Category(1, "Yogurt", yogurt, new ArrayList<>()));
//        Category cdairy = new Category(1, "Dairy", dairy, new ArrayList<>());
//        categories.put(categories.size() + 1, cdairy);
//        Category cHealth = new Category(1, "Health,", health, new ArrayList<>());
//        categories.put(categories.size() + 1, cHealth);
//        Category cOrg1 = new Category(1, "Organic", new ArrayList<>(), new ArrayList<>());
//        categories.put(categories.size() + 1, cOrg1);
//        Category cOrg2 = new Category(1, "Organic", new ArrayList<>(), new ArrayList<>());
//        categories.put(categories.size() + 1, cOrg2);
//        List<Category> veggies = new ArrayList();
//        veggies.add(cOrg1);
//        List<Category> fruits = new ArrayList();
//        fruits.add(cOrg2);
//        Category cVeggie = new Category(1, "Vegetables", veggies, new ArrayList<>());
//        categories.put(categories.size() + 1, cVeggie);
//        Category cFruit = new Category(1, "Fruits", veggies, new ArrayList<>());
//        categories.put(categories.size() + 1, cFruit);
//        List<Category> produce = new ArrayList();
//        produce.add(cFruit);
//        produce.add(cVeggie);
//        Category cProduce = new Category(1, "Produce", produce, new ArrayList<>());
//        categories.put(categories.size() + 1, cProduce);
    }

    private void addProductsForTests () {
    }

    private void addSalesForTests () {
    }
}
