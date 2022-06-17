package Presentation.WebPresentation.Screens.ViewModels.Transport.Transport;

import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Document.DocumentManagementMenu;
import Presentation.WebPresentation.Screens.ViewModels.Transport.TransportMainMenu;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Truck.TruckManagementMenu;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransportManagementMenu extends Screen {
    private static final String greet = "TransportManagement Menu";
    private static final String[] menuOptions = {
            "Create new transport",         //1
            "Update transport",             //2
            "Get pending transport",        //3
            "Get in progress transport",    //4
            "Get complete transport",       //5
            "Exit"                          //6
    };
    public TransportManagementMenu() {
        super(greet);
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printMenu(resp, menuOptions);
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        switch (getIndexOfButtonPressed(req)) {
            case 0:
                //TODO: redirect(resp, CreateNewTransport.class);
                break;
            case 1:
                //TODO: redirect(resp, UpdateTransport.class);
                break;
            case 2:
                //TODO: Get pending transport
                redirect(resp, DocumentManagementMenu.class);
                break;
            case 3:
                //TODO: Get pending transport
                redirect(resp, TransportManagementMenu.class);
                break;
            case 4:
                //TODO: Get complete transport
                redirect(resp, TruckManagementMenu.class);
                break;
            case 5://Exit
                redirect(resp, TransportMainMenu.class);
                break;
            default:
                redirect(resp, TransportManagementMenu.class);
        }
    }
}
