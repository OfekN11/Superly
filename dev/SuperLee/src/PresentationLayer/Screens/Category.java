package PresentationLayer.Screens;

import Domain.ServiceLayer.Result;

import java.util.Arrays;
import java.util.stream.Stream;

public class Category extends Screen{

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

    public Category(Screen caller, Domain.ServiceLayer.Objects.Sale sProduct, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new), "Category Management");
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

    public void addCategory() {
        System.out.println("Please insert category name");
        String name = scanner.nextLine();
        System.out.println("Please insert parent category ID, or 0 if there is none");
        int parent = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Domain.ServiceLayer.Objects.Category> r = controller.addNewCategory(name, parent);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.Objects.Category c = r.getValue();
            System.out.println(c);
        }
    }

    public void changeCatParent() {
        System.out.println("Which category would you like to edit?");
        int id = scanner.nextInt();
        System.out.println("Please insert new category parent ID");
        int parent = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Domain.ServiceLayer.Objects.Category> r = controller.changeCategoryParent(id, parent);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.Objects.Category c = r.getValue();
            System.out.println(c);
        }
    }

    public void changeCatName() {
        System.out.println("Which category would you like to edit?");
        int id = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        System.out.println("Please insert new category name");
        String name = scanner.nextLine();
        Result<Domain.ServiceLayer.Objects.Category> r = controller.editCategoryName(id, name);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.Objects.Category c = r.getValue();
            System.out.println(c);
        }
    }

    public void deleteCategory() {
        System.out.println("What category would you like to delete?");
        listCategoryIDs();
        int catID = scanner.nextInt();
        scanner.nextLine(); //to remove extra \n
        Result r = controller.deleteCategory(catID);
        if (r.isError()) {
            System.out.println(r.getError());
        }
        else
            System.out.println("Category deleted successfully");
    }
}
