package PresentationLayer.Screens.SupplierScreens;

import PresentationLayer.Screens.Screen;

public class EditCardScreen extends Screen {

    private int supplierID;

    private static final String[] menuOptions = {
            "Edit ID",          //1
            "Edit Bank Number",           //2
            "Edit Address",           //3
            "Edit Name",       //4
            "Edit Paying Agreement",            //5
            "Exit",        //6
    };

//            System.out.println("1) ID\n2) Bank number\n3) Address\n4) Name\n5) Paying agreement\n6) Back\n");

    public EditCardScreen(Screen caller, int id) {
        super(caller, menuOptions);
        supplierID = id;
    }

    @Override
    public void run() {
        System.out.println("\nHere you can change the Card's info");
        int option = 0;
        while (option != 6) {
            option = runMenu();
            try {
                switch (option) {
                    case 1:
                        supplierID = editID(supplierID);
                        break;
                    case 2:
                        editBunkNumber(supplierID);
                        break;
                    case 3:
                        editAddress(supplierID);
                        break;
                    case 4:
                        editName(supplierID);
                        break;
                    case 5:
                        editPayingAgreement(supplierID);
                        break;
                    case 6:
                        System.out.println("Returning..");
                        endRun();
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }



    private int editID(int supplierID){
        int input, newID = -1;
        boolean correctInput = false;

        while(!correctInput){
            System.out.println("Insert the new id please.");

            System.out.println("If you want to go back, please insert \"-1\".\n");
            input = getInput();

            if(input == -1){
                System.out.println("Returning..\n");
                return supplierID;
            }


            boolean r = false;
            try {
                r = controller.updateSupplierID(supplierID, input);
            } catch (Exception e) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }

            if(r){
                correctInput = true;
                newID = input;
            }
            else{
                System.out.println("Something went wrong, please try again.");
            }
        }

        System.out.println("ID was changed successfully.\n\n");
        return newID;

    }

    private void editBunkNumber(int supplierID){
        int input;
        boolean correctInput = false;

        while(!correctInput){
            System.out.println("Insert the new bunk number please.");
            System.out.println("If you want to go back, please insert \"-1\".\n");
            input = getInput();

            if(input == -1){
                System.out.println("Returning..\n");
                return;
            }

            boolean r = false;
            try {
                r = controller.updateSupplierBankNumber(supplierID, input);
            } catch (Exception e) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }

            if(r){
                correctInput = true;
            }
            else{
                System.out.println("Something went wrong, please try again.");
            }
        }

        System.out.println("Bunk number was changed successfully.\n\n");
    }

    private void editAddress(int supplierID){
        String input;
        boolean correctInput = false;

        while(!correctInput){
            System.out.println("Insert the new address please.");
            System.out.println("If you want to go back, please insert \"-1\".\n");
            input = scanner.nextLine();

            if(input.equals("-1")){
                System.out.println("Returning..\n");
                return;
            }

            boolean r = false;
            try {
                r = controller.updateSupplierAddress(supplierID, input);
            } catch (Exception e) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }

            if(r){
                correctInput = true;
            }
            else{
                System.out.println("Something went wrong, please try again.");
            }
        }

        System.out.println("Address was changed successfully.\n\n");
    }

    private void editName(int supplierID){
        String input;
        boolean correctInput = false;

        while(!correctInput){
            System.out.println("Insert the new name please.");
            System.out.println("If you want to go back, please insert \"-1\".\n");
            input = scanner.nextLine();

            if(input.equals("-1")){
                System.out.println("Returning..\n");
                return;
            }

            boolean r = false;
            try {
                r = controller.updateSupplierName(supplierID, input);
            } catch (Exception e) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }

            if(r){
                correctInput = true;
            }
            else{
                System.out.println("Something went wrong, please try again.");
            }
        }

        System.out.println("Name was changed successfully.\n\n");
    }

    private void editPayingAgreement(int supplierID){
        String input;
        boolean correctInput = false;

        while(!correctInput){
            System.out.println("Insert the new paying agreement please.");
            System.out.println("If you want to go back, please insert \"-1\".\n");
            input = scanner.nextLine();

            if(input.equals("-1")){
                System.out.println("Returning..\n");
                return;
            }

            boolean r = false;
            try {
                r = controller.updateSupplierPayingAgreement(supplierID, input);
            } catch (Exception e) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }

            if(r){
                correctInput = true;
            }
            else{
                System.out.println("Something went wrong, please try again.");
            }
        }

        System.out.println("The paying agreement was changed successfully.\n\n");
    }

}
