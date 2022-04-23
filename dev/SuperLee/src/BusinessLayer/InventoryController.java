package BusinessLayer;

import BusinessLayer.DiscountsAndSales.PurchaseFromSupplier;
import BusinessLayer.DiscountsAndSales.SaleToCustomer;
import jdk.jfr.Percentage;

import java.util.*;

import static java.util.Collections.max;

public class InventoryController {
    private List<Integer> storeIds;
    private Map<Integer, Category> categories;
    private List<SaleToCustomer> sales;
    private Map<Integer, Product> products;
    private int saleID;
    private int catID;
    private int productID;
    private int storeID;
    public InventoryController() {
        storeIds = new ArrayList<>();
        categories = new HashMap<>();
        sales = new ArrayList<>();
        products = new HashMap<>();
        storeID=0;
        saleID=1;
        catID=1;
        productID=1;
    }

    public void loadTestData() {
        //initialize stuff for tests
        //add stores
        for (int i = 1; i <= 10; i++)
            addStore();
        addCategoriesForTests();
        addProductsForTests();
        addSalesForTests();
    }

    public SaleToCustomer addSale(List<Integer> categoriesList, List<Integer> productIDs, int percent, Date start, Date end) {
        if (!start.before(end)) //could add more restrictions regarding adding past sales but would be problematic for tests
            throw new IllegalArgumentException("Illegal dates. start must be before end");
        if (!(percent>0 && percent<100))
            throw new IllegalArgumentException("Percent sale must be between 1 and 99. Received: " + percent);
        if (categoriesList.isEmpty()&&productIDs.isEmpty())
            throw new IllegalArgumentException("Must specify categories and/or products for the sale to apply to");

        //remove redundant products and categories
        List<Integer> redundantCategories = new ArrayList<>();
        //search forward
        for (Integer i = 0; i<categoriesList.size(); i++) {
            Category category1 = categories.get(i);
            for (Integer j = i + 1; i < categoriesList.size(); j++) {
                Category category2 = categories.get(j);
                if (category2.belongsToCategory(category1)) {
                    redundantCategories.add(j);
                }
                if (category1.belongsToCategory(category2)) {
                    redundantCategories.add(i);
                }
            }
        }
        categoriesList.removeAll(redundantCategories);
        //search products
        List<Integer> redundantProducts = new ArrayList<>();
        for (Integer i = 0; i< productIDs.size(); i++) {
            Product product = getProduct(i);
            for (Integer c : categoriesList) {
                Category category = categories.get(c);
                if (product.belongsToCategory(category)) {
                    redundantProducts.add(i);
                }
            }
        }
        productIDs.removeAll(redundantProducts);

        SaleToCustomer sale = new SaleToCustomer(saleID++, start, end, percent, categoriesList, productIDs);
        sales.add(sale);

        Product product = null;
        for (Integer pID: productIDs) {
            //should we throw error if only one of them is illegal
            product = getProduct(pID);
            product.addSale(sale);
        }
        Category category = null;
        for (Integer cID: categoriesList) {
            category = categories.get(cID);
            if (category!=null)
                category.addSale(sale);        }
        return sale;
    }

    private void copySale(SaleToCustomer sale) {
        SaleToCustomer newSale = new SaleToCustomer(saleID++, sale.getStartDate(), new Date(), sale.getPercent(), sale.getCategories(), sale.getProducts());
        sales.add(newSale);
        Product product = null;
        for (Integer pID: newSale.getProducts()) {
            //should we throw error if only one of them is illegal
            product = getProduct(pID);
            product.addSale(newSale);
        }
        Category category = null;
        for (Integer cID: newSale.getCategories()) {
            category = categories.get(cID);
            if (category!=null)
                category.addSale(newSale);        }
    }
    private void removeSaleFromProductsAndCategories(SaleToCustomer sale) {
        Product product = null;
        for (Integer pID: sale.getProducts()) {
            product = getProduct(pID);
            product.removeSale(sale);
        }
        Category category = null;
        for (Integer cID: sale.getCategories()) {
            category = categories.get(cID);
            if (category!=null)
                category.removeSale(sale);
        }
    }

    public void removeSale(int saleID) {
        for (SaleToCustomer sale : sales) {
            if (sale.getId()==saleID) {
                if (sale.isActive()) {
                    copySale(sale);
                    removeSaleFromProductsAndCategories(sale);
                }
                else if (sale.isUpcoming()) {
                    sales.remove(sale);
                    removeSaleFromProductsAndCategories(sale);
                }
            }
        }
    }

    public List<PurchaseFromSupplier> getPurchaseFromSupplierHistory(int productID) {
        return getProduct(productID).getPurchaseFromSupplierList();
    }

    public List<PurchaseFromSupplier> getDiscountFromSupplierHistory(int productID) {
        return getProduct(productID).getDiscountFromSupplierHistory();
    }

    public PurchaseFromSupplier addPurchaseFromSupplier(int productID, Date date, int supplierID, String description, int amountBought, int pricePaid, int originalPrice) {
        return  getProduct(productID).addPurchaseFromSupplier(date, supplierID, description, amountBought, pricePaid, originalPrice);
    }

    private Product getProduct(int productID) {
        Product output = products.get(productID);
        if (output==null)
            throw new IllegalArgumentException("Product ID Invalid: " + productID);
        return output;
    }

    public List<SaleToCustomer> getSaleHistoryByProduct(int productID) {
        return getProduct(productID).getSaleHistory();
    }

    public List<SaleToCustomer> getSaleHistoryByCategory(int categoryID) {
        return categories.get(categoryID).getSaleHistory();
    }

    public List<DefectiveItems> getDefectiveItemsByStore(Date start, Date end, List<Integer> storeIDs) {
        List<DefectiveItems> defective = new ArrayList<>();
        defective.addAll(getDamagedItemReportsByStore(start, end, storeIDs));
        defective.addAll(getExpiredItemReportsByStore(start, end, storeIDs));
        return defective;
    }

    public List<DefectiveItems> getDefectiveItemsByCategory(Date start, Date end, List<Integer> catIDs) {
        List<DefectiveItems> defective = new ArrayList<>();
        defective.addAll(getDamagedItemReportsByCategory(start, end, catIDs));
        defective.addAll(getExpiredItemReportsByCategory(start, end, catIDs));
        return defective;
    }

    public List<DefectiveItems> getDefectiveItemsByProduct(Date start, Date end, List<Integer> productIDs) {
        List<DefectiveItems> defective = new ArrayList<>();
        defective.addAll(getDamagedItemReportsByProduct(start, end, productIDs));
        defective.addAll(getExpiredItemReportsByProduct(start, end, productIDs));
        return defective;
    }

    public Collection<Product> getProducts() {
        return products.values();
    }

    public Collection<Category> getCategories() {
        return categories.values();
    }

    public List<Product> getProductsFromCategory(List<Integer> categoryIDs) {
        List<Product> products = new ArrayList<>();
        for (int i : categoryIDs)
            products.addAll(categories.get(i).getProducts());
        return products;
    }

    public void moveItems(int productID, int storeID, int amount) {
        Product product = getProduct(productID);
        product.moveItems(storeID, amount);
    }
    public void addItems(int storeID, int productID, int amount) {
        Product product = getProduct(productID);
        product.addItems(storeID, amount);
    }
    public Double returnItems(int storeID, int productID, int amount, Date dateBought) {
        //find product add amount
        Product product = getProduct(productID);
        return product.returnItems(storeID, amount, dateBought);
    }

    public int addStore() {
        int id = storeID++;
        storeIds.add(id);
        return id;
    }

    public void removeStore(int storeID) {
        if (!storeIds.contains(storeID))
            throw new IllegalArgumentException("There is no store with ID" + storeID);
        for (int i : products.keySet()) {
            removeProductFromStore(storeID, i);
        }
        storeIds.remove(storeIds.indexOf(storeID));
    }

    public Product addProductToStore(int storeID, List<Integer> shelvesInStore, List<Integer> shelvesInWarehouse, int productID, int minAmount, int maxAmount) { //affect 4 maps in product.
        Product product = getProduct(productID);
        product.addLocation(storeID, shelvesInStore, shelvesInWarehouse, minAmount, maxAmount);
        return product;
    }

    public Product removeProductFromStore(int storeID, int productID) {
        Product product = getProduct(productID);
        product.removeLocation(storeID);
        return product;
    }
    
    public void loadData() throws NoSuchMethodException {
        throw new NoSuchMethodException();
    }

    public Product newProduct(String name, int categoryID, int weight, double price, Map<Integer, Integer> suppliers, int manufacturerID) {
        Category category = categories.get(categoryID);
        int id = productID++;
        Product product = new Product(id, name, category, weight, price, suppliers, manufacturerID);
        products.put(id, product);
        return product;
    }

    public void deleteProduct(int id){
        products.remove(id);
        //remove sales? remove empty categories?
    }

    public DefectiveItems reportDamaged(int storeID, int productID, int amount, int employeeID, String description) {
        Product product = getProduct(productID);
        return product.reportDamaged(storeID, amount, employeeID, description);
    }
    public DefectiveItems reportExpired(int storeID, int productID, int amount, int employeeID, String description) {
        Product product = getProduct(productID);
        return product.reportExpired(storeID, amount, employeeID, description);
    }

    //why is storeIDS a list?
    public List<DefectiveItems> getDamagedItemReportsByStore(Date start, Date end, List<Integer> storeID) { //when storeID is empty, then no restrictions.
        List<DefectiveItems> dirList = new ArrayList<>();
        Collection<Product> productList = getProducts();
        for (Product p: productList) {
            dirList.addAll(p.getDamagedItemReportsByStore(start, end, storeID));
        }
        return dirList;
    }
    public List<DefectiveItems> getDamagedItemReportsByCategory(Date start, Date end, List<Integer> categoryID) {
        List<DefectiveItems> dirList = new ArrayList<>();
        for (Integer c: categoryID) {
            dirList.addAll(categories.get(c).getDamagedItemReports(start, end));
        }
        return dirList;
    }

    public List<DefectiveItems> getDamagedItemReportsByProduct(Date start, Date end, List<Integer> productID) {
        List<DefectiveItems> dirList = new ArrayList<>();
        for (Integer p: productID) {
            dirList.addAll(getProduct(p).getDamagedItemReports(start, end));
        }
        return dirList;
    }

    public List<DefectiveItems> getExpiredItemReportsByStore(Date start, Date end, List<Integer> storeID) { //when storeID is empty, then no restrictions.
        List<DefectiveItems> eirList = new ArrayList<>();
        Collection<Product> productList = getProducts();
        for (Product p: productList) {
            eirList.addAll(p.getExpiredItemReportsByStore(start, end, storeID));
        }
        return eirList;
    }

    public List<DefectiveItems> getExpiredItemReportsByCategory(Date start, Date end, List<Integer> categoryID) {
        List<DefectiveItems> eirList = new ArrayList<>();
        for (Integer c: categoryID) {
            eirList.addAll(categories.get(c).getExpiredItemReports(start, end));
        }
        return eirList;
    }

    public List<DefectiveItems> getExpiredItemReportsByProduct(Date start, Date end, List<Integer> productID) {
        if (!start.before(end) || !start.before(new Date()))
            throw new IllegalArgumentException("Illegal start/end dates");
        List<DefectiveItems> eirList = new ArrayList<>();
        for (Integer p: productID) {
            eirList.addAll(getProduct(p).getExpiredItemReports(start, end));
        }
        return eirList;
    }

    public List<Integer> getStoreIDs() {
        return storeIds;
    }

    public Double buyItems(int productID, int storeID, int amount) {
        Product product = getProduct(productID);
        double price = product.getCurrentPrice()*amount;
        product.removeItems(storeID, amount);
        return price;
    }

    private void addCategoriesForTests () {
        Category cSmall1 = new Category("Small", new HashSet<>(), new ArrayList<>(), null);
        categories.put(catID++, cSmall1);
        Category cSmall2 = new Category("Small", new HashSet<>(), new ArrayList<>(), null);
        categories.put(catID++, cSmall2);
        Category cLarge1 = new Category("Large", new HashSet<>(), new ArrayList<>(), null);
        categories.put(catID++, cLarge1);
        Category cLarge2 = new Category("Large", new HashSet<>(), new ArrayList<>(), null);
        categories.put(catID++, cLarge2);
        Category cMedium = new Category("Medium", new HashSet<>(), new ArrayList<>(), null);
        categories.put(catID++, cMedium);
        Category cShampoo = new Category("Shampoo", new HashSet<>(), new ArrayList<>(), null);
        Set<Category> milk = new HashSet<>();
        milk.add(cSmall1);
        milk.add(cMedium);
        milk.add(cLarge1);
        Set<Category> yogurt = new HashSet<>();
        yogurt.add(cSmall2);
        yogurt.add(cLarge2);
        Set<Category> dairy = new HashSet<>();
        Category cMilk = new Category("Milk", milk, new ArrayList<>(), null);
        Category cYogurt = new Category("Yogurt", yogurt, new ArrayList<>(), null);
        dairy.add(cMilk);
        dairy.add(cYogurt);
        Category cdairy = new Category("Dairy", dairy, new ArrayList<>(), null);
        categories.put(catID++, cdairy);
        categories.put(catID++, cShampoo);
        Category cToothpaste = new Category("Toothpaste", new HashSet<>(), new ArrayList<>(), null);
        categories.put(catID++, cToothpaste);
        Set<Category> health = new HashSet<>();
        health.add(cShampoo);
        health.add(cToothpaste);
        Category cHealth = new Category("Health,", health, new ArrayList<>(), null);
        categories.put(catID++, cHealth);
        Category cOrg1 = new Category("Organic", new HashSet<>(), new ArrayList<>(), null);
        categories.put(catID++, cOrg1);
        Category cOrg2 = new Category("Organic", new HashSet<>(), new ArrayList<>(), null);
        categories.put(catID++, cOrg2);
        Set<Category> veggies = new HashSet();
        veggies.add(cOrg1);
        Set<Category> fruits = new HashSet();
        fruits.add(cOrg2);
        Category cVeggie = new Category("Vegetables", veggies, new ArrayList<>(), null);
        //12
        categories.put(catID++, cVeggie);
        Category cFruit = new Category("Fruits", veggies, new ArrayList<>(), null);
        categories.put(catID++, cFruit);
        Set<Category> produce = new HashSet<>();
        produce.add(cFruit);
        produce.add(cVeggie);
        Category cProduce = new Category("Produce", produce, new ArrayList<>(), null);
        categories.put(catID++, cProduce);
    }

    private void addProductsForTests () {
        products.put(productID, new Product(productID++, "tomato", categories.get(12), -1, 7.2, new HashMap<>(), 0));
        products.put(productID, new Product(productID++, "tomato", categories.get(10), -1, 9.2, new HashMap<>(), 0));
        products.put(productID, new Product(productID++, "strawberry", categories.get(11), -1, 7.2, new HashMap<>(), 0));
        products.put(productID, new Product(productID++, "melon", categories.get(13), -1, 7.2, new HashMap<>(), 0));
        products.put(productID, new Product(productID++, "Hawaii", categories.get(7), 1.2, 13, new HashMap<>(), 1));
        products.put(productID, new Product(productID++, "Crest", categories.get(8), 0.7, 7.2, new HashMap<>(), 2));
        products.put(productID, new Product(productID++, "Tara 1L", categories.get(5), 1.2, 8.6, new HashMap<>(), 17));
        products.put(productID, new Product(productID++, "Tnuva 1L", categories.get(5), 1.2, 8, new HashMap<>(), 18));
        products.put(productID, new Product(productID++, "yoplait strawberry", categories.get(2), 0.5, 5.3, new HashMap<>(), 9));
        products.put(productID, new Product(productID++, "yoplait vanilla", categories.get(2), 0.5, 5.3, new HashMap<>(), 9));
        for (int i : storeIds) {
            List<Integer> shelves = new ArrayList<>();
            shelves.add(2*i); shelves.add(2*i+1);
            for (int p : products.keySet()) {
                addProductToStore(i, shelves, shelves, p, 10*shelves.get(1), 30*shelves.get(1));
                addItems(i, p, 37);
            }
        }
    }

    private void addSalesForTests () {
        List<Integer> categories = new ArrayList<>();
        List<Integer> products = new ArrayList<>();
        List<Integer> empty = new ArrayList<>();
        //small milk,       medium milk,         toothpaste,         health
        categories.add(1); categories.add(5); categories.add(8); categories.add(9);
        //crest             tara1L          tnuva1L         organic tomato  organic strawberry
        products.add(6); products.add(7); products.add(8); products.add(2); products.add(3);
        Date threeDaysAgo = new Date(); threeDaysAgo.setHours(-72);
        Date twoDaysAgo = new Date(); twoDaysAgo.setHours(-48);
        Date yesterday = new Date(); yesterday.setHours(-24);
        Date today = new Date();
        Date tomorrow = new Date(); tomorrow.setHours(24);
        Date twoDays = new Date(); twoDays.setHours(48);
        Date threeDays = new Date(); threeDays.setHours(72);
        addSale(categories, products, 15, threeDaysAgo, tomorrow);
        addSale(categories, empty, 20, threeDaysAgo, yesterday);
        addSale(empty, products, 5, today, tomorrow);
        addSale(categories, products, 12, tomorrow, threeDays);
        addSale(categories, empty, 17, twoDaysAgo, twoDays);
    }

    private void addReportsForTests () {
        //add expired reports for items 2 and 3, none for 1
    }

    public Product editProductPrice(int productID, double newPrice) {
        Product p = getProduct(productID);
        p.setPrice(newPrice);
        return p;
    }

    public Product editProductName(int productID, String newName) {
        Product p = getProduct(productID);
        p.setName(newName);
        return p;
    }

    public Product moveProductToCategory(int productID, int newCatID) {
        Product p = getProduct(productID);
        p.setCategory(categories.get(newCatID));
        return p;
    }

    public Category editCategoryName(int categoryID, String newName) {
        Category c = categories.get(categoryID);
        c.setName(newName);
        return c;
    }

    public Category changeParentCategory(int categoryID, int newCatID) {
        Category c = categories.get(categoryID);
        c.changeParentCategory(categories.get(newCatID));
        return c;
    }

    public Category addCategory(String name, int parentCategoryID) {
        int id = catID++;
        if (parentCategoryID==0) {
            categories.put(id, new Category(name, new HashSet<>(), new ArrayList<>(), null));
        }
        else {
            categories.put(id, new Category(name, new HashSet<>(), new ArrayList<>(), categories.get(parentCategoryID)));
        }
        return categories.get(id);
    }

    public List<StockReport> getMinStockReport() {
        List<StockReport> lowOnStock = new ArrayList<>();
        for (Product p : products.values()) {
            for (int i : storeIds) {
                if (p.isLow(i))
                    lowOnStock.add(new StockReport(i, p.getId(), p.getName(), p.getInStore(i)+p.getInWarehouse(i), p.getMinInStore(i)));
            }
        }
        return lowOnStock;
    }

    public Product addSupplierToProduct(int productID, int supplierID, int productIDWithSupplier) {
        Product product = getProduct(productID);
        product.addSupplier(supplierID, productIDWithSupplier);
        return product;
    }

    public Product removeSupplierFromProduct(int productID, int supplierID) {
        Product product = getProduct(productID);
        product.removeSupplier(supplierID);
        return product;
    }

    public boolean isUnderMin(int store, int product) {
        return getProduct(product).isUnderMin(store);
    }

    public int getAmountInStore(int store, int productID) {
        if (!storeIds.contains(store))
            throw new IllegalArgumentException("Store " + store + " is not registered in the system");
        Product p = getProduct(productID);
        return p.getInStore(store)+p.getInWarehouse(store);
    }

    public List<StockReport> getStockReport(int store) {
        List<StockReport> stock = new ArrayList<>();
        for (Product p : products.values()) {
            stock.add(new StockReport(store, p.getId(), p.getName(), p.getInStore(store)+p.getInWarehouse(store), p.getMinInStore(store)));
        }
        return stock;
    }
}
