package PresentationLayer.Screens.SupplierScreens;

import PresentationLayer.Screens.Screen;
import Domain.ServiceLayer.Suppliers.ServiceOrderObject;

public class ViewOrder extends Screen {

    private int supplierId;
    private static final String[] menuOptions = {
            "View Order",     //1
            "Make new Order",     //2
            "Exit"            //3
    };

    public ViewOrder(Screen caller, int supplierId) {
        super(caller, menuOptions);
        this.supplierId = supplierId;
    }

    @Override
    public void run() {
        System.out.println("\nHere you can view Orders and make new Ones!");
        int option = 0;
        while (option != 3) {
            option = runMenu();
            try {
                switch (option) {
                    case 1:
                        viewOrder();
                        break;
                    case 2:
                        makeOrder();
                        break;
                    case 3:
                        endRun();
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }

    }

    private void makeOrder() {
        int input;
        boolean correctInput = false;

        System.out.println("Insert the ID of the new order:");
        System.out.println("If you wish to return, please insert \"-1\"");

        input = getInput();

        if(input == -1){
            System.out.println("Returning..\n");
            return;
        }

        boolean r = false;
        try {
            r = controller.order(supplierId, input);
        } catch (Exception e) {
            System.out.println(e.getMessage());;
        }

        if(!r){
            System.out.println("Returning, please try again.\n");
            return;
        }

        addItemsToOrder(supplierId, input);

        System.out.println("Order was placed successfully, returning.\n");
    }

    private void addItemsToOrder(int supplierID, int orderId) {
        boolean _continue = true, correctInput;
        int itemID, quantity, input;

        System.out.println("Now we shall add items to your new order.");
        System.out.println("PLEASE NOTICE: the order must contain at least one item.\n");

        while (_continue){
            System.out.println("Please insert the following details:");
            System.out.println("ID:");

            itemID = getInput();

            System.out.println("Quantity:");

            quantity = getInput();

            boolean r = false;
            try {
                r = controller.addItemToOrder(supplierID, orderId, itemID, quantity);
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }

            if(r){
                System.out.println("Choose:");
                System.out.println("1) Add another item");
                System.out.println("2) Back");

                correctInput = false;

                while(!correctInput){
                    input = getInput();

                    switch (input){
                        case 1: {
                            correctInput = true;
                            break;
                        }
                        case 2: {
                            _continue = false;
                            correctInput = true;
                            break;
                        }
                        default: {
                            System.out.println("Wrong value was inserted, please try again.");
                        }
                    }
                }
            }
            else{
                System.out.println("Please try again.\n");
            }


        }
    }

    private void viewOrder() {
        int input;

        System.out.println("Insert the ID of the order you wish to watch.");
        System.out.println("If you want to return, insert \"-1\"");

        input = getInput();

        if (input == -1) {
            System.out.println("Returning..\n");
            return;
        }
        try {
            ServiceOrderObject r = controller.getOrder(supplierId, input);
            if(r != null){
                System.out.println("\n");
                System.out.println(r.toString());
            }
            else{
                System.out.println("Order doesn't exist!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }


    }
}
