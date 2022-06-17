package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class RemoveViewOrder extends Screen {



    public RemoveViewOrder(String greet, Set<Class<? extends Employee>> allowed) {
        super(greet, allowed);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAllowed(req, resp)){
            redirect(resp, Login.class);
        }
        header(resp);
        greet(resp);


        printOrderIds(resp);
        printForm(resp, new String[] {"orderId1"}, new String[]{"Order ID"}, new String[]{"Remove Order"});
        printForm(resp, new String[] {"orderId3"}, new String[]{"Order ID"}, new String[]{"View Order"});

        String val;
        if ((val = getParamVal(req,"viewOrder")) != null && val.equals("true")){
            String orderId = getParamVal(req,"orderId");
            if(orderId != null )
                printOrder(req, resp, orderId);
        }
        handleError(resp);
    }

    protected void printOrderIds(HttpServletResponse resp) {
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

        if(isButtonPressed(req, "Remove Order")){
            removeOrder(req, resp);
        }

        else if(isButtonPressed(req, "View Order")){
            String orderId = req.getParameter("orderId3");
            redirect(resp, RemoveViewOrder.class, new String[]{"viewOrder", "orderId"}, new String[]{"true", orderId});
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



    protected void printOrder(HttpServletRequest req, HttpServletResponse resp, String orderIdString) throws IOException {
        try {
            int orderId = Integer.parseInt(orderIdString);
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
            //refresh(req, resp);
        }
        catch (Exception e) {
            setError(e.getMessage());
            //refresh(req, resp);
        }
    }
}
