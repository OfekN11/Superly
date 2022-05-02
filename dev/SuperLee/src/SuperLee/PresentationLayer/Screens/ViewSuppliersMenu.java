package SuperLee.PresentationLayer.Screens;

import SuperLee.ServiceLayer.ServiceSupplierObject;

public class ViewSuppliersMenu extends Screen {


    //FOR TESTING!!
    private int supplierId = -1;

    private static final String[] menuOptions = {
            "View Information",     //1
            "Edit card",          //2
            "View agreement",           //3
            "New agreement",           //4
            "View contacts",       //5
            "View represented manufacturers",            //6
            "View order", //7
            "Exit"                          //8
    };



    public ViewSuppliersMenu(Screen caller) {
        super(caller, menuOptions);
    }


    @Override
    public void run() {
        System.out.println("\nHere you can view all the Supplier's info");

        try {
            if(controller.isSuppliersEmpty()){
                System.out.println("NO SUPPLIERS ARE AVAILABLE!\nPress \"Enter\" to return.");
                scanner.nextLine();
                return;
            }
        } catch (Exception e) {
            System.out.println("Something went wrong, please try again");
        }

        getSupplierId();

        int option = 0;
        while (option != 8) {
            option = runMenu();
            try {
                switch (option) {
                    case 1:
                        printSupplierInfo();
                        break;
                    case 2:
                        //Passing the id in the constructor, need to check what happens if we change the ID!!
                        new Thread(new EditCardScreen(this, supplierId)).start();
                        break;
                    case 3:
                        new Thread ( new ViewAgreement(this, supplierId)).start();
                        break;
                    case 4:
                        addNewAgreement(supplierId);
                        break;
                    case 5:
                        new Thread(new ViewContacts(this, supplierId)).start();
                        break;
                    case 6:
                        new Thread( new ViewManufacturers(this, supplierId)).start();
                        break;
                    case 7:
                        new Thread( new ViewOrder(this, supplierId)).start();
                        break;
                    case 8:
                        endRun();
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }


    private void printSupplierInfo() {
        boolean correctInput = false, _continue = true;
        while(!correctInput){

            if(supplierId == -1){
                System.out.println("\n\n");
                return;
            }

            ServiceSupplierObject r = null;
            try {
                r = controller.getSupplierInfo(supplierId);
            } catch (Exception e) {
                System.out.println("please try again.");
            }

            if(r != null){
                System.out.println(r.toString());
                correctInput = true;
            }
            else{
                System.out.println("please try again.");
            }
        }

    }

    private void getSupplierId() {
        System.out.println("Insert the supplier ID you wish to view, then press \"Enter\" to continue.\n");
        System.out.println("If you wish to return, type \"-1\" and press \"Enter\"");
        int input, supplierID = -1;
        boolean correctInput, _continue = true;
        correctInput = false;

        while(!correctInput){
            input = getInput();

            if(input == -1){
                System.out.println("\n\n");
                return;
            }

            boolean r = false;
            try {
                r = controller.doesSupplierExists(input);
            } catch (Exception e) {
                e.getMessage();
            }

            if(r){
                System.out.println("You are now watching suppiler " + input);
                supplierId = input;
                correctInput = true;
                supplierID = input;
            }
            else{
                System.out.println("No such supplier or your input is incorrect, please try again.");
            }
        }
    }

    private void addNewAgreement(int supplierId) {
        int input = -1;
        boolean correctInput = false;
        String stringInput = "";

        System.out.println("Insert the number of the option you wish to choose, then press \"Enter\"");
        System.out.println("1) Routine delivery");
        System.out.println("2) By order delivery");
        System.out.println("3) Self-transport");
        System.out.println("4) Back");


        while(!correctInput){
            input = getInput();

            if(input == 4){
                System.out.println("Returning..\n\n");
                return;
            }

            if(input == 1 || input == 2 || input == 3){
                correctInput = true;
            }
            else{
                System.out.println("Wrong value was inserted, please try again.\n\n");
            }
        }

        if(input == 1){
            System.out.println("Insert the delivery days as numbers with whitespaces in between. For example:");
            System.out.println("1 2 5");
            System.out.println("Meaning: Sunday, Monday, Thursday");

            stringInput = scanner.nextLine();
        }
        if(input == 2){
            System.out.println("Insert the number of days to deliver:");
            stringInput = scanner.nextLine();
        }

        try {
            if(controller.addAgreement(supplierId, input, stringInput)){
                System.out.println("A problem has occurred, please try again later");
                return;
            }
        } catch (Exception e) {
            e.getMessage();
        }

        System.out.println("Now, let's add the items included in the agreement.");
        //addItemToAgreement(supplierId);

        System.out.println("All is done, returning\n\n");


    }


}
