package Presentation.WebPresentation.Screens.InventoryScreens;

import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.Suppliers.Screens.ManageOrders;
import Presentation.WebPresentation.Screens.Suppliers.Screens.ManageSuppliers;
import Presentation.WebPresentation.Screens.Suppliers.Screens.ViewSupplier;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class InventoryMainMenu extends Screen {

    private static final String greet = "Inventory's Main Menu";

    protected InventoryMainMenu() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printMenu(resp, new String[]{"View Catalog", "View Categories", "View sales"});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        switch (getIndexOfButtonPressed(req)){
            case 0:
                redirect(resp, Catalog.class);
                break;
            case 1:
                redirect(resp, Categories.class);
                break;
            case 2:
                redirect(resp, Sales.class);
                break;
            default:
                redirect(resp, InventoryMainMenu.class);
        }
    }
}
