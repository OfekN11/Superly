package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class RemoveViewOrder extends Screen {


    //private static final String greet = "Remove Order for HR & Logistics";


    public RemoveViewOrder(String greet) {
        super(greet);
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);


        printOrderIds(resp);
        printForm(resp, new String[] {"orderId1"}, new String[]{"Order ID"}, new String[]{"Remove Order"});
        printForm(resp, new String[] {"orderId3"}, new String[]{"Order ID"}, new String[]{"View Order"});


        handleError(resp);
    }

    protected void printOrderIds(HttpServletResponse resp) {
        try {
            PrintWriter out = resp.getWriter();
            out.println("<h2>");
            ArrayList<Integer> supplierIds = controller.getSuppliersID();
            for(Integer id : supplierIds){
                List<Integer> orderIds = controller.geOrdersID(id);
                // TODO: Supplier change this to normal print!
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



    protected void printOrder(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
}
