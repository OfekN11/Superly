package PresentationLayer;

import Globals.Pair;
import PresentationLayer.Screens.SupplierScreens.SuppliersMenu;
import Domain.ServiceLayer.SupplierService;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BackendController controller = new BackendController(initWithData());
        new Thread(new SuppliersMenu(controller)).start();

        /*
        System.out.println("Choose 1 for init with data or 2 for init with no data: ");
        int input = scan.nextInt();
        scan.nextLine();
        CLI cli = new CLI();


        switch (input) { // temporary
            case 1 : {
                initWithData(cli);
            }
            case 2 : {
                System.out.println("Init without data");
                cli.init();
            }

        }

         */

    }

  //private static SupplierService initWithData(CLI cli) {
    private static SupplierService initWithData() {
        System.out.println("Init with data");
        SupplierService service = new SupplierService();

        ArrayList<Pair<String, String >> contacts = new ArrayList<>();
        contacts.add(new Pair<>("Yael", "0508647894"));
        contacts.add(new Pair<>("Avi", "086475421"));
        ArrayList<String> manufacturers = new ArrayList<>();
        manufacturers.add("Osem") ; manufacturers.add("Elit");
        service.addSupplier(1, "Avi", 123456, "Beer sheva" , "check", contacts, manufacturers);
        service.addAgreement(1 ,1 , "2 3 4");

        ArrayList<String> items = new ArrayList<>();
        items.add("1 , bamba , Osem , 5 , 100 , 20 , 200 , 50 ");
        items.add("2 , Halva , Elit , 10 , 100 , 20 , 200 , 50");
        items.add("3 , Chocolate , Elit , 10 , 100 , 20 , 200 , 50 ");
        service.addAgreementItems(1, items);

        service.order(1);

        ArrayList<String> itemsToOrder = new ArrayList<>();
        itemsToOrder.add("1");  itemsToOrder.add("bamba");   itemsToOrder.add("100");  itemsToOrder.add("2");  itemsToOrder.add("Halva");  itemsToOrder.add("50");
        service.addItemsToOrder(1, 1, itemsToOrder);

        return service;
        //cli.init(service);

    }
}
