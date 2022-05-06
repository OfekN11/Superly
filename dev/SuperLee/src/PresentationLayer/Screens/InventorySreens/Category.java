package PresentationLayer.Screens.InventorySreens;

import Domain.ServiceLayer.Result;
import PresentationLayer.Screens.Screen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Category extends Screen {

    private static final String[] menuOptions = {
            "Update name",                  //1
            "Update Parent Category",          //2
            "Delete Category",      //3
            "exit", //4
    };

    private int id;
    private String name;
    private String parentCategory;
    private List<Domain.ServiceLayer.InventoryObjects.Category> subCategories;
    private int numOfProducts;
    public Category(Screen caller, String[] extraMenuOptions, Domain.ServiceLayer.InventoryObjects.Category category) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
        id = category.getID();
        name = category.getName();
        parentCategory = category.getParentCategory();
        subCategories = category.getSubCategories();
        numOfProducts = category.getNumOfProducts();
    }

    public void run() {
        System.out.println("\nWelcome to the Management Menu of the category: " + name);
        int option = 0;
        while (option != 4) {
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
                changeCatName();
                break;
            case 2:
                changeCatParent();
                break;
            case 3:
                deleteCategory();
                break;
            case 4:
                endRun();
        }
    }



    public void changeCatParent() {
        System.out.println("Please insert new category parent ID");
        int parent = scanner.nextInt();
        scanner.nextLine(); //without this line the next scanner will be passed without the user's input.
        Result<Domain.ServiceLayer.InventoryObjects.Category> r = controller.changeCategoryParent(id, parent);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.InventoryObjects.Category c = r.getValue();
            System.out.println(c);
        }
    }

    public void changeCatName() {
        System.out.println("Please insert new category name");
        String name = scanner.nextLine();
        Result<Domain.ServiceLayer.InventoryObjects.Category> r = controller.editCategoryName(id, name);
        if (r.isError())
            System.out.println(r.getError());
        else {
            Domain.ServiceLayer.InventoryObjects.Category c = r.getValue();
            System.out.println(c);
        }
    }

    public void deleteCategory() {
        Result r = controller.deleteCategory(id);
        if (r.isError()) {
            System.out.println(r.getError());
        }
        else
            System.out.println("Category deleted successfully");
    }
}
