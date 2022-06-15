package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Screen;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddOrderItem extends Screen {


    private static final String greet = "Add Items to order";
    private int supplierId;
    private int orderId;


    public AddOrderItem() {
        super(greet);
        // TODO: Supplier pass orderId and SupplierId
        orderId = 1;
        supplierId = 1;
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);


        printForm(resp, new String[] {"idBySupplier", "quantity"}, new String[]{"ID By Supplier", "Quantity"}, new String[]{"Add Item"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Add Item")) {
            try {
                int idBySupplier = Integer.parseInt(req.getParameter("idBySupplier"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                int itemId = controller.getMatchingProductIdForIdBySupplier(idBySupplier);
                if(controller.orderItemExistsInOrder(supplierId, orderId, itemId)){
                    // TODO: Supplier change this to normal print!
                    setError(String.format("Item %d already exists in Order %d!. If you want to add, use Update quantity", itemId, orderId));
                    refresh(req, resp);
                }
                else {
                    if (controller.addItemToOrder(supplierId, orderId, itemId, quantity)) {

                        // TODO: Supplier change this to normal print!
                        setError(String.format("Item %d added to Order %d!", itemId, orderId));
                        refresh(req, resp);
                    } else {
                        setError("Item wasn't added!");
                        refresh(req, resp);
                    }
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


}
