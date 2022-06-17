package Presentation.WebPresentation.Screens.ViewModels.Transport.Transport;

import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.Transport.TransportMainMenu;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransportManagementMenu extends Screen {
    private static final String greet = "Transport Management Menu";
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
                redirect(resp, CreateTransport.class);
                break;
            case 1:
                //TODO: redirect(resp, UpdateTransport.class);
                break;
            case 2:
                //TODO: Get pending transport
                break;
            case 3:
                //TODO: Get pending transport
                break;
            case 4:
                //TODO: Get complete transport
                break;
            case 5://Exit
                redirect(resp, TransportMainMenu.class);
                break;
            default:
                redirect(resp, TransportManagementMenu.class);
        }
    }


}
