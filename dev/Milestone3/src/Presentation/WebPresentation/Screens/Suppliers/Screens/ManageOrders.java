package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ManageOrders extends Screen {

    private static final String greet = "Manage orders for Storekeeper";


    public ManageOrders() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);


        printPrderIds(resp);
        printForm(resp, new String[] {"supplierId", "orderId"}, new String[]{"Supplier ID", "Order ID"}, new String[]{"Add Order"});
        printForm(resp, new String[] {"orderId"}, new String[]{"Order ID"}, new String[]{"Remove Order"});
        printForm(resp, new String[] {"orderId"}, new String[]{"Order ID"}, new String[]{"Edit Order"});
        printForm(resp, new String[] {"orderId"}, new String[]{"Order ID"}, new String[]{"View Order"});


        handleError(resp);
    }

    private void printPrderIds(HttpServletResponse resp) {
        //Print orderIds from all suppliers
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Add Order")){

            redirect(resp, AddOrderItem.class);
        }
        else if(isButtonPressed(req, "Remove Order")){
        }
        else if(isButtonPressed(req, "Edit Order")){
            //get the order id...
            // TODO: Supplier pass orderId, supplierId
            redirect(resp, EditOrder.class);
        }
        else if(isButtonPressed(req, "View Order")){

        //print Order
        }

    }
}
