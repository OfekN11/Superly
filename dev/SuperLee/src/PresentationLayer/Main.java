package PresentationLayer;

import Domain.BusinessLayer.Supplier.Contact;
import Domain.BusinessLayer.Supplier.Supplier;
import Domain.BusinessLayer.SupplierController;
import Domain.PersistenceLayer.Controllers.ManufacturerDAO;
import Domain.PersistenceLayer.Controllers.SuppliersDAO;
import Domain.ServiceLayer.Result;
import Globals.Pair;
import Domain.ServiceLayer.SupplierService;
import PresentationLayer.Screens.MainMenu;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        BackendController controller = new BackendController();
        ArrayList products = new ArrayList(); products.add(1);
        Result addSale = controller.addSale(products, products, 5, LocalDate.of(2022, 05, 15),LocalDate.of(2022, 05, 18));
        Result getSale = controller.getSaleHistoryByProduct(1);
        System.out.println("done");
//        testSuppliers();
//        new Thread(new MainMenu(controller)).start();
    }

    private static void testSuppliers() {

        SupplierController supplierController = new SupplierController();

        try {
            supplierController.loadSuppliersData();
            supplierController.orderExists(1, 1);
            supplierController.uploadOrderItems(1);

            /*
            ArrayList<Pair<String, String >> contacts = new ArrayList<>();
            contacts.add(new Pair<>("Yael", "0508647894"));             contacts.add(new Pair<>("Avi", "086475421"));
            ArrayList<String> manufacturers = new ArrayList<>();        manufacturers.add("Osem") ; manufacturers.add("Elit");
            supplierController.addSupplier("name1", 1, "address", "pay", contacts,manufacturers);
            supplierController.addAgreement(1, 1, "2 3 4 5");
            ArrayList<String> items = new ArrayList<>();        items.add("1 , 1,  bamba , Osem , 5 , 100 , 20 ");      items.add("2 , 2, Halva , Elit , 10 , 100 , 20 ");
            supplierController.addItemsToAgreement(1, items);

            int orderId = supplierController.addNewOrder(1,1);
            int orderId2 = supplierController.addNewOrder(1,1);

            supplierController.addItemToOrder(1, orderId, 1, 50);
            supplierController.addItemToOrder(1, orderId2, 2, 100);

            supplierController.updateItemQuantityInOrder(1, orderId, 1, 1000);
            //supplierController.removeSupplier(1);


             */
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /*
    private static SupplierService initWithData() {
        System.out.println("Init with data");
        SupplierService service = new SupplierService();

        ArrayList<Pair<String, String >> contacts = new ArrayList<>();
        contacts.add(new Pair<>("Yael", "0508647894"));
        contacts.add(new Pair<>("Avi", "086475421"));
        ArrayList<String> manufacturers = new ArrayList<>();
        manufacturers.add("Osem") ; manufacturers.add("Elit");
        service.addSupplier( "Avi", 123456, "Beer sheva" , "check", contacts, manufacturers);
        service.addSupplier( "Avi", 123456, "Beer sheva" , "check", contacts, manufacturers);
        service.addAgreement(1 ,1 , "2 4 5");

        ArrayList<String> items = new ArrayList<>();
        items.add("1 , 1,  bamba , Osem , 5 , 100 , 20 , 200 , 50 ");
        items.add("2 , 2, Halva , Elit , 10 , 100 , 20 , 200 , 50");
        items.add("3 , 3, Chocolate , Elit , 10 , 100 , 20 , 200 , 50 ");
        service.addAgreementItems(1, items);
        service.order(1, 1);

        ArrayList<String> itemsToOrder = new ArrayList<>();
        itemsToOrder.add("1"); itemsToOrder.add("1");  itemsToOrder.add("100");  itemsToOrder.add("2");  itemsToOrder.add("2");  itemsToOrder.add("50");
        service.addItemsToOrder(1, 1, itemsToOrder);

        service.addAgreement(2 ,2 , "2");
        ArrayList<String> items2 = new ArrayList<>();
        items2.add("1 ,1,  bamba , Osem , 4 , 100 , 20 , 200 , 50 ");
        service.addAgreementItems(2, items2);
        service.order(2);
        service.addItemsToOrder(2, 2, itemsToOrder);
        return service;
        //cli.init(service);

     */
}

