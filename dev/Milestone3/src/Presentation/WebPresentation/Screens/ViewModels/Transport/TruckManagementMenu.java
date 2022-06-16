package Presentation.WebPresentation.Screens.ViewModels.Transport;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TruckManagementMenu extends Screen {
    private static final String greet = "Truck Management Menu";

    public TruckManagementMenu() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printMenu(resp, new String[]{"Add truck", "Delete truck", "Exit"});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        switch (getIndexOfButtonPressed(req)) {
            case 0:
                //TODO: redirect(resp, TransportManage.class);
                break;
            case 1:
                //TODO: redirect(resp, TruckManage.class);
                break;
            case 2:
                redirect(resp, TransportMainMenu.class);
                break;
            default:
                redirect(resp, TruckManagementMenu.class);
        }
    }
}
