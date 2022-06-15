package Presentation.WebPresentation;


import Presentation.WebPresentation.Screens.InventoryScreens.*;
import Presentation.WebPresentation.Screens.ViewModels.HR.EmployeeServlet;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;
import Presentation.WebPresentation.Screens.ViewModels.Suppliers.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.Servlet;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebMain {
    private static int PORT = 8080;

    public static final Map<Class<? extends Servlet>, String> servletToPath= Stream.of(
            /***
             * All screens in the system.
             * For each new screen add another entry and make sure it doesn't share a path with another screen
             */
            new AbstractMap.SimpleEntry<>(Login.class, "/"),
            new AbstractMap.SimpleEntry<>(EmployeeServlet.class, "/home"),
            new AbstractMap.SimpleEntry<>(AddItemToAgreement.class, "/AddItemToAgreement"),
            new AbstractMap.SimpleEntry<>(AddOrderItem.class, "/AddOrderItem"),
            new AbstractMap.SimpleEntry<>(EditCard.class, "/EditCard"),
            new AbstractMap.SimpleEntry<>(EditOrder.class, "/EditOrder"),
            new AbstractMap.SimpleEntry<>(ManageContacts.class, "/ManageContacts"),
            new AbstractMap.SimpleEntry<>(ManageManufacturers.class, "/ManageManufacturers"),
            new AbstractMap.SimpleEntry<>(ManageOrders.class, "/ManageOrders"),
            new AbstractMap.SimpleEntry<>(ManageSuppliers.class, "/ManageSuppliers"),
            new AbstractMap.SimpleEntry<>(OrderHRLogistics.class, "/RemoveOrder"),
            new AbstractMap.SimpleEntry<>(OrderStoreManager.class, "/OrderStoreManager"),
            new AbstractMap.SimpleEntry<>(RemoveViewOrder.class, "/RemoveViewOrder"),
            new AbstractMap.SimpleEntry<>(ShowAgreement.class, "/ShowAgreement"),
            new AbstractMap.SimpleEntry<>(ShowAgreementItem.class, "/ShowAgreementItem"),
            new AbstractMap.SimpleEntry<>(SupplierMainMenu.class, "/SupplierMainMenu"),
            new AbstractMap.SimpleEntry<>(SupplierMainMenuStorekeeper.class, "/SupplierMainMenuStorekeeper"),
            new AbstractMap.SimpleEntry<>(SupplierMainMenuStoreManager.class, "/SupplierMainMenuStoreManager"),
            new AbstractMap.SimpleEntry<>(ViewSupplier.class, "/ViewSupplier"),
            new AbstractMap.SimpleEntry<>(Product.class, "/Product"),
            new AbstractMap.SimpleEntry<>(InventoryMainMenu.class, "/InventoryMainMenu"),
            new AbstractMap.SimpleEntry<>(Catalog.class, "/Catalog"),
            new AbstractMap.SimpleEntry<>(Categories.class, "/Categories"),
            new AbstractMap.SimpleEntry<>(Category.class, "/Category"),
            new AbstractMap.SimpleEntry<>(Sales.class, "/Sales"),
            new AbstractMap.SimpleEntry<>(Sale.class, "/Sale")


            ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    public static void main(String[] args) throws Exception {
        if (args.length > 0) {
            try {
                PORT = Integer.parseInt(args[0]);
                if (PORT <8000 || PORT > 9000) {
                    System.out.println("Please enter port between 8000 and 9000");
                    return;
                }
            } catch (Exception e) {
                System.out.println("Illegal input for port");
                return;
            }
        }

        Server server = new Server(PORT);
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        for (Class<? extends Servlet> servlet : servletToPath.keySet())
            handler.addServlet(servlet, servletToPath.get(servlet));
        server.start();
        server.join();
    }
}
