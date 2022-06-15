package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public abstract class SupplierMainMenu extends Screen {

    //private static final String greet = "Supplier's Main Menu ";
    private static final String button = "View Supplier";


    public SupplierMainMenu(String greet) {
        super(greet);
    }


    @Override
    protected abstract void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    @Override
    protected abstract void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;



    /*
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printMenu(resp, new String[]{"Manage Suppliers", "View/Remove Orders"});

        printSupplierIds(resp);
        printForm(resp, new String[] {"ID"}, new String[]{"Supplier ID"}, new String[]{button});

        handleError(resp);
    }

     */

    /*
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, button)){
            viewSupplier(req, resp);
        }

        if (getIndexOfButtonPressed(req) == 0) {
            redirect(resp, ManageSuppliers.class);
        }

        //Manage Orders should be in extended classes!
    }

     */

    protected void viewSupplier(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int supplierId = Integer.parseInt(req.getParameter("ID"));
            if(controller.doesSupplierExists(supplierId)) {
                // TODO: Supplier Pass supplierID to the the supplier
                //enter the list a cookie named sup_id with value supplierId
                Cookie c = new Cookie("sup_id", String.valueOf(supplierId));
                resp.addCookie(c);
                redirect(resp, ViewSupplier.class);
            }
            else{
                setError("No such supplier, please try again.");
                refresh(req, resp);
            }
        }catch (NumberFormatException e1){
            setError("Please enter a number!");
            refresh(req, resp);
        }
        catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }


    protected void printSupplierIds(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        try {
            ArrayList<Integer> supplierIds = controller.getSuppliersID();
            PrintWriter out = resp.getWriter();
            out.println("<h2>");
            out.println("Suppliers available:  " + supplierIds);
            out.println("</h2>");
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }

    }
}
