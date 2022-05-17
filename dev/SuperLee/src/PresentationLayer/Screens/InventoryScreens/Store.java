package PresentationLayer.Screens.InventoryScreens;

import Domain.ServiceLayer.Result;
import Domain.ServiceLayer.SupplierObjects.ServiceOrderObject;
import Globals.Pair;
import PresentationLayer.Screens.Screen;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Stream;

public class Store extends Screen {
    private static final String[] menuOptions = {
            "Manage Sales",  //1
            "Buy Items",                  //2
            "Return Items",          //3
            "Order Arrived",      //4
            "Add Store",        //5
            "Remove Store",             //6
            "exit",      //7
    };

    public Store(Screen caller, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
    }

    public void run() {
        System.out.println("\nWelcome to the Management Menu of Store!");
        int option = 0;
        while (option != 7 && option != 1) {
            option = runMenu();
            try {
                if (option <= 7)
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


    private void handleBaseOptions(int option) throws Exception {
        switch (option) {
            case 1:
                new Thread(new Sales(this, new String[]{})).start();
                break;
            case 2:
                buyItems();
                break;
            case 3:
                returnItems();
                break;
            case 4:
                addItems();
                break;
            case 5:
                addStore();
                break;
            case 6:
                removeStore();
                break;
            case 7:
                endRun();
        }
    }
    private void removeStore() {
        System.out.println("Which store would you like remove?");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result r = controller.removeStore(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Store removed");
        }
    }

    private void addStore() {
        Result<Integer> r = controller.addStore();
        if (r.isError())
            System.out.println(r.getError());
        else {
            int id = r.getValue();
            System.out.println("new store created, ID is: " + id);
        }
    }

    private void addItems() {
        System.out.println("Please insert the ID of the arrived order");
        int orderID = scanner.nextInt();
        System.out.println("Please insert the ID of the supplier of the arrived order");
        int supplierID = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Object> r = controller.orderArrived(orderID, supplierID);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Order inserted into system successfully");
        }
    }

    private void buyItems() {
        int storeID = getStoreID();
        System.out.println("Please insert product ID of product you would like to buy");
        int productId = scanner.nextInt();
        System.out.println("Please insert amount of product you would like to buy");
        int amount = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Pair<Double, String>> r = controller.buyItems(storeID, productId, amount);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Purchase successful! Total price is " + round(r.getValue().getFirst()) + "NIS");
            if (r.getValue().getSecond()!=null)
                System.out.println(r.getValue().getSecond());
        }
    }

    private void returnItems() {
        int storeID = getStoreID();
        System.out.println("Please insert product ID of the product's items you would like to return");
        int productId = scanner.nextInt();
        System.out.println("Please insert amount of items you would like to return");
        int amount = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please insert the date the items were bought");
        LocalDate dateBought = getDate();
        if (dateBought==null)
            return;
        Result<Double> r = controller.returnItems(storeID, productId, amount, dateBought);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Total refund is " + round(r.getValue()) + "NIS");
        }
    }
}
