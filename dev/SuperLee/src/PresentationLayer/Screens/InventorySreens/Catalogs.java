package PresentationLayer.Screens.InventorySreens;

import Domain.ServiceLayer.InventoryObjects.Category;
import Domain.ServiceLayer.InventoryObjects.Product;
import Domain.ServiceLayer.Result;
import PresentationLayer.Screens.Screen;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Catalogs extends Screen {

    private static final String[] menuOptions = {
            "View Products",  //1
            "View Products By Category",  //2
            "View Categories",                  //3
            "exit"        //4
    };

    public Catalogs(Screen caller, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
    }

    public void run() {
        System.out.println("\nWelcome to the Catalog Menu!");
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
                listProducts();
                break;
            case 2:
                listProductsByCategory();
                break;
            case 3:
                listCategories();
                break;
            case 4:
                endRun();
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
}
