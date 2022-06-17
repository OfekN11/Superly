package Presentation.WebPresentation;

import Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.*;
import Presentation.WebPresentation.Screens.ViewModels.HR.*;
import Presentation.WebPresentation.Screens.ViewModels.Suppliers.*;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Document.DocumentManagementMenu;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.CreateTransport;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.TransportManagementMenu;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.TransportsView;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.Update.PlaceCarrier;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.Update.PlaceTruck;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.Update.AddOrderToTransport;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.Update.UpdateTransport;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Transport.Update.ViewPendingOrders;
import Presentation.WebPresentation.Screens.ViewModels.Transport.TransportMainMenu;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Truck.AddTruck;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Truck.DeleteTruck;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Truck.TruckManagementMenu;
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
            new AbstractMap.SimpleEntry<>(UpcomingShifts.class, "/UpcomingShifts"),
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
            new AbstractMap.SimpleEntry<>(Products.class, "/Products"),
            new AbstractMap.SimpleEntry<>(Categories.class, "/Categories"),
            new AbstractMap.SimpleEntry<>(Category.class, "/Category"),
            new AbstractMap.SimpleEntry<>(Sales.class, "/Sales"),
            new AbstractMap.SimpleEntry<>(Sale.class, "/Sale"),
            new AbstractMap.SimpleEntry<>(TransportMainMenu.class, "/TransportMainMenu"),
            new AbstractMap.SimpleEntry<>(TruckManagementMenu.class, "/TransportMainMenu/TruckManagementMenu"),
            new AbstractMap.SimpleEntry<>(AddTruck.class, "/TransportMainMenu/TruckManagementMenu/AddTruck"),
            new AbstractMap.SimpleEntry<>(DeleteTruck.class, "/TransportMainMenu/TruckManagementMenu/DeleteTruck"),
            new AbstractMap.SimpleEntry<>(DocumentManagementMenu.class, "/TransportMainMenu/DocumentManagementMenu"),
            new AbstractMap.SimpleEntry<>(TransportManagementMenu.class, "/TransportMainMenu/TransportManagementMenu"),
            new AbstractMap.SimpleEntry<>(CreateTransport.class, "/TransportMainMenu/TransportManagementMenu/CreateTransport"),
            new AbstractMap.SimpleEntry<>(TransportsView.class, "/TransportMainMenu/TransportManagementMenu/TransportsView"),
            new AbstractMap.SimpleEntry<>(PlaceTruck.class, "/TransportMainMenu/TransportManagementMenu/UpdateTransport/PlaceTruck"),
            new AbstractMap.SimpleEntry<>(PlaceCarrier.class, "/TransportMainMenu/TransportManagementMenu/UpdateTransport/PlaceCarrier"),
            new AbstractMap.SimpleEntry<>(UpdateTransport.class, "/TransportMainMenu/TransportManagementMenu/UpdateTransport"),
            new AbstractMap.SimpleEntry<>(ViewPendingOrders.class, "/TransportMainMenu/TransportManagementMenu/UpdateTransport/ViewPendingOrders"),
            new AbstractMap.SimpleEntry<>(AddOrderToTransport.class, "/TransportMainMenu/TransportManagementMenu/UpdateTransport/AddOrderToTransport")

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
