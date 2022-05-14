package PresentationLayer.Screens.InventorySreens;

import Domain.ServiceLayer.InventoryObjects.*;
import Domain.ServiceLayer.Result;
import PresentationLayer.Screens.Screen;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Product extends Screen {
    private static final String[] menuOptions = {
            "Update Name",                  //1
            "Update Min Amount",          //2
            "Update Target Amount",      //3
            "Add To Store",        //4
            "Remove From Store",             //5
            "Change Category",      //6
            "Move From Warehouse to Store",         //7
            "Change Price",         //8
            "Delete Product",         //9
            "exit" //10
    };

    private final int id;
    private String name;
    private int categoryID;
    private double originalPrice;
    private double currentPrice;
    private String weight;
    private String manufacturer;

    public Product(Screen caller, String[] extraMenuOptions, Domain.ServiceLayer.InventoryObjects.Product sProduct) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
        this.id = sProduct.getId();
        this.name = sProduct.getName();
        this.categoryID = sProduct.getCategoryID();
        this.originalPrice = sProduct.getOriginalPrice();
        this.currentPrice = sProduct.getCurrentPrice();
        this.weight = sProduct.getWeight();
        this.manufacturer = sProduct.getManufacturer();
    }

    public void run() {
        System.out.println("\nWelcome to the Management Menu of " + name + "!");
        int option = 0;
        while (option != 10) {
            option = runMenu();
            try {
                if (option <= 10)
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
                changeProductName();
                break;
            case 2:
                changeProductMin();
                break;
            case 3:
                changeProductMax();
                break;
            case 4:
                addProductToStore();
                break;
            case 5:
                removeProductFromStore();
                break;
            case 6:
                changeProductCategory();
                break;
            case 7:
                moveItems();
                break;
            case 8:
                changeProductPrice();
                break;
            case 9:
                deleteProduct();
                break;
            case 10:
                endRun();
        }
    }

    public void changeProductMin() {
        int store = getStoreID();
        System.out.println("What would you like the new min amount to be?");
        int min = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.changeProductMin(store, id, min);
        if (r.isError()) {
            System.out.println(r.getError());
        }
        else {
            System.out.println("Min changed successfully");
            System.out.println(r.getValue());
        }
    }

    public void changeProductMax() {
        int store = getStoreID();
        System.out.println("What would you like the new max amount to be?");
        int max = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.changeProductMax(store, id, max);
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
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.addProductToStore(store, inStore, inWareHouse, id, min, max);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Product added");
            System.out.println(r.getValue());
        }
    }

    public void removeProductFromStore() {
        int store = getStoreID();
        scanner.nextLine(); //to remove extra \n
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.removeProductFromStore(store, id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Product removed");
            System.out.println(r.getValue());
        }
    }

    public void changeProductCategory() {
        System.out.println("To which category would you like to move it? (insert ID)");
        int category = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.moveProductToCategory(id, category);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("product successfully moved to new category");
            Domain.ServiceLayer.InventoryObjects.Product p = r.getValue();
            System.out.println(p);
            this.categoryID=p.getCategoryID();
        }
    }

    public void moveItems() {
        int store = getStoreID();
        System.out.println("How much is being moved?");
        int amount = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result r = controller.moveItems(store, id, amount);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Moving of items successful");
        }
    }

    public void deleteProduct() {
        Result r = controller.deleteProduct(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Product removed");
        }
    }

    public void changeProductPrice() {
        System.out.println("Please insert new product price");
        double price = scanner.nextDouble();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.editProductPrice(id, price);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.InventoryObjects.Product p = r.getValue();
            originalPrice = p.getOriginalPrice();
            System.out.println(p);
        }
    }

    public void changeProductName() {
        System.out.println("Please insert new product name");
        String name = scanner.nextLine();
        Result<Domain.ServiceLayer.InventoryObjects.Product> r = controller.editProductName(id, name);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.InventoryObjects.Product p = r.getValue();
            System.out.println(p);
            this.name=p.getName();
        }
    }


}
