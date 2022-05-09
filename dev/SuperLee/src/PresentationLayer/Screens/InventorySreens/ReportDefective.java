package PresentationLayer.Screens.InventorySreens;

import Domain.ServiceLayer.InventoryObjects.DefectiveItemReport;
import Domain.ServiceLayer.Result;
import Globals.Pair;
import PresentationLayer.Screens.Screen;

import java.util.Arrays;
import java.util.stream.Stream;

public class ReportDefective extends Screen {
    private static final String[] menuOptions = {
            "Report Damaged Items",  //1
            "Report Expired Items",  //2
            "exit"        //3
    };

    public ReportDefective(Screen caller, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
    }

    public void run() {
        System.out.println("\nPlease choose action");
        int option = 0;
        while (option != 3) {
            option = runMenu();
            try {
                if (option <= 3)
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
                reportDamaged();
                break;
            case 2:
                reportExpired();
                break;
            case 3:
                endRun();
        }
    }

    public void reportDamaged() {
        int store = getStoreID();
        System.out.println("Which product is damaged? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("How much of the product is damaged?");
        int amount = scanner.nextInt();
        System.out.println("Please enter your ID");
        int employeeID = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please describe the damage");
        String description = scanner.nextLine();
        Result<Pair<DefectiveItemReport, String>> r = controller.reportDamaged(store, productID, amount, employeeID, description);
        if (r.isError())
            System.out.println(r.getError());
        else {
            DefectiveItemReport dir = r.getValue().getFirst();
            System.out.println(dir);
            if (r.getValue().getSecond()!=null)
                System.out.println(r.getValue().getSecond());
        }
    }

    public void reportExpired() {
        int store = getStoreID();
        System.out.println("Which product is expired? (insert ID)");
        int productID = scanner.nextInt();
        System.out.println("How much of the product is expired?");
        int amount = scanner.nextInt();
        System.out.println("Please enter your ID");
        int employeeID = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please add a description (not mandatory)");
        String description = scanner.nextLine();
        Result<Pair<DefectiveItemReport, String>> r = controller.reportExpired(store, productID, amount, employeeID, description);
        if (r.isError())
            System.out.println(r.getError());
        else {
            DefectiveItemReport eir = r.getValue().getFirst();
            System.out.println(eir);
            if (r.getValue().getSecond()!=null)
                System.out.println(r.getValue().getSecond());
        }
    }
}
