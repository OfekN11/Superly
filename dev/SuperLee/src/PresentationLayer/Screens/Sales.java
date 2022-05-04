package PresentationLayer.Screens;

import Domain.ServiceLayer.Objects.Sale;
import Domain.ServiceLayer.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Sales extends Screen{

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

    public Sales(Screen caller, Domain.ServiceLayer.Objects.Sale sProduct, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new), "Sale Management");
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

    public void removeSale() {
        System.out.println("Which sale would you like to remove? (insert ID)");
        PrintRemovableSales();
        int saleID = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result r = controller.removeSale(saleID);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("sale successfully removed");
        }
    }

    public void PrintRemovableSales() {
        Result<List<Sale>> removableSales = controller.getRemovableSales();
        for (Sale sale: removableSales.getValue())
            System.out.println(sale);
    }

    public void saleHistoryByProduct() {
        System.out.println("Please insert product ID for which you would like to see history");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<List<Sale>> r = controller.getSaleHistoryByProduct(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Sale> saleReport = r.getValue();
            for (Sale s : saleReport)
                System.out.println(s);
            if (saleReport.isEmpty())
                System.out.println("there are no sales for this product in the system");
        }
    }

    public void saleHistoryByCategory() {
        System.out.println("Please insert category ID for which you would like to see history");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<List<Sale>> r = controller.getSaleHistoryByCategory(id);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Sale> saleReport = r.getValue();
            for (Sale s : saleReport)
                System.out.println(s);
            if (saleReport.isEmpty())
                System.out.println("there are no sales for this category in the system");
        }
    }
}
