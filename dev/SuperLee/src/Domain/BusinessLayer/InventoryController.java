package Domain.BusinessLayer;

import Domain.BusinessLayer.Inventory.Category;
import Domain.BusinessLayer.Inventory.DefectiveItems;
import Domain.BusinessLayer.Inventory.DiscountsAndSales.PurchaseFromSupplier;
import Domain.BusinessLayer.Inventory.DiscountsAndSales.SaleToCustomer;
import Domain.BusinessLayer.Inventory.Product;
import Domain.BusinessLayer.Inventory.StockReport;
import Domain.BusinessLayer.Supplier.Order;
import Domain.BusinessLayer.Supplier.OrderItem;
import Globals.Defect;
import Globals.Pair;

import java.util.*;

public class InventoryController {
    private List<Integer> storeIds;
    private Map<Integer, Category> categories;
    private List<SaleToCustomer> sales;
    private Map<Integer, Product> products;
    private int saleID;
    private int catID;
    private int productID;
    private int storeID;
    private SupplierController supplierController;
    public InventoryController() {
        storeIds = new ArrayList<>();
        categories = new HashMap<>();
        sales = new ArrayList<>();
        products = new HashMap<>();
        storeID=1;
        saleID=1;
        catID=1;
        productID=1;
        supplierController = new SupplierController();
    }

    public void loadTestData() {
        //initialize stuff for tests
        //add stores
        for (int i = 1; i <= 10; i++)
            addStore();
        addCategoriesForTests();
        addProductsForTests();
        addSalesForTests();
        addReportsForTests();
    }

    public Category getCategory(int categoryID) {
        return categories.get(categoryID);
    }

    public List<SaleToCustomer> getRemovableSales() {
        List<SaleToCustomer> removableSales = new ArrayList<>();
        for (SaleToCustomer sale: sales) {
            if (!sale.isPassed())
                removableSales.add(sale);
        }
        return removableSales;
    }

    private void redundantCategories (List<Integer> categoriesList) {
        //remove redundant categories
        List<Integer> redundantCategories = new ArrayList<>();
        //search forward
        for (Integer i : categoriesList) {
            Category category1 = categories.get(i);
            for (Integer j :categoriesList) {
                if (!(i.equals(j))) {
                    Category category2 = categories.get(j);
                    if (category2.belongsToCategory(category1)) {
                        redundantCategories.add(j);
                    }
                    if (category1.belongsToCategory(category2)) {
                        redundantCategories.add(i);
                    }
                }
            }
        }
        categoriesList.removeAll(redundantCategories);
    }

    private void redundantProducts(List<Integer> productIDs, List<Integer> categoriesList) {
        //search products
        List<Integer> redundantProducts = new ArrayList<>();
        for (Integer i : productIDs) {
            Product product = getProduct(i);
            for (Integer c : categoriesList) {
                Category category = categories.get(c);
                if (product.belongsToCategory(category)) {
                    redundantProducts.add(i);
                }
            }
        }
        productIDs.removeAll(redundantProducts);
    }
    public SaleToCustomer addSale(List<Integer> categoriesList, List<Integer> productIDs, int percent, Date start, Date end) {
        /*if (!start.before(end)) //could add more restrictions regarding adding past sales but would be problematic for tests
            throw new IllegalArgumentException("Illegal dates. start must be before end");
        if (!(percent>0 && percent<100))
            throw new IllegalArgumentException("Percent sale must be between 1 and 99. Received: " + percent);*/
        if (categoriesList.isEmpty()&&productIDs.isEmpty())
            throw new IllegalArgumentException("Must specify categories and/or products for the sale to apply to");
        redundantCategories(categoriesList);
        redundantProducts(productIDs, categoriesList);
        SaleToCustomer sale = new SaleToCustomer(saleID++, start, end, percent, categoriesList, productIDs);
        sales.add(sale);

        Product product;
        for (Integer pID: productIDs) {
            //should we throw error if only one of them is illegal
            product = getProduct(pID);
            product.addSale(sale);
        }
        Category category;
        for (Integer cID: categoriesList) {
            category = categories.get(cID);
            if (category!=null)
                category.addSale(sale);        }
        return sale;
    }

    private void copySale(SaleToCustomer sale) {
        Date endOfToday = new Date();
        endOfToday.setHours(24);
        endOfToday.setMinutes(1);
        endOfToday.setSeconds(-1);
        SaleToCustomer newSale = new SaleToCustomer(saleID++, sale.getStartDate(), endOfToday, sale.getPercent(), sale.getCategories(), sale.getProducts());
        sales.add(newSale);
        Product product;
        for (Integer pID: newSale.getProducts()) {
            //should we throw error if only one of them is illegal
            product = getProduct(pID);
            product.addSale(newSale);
        }
        Category category;
        for (Integer cID: newSale.getCategories()) {
            category = categories.get(cID);
            if (category!=null)
                category.addSale(newSale);        }
    }
    private void removeSaleFromProductsAndCategories(SaleToCustomer sale) {
        Product product;
        for (Integer pID: sale.getProducts()) {
            product = getProduct(pID);
            product.removeSale(sale);
        }
        Category category;
        for (Integer cID: sale.getCategories()) {
            category = categories.get(cID);
            if (category!=null)
                category.removeSale(sale);
        }
        sales.remove(sale);
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

    //needs to be deleted
    private PurchaseFromSupplier addItems(int storeID, int productID, int supplierID, int amountBought, double pricePaid, double originalPrice, int orderID) {
        return  getProduct(productID).addItems(storeID, new Date(), supplierID, amountBought, pricePaid, originalPrice, orderID);
    }

    public Order orderArrived(int orderID) {
        return null;
//        Order arrivedOrder = supplierController.getOrder(orderID);
//        supplierController.orderHasArrived(orderID);
//        int storeID = arrivedOrder.getStoreID();
//        int supplierID = arrivedOrder.getSupplierID();
//        int amount;
//        double pricePaid;
//        double originalPrice;
//        Product product;
//        for (OrderItem orderItem: arrivedOrder.getOrderItems()) {
//            product = getProduct(orderItem.getProdectID());
//            amount = orderItem.getQuantity();
//            pricePaid = orderItem.getFinalPrice();
//            originalPrice = orderItem.getOriginalPrice();
//            product.addItems(storeID, new Date(), supplierID, amount, pricePaid, originalPrice, orderID);
//        }
//        return arrivedOrder;
    }

    public List<PurchaseFromSupplier> getPurchaseFromSupplierHistory(int productID) {
        return getProduct(productID).getPurchaseFromSupplierList();
    }

    public List<PurchaseFromSupplier> getDiscountFromSupplierHistory(int productID) {
        return getProduct(productID).getDiscountFromSupplierHistory();
    }

    public Product getProduct(int productID) {
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
        //remove redundancies?
        for (int i : categoryIDs)
            products.addAll(categories.get(i).getAllProductsInCategory());
        return products;
    }

    public void moveItems(int storeID, int productID, int amount) {
        Product product = getProduct(productID);
        product.moveItems(storeID, amount);
    }

    public double returnItems(int storeID, int productID, int amount, Date dateBought) {
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
        storeIds.remove(storeID);
    }

    public Product addProductToStore(int storeID, List<Integer> shelvesInStore, List<Integer> shelvesInWarehouse, int productID, int minAmount, int targetAmount) { //affect 4 maps in product.
        Product product = getProduct(productID);
        product.addLocation(storeID, shelvesInStore, shelvesInWarehouse, minAmount, targetAmount);
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

    public Product newProduct(String name, int categoryID, double weight, double price, Map<Integer, Integer> suppliers, int manufacturerID) {
        Category category = categories.get(categoryID);
        int id = productID++;
        Product product = new Product(id, name, category, weight, price, suppliers, manufacturerID);
        products.put(id, product);
        category.addProduct(product);
        return product;
    }

    public void deleteProduct(int id){
        products.remove(id);
        //remove sales? remove empty categories?
    }

    public Pair<DefectiveItems, String> reportDamaged(int storeID, int productID, int amount, int employeeID, String description, boolean inWarehouse) {
        Product product = getProduct(productID);
        DefectiveItems DI = product.reportDamaged(storeID, amount, employeeID, description, inWarehouse);
        boolean underMin = product.isLow(storeID);
        return new Pair<>(DI, underMin ? underMinMessage(productID, storeID) : null);
    }

    public Pair<DefectiveItems, String> reportExpired(int storeID, int productID, int amount, int employeeID, String description, boolean inWarehouse) {
        Product product = getProduct(productID);
        DefectiveItems DI = product.reportExpired(storeID, amount, employeeID, description, inWarehouse);
        boolean underMin = product.isLow(storeID);
        return new Pair<>(DI, underMin ? underMinMessage(productID, storeID) : null);
    }

    public Pair<Double, String> buyItems(int storeID, int productID, int amount) {
        Product product = getProduct(productID);
        double price = product.getCurrentPrice()*amount;
        product.removeItems(storeID, amount, false);
        boolean underMin = product.isLow(storeID);
        return new Pair<>(price, underMin ? underMinMessage(productID, storeID) : null);
    }

    private String underMinMessage(int productID, int storeID) {
        return "WARNING: product with ID " + productID + " is in low stock in store " + storeID;
    }

    private void checkDates(Date start, Date end) {
        Date today = new Date(); today.setHours(24); today.setMinutes(0); today.setSeconds(0);
        if (!start.before(today) || end.before(start))
            throw new IllegalArgumentException("Illegal Dates. Cannot be in the future and end cannot be before start");
    }
    //why is storeIDS a list?
    public List<DefectiveItems> getDamagedItemReportsByStore(Date start, Date end, List<Integer> storeID) { //when storeID is empty, then no restrictions.
        checkDates(start, end);
        List<DefectiveItems> dirList = new ArrayList<>();
        Collection<Product> productList = getProducts();
        for (Product p: productList) {
            dirList.addAll(p.getDamagedItemReportsByStore(start, end, storeID));
        }
        return dirList;
    }
    public List<DefectiveItems> getDamagedItemReportsByCategory(Date start, Date end, List<Integer> categoryID) {
        checkDates(start, end);
        List<DefectiveItems> dirList = new ArrayList<>();
        for (Integer c: categoryID) {
            dirList.addAll(categories.get(c).getDamagedItemReports(start, end));
        }
        return dirList;
    }

    public List<DefectiveItems> getDamagedItemReportsByProduct(Date start, Date end, List<Integer> productID) {
        checkDates(start, end);
        List<DefectiveItems> dirList = new ArrayList<>();
        for (Integer p: productID) {
            dirList.addAll(getProduct(p).getDamagedItemReports(start, end));
        }
        return dirList;
    }

    public List<DefectiveItems> getExpiredItemReportsByStore(Date start, Date end, List<Integer> storeID) { //when storeID is empty, then no restrictions.
        checkDates(start, end);
        List<DefectiveItems> eirList = new ArrayList<>();
        Collection<Product> productList = getProducts();
        for (Product p: productList) {
            eirList.addAll(p.getExpiredItemReportsByStore(start, end, storeID));
        }
        return eirList;
    }

    public List<DefectiveItems> getExpiredItemReportsByCategory(Date start, Date end, List<Integer> categoryID) {
        checkDates(start, end);
        List<DefectiveItems> eirList = new ArrayList<>();
        for (Integer c: categoryID) {
            eirList.addAll(categories.get(c).getExpiredItemReports(start, end));
        }
        return eirList;
    }

    public List<DefectiveItems> getExpiredItemReportsByProduct(Date start, Date end, List<Integer> productID) {
        checkDates(start, end);
        List<DefectiveItems> eirList = new ArrayList<>();
        for (Integer p: productID) {
            eirList.addAll(getProduct(p).getExpiredItemReports(start, end));
        }
        return eirList;
    }

    public List<Integer> getStoreIDs() {
        return storeIds;
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
            categories.put(id, new Category(id, name, new HashSet<>(), new ArrayList<>(), null));
        }
        else {
            categories.put(id, new Category(id, name, new HashSet<>(), new ArrayList<>(), categories.get(parentCategoryID)));
        }
        return categories.get(id);
    }

    public List<StockReport> getMinStockReport() {
        List<StockReport> lowOnStock = new ArrayList<>();
        for (Product p : products.values()) {
            for (int i : storeIds) {
                if (p.isLow(i))
                    lowOnStock.add(p.getStockReport(i));
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

    public boolean isUnderMin(int storeID, int productID) {
        return getProduct(productID).isLow(storeID);
    }

    public int getAmountInStore(int storeID, int productID) {
        if (!storeIds.contains(storeID))
            throw new IllegalArgumentException("Store " + storeID + " is not registered in the system");
        Product p = getProduct(productID);
        return p.getInStore(storeID)+p.getInWarehouse(storeID);
    }

    public List<StockReport> getStockReport(List<Integer> stores, List<Integer> categoryIDs) {
        redundantCategories(categoryIDs);
        List<StockReport> stock = new ArrayList<>();
        for (Integer store : stores) {
            for (Integer catID : categoryIDs) {
                Category category = categories.get(catID);
                for (Product p : category.getAllProductsInCategory()) {
                    stock.add(p.getStockReport(store));
                }
            }
        }
        return stock;
    }



    public void deleteCategory(int catID) {
        Category categoryToRemove = categories.get(catID);
        if (categoryToRemove==null)
            throw new IllegalArgumentException("There is no category with id " + catID);
        if (!categoryToRemove.getSubcategories().isEmpty())
            throw new IllegalArgumentException("Cannot delete a category that has subcategories");
        if (!categoryToRemove.getAllProductsInCategory().isEmpty())
            throw new IllegalArgumentException("Cannot delete a category that has products still assigned to it");
        categoryToRemove.changeParentCategory(null);
        categories.remove(catID);
    }

    public Product changeProductMin(int store, int product, int min) {
        Product p = getProduct(product);
        p.changeProductMin(store, min);
        return p;
    }

    public Product changeProductTarget(int store, int product, int target) {
        Product p = getProduct(product);
        p.changeProductTarget(store, target);
        return p;
    }

    private int getAmountForOrder(int storeID, int productID) { //Periodically
        return getProduct(productID).getAmountForOrder(storeID);
    }

    private Map<Integer, Integer> getAmountsForMinOrders(Product product) {
        Map<Integer, Integer> amounts = new HashMap<>();
        int amount;
        for (int storeID: product.getStoreIDs()) {
            amount = product.getAmountForOrder(storeID);
            if (amount>0)
                amounts.put(storeID, amount);
        }
        return amounts;
    }

    //needs to be called every morning from service by some kind of trigger
    public List<Order> getOrdersOfTomorrow() throws Exception {
        Map<Integer, Map<Integer,Integer>> thingsToOrder = new HashMap<>(); // <productID, <storeID, amount>>
        Map<Integer, Integer> amounts;
        for (Product product: products.values()) {
            amounts = getAmountsForMinOrders(product);
            if (amounts.size()>0)
                thingsToOrder.put(product.getId(), amounts);
        }
        List<Order> orders = supplierController.createAllOrders(thingsToOrder); //orders we print on screen (=order to issue from suppliers) (new orders and edited order)
        //document every order we print on screen (new orders or edit amount of existed orders)
        for (Order order: orders) {
            for (OrderItem orderItem: order.getOrderItems()) {
                getProduct(orderItem.getProductId()).addDelivery(order.getId(), order.getStoreID(), orderItem.getQuantity());
            }
        }
        return orders;
    }

    private void addCategoriesForTests () {
        addCategory("Small", 0);
        addCategory("Small", 0);
        addCategory("Large", 0);
        addCategory("Large", 0);
        addCategory("Medium", 0);

        addCategory("Shampoo", 0);

        addCategory("Milk", 0);
        changeParentCategory(1, 7);
        changeParentCategory(3, 7);
        changeParentCategory(5, 7);

        addCategory("Yogurt", 0);
        changeParentCategory(2, 8);
        changeParentCategory(4, 8);

        addCategory("Dairy", 0);
        changeParentCategory(7, 9);
        changeParentCategory(8, 9);

        addCategory("Toothpaste", 0);

        addCategory("Health", 0);
        changeParentCategory(6, 11);
        changeParentCategory(10, 11);

        addCategory("Organic", 0);
        addCategory("Organic", 0);

        addCategory("Vegetables", 0);
        addCategory("Fruit", 0);
        changeParentCategory(12, 14);
        changeParentCategory(13, 15);

        addCategory("Produce", 0);
        changeParentCategory(14, 16);
        changeParentCategory(15, 16);
    }

    private void addProductsForTests () {
        newProduct("tomato", 14, -1, 7.2, new HashMap<>(), 0);
        newProduct("tomato", 12, -1, 9.2, new HashMap<>(), 0);
        newProduct("strawberry", 13, -1, 7.2, new HashMap<>(), 0);
        newProduct("melon", 15, -1, 7.2, new HashMap<>(), 0);
        newProduct( "Hawaii", 6, 1.2, 13, new HashMap<>(), 1);
        newProduct("Crest", 10, 0.7, 7.2, new HashMap<>(), 2);
        newProduct("Tara 1L", 5, 1.2, 8.6, new HashMap<>(), 17);
        newProduct("Tnuva 1L", 5, 1.2, 8, new HashMap<>(), 18);
        newProduct("yoplait strawberry", 2, 0.5, 5.3, new HashMap<>(), 9);
        newProduct("yoplait vanilla", 2, 0.5, 5.3, new HashMap<>(), 9);
        for (int i : storeIds) {
            List<Integer> shelves = new ArrayList<>();
            shelves.add(2*i); shelves.add(2*i+1);
            for (int p : products.keySet()) {
                addProductToStore(i, shelves, shelves, p, 10*shelves.get(1), 30*shelves.get(1));
                addItems(i, p,3, 37, 37*10/10*shelves.get(1), 37*10/10*shelves.get(1), 1);
            }
        }
    }

    private void addSalesForTests () {
        List<Integer> categories = new ArrayList<>();
        List<Integer> products = new ArrayList<>();
        List<Integer> empty = new ArrayList<>();
        //small milk,       medium milk,         yogurt,         Dairy
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
        products.add(7); products.add(8);
        addSale(empty, products, 5, today, tomorrow);
        addSale(categories, products, 12, tomorrow, threeDays);
        addSale(categories, empty, 17, twoDaysAgo, twoDays);
    }

    private void addReportsForTests () {
        //add expired reports for items 2 and 3, none for 1
        Date threeDaysAgo = new Date(); threeDaysAgo.setHours(-72);
        Date twoDaysAgo = new Date(); twoDaysAgo.setHours(-48);
        Date yesterday = new Date(); yesterday.setHours(-24);
        Date today = new Date();

        reportDefectiveForTest(4,2,10, 23, "", Defect.Expired, threeDaysAgo, false);
        reportDefectiveForTest(4,6,11, 23, "", Defect.Expired, threeDaysAgo, false);
        reportDefectiveForTest(4,3,3, 23, "", Defect.Expired, twoDaysAgo, true);
        reportDefectiveForTest(5,2,2, 23, "", Defect.Expired, yesterday, true);
        reportDefectiveForTest(5,2,6, 23, "", Defect.Expired, today, false);

        reportDefectiveForTest(4,4,10, 24, "broken spout", Defect.Damaged, threeDaysAgo, true);
        reportDefectiveForTest(4,4,11, 2, "fell on floor", Defect.Damaged, threeDaysAgo, true);
        reportDefectiveForTest(4,1,3, 3, "the dog ate it", Defect.Damaged, twoDaysAgo, false);
        reportDefectiveForTest(4,9,2, 23, "alarm didn't go off", Defect.Damaged, yesterday, false);
        reportDefectiveForTest(4,2,6, 23, "very sour", Defect.Damaged, today, true);
    }

    private void reportDefectiveForTest(int storeID, int productID, int amount, int employeeID, String description, Defect defect, Date date, boolean inWarehouse) {
        Product product = getProduct(productID);
        product.reportDefectiveForTest(storeID, amount, employeeID, description, defect, date, inWarehouse);
    }
}