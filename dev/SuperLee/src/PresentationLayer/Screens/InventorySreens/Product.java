package PresentationLayer.Screens.InventorySreens;

import Domain.ServiceLayer.InventoryObjects.*;
import Domain.ServiceLayer.Result;
import PresentationLayer.Screens.Screen;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Product extends Screen {
    private static final String[] menuOptions = {
            "Print employment conditions",  //1
            "Update name",                  //2
            "Update bank details",          //3
            "Update salary per shift",      //4
            "Update certifications",        //5
            "Calculate Salary",             //6
            "Manage work constraints",      //7
            "Print upcoming shifts"         //8
    };

    private final int id;
    private final String name;
    private final int categoryID;
    private final double originalPrice;
    private final double currentPrice;
    private String weight;
    private int manufacturerID;

    public Product(Screen caller, Domain.ServiceLayer.InventoryObjects.Product sProduct, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
        this.id = sProduct.getId();
        this.name = sProduct.getName();
        this.categoryID = sProduct.getCategoryID();
        this.originalPrice = sProduct.getOriginalPrice();
        this.currentPrice = sProduct.getCurrentPrice();
        this.weight = sProduct.getWeight();
        this.manufacturerID = sProduct.getManufacturerID();
    }

    public void run() {
        System.out.println("\nWelcome to the Management Menu of " + name + "!");
        int option = 0;
        while (option != 9) {
            option = runMenu();
            try {
                if (option <= 8)
                    handleBaseOptions(option);
                else if (option == 9)
                    endRun();
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }


    protected void handleBaseOptions(int option) throws Exception {
        switch (option) {
            case 1:
//                System.out.println(employmentConditions);
                break;
            case 2:
//                updateName();
                break;
            case 3:
//                updateBankDetails();
                break;
            case 4:
//                updateSalary();
                break;
            case 5:
//                updateCertifications();
                break;
            case 6:
//                calculateSalary();
                break;
            case 7:
//                manageConstraints();
                break;
            case 8:
//                printUpcomingShifts();
        }
    }





    public void changeProductMin() {
        System.out.println("What Product would you like to edit?");
        int product = scanner.nextInt();
        int store = getStoreID();
        System.out.println("What would you like the new min amount to be?");
        int min = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.changeProductMin(store, product, min);
        if (r.isError()) {
            System.out.println(r.getError());
        }
        else {
            System.out.println("Min changed successfully");
            System.out.println(r.getValue());
        }
    }

    public void changeProductMax() {
        System.out.println("What Product would you like to edit?");
        int product = scanner.nextInt();
        int store = getStoreID();
        System.out.println("What would you like the new max amount to be?");
        int max = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.changeProductMax(store, product, max);
        if (r.isError()) {
            System.out.println(r.getError());
        }
        else {
            System.out.println("Max changed successfully");
            System.out.println(r.getValue());
        }
    }

//    public void removeSupplierFromProduct() {
//        System.out.println("Which product would you like to remove a supplier from? (insert ID)");
//        int productID = scanner.nextInt();
//        System.out.println("Which supplier would you like to remove? (insert ID)");
//        int supplierID = scanner.nextInt();
//        scanner.nextLine(); //to remove extra \n
//        Result<Domain.ServiceLayer.Objects.Product> r = controller.removeSupplierFromProduct(productID, supplierID);
//        if (r.isError())
//            System.out.println(r.getError());
//        else {
//            System.out.println("Supplier successfully removed");
//            System.out.println(r.getValue());
//        }
//    }

//    public void addSupplierToProduct() {
//        System.out.println("Which product would you like to add a supplier to? (insert ID)");
//        int productID = scanner.nextInt();
//        System.out.println("Which supplier would you like to add? (insert ID)");
//        int supplierID = scanner.nextInt();
//        System.out.println("What is the supplier's ID for the product?");
//        int productIDWithSupplier = scanner.nextInt();
//        scanner.nextLine(); //to remove extra \n
//        Result<Domain.ServiceLayer.Objects.Product> r = controller.addSupplierToProduct(productID, supplierID, productIDWithSupplier);
//        if (r.isError())
//            System.out.println(r.getError());
//        else {
//            System.out.println("Supplier successfully added");
//            System.out.println(r.getValue());
//        }
//    }

    public void addProductToStore() {
        int store = getStoreID();
        System.out.println("Which product would you like to add? (insert ID)");
        int product = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        System.out.println("What will be the product's shelves in the store? (please insert shelf numbers, separated by commas without spaces)");
        List<Integer> inStore = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("What will be the product's shelves in the warehouse? (please insert shelf numbers, separated by commas without spaces)");
        List<Integer> inWareHouse = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        System.out.println("What will be the min amount in the store?");
        int min = scanner.nextInt();
        System.out.println("What will be the max amount in the store?");
        int max = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.addProductToStore(store, inStore, inWareHouse, product, min, max);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Product added");
            System.out.println(r.getValue());
        }
    }

    public void removeProductFromStore() {
        int store = getStoreID();
        System.out.println("What product would you like to remove?");
        int product = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.removeProductFromStore(store, product);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Product removed");
            System.out.println(r.getValue());
        }
    }

    public void changeProductCategory() {
        System.out.println("Which product would you like to edit? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("To which category would you like to move it? (insert ID)");
        int category = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.moveProductToCategory(productID, category);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("product successfully moved to new category");
            Domain.ServiceLayer.InventoryObjects.Product p = r.getValue();
            System.out.println(p);
        }
    }

    public void moveItems() {
        int store = getStoreID();
        System.out.println("Which product's items are you moving? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("How much is being moved?");
        int amount = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result r = controller.moveItems(store, productID, amount);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Moving of items successful");
        }
    }

    public void addItems() {
        int storeID = getStoreID();
        System.out.println("Which product was purchased? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("Which supplier was the purchase from? (insert ID)");
        int supplier = scanner.nextInt();
        System.out.println("How much of the product was purchased?");
        int amount = scanner.nextInt();
        System.out.println("How much was the original price?");
        int originalPrice = scanner.nextInt();
        System.out.println("How much was paid?");
        int finalPrice = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please insert a general description of the purchase");
        String description = scanner.nextLine();
        Result<PurchaseFromSupplierReport> r = controller.addItems(storeID); //needs to be changed to orderID
        if (r.isError())
            System.out.println(r.getError());
        else {
            PurchaseFromSupplierReport dr = r.getValue();
            System.out.println("Purchase added to system successfully");
            System.out.println(dr);
        }
    }

    public void deleteProduct() {
        System.out.println("Which product would you like remove? (insert ID)");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result r = controller.deleteProduct(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Product removed");
        }
    }

    public void changeProductPrice() {
        System.out.println("Which product would you like to edit? (insert ID)");
        int id = scanner.nextInt();
        System.out.println("Please insert new product price");
        double price = scanner.nextDouble();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.editProductPrice(id, price);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.InventoryObjects.Product p = r.getValue();
            System.out.println(p);
        }
    }

    public void changeProductName() {
        System.out.println("Which product would you like to edit? (insert ID)");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please insert new product name");
        String name = scanner.nextLine();
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.editProductName(id, name);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.InventoryObjects.Product p = r.getValue();
            System.out.println(p);
        }
    }

    public void addProduct() {
        System.out.println("Please insert product name, categoryID, weight, price, List of <supplierID><space><productID> with spaces between entries, and manufacturerID. Separated by commas, no spaces");
        String[] info = scanner.nextLine().split(",");
        Map<Integer, Integer> suppliersMap = new HashMap<>();
        String[] suppliersStringArray = info[4].split(" ");
        for (int i=0; i< suppliersStringArray.length; i=i+2) {
            suppliersMap.put(Integer.parseInt(suppliersStringArray[i]), Integer.parseInt(suppliersStringArray[i + 1]));
        }
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.newProduct(info[0],Integer.parseInt(info[1]), Integer.parseInt(info[2]), Double.parseDouble(info[3]), suppliersMap, Integer.parseInt(info[5]));
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.InventoryObjects.Product p = r.getValue();
            System.out.println(p);
        }
    }
}
