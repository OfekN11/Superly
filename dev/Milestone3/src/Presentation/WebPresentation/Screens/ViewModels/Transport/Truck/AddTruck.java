package Presentation.WebPresentation.Screens.ViewModels.Transport.Truck;

import Globals.Enums.TruckModel;
import Presentation.CLIPresentation.Screens.TruckMenu;
import Presentation.WebPresentation.Screens.InventoryScreens.InventoryMainMenu;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.EmployeeServlet;
import Presentation.WebPresentation.Screens.ViewModels.Suppliers.SupplierMainMenuStorekeeper;
import Presentation.WebPresentation.Screens.ViewModels.Transport.DocumentManagementMenu;
import Presentation.WebPresentation.Screens.ViewModels.Transport.TransportMainMenu;
import Presentation.WebPresentation.Screens.ViewModels.Transport.TransportManagementMenu;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class AddTruck extends Screen {
    private static final String greet = "Add Truck:";

    public AddTruck() {
        super(greet);
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printForm(resp, new String[]{"OK", "Cancel"});
        handleError(resp);
    }

    protected static void printForm(HttpServletResponse resp, String[] buttons) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<form method=\"post\">\n");
        out.println(String.format("<input type=\"text\" name=\"%s\" placeholder=\"%s\"><br><br>", "LN", "License number"));
        out.println(String.format("<input list=\"Truck Models\" name=\"model\" id=\"model\">"));
        out.println(String.format("<datalist id=\"Truck Models\">"));
        out.println(String.format("<option value=\"%s\">", TruckModel.Van.toString()));
        out.println(String.format("<option value=\"%s\">", TruckModel.SemiTrailer.toString()));
        out.println(String.format("<option value=\"%s\">", TruckModel.FullTrailer.toString()));
        out.println(String.format("<option value=\"%s\">", TruckModel.DoubleTrailer.toString()));
        out.println(String.format("</datalist><br><br>"));
        out.println(String.format("<input type=\"text\" name=\"%s\" placeholder=\"%s\"><br><br>", "NetWeight", "Net weight"));
        out.println(String.format("<input type=\"text\" name=\"%s\" placeholder=\"%s\"><br><br>", "MaxCapacityWeight", "Max capacity weight"));
        for (String button : buttons)
            out.println(String.format("<input type=\"submit\" name=\"%s\" value=\"%s\">", button, button));
        out.println("<br><br></form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, "OK")){
            try {
                int ln = Integer.parseInt(req.getParameter("LN"));
                String tm = req.getParameter("model");
                int netWeight = Integer.parseInt(req.getParameter("LN"));
                int maxCapacityWeight = Integer.parseInt(req.getParameter("MaxCapacityWeight"));
                controller.addTruck(ln, TruckModel.valueOf(tm), netWeight, maxCapacityWeight);
                System.out.println("The truck was successfully added!");
                redirect(resp, TruckManagementMenu.class);
            } catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }
        }
        else if(isButtonPressed(req, "Cancel"))
            redirect(resp, TruckManagementMenu.class);
    }

}
