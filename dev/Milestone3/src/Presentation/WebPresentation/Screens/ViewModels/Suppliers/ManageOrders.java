package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class ManageOrders extends Screen {

    private static final String greet = "Manage orders for Storekeeper";


    public ManageOrders() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);


        printOrderIds(resp,req);
        printForm(resp, new String[] {"supplierId","storeId"}, new String[]{"Supplier ID", "Store ID"}, new String[]{"Add Order"});
        printForm(resp, new String[] {"orderId1"}, new String[]{"Order ID"}, new String[]{"Remove Order"});
        printForm(resp, new String[] {"orderId2"}, new String[]{"Order ID"}, new String[]{"Edit Order"});
        printForm(resp, new String[] {"orderId3"}, new String[]{"Order ID"}, new String[]{"View Order"});
        printForm(resp, new String[] {"supplierId"}, new String[]{"Supplier ID"}, new String[]{"View Orders From Supplier"});

        String val;


        if ((val = getParamVal(req,"addOrder")) != null &&  val.equals("true")){
            String supId = getParamVal(req,"supId");
            String  storeId = getParamVal(req,"storeId");
            if(storeId != null & supId != null)
                addOrder(req, resp, Integer.parseInt(storeId), Integer.parseInt(supId));
        }
        else if ((val = getParamVal(req,"viewAllOrders")) != null && val.equals("true")){
            String supId = getParamVal(req,"supId");
            if(supId != null)
                showAllOrders(req, resp, Integer.parseInt(supId));
        }
        else if ((val = getParamVal(req,"editOrder")) != null && val.equals("true")){
            String orderId = getParamVal(req,"orderId");
            if(orderId != null)
                editOrder(req, resp, Integer.parseInt(orderId));
        }
        else if ((val = getParamVal(req,"viewOrder")) != null && val.equals("true")){
            String orderId = getParamVal(req,"orderId");
            if(orderId != null )
                printOrder(req, resp, Integer.parseInt(orderId));
        }
        handleError(resp);
    }

    private void printOrderIds(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        try {
            PrintWriter out = resp.getWriter();
            out.println("<h2>");
            ArrayList<Integer> supplierIds = controller.getSuppliersID();
            for(Integer id : supplierIds){
                List<Integer> orderIds = controller.geOrdersID(id);
                out.print(String.format("Order from Supplier %s  : ", id));
                out.println(orderIds + "<br>");
            }
            out.println("</h2>");
        } catch (Exception e) {
            setError("No orders available!");
            refresh(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Add Order")){
            String supplierId = req.getParameter("supplierId");
            String storeId = req.getParameter("storeId");
            redirect(resp, ManageOrders.class, new String[]{"addOrder","supId","storeId"}, new String[]{"true",supplierId, storeId});
            //addOrder(req, resp);
        }
        else if(isButtonPressed(req, "Remove Order")){
            removeOrder(req, resp);
        }
        else if(isButtonPressed(req, "Edit Order")){
            String orderId = req.getParameter("orderId2");
            redirect(resp, ManageOrders.class, new String[]{"editOrder", "orderId"}, new String[]{"true", orderId});
            //editOrder(req, resp);
        }
        else if(isButtonPressed(req, "View Order")){
            String orderId = req.getParameter("orderId3");
            redirect(resp, ManageOrders.class, new String[]{"viewOrder", "orderId"}, new String[]{"true", orderId});
            //printOrder(req, resp);
        }
        else if(isButtonPressed(req,"View Orders From Supplier")){
            String supplierId = req.getParameter("supplierId");
            redirect(resp, ManageOrders.class, new String[]{"viewAllOrders","supId"}, new String[]{"true",supplierId});
            //showAllOrders(req, resp);
        }

    }



    private void showAllOrders(HttpServletRequest req, HttpServletResponse resp, int supplierId) throws IOException {
        try {
            //int supplierId = Integer.parseInt(req.getParameter("supplierId"));
            ArrayList<ServiceOrderObject> r = controller.getAllOrdersForSupplier(supplierId);
            if(r != null && r.size() > 0){
                PrintWriter out = resp.getWriter();
                for(ServiceOrderObject orderObject : r){
                    out.println(orderObject.toString() + "<br><br>");
                }
            }
            else{
                setError("No orders available!");
                //refresh(req, resp);
            }
        } catch (NumberFormatException e1){
            setError("Please enter a number!");
            //refresh(req, resp);
        }
        catch (Exception e) {
            setError(e.getMessage());
            //refresh(req, resp);
        }
    }

    private void removeOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int orderId = Integer.parseInt(req.getParameter("orderId1"));
            if(controller.removeOrder(orderId) ){
                setError(String.format("Order %d was removed", orderId));
                refresh(req, resp);
            }
            else{
                setError("Order wasn't removed!");
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

    private void editOrder(HttpServletRequest req, HttpServletResponse resp, int orderId) throws IOException {
        try {
            int supplierId = controller.getSupplierWIthOrderID(orderId);
            //addCookie(String.valueOf(orderId), "OrderIdToEditOrder", resp, 10);
            redirect(resp, EditOrder.class, new String[]{"supId","orderId"},  new String[]{String.valueOf(supplierId),String.valueOf(orderId) });
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void printOrder(HttpServletRequest req, HttpServletResponse resp, int orderId) throws IOException {
        try {
            ServiceOrderObject result = controller.getOrder(orderId);
            if(result != null){
                PrintWriter out = resp.getWriter();
                out.println(result.toString() + "<br><br>");
            }
            else{
                setError("Something went wrong, try again later");
                //refresh(req, resp);
            }
        } catch (NumberFormatException e1){
            setError("Please enter a number!");
            //refresh(req, resp);
        }
        catch (Exception e) {
            setError(e.getMessage());
            //refresh(req, resp);
        }
    }

    private void addOrder(HttpServletRequest req, HttpServletResponse resp, int storeId, int supplierId) throws IOException {
        try {
            //int supplierId = Integer.parseInt(req.getParameter("supplierId"));
            //int storeId = Integer.parseInt(req.getParameter("storeId"));
            int orderId = controller.order(supplierId, storeId);

            if(orderId != -1){
                redirect(resp, AddOrderItem.class, new String[]{"supId","orderId"}, new String[]{String.valueOf(supplierId) ,String.valueOf(orderId)});

                /*
                if (getCookie("OrderIdToAddItem",req,resp,10) == null || !getCookie("OrderIdToAddItem",req,resp,10).equals(String.valueOf(orderId))) {  //if addOrder didn't enter the cookie
                    addCookie(String.valueOf(orderId), "OrderIdToAddItem", resp, 10);
                }
                //if (getCookie("supIdAddOrderItem",req,resp,10) == null || getCookie("supIdAddOrderItem",req,resp,10).equals("")) {  //if addOrder didn't enter the cookie
                addCookie(String.valueOf(supplierId), "supIdAddOrderItem", resp, 10);
                //}

                 */
            }
            else{
                setError("Order wasn't added!");
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


    /*
    private void addCookie(String value, String nameOfCookie, HttpServletResponse resp, int time) {
        Cookie c = new Cookie(nameOfCookie, value);
        c.setMaxAge((int) TimeUnit.MINUTES.toSeconds(time));
        resp.addCookie(c);
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
