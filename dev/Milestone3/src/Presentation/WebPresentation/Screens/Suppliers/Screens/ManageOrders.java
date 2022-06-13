package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class ManageOrders extends Screen {

    private static final String greet = "Manage orders for Storekeeper";


    public ManageOrders() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);


        printOrderIds(resp);
        printForm(resp, new String[] {"supplierId","storeId"}, new String[]{"Supplier ID", "Store ID"}, new String[]{"Add Order"});
        printForm(resp, new String[] {"orderId1"}, new String[]{"Order ID"}, new String[]{"Remove Order"});
        printForm(resp, new String[] {"orderId2"}, new String[]{"Order ID"}, new String[]{"Edit Order"});
        printForm(resp, new String[] {"orderId3"}, new String[]{"Order ID"}, new String[]{"View Order"});


        handleError(resp);
    }

    private void printOrderIds(HttpServletResponse resp) {
        try {
            PrintWriter out = resp.getWriter();
            out.println("<h2>");
            ArrayList<Integer> supplierIds = controller.getSuppliersID();
            for(Integer id : supplierIds){
                List<Integer> orderIds = controller.geOrdersID(id);
                out.print(String.format("Order from Supplier %s  : ", id));
                out.println(orderIds);
            }
            out.println("</h2>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Add Order")){
            addOrder(req, resp);
        }
        else if(isButtonPressed(req, "Remove Order")){
            removeOrder(req, resp);
        }
        else if(isButtonPressed(req, "Edit Order")){
            //get the order id...
            editOrder(req, resp);

        }
        else if(isButtonPressed(req, "View Order")){
            printOrder(req, resp);
        }

    }

    private void removeOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int orderId = Integer.parseInt(req.getParameter("orderId1"));
            if(controller.removeOrder(orderId) ){

                // TODO: Supplier change this to normal print!
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

    private void editOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // TODO: Supplier pass orderId, supplierId
        int orderId = Integer.parseInt(req.getParameter("orderId2"));
        redirect(resp, EditOrder.class);
    }

    private void printOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int orderId = Integer.parseInt(req.getParameter("orderId3"));
            ServiceOrderObject result = controller.getOrder(orderId);
            if(result != null){

                // TODO: Supplier change this to normal print!
                setError(result.toString());
                refresh(req, resp);
            }
            else{
                setError("Something went wrong, try again later");
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

    private void addOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int supplierId = Integer.parseInt(req.getParameter("supplierId"));
            int storeId = Integer.parseInt(req.getParameter("storeId"));
            int orderId = -1;
            orderId = controller.order(supplierId, storeId);

            if(orderId != -1){

                // TODO: Supplier pass orderId, supplierId
                redirect(resp, AddOrderItem.class);

                // TODO: Supplier change this to normal print!
                setError(String.format("Order %s added successfully", orderId));
                refresh(req, resp);
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
}
