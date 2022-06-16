package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Presentation.WebPresentation.Screens.Screen;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddOrderItem extends Screen {


    private static final String greet = "Add Items to order";

    public AddOrderItem() {
        super(greet);

    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        int supId = getSupplierId(req);
        int orderId = getOrderId(req);
        // TODO: Supplier : check if multiple screens work here

        printForm(resp, new String[] {"idBySupplier", "quantity"}, new String[]{"ID By Supplier", "Quantity"}, new String[]{"Add Item"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        int supplierId = getSupplierId(req);
        int orderId = getOrderId(req);

        if (isButtonPressed(req, "Add Item")) {
            try {
                int idBySupplier = Integer.parseInt(req.getParameter("idBySupplier"));
                int quantity = Integer.parseInt(req.getParameter("quantity"));

                int itemId = controller.getMatchingProductIdForIdBySupplier(idBySupplier);
                if(controller.orderItemExistsInOrder(supplierId, orderId, itemId)){
                    setError(String.format("Item %d already exists in Order %d!. If you want to add, use Update quantity", idBySupplier, orderId));
                    refresh(req, resp);
                }
                else {
                    if (controller.addItemToOrder(supplierId, orderId, itemId, quantity)) {
                        setError(String.format("Item %d added to Order %d!", idBySupplier, orderId));
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

    private int getOrderId(HttpServletRequest req) {
        return Integer.parseInt(getParamVal(req,"orderId"));

    }

    private int getSupplierId(HttpServletRequest req) {
        return Integer.parseInt(getParamVal(req,"supId"));

    }

        /*
    private String getCookie(String name, HttpServletRequest req, HttpServletResponse resp, int time) throws IOException {
        String cookie = "";
        for (Cookie c : req.getCookies()) {
            if (c.getName().equals(name)) {
                cookie = c.getValue();
            }
            c.setMaxAge((int) TimeUnit.MINUTES.toSeconds(time)); //time of life of the cookie, if bot listed its infinite
            resp.addCookie(c);
        }
        return cookie;
    }



    private String getCookie(String name, HttpServletRequest req, HttpServletResponse resp, int time) throws IOException {
        String cookie = "";
        for (Cookie c : req.getCookies()) {
            if (c.getName().equals(name)) {
                c.setMaxAge((int) TimeUnit.MINUTES.toSeconds(time)); //time of life of the cookie, if bot listed its infinite
                resp.addCookie(c);
                return c.getValue();
            }
        }
        return cookie;
    }
 */

}
