package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAgreementItem extends Screen {



    private static final String greet = "View Agreement Item";
    private int supplierId;
    private int itemId;


    public ShowAgreementItem() {
        super(greet);
        // TODO: Supplier pass itemId and SupplierId
        itemId = 1;
        supplierId = 1;
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printMenu(resp, new String[]{"View Item", "Calculate total price of an order of viewed item"});

        printForm(resp, new String[] {"itemId"}, new String[]{"Item ID"}, new String[]{"Change Id"});
        printForm(resp, new String[] {"itemName"}, new String[]{"Item Name"}, new String[]{"Change Name"});
        printForm(resp, new String[] {"manufacturer"}, new String[]{"Item Manufacturer"}, new String[]{"Change manufacturer"});
        printForm(resp, new String[] {"ppu"}, new String[]{"Item price per unit"}, new String[]{"Change price per unit"});
        printForm(resp, new String[] {"price", "quantity"}, new String[]{"Price", "Quantity"}, new String[]{"Add Bulk"});
        printForm(resp, new String[] {"price", "quantity"}, new String[]{"Price", "Quantity"}, new String[]{"Remove Bulk"});
        printForm(resp, new String[] {"price", "quantity"}, new String[]{"Price", "Quantity"}, new String[]{"Change Bulk Price"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Change Id")) {

        }
        else if (isButtonPressed(req, "Change Name")) {

        }
        else if (isButtonPressed(req, "Change manufacturer")) {

        }
        else if (isButtonPressed(req, "Change price per unit")) {

        }
        else if (isButtonPressed(req, "Remove Bulk")) {

        }
        else if (isButtonPressed(req, "Change Bulk Price")) {

        }
        else if(getIndexOfButtonPressed(req) == 0){ //view Item

        }
        else if(getIndexOfButtonPressed(req) == 1){ //calculate...

        }

    }


}
