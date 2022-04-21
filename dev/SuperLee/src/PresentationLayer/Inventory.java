package PresentationLayer;

import ServiceLayer.InventoryService;
import ServiceLayer.Objects.Category;
import ServiceLayer.Objects.DiscountReport;
import ServiceLayer.Objects.Product;
import ServiceLayer.Objects.Sale;
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
        System.out.println(help());
        while (!quit) {
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
        System.out.println("Please insert store ID of store you are in. Current store IDs are:");
        System.out.println(is.getStoreIDs());
        return scanner.nextInt();
    }

    private static void doCommand(String command) {
        if (command.equals("list products"))
            listProducts();
        else if (command.equals("list categories"))
            listCategories();
        else if (command.equals("add product"))
            addProduct();
        else if (command.equals("sale history by product"))
            saleHistoryByProduct();
        else if (command.equals("sale history by category"))
            saleHistoryByCategory();
        else if (command.equals("discount history"))
            discountHistory();
        else if (command.equals("add sale"))
            addSale();
        else if (command.equals("list products in category"))
            listProductsByCategory();
        else if (command.equals("return item"))
            returnItem();
        else if (command.equals("buy items"))
            buyItems();
        else if (command.equals("add items"))
            addItems();
        else if (command.equals("remove items"))
            addItems();
        else if (command.equals("help"))
            help();
        else if (command.equals("load data"))
            System.out.println("Persistence Layer is not implemented yet");
        else
            System.out.println("Command not found. please use 'help' for info or 'q' to quit");
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
        System.out.println("Please insert product name, categoryID, weight, and price. Separated by commas, no spaces");
        String[] info = scanner.nextLine().split(",");
        Result<Product> r = is.newProduct(info[0],Integer.parseInt(info[1]), Integer.parseInt(info[2]),Double.parseDouble(info[3]),new ArrayList<>());
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

    private static void discountHistory() {
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
        int categoryID = scanner.nextInt();
        Result<List<Product>> r = is.getProductsFromCategory(categoryID);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Product> productList = r.getValue();
            for (Product p : productList)
                System.out.println(p);
        }
    }

    private static void returnItem() {
        int storeID = getStoreID();
        System.out.println("Please insert product ID of product you would like to return");
        int productId = scanner.nextInt();
        System.out.println("Please insert amount of product you would like to return");
        int amount = scanner.nextInt();
        System.out.println("Please insert date items were bought");
        Date dateBought = getDate();
        if (dateBought==null)
            return;
        Result<Double> r = is.returnProduct(storeID, productId, amount, dateBought);
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

    private static String help() {
        return "This will be the guide to what commands are available";
    }
}
