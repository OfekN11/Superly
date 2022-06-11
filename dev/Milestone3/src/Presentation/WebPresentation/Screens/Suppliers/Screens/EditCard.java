package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EditCard extends Screen {


    private static final String greet = "Edit Card";
    private final int supplierId;

    public EditCard() {
        super(greet);
        supplierId = 1;
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printForm(resp, new String[] {"bankNumber"}, new String[]{"Bank Number"}, new String[]{"Update Bank Number"});
        printForm(resp, new String[] {"address" }, new String[]{"Address"}, new String[]{"Update Address"});
        printForm(resp, new String[] {"name" }, new String[]{"name"}, new String[]{"Update Name"});
        printForm(resp, new String[] {"payingAgreement" }, new String[]{"Paying Agreement"}, new String[]{"Update Paying Agreement"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Update Bank Number")) {

        }
        else if(isButtonPressed(req, "Update Address")){

        }
        else if(isButtonPressed(req, "Update Name")){

        }
        else if(isButtonPressed(req, "Update Paying Agreement")){

        }
    }

}
