package PresentationLayer;

import Domain.BusinessLayer.Supplier.Contact;
import Domain.BusinessLayer.Supplier.Supplier;
import Domain.BusinessLayer.SupplierController;
import Domain.PersistenceLayer.Controllers.ManufacturerDAO;
import Domain.PersistenceLayer.Controllers.SuppliersDAO;
import Globals.Pair;
import Domain.ServiceLayer.SupplierService;
import PresentationLayer.Screens.MainMenu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        BackendController controller = new BackendController();
        controller.addNewCategory("catName", 0);
        controller.newProduct("productName", 1,1,1,"1");
        new Thread(new MainMenu(controller)).start();


//        testSuppliers();

    }




    private static void testSuppliers() {
                /*
        ArrayList<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Yael", "0508647894"));      contacts.add(new Contact("Avi", "086475421"));
        ArrayList<String> manufacturers = new ArrayList<>();
        manufacturers.add("Osem") ; manufacturers.add("Elit");
        suppliersDAO.addSupplier(new Supplier("Avi", 123456, "Beer sheva" , "check", contacts, manufacturers), contacts, manufacturers);
        suppliersDAO.addSupplierManufacturer(1, "Osem");
        suppliersDAO.addSupplierManufacturer(1, "Elit");
         */

        /*
        SupplierController supplierController = new SupplierController();
        try {
            supplierController.loadSuppliersData();
            ArrayList<Pair<String, String >> contacts = new ArrayList<>();
            contacts.add(new Pair<>("Yael", "0508647894"));             contacts.add(new Pair<>("Avi", "086475421"));
            ArrayList<String> manufacturers = new ArrayList<>();        manufacturers.add("Osem") ; manufacturers.add("Elit");
            supplierController.addSupplier("name1", 1, "address", "pay", contacts,manufacturers);
            supplierController.addAgreement(1, 1, "2 3 4 5");
            ArrayList<String> items = new ArrayList<>();        items.add("1 , 1,  bamba , Osem , 5 , 100 , 20 ");      items.add("2 , 2, Halva , Elit , 10 , 100 , 20 ");
            supplierController.addItemsToAgreement(1, items);

            supplierController.removeSupplier(1);

        } catch (Exception e) {
            try {
                supplierController.removeSupplier(1);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            e.printStackTrace();
        }
        */

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

