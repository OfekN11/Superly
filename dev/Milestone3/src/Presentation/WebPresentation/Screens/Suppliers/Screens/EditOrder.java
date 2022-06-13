package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditOrder extends Screen {


    private static final String greet = "Edit orders";
    private int supplierId;
    private int orderId;


    public EditOrder() {
        // TODO: Supplier pass orderId and SupplierId
        super(greet);
        orderId = 1;
        supplierId = 1;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printMenu(resp, new String[]{"Add an item"});

        printForm(resp, new String[] {"orderItemId1"}, new String[]{"Item ID"}, new String[]{"Remove Item"});
        printForm(resp, new String[] {"orderItemId", "quantity"}, new String[]{"Item ID", "Quantity"}, new String[]{"Update Quantity"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Remove Item")) {
            removeOrderItem(req, resp);
        }
        else if (isButtonPressed(req, "Update Quantity")) {
            updateQuantity(req, resp);
        }
        else if (getIndexOfButtonPressed(req) == 0) {

            // TODO: Supplier pass orderId, supplierId
            redirect(resp, AddOrderItem.class);


        }
    }



    private void updateQuantity(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int itemId = Integer.parseInt(req.getParameter("orderItemId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));

            Result<Boolean> r = controller.updateItemQuantityInOrder(supplierId, orderId, itemId, quantity);
            if(r.isOk()){
                // TODO: Supplier change this to normal print!
                setError(String.format("Item %d updated to quantity %d!", itemId, quantity));
                refresh(req, resp);
            }
            else{
                setError("Item's quantity wasn't updated!");
                refresh(req, resp);
            }
        } catch (NumberFormatException e1){
            setError("Please enter a number!");
            refresh(req, resp);
        }
        catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void removeOrderItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            int num = Integer.parseInt(req.getParameter("orderItemId1"));

            Result<Boolean> r = controller.removeItemFromOrder(supplierId, orderId, num);
            if(r.isOk()){
                // TODO: Supplier change this to normal print!
                setError(String.format("Item %d removed from order %d!", num, orderId));
                refresh(req, resp);
            }
            else{
                setError("Item wasn't removed!");
                refresh(req, resp);
            }
        } catch (NumberFormatException e1){
            setError("Please enter a number!");
            refresh(req, resp);
        }
        catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }

    }

}
