package PresentationLayer.Screens.InventorySreens;

import Domain.ServiceLayer.InventoryObjects.Category;
import Domain.ServiceLayer.InventoryObjects.Product;
import Domain.ServiceLayer.Result;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Catalogs extends Screen{

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

    public Catalogs(Screen caller, String[] extraMenuOptions) {
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

    public void listProducts() {
        Result<List<Domain.ServiceLayer.InventoryObjects.Product>> r = controller.getProducts();
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Domain.ServiceLayer.InventoryObjects.Product> productList = r.getValue();
            for (Domain.ServiceLayer.InventoryObjects.Product p : productList)
                System.out.println(p);
            if (productList.isEmpty())
                System.out.println("there are no products in the system");
        }
    }

    public void listCategories() {
        Result<List<Category>> r = controller.getCategories();
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Category> categoryList = r.getValue();
            for (Category c : categoryList)
                System.out.println(c);
            if (categoryList.isEmpty())
                System.out.println("there are no categories in the system");
        }
    }

    public void listProductsByCategory() {
        System.out.println("Which categories would you like to examine? (Please insert categories IDs separated by ',' without spaces)");
        listCategoryIDs();
        List<Integer> categories = Arrays.asList(scanner.nextLine().split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        Result<List<Product>> r = controller.getProductsFromCategory(categories);
        if (r.isError())
            System.out.println(r.getError());
        else {
            List<Domain.ServiceLayer.InventoryObjects.Product> productList = r.getValue();
            for (Domain.ServiceLayer.InventoryObjects.Product p : productList)
                System.out.println(p);
            if (productList.isEmpty())
                System.out.println("there are no products in specified categories");
        }
    }
}
