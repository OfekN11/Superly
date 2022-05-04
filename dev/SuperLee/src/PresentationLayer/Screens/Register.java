package PresentationLayer.Screens;

import Domain.ServiceLayer.Result;

import java.util.Arrays;
import java.util.stream.Stream;

public class Register extends Screen{
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

    public Register(Screen caller, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new), "Register");
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
    private void buyItems() {
        int storeID = getStoreID();
        System.out.println("Please insert product ID of product you would like to buy");
        int productId = scanner.nextInt();
        System.out.println("Please insert amount of product you would like to buy");
        int amount = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Double> r = controller.buyItems(storeID, productId, amount);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("Purchase successful! Total price is " + round(r.getValue()) + "NIS");
            isUnderMin(storeID, productId);
        }
    }
}
