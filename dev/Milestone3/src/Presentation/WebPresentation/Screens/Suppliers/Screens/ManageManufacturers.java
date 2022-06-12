package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManageManufacturers extends Screen {

    private static final String greet = "Manage Manufacturers";
    private final int supplierId;

    public ManageManufacturers() {
        super(greet);
        supplierId = 1;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printMenu(resp, new String[]{"Show Manufacturers"});
        printForm(resp, new String[] {"name"}, new String[]{"Name"}, new String[]{"Add Manufacturer"});
        printForm(resp, new String[] {"name" }, new String[]{"Name"}, new String[]{"Remove Manufacturer"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Add Manufacturer")) {

        }
        else if(isButtonPressed(req, "Remove Manufacturer")){

        }
        if(getIndexOfButtonPressed(req) == 0){
            //show manufacturers
        }

    }

}
