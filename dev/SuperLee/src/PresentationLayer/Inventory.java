package PresentationLayer;

import ServiceLayer.InventoryService;
import ServiceLayer.Objects.Category;
import ServiceLayer.Objects.Product;
import ServiceLayer.Objects.SaleReport;
import ServiceLayer.Result;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Inventory {
    public static InventoryService is = new InventoryService();
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Welcome to the Inventory Mangaer!");
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
            doCommand(command);
        }
    }

    private static void doCommand(String command) {
        if (command.equals("list products")) {
            Result<List<Product>> r = is.getProducts();
            if (r.isError())
                System.out.println(r.getError());
            else {
                List<Product> productList = r.getValue();
                for (Product p : productList)
                    System.out.println(p);
            }
        }
        else if (command.equals("sale history by product")) {
            System.out.println("Please insert product ID");
            int id = scanner.nextInt();
            Result<SaleReport> r = is.getSaleHistoryByProduct(id);
            if (r.isError())
                System.out.println(r.getError());
            else {
                SaleReport report = r.getValue();
                System.out.println(report);
            }
        }
        else if (command.equals("add sale")) {
//            System.out.println("Please insert category IDs, separated by ',' without spaces");
//            System.out.println("For example: 12,3,4,1");
//            String[] categories = scanner.nextLine().split(",");
//            System.out.println("Please insert product IDs, separated by ',' without spaces");
//            System.out.println("For example: 12,3,4,1");
//            Result<SaleReport> r = is.addSale(categories, products, percent, start, end);
        }
        else if (command.equals("list products in category")) {
            System.out.println("Please insert product ID");
            int id = scanner.nextInt();
            Result<List<Product>> r = is.getProductsFromCategory(id);
            if (r.isError())
                System.out.println(r.getError());
            else {
                List<Product> productList = r.getValue();
                for (Product p : productList)
                    System.out.println(p);
            }
        }
        else if (command.equals("return item")) {
        }
        else if (command.equals("buy item")) {
        }
        else if (command.equals("add sale")) {
        }
    }

    private static String help() {
        return "This will be the guide to what commands are available";
    }
}
