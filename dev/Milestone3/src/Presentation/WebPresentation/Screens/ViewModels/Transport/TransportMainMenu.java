package Presentation.WebPresentation.Screens.ViewModels.Transport;

import Presentation.WebPresentation.Screens.InventoryScreens.Catalog;
import Presentation.WebPresentation.Screens.InventoryScreens.Categories;
import Presentation.WebPresentation.Screens.InventoryScreens.InventoryMainMenu;
import Presentation.WebPresentation.Screens.InventoryScreens.Sales;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Truck.TruckManagementMenu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransportMainMenu extends Screen {
    private static final String greet = "Transport's Main Menu";

    public TransportMainMenu() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printMenu(resp, new String[]{"Transport Manage", "Truck Management", "Document Management"});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        switch (getIndexOfButtonPressed(req)) {
            case 0:
                redirect(resp, TransportManagementMenu.class);
                break;
            case 1:
                redirect(resp, TruckManagementMenu.class);
                break;
            case 2:
                redirect(resp, DocumentManagementMenu.class);
                break;
            default:
                redirect(resp, TransportMainMenu.class);
        }
    }
}
