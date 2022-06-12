package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditOrder extends Screen {


    private static final String greet = "Edit orders";
    private int supplierId;
    private int orderId;


    public EditOrder() {
        super(greet);
        // TODO: Supplier pass orderId and SupplierId
        orderId = 1;
        supplierId = 1;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printMenu(resp, new String[]{"Add an item"});

        printForm(resp, new String[] {"orderItemId"}, new String[]{"Item ID"}, new String[]{"Remove Item"});
        printForm(resp, new String[] {"orderItemId", "quantity"}, new String[]{"Item ID", "Quantity"}, new String[]{"Update Quantity"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Remove Item")) {

        }
        else if (isButtonPressed(req, "Update Quantity")) {

        }
        else if (getIndexOfButtonPressed(req) == 0) {
            // TODO: Supplier pass orderId, supplierId
            redirect(resp, AddOrderItem.class);


        }
    }

}
