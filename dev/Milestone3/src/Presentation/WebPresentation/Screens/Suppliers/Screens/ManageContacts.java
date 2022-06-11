package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ManageContacts extends Screen {

    private static final String greet = "Contacts Management";
    private final int supplierId;

    public ManageContacts() {
        super(greet);
        supplierId = 1;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printMenu(resp, new String[]{"Show Contacts"});
        printForm(resp, new String[] {"name", "phone" }, new String[]{"Name", "Phone number"}, new String[]{"Add Contact"});
        printForm(resp, new String[] {"name" }, new String[]{"Name"}, new String[]{"Remove Contact"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Add Contact")) {
        }
        else if(isButtonPressed(req, "Remove Contact")){

        }
        if(getIndexOfButtonPressed(req) == 0){
            //show contacts
        }

    }

}
