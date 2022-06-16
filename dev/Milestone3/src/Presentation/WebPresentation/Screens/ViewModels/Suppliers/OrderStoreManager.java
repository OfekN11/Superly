package Presentation.WebPresentation.Screens.ViewModels.Suppliers;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderStoreManager extends RemoveViewOrder{

    private static final String greet = "View Order for Store Manager";

    public OrderStoreManager() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);


        printOrderIds(resp);
        printForm(resp, new String[] {"orderId3"}, new String[]{"Order ID"}, new String[]{"View Order"});


        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if(isButtonPressed(req, "View Order")){
            printOrder(req, resp);
        }
    }
}
