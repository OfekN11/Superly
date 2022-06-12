package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SupplierMainMenuStorekeeper extends Screen {

    private static final String greet = "Supplier's Main Menu for StoreKeeper!";
    private static final String button = "View Supplier";

    public SupplierMainMenuStorekeeper() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printMenu(resp, new String[]{"Manage Suppliers", "Manage Orders"});

        printSupplierIds(resp);
        printForm(resp, new String[] {"ID"}, new String[]{"Supplier ID"}, new String[]{button});

        handleError(resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, button)){
            try {
                int supplierId = Integer.parseInt(req.getParameter("ID"));
                if(/*controller.doesSupplierExists(supplierId)*/supplierId == -1) {
                    // TODO: Supplier Pass supplierID to the the supplier
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

        switch (getIndexOfButtonPressed(req)){
            case 0:
                redirect(resp, ManageSuppliers.class);
                break;
            case 1:
                redirect(resp, ManageOrders.class);
                break;

        }
    }



    private void printSupplierIds(HttpServletResponse resp) {
        try {
            ArrayList<Integer> supplierIds = controller.getSuppliersID();
            PrintWriter out = resp.getWriter();
            out.println("<h2>");
            out.println("Suppliers available:  " + supplierIds);
            out.println("</h2>");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
