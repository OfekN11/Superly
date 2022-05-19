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
        //ArrayList products = new ArrayList(); products.add(1);
//        Result addSale = controller.addSale(products, products, 5, LocalDate.of(2022, 05, 15),LocalDate.of(2022, 05, 18));
//        Result getSale = controller.getSaleHistoryByProduct(1);
        //System.out.println("done");
//        testSuppliers();
        new Thread(new MainMenu(controller)).start();
    }



    private static void testSuppliers() {

        SupplierController supplierController = new SupplierController();

        try {
            //supplierController.insertFirstDataToDB();
            supplierController.loadSuppliersData();
            testOrder(supplierController);
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    private static void testOrder(SupplierController supplierController) {
        Map<Integer, Map<Integer, Integer>> shortage = new HashMap<>();
        Map<Integer, Integer> item1 = new HashMap<>();
        //Map<Integer, Integer> item2 = new HashMap<>();

        shortage.put(1, item1);

        item1.put(5, 200);

        try{
            supplierController.createAllOrders(shortage);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}


