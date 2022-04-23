package PresentationLayer;

import ServiceLayer.InventoryService;
import ServiceLayer.Objects.*;
import ServiceLayer.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Inventory {
    public static InventoryService is = new InventoryService();
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Welcome to the Inventory Manager!");
        System.out.println("Would you like to load test data?");
        System.out.println("(yes/no)");
        boolean testData=true;
        while (testData) {
            String ans = scanner.nextLine();
            if (ans.equals("yes") || ans.equals("no")) {
                testData = false;
                if (ans.equals("yes"))
                    is.loadTestData();
            } else
                System.out.println("Please only type yes or no");
        }
        boolean quit = false;
        System.out.println("System ready: At any point enter q to quit");
        System.out.println("Before you begin, here is the help menu with the possible commands: \n");
        help();
        System.out.println();
        while (!quit) {
            System.out.print("Enter command: ");
            String command  = scanner.nextLine();
            if (command.equals("q"))
                break;
            doCommand(command);
        }
        System.out.println("Shutting down - Thanks!");
    }

    private static Date getDate() {
        while (true) {
            try {
                System.out.println("Please insert date in format: DD/MM/YYYY");
                String dateInput = scanner.nextLine();
                if (dateInput.equals("c")) {
                    System.out.println("Cancelling command");
                    return null;
                }
                return new SimpleDateFormat("dd/MM/yyyy").parse(dateInput);
            } catch (ParseException p) {
                System.out.println("Date in wrong format! please try again. c to cancel command");
            }
        }
    }

    private static int getStoreID() {
        System.out.println("Please insert store ID of store you are interested in.");
        System.out.println("Current store IDs are:");
        System.out.println(is.getStoreIDs().getValue());
        return scanner.nextInt();
    }

    private static void isUnderMin(int store, int product) {
        Result<Boolean> r = is.isUnderMin(store, product);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("WARNING: product with ID " + product + " is in low stock in store " + store);
        }
    }

    private static void doCommand(String command) {
        if (command.equals("list products"))
            listProducts();
        else if (command.equals("list categories"))
            listCategories();
        else if (command.equals("store stock report"))
            storeStockReport();
        else if (command.equals("add product"))
            addProduct();
        else if (command.equals("change product name"))
            changeProductName();
        else if (command.equals("change product price"))
            changeProductPrice();
        else if (command.equals("delete product"))
            deleteProduct();
        else if (command.equals("add category"))
            addCategory();
        else if (command.equals("change category parent"))
            changeCatParent();
        else if (command.equals("change category name"))
            changeCatName();
        else if (command.equals("move items"))
            moveItems();
        else if (command.equals("change product category"))
            changeProductCategory();
        else if (command.equals("sale history by product"))
            saleHistoryByProduct();
        else if (command.equals("sale history by category"))
            saleHistoryByCategory();
        else if (command.equals("purchase from supplier history"))
            getPurchaseFromSupplierHistory();
        else if (command.equals("discount from supplier history"))
            getDiscountFromSupplierHistory();
        else if (command.equals("add sale"))
            addSale();
        else if (command.equals("add discount from supplier"))
            addDiscount();
        else if (command.equals("list products in category"))
            listProductsByCategory();
        else if (command.equals("return items"))
            returnItems();
        else if (command.equals("buy items"))
            buyItems();
        else if (command.equals("add items"))
            addItems();
        else if (command.equals("report expired"))
            reportExpired();
        else if (command.equals("expired items"))
            expiredItems();
        else if (command.equals("report damaged"))
            reportDamaged();
        else if (command.equals("damaged items"))
            damagedItems();
        else if (command.equals("defective items"))
            defectiveItems();
        else if (command.equals("add store"))
            addStore();
        else if (command.equals("remove store"))
            removeStore();
        else if (command.equals("remove sale"))
            removeSale();
        else if (command.equals("add product to store"))
            addProductToStore();
        else if (command.equals("remove product from store"))
            removeProductFromStore();
        else if (command.equals("min stock report"))
            getMinStockReport();
        else if (command.equals("add supplier to product"))
            addSupplierToProduct();
        else if (command.equals("remove supplier from product"))
            removeSupplierFromProduct();
        else if (command.equals("help"))
            help();
        else if (command.equals("load data"))
            System.out.println("Persistence Layer is not implemented yet");
        else
            System.out.println("Command not found. please use 'help' for info or 'q' to quit");
    }

    private static void storeStockReport() {
        int store = getStoreID();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<List<StockReport>> r = is.storeStockReport(store);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<StockReport> stockReports = r.getValue();
            for (StockReport s : stockReports)
                System.out.println(s);
        }
    }

    private static void removeSupplierFromProduct() {
        System.out.println("Which product would you like to remove a supplier from? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("Which supplier would you like to remove? (insert ID)");
        int supplierID = scanner.nextInt();
        Result<Product> r = is.removeSupplierFromProduct(productID, supplierID);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Supplier successfully removed");
            System.out.println(r.getValue());
        }
    }

    private static void addSupplierToProduct() {
        System.out.println("Which product would you like to add a supplier to? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("Which supplier would you like to add? (insert ID)");
        int supplierID = scanner.nextInt();
        System.out.println("What is the supplier's ID for the product?");
        int productIDWithSupplier = scanner.nextInt();
        Result<Product> r = is.addSupplierToProduct(productID, supplierID, productIDWithSupplier);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Supplier successfully added");
            System.out.println(r.getValue());
        }
    }

    private static void removeSale() {
        System.out.println("Which sale would you like to remove? (insert ID)");
        int saleID = scanner.nextInt();
        Result r = is.removeSale(saleID);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("sale successfully removed");
        }
    }

    private static void addProductToStore() {
        int store = getStoreID();
        System.out.println("Which product would you like to add? (insert ID)");
        int product = scanner.nextInt();
        System.out.println("What will be the product's shelves in the store?");
        System.out.println("please insert shelf numbers, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> inStore = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("What will be the product's shelves in the warehouse?");
        System.out.println("please insert shelf numbers, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> inWareHouse = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("What will be the min amount in the store?");
        int min = scanner.nextInt();
        System.out.println("What will be the max amount in the store?");
        int max = scanner.nextInt();
        Result<Product> r = is.addProductToStore(store, inStore, inWareHouse, product, min, max);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Product added");
            System.out.println(r.getValue());
        }
    }

    private static void removeProductFromStore() {
        int store = getStoreID();
        System.out.println("What product would you like to remove?");
        int product = scanner.nextInt();
        Result<Product> r = is.removeProductFromStore(store, product);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Product removed");
            System.out.println(r.getValue());
        }
    }

    private static void getMinStockReport() {
        Result<List<StockReport>> r = is.getMinStockReport();
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.printf("%-30.30s %-30.50s\n", "Store ID", "Product");
            List<StockReport> stockReports = r.getValue();
            for (StockReport s : stockReports)
                System.out.println(s);
        }
    }

    private static void changeProductCategory() {
        System.out.println("Which product would you like to edit? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("To which category would you like to move it? (insert ID)");
        int category = scanner.nextInt();
        Result<Product> r = is.moveProductToCategory(productID, category);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("product successfully moved to new category");
            Product p = r.getValue();
            System.out.println(p);
        }
    }

    private static void changeCatName() {
        System.out.println("Which category would you like to edit?");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please insert new category name");
        String name = scanner.nextLine();
        Result<Category> r = is.editCategoryName(id, name);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Category c = r.getValue();
            System.out.println(c);
        }
    }

    private static void moveItems() {
        int store = getStoreID();
        System.out.println("Which product's items are you moving? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("How much is being moved?");
        int amount = scanner.nextInt();
        Result r = is.moveItems(store, productID, amount);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Moving of items successful");
        }
    }

    private static void addDiscount() {
        System.out.println("Which product received a discount? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("Which supplier gave the discount? (insert ID)");
        int supplier = scanner.nextInt();
        System.out.println("How much of the product was purchased?");
        int amount = scanner.nextInt();
        System.out.println("How much was the original price?");
        int originalPrice = scanner.nextInt();
        System.out.println("How much was paid?");
        int finalPrice = scanner.nextInt();
        System.out.println("When did the discount occur?");
        Date date = getDate();
        if (date==null)
            return;
        System.out.println("Please insert a general description of the discount");
        String description = scanner.nextLine();
        Result<DiscountReport> r = is.addPurchaseFromSupplier(productID, date, supplier, description, amount, finalPrice, originalPrice);
        if (r.isError())
            System.out.println(r.getError());
        else {
            DiscountReport dr = r.getValue();
            System.out.println(dr);
        }
    }

    private static void reportExpired() {
        int store = getStoreID();
        System.out.println("Which product is expired? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("How much of the product is expired?");
        int amount = scanner.nextInt();
        System.out.println("Please enter your ID");
        int employeeID = scanner.nextInt();
        System.out.println("Please add a description (not mandatory)");
        String description = scanner.nextLine();
        Result<DefectiveItemReport> r = is.reportExpired(store, productID, amount, employeeID, description);
        if (r.isError())
            System.out.println(r.getError());
        else {
            DefectiveItemReport eir = r.getValue();
            System.out.println(eir);
            isUnderMin(store, productID);
        }
    }

    private static void expiredItems() {
        System.out.println("Please insert for which items you would like to see expired item history: (choose the corresponding number)");
        System.out.println("1: A product/products");
        System.out.println("2: A category/categories");
        System.out.println("3: A store/number of stores");
        System.out.println("4: all products");
        int expireCase = scanner.nextInt();
        switch (expireCase) {
            case (1):
                expiredItemsByProduct();
                break;
            case (2):
                expiredItemsByCategory();
                break;
            case (3):
                expiredItemsByStore();
                break;
            case (4):
                expiredItemsAll();
                break;
            default:
                System.out.println("Incorrect command, please try again");
        }
    }

    private static void expiredItemsAll() {
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getExpiredItemReportsByStore(start, end, new ArrayList<>());
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport eir : reportList)
                System.out.println(eir);
        }
    }

    private static void expiredItemsByStore() {
        System.out.println("Please insert store IDs, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> storeIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getExpiredItemReportsByStore(start, end, storeIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport eir : reportList)
                System.out.println(eir);
        }
    }

    private static void expiredItemsByCategory() {
        System.out.println("Please insert category IDs, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> categoryIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getExpiredItemReportsByCategory(start, end, categoryIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport eir : reportList)
                System.out.println(eir);
        }
    }

    private static void expiredItemsByProduct() {
        System.out.println("Please insert product IDs, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> productIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getExpiredItemReportsByProduct(start, end, productIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport eir : reportList)
                System.out.println(eir);
        }
    }

    private static void reportDamaged() {
        int store = getStoreID();
        System.out.println("Which product is damaged? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("How much of the product is damaged?");
        int amount = scanner.nextInt();
        System.out.println("Please enter your ID");
        int employeeID = scanner.nextInt();
        System.out.println("Please describe the damage");
        String description = scanner.nextLine();
        Result<DefectiveItemReport> r = is.reportDamaged(store, productID, amount, employeeID, description);
        if (r.isError())
            System.out.println(r.getError());
        else {
            DefectiveItemReport dir = r.getValue();
            System.out.println(dir);
            isUnderMin(store, productID);
        }
    }

    private static void defectiveItems() {
        System.out.println("Please insert for which items you would like to see defect item history: (choose the corresponding number)");
        System.out.println("1: A product/products");
        System.out.println("2: A category/categories");
        System.out.println("3: A store/number of stores");
        System.out.println("4: all products");
        int defectCase = scanner.nextInt();
        switch (defectCase) {
            case (1):
                defectiveItemsByProduct();
                break;
            case (2):
                defectiveItemsByCategory();
                break;
            case (3):
                defectiveItemsByStore();
                break;
            case (4):
                defectiveItemsAll();
                break;
            default:
                System.out.println("Incorrect command, please try again");
        }
    }
    private static void defectiveItemsAll() {
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getDefectiveItemsByStore(start, end, new ArrayList<>());
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
        }
    }

    private static void defectiveItemsByStore() {
        System.out.println("Please insert store IDs, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> storeIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getDefectiveItemsByStore(start, end, storeIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
        }
    }

    private static void defectiveItemsByCategory() {
        System.out.println("Please insert category IDs, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> categoryIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getDefectiveItemsByCategory(start, end, categoryIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
        }
    }

    private static void defectiveItemsByProduct() {
        System.out.println("Please insert product IDs, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> productIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getDefectiveItemsByProduct(start, end, productIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
        }
    }

    private static void damagedItems() {
        System.out.println("Please insert for which items you would like to see damaged item history: (choose the corresponding number)");
        System.out.println("1: A product/products");
        System.out.println("2: A category/categories");
        System.out.println("3: A store/number of stores");
        System.out.println("4: all products");
        int damagedCase = scanner.nextInt();
        switch (damagedCase) {
            case (1):
                damagedItemsByProduct();
                break;
            case (2):
                damagedItemsByCategory();
                break;
            case (3):
                damagedItemsByStore();
                break;
            case (4):
                damagedItemsAll();
                break;
            default:
                System.out.println("Incorrect command, please try again");
        }
    }

    private static void damagedItemsAll() {
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getDamagedItemsReportByStore(start, end, new ArrayList<>());
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
        }
    }

    private static void damagedItemsByStore() {
        System.out.println("Please insert store IDs, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> storeIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getDamagedItemsReportByStore(start, end, storeIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
        }
    }

    private static void damagedItemsByCategory() {
        System.out.println("Please insert category IDs, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> categoryIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getDamagedItemsReportByCategory(start, end, categoryIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
        }
    }

    private static void damagedItemsByProduct() {
        System.out.println("Please insert product IDs, separated by commas");
        System.out.println("For example: 2,4,1,11");
        List<Integer> productIDs = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<List<DefectiveItemReport>> r = is.getDamagedItemsReportByProduct(start, end, productIDs);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DefectiveItemReport> reportList = r.getValue();
            for (DefectiveItemReport dir : reportList)
                System.out.println(dir);
        }
    }

    private static void addStore() {
        Result<Integer> r = is.addStore();
        if (r.isError())
            System.out.println(r.getError());
        else {
            int id = r.getValue();
            System.out.println("new store created, ID is: " + id);
        }
    }

    private static void removeStore() {
        System.out.println("Which store would you like remove?");
        int id = scanner.nextInt();
        Result r = is.removeStore(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Store removed");
        }
    }

    private static void changeCatParent() {
        System.out.println("Which category would you like to edit?");
        int id = scanner.nextInt();
        System.out.println("Please insert new category parent ID");
        int parent = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Category> r = is.changeCategoryParent(id, parent);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Category c = r.getValue();
            System.out.println(c);
        }
    }

    private static void deleteProduct() {
        System.out.println("Which product would you like remove? (insert ID)");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result r = is.deleteProduct(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Product removed");
        }
    }

    private static void changeProductPrice() {
        System.out.println("Which product would you like to edit? (insert ID)");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please insert new product price");
        double price = scanner.nextDouble();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Product> r = is.editProductPrice(id, price);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Product p = r.getValue();
            System.out.println(p);
        }
    }

    private static void changeProductName() {
        System.out.println("Which product would you like to edit? (insert ID)");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please insert new product name");
        String name = scanner.nextLine();
        Result<Product> r = is.editProductName(id, name);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Product p = r.getValue();
            System.out.println(p);
        }
    }

    private static void addCategory() {
        System.out.println("Please insert category name");
        String name = scanner.nextLine();
        System.out.println("Please insert parent category ID, or 0 if there is none");
        int parent = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Category> r = is.addNewCategory(name, parent);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Category c = r.getValue();
            System.out.println(c);
        }
    }

    private static void listProducts() {
        Result<List<Product>> r = is.getProducts();
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Product> productList = r.getValue();
            for (Product p : productList)
                System.out.println(p);
        }
    }

    private static void listCategories() {
        Result<List<Category>> r = is.getCategories();
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Category> categoryList = r.getValue();
            for (Category c : categoryList)
                System.out.println(c);
        }
    }

    private static void addProduct() {
        System.out.println("Please insert product name, categoryID, weight, price, List of <supplierID><space><productID> with spaces between entries, and manufacturerID. Separated by commas, no spaces");
        String[] info = scanner.nextLine().split(",");
        Map<Integer, Integer> suppliersMap = new HashMap<>();
        String[] suppliersStringArray = info[4].split(" ");
        for (int i=0; i< suppliersStringArray.length; i=i+2) {
            suppliersMap.put(Integer.parseInt(suppliersStringArray[i]), Integer.parseInt(suppliersStringArray[i + 1]));
        }
        Result<Product> r = is.newProduct(info[0],Integer.parseInt(info[1]), Integer.parseInt(info[2]), Double.parseDouble(info[3]), suppliersMap, Integer.parseInt(info[5]));
        if (r.isError())
            System.out.println(r.getError());
        else {
            Product p = r.getValue();
            System.out.println(p);
        }
    }

    private static void saleHistoryByProduct() {
        System.out.println("Please insert product ID");
        int id = scanner.nextInt();
        Result<List<Sale>> r = is.getSaleHistoryByProduct(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Sale> saleReport = r.getValue();
            for (Sale s : saleReport)
                System.out.println(s);
        }
    }

    private static void saleHistoryByCategory() {
        System.out.println("Please insert category ID");
        int id = scanner.nextInt();
        Result<List<Sale>> r = is.getSaleHistoryByCategory(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Sale> saleReport = r.getValue();
            for (Sale s : saleReport)
                System.out.println(s);
        }
    }

    private static void getPurchaseFromSupplierHistory() {
        System.out.println("Please insert product ID");
        int id = scanner.nextInt();
        Result<List<DiscountReport>> r = is.getPurchaseFromSupplierHistory(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DiscountReport> discountReports = r.getValue();
            for (DiscountReport dr : discountReports)
                System.out.println(dr);
        }
    }

    private static void getDiscountFromSupplierHistory() {
        System.out.println("Please insert product ID");
        int id = scanner.nextInt();
        Result<List<DiscountReport>> r = is.getDiscountFromSupplierHistory(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<DiscountReport> discountReports = r.getValue();
            for (DiscountReport dr : discountReports)
                System.out.println(dr);
        }
    }

    private static void addSale() {
        System.out.println("Please insert category IDs, separated by ','");
        System.out.println("For example: 12,3,4,1");
        List<Integer> categories = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert product IDs, separated by ',' without spaces");
        System.out.println("For example: 12,3,4,1");
        List<Integer> products = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("Please insert percent of sale: 0-100");
        int percent = scanner.nextInt();
        System.out.println("Please insert start date");
        Date start = getDate();
        if (start==null)
            return;
        System.out.println("Please insert end date");
        Date end = getDate();
        if (end==null)
            return;
        Result<Sale> r = is.addSale(categories, products, percent, start, end);
        if (r.isError())
            System.out.println(r.getError());
        else
            System.out.println(r.getValue());
    }

    private static void listProductsByCategory() {
        System.out.println("Which category would you like to examine?");
        System.out.println("Please insert category IDs, separated by ','");
        System.out.println("For example: 12,3,4,1");
        List<Integer> categories = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        Result<List<Product>> r = is.getProductsFromCategory(categories);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Product> productList = r.getValue();
            for (Product p : productList)
                System.out.println(p);
        }
    }

    private static void returnItems() {
        int storeID = getStoreID();
        System.out.println("Please insert product ID of the product's items you would like to return");
        int productId = scanner.nextInt();
        System.out.println("Please insert amount of items you would like to return");
        int amount = scanner.nextInt();
        System.out.println("Please insert the date the items were bought");
        Date dateBought = getDate();
        if (dateBought==null)
            return;
        Result<Double> r = is.returnItems(storeID, productId, amount, dateBought);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Purchase successful! Total refund is " + r.getValue() + "NIS");
        }
    }

    private static void buyItems() {
        int storeID = getStoreID();
        System.out.println("Please insert product ID of product you would like to buy");
        int productId = scanner.nextInt();
        System.out.println("Please insert amount of product you would like to buy");
        int amount = scanner.nextInt();
        Result<Double> r = is.buyItems(productId, storeID, amount);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Purchase successful! Total price is " + r.getValue() + "NIS");
            isUnderMin(storeID, productId);
        }
    }

    private static void addItems() {
        int storeID = getStoreID();
        System.out.println("Please insert product ID of product you are receiving");
        int productId = scanner.nextInt();
        System.out.println("Please insert amount of product you have received");
        int amount = scanner.nextInt();
        Result r = is.addItems(storeID, productId, amount);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Process complete");
        }
    }

    private static void help() {
        //addSupplier
        //removeSupplier
        System.out.println("Welcome to help session");
        System.out.println("Please notice that commands are case sensitive");
        System.out.println("Possible commands are:");

        System.out.printf("%-30.30s %-30.50s\n", "list products", "list of all the products in catalog");
        System.out.printf("%-30.30s %-30.50s\n", "list categories", "list of all the different categories");
        System.out.printf("%-30.30s %-30.50s\n", "list products in category", "list all products in specified category/ies");

        System.out.printf("%-30.30s %-30.50s\n", "add items", "add items to a store");
        System.out.printf("%-30.30s %-30.50s\n", "move items", "move product's items from the warehouse to the store");
        System.out.printf("%-30.30s %-30.50s\n", "buy items", "buy items from a store");
        System.out.printf("%-30.30s %-30.50s\n", "return items", "return a previously purchased product's items to the store");

        System.out.printf("%-30.30s %-30.50s\n", "add product to store", "add a product to specific store in system");
        System.out.printf("%-30.30s %-30.50s\n", "remove product from store", "add a product to specific store in system");

        System.out.printf("%-30.30s %-30.50s\n", "add sale", "create a new sale");
        System.out.printf("%-30.30s %-30.50s\n", "remove sale", "remove future or current sale from the system");

        System.out.printf("%-30.30s %-30.50s\n", "add product", "add a new product");
        System.out.printf("%-30.30s %-30.50s\n", "delete product", "delete product from catalog");
        System.out.printf("%-30.30s %-30.50s\n", "change product name", "change product name");
        System.out.printf("%-30.30s %-30.50s\n", "change product price", "change product price");

        System.out.printf("%-30.30s %-30.50s\n", "add category", "create a new category");
        System.out.printf("%-30.30s %-30.50s\n", "change category parent", "change a category's \"parent\" category");
        System.out.printf("%-30.30s %-30.50s\n", "change category name", "change a category's name");
        System.out.printf("%-30.30s %-30.50s\n", "change product category", "move a product to a new category");

        System.out.printf("%-30.30s %-30.50s\n", "min stock report", "prints stock report of items under the min amount");
        System.out.printf("%-30.30s %-30.50s\n", "store stock report", "report of stock in specified store");
        System.out.printf("%-30.30s %-30.50s\n", "sale history by product", "see history of sales on a specific product");
        System.out.printf("%-30.30s %-30.50s\n", "sale history by category", "see history of sales on a specific category");
        System.out.printf("%-30.30s %-30.50s\n", "purchase from supplier history", "see history of all purchases from suppliers");
        System.out.printf("%-30.30s %-30.50s\n", "discount from supplier history", "see history of all discounts from suppliers");
        System.out.printf("%-30.30s %-30.50s\n", "add discount from supplier", "input a purchase of stock from supplier");

        System.out.printf("%-30.30s %-30.50s\n", "report expired", "report finding of expired items");
        System.out.printf("%-30.30s %-30.50s\n", "report damaged", "report finding of damaged items");
        System.out.printf("%-30.30s %-30.50s\n", "expired items", "print a report of expired items");
        System.out.printf("%-30.30s %-30.50s\n", "damaged items", "print a report of damaged items");
        System.out.printf("%-30.30s %-30.50s\n", "defective items", "print a report of defective (damaged and expired together) items");

        System.out.printf("%-30.30s %-30.50s\n", "add supplier to product", "adds a supplier as one of the product's suppliers");
        System.out.printf("%-30.30s %-30.50s\n", "remove supplier from product", "removes a supplier from list of product's suppliers");

        System.out.printf("%-30.30s %-30.50s\n", "add store", "add a new store to the system");
        System.out.printf("%-30.30s %-30.50s\n", "remove store", "remove store from the system");

        System.out.printf("%-30.30s %-30.50s\n", "help", "prints this menu");
        System.out.printf("%-30.30s %-30.50s\n", "q", "quits program");

//        System.out.println("load data", "Persistence Layer is not implemented yet");
    }
}
