package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Domain.Service.Objects.SupplierObjects.ServiceOrderItemObject;
import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Domain.Service.Objects.SupplierObjects.ServiceSupplierObject;
import Presentation.CLIPresentation.Screens.SupplierScreens.ViewByOrderAgreement;
import Presentation.CLIPresentation.Screens.SupplierScreens.ViewNotTransportingAgreement;
import Presentation.CLIPresentation.Screens.SupplierScreens.ViewRoutineAgreement;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class ViewSupplier extends Screen {

    private static final String greet = "View Supplier for Storekeeper and Store Manager";
    private final int supplierId;
    private static final String addAgreement = "Add New Agreement";

    private static boolean showItems = false;
    public ViewSupplier() {
        // TODO: Supplier pass supplierId
        super(greet);
        supplierId = 1;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        for (Cookie c : req.getCookies()) {
            if (c.getName().equals("sup_id")) {
                resp.getWriter().println("<h2>handling supplier id " + c.getValue() + "</h2><br>");
            }
            //time of life of the cookie, if bot listed its infinite
            c.setMaxAge((int) TimeUnit.MINUTES.toSeconds(2));
            resp.addCookie(c);
        }
        printMenu(resp, new String[]{"Show Supplier Info", "Show Contacts","Show Manufacturers", "Show Agreement", "Show all Orders", "Show all discount items", "Edit Card"});


        printForm(resp, new String[] {"agreementType", "agreementDays" }, new String[]{"Agreement Type", "Agreement Days"}, new String[]{addAgreement});
        printInstructions(resp);

        if (req.getParameter("showItems") != null && req.getParameter("showItems").equals("true")){
            showSupplierInfo(req, resp);
            showItems = false;
        }
        handleError(resp);
    }

    private void printInstructions(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        //out.println("<h4>");
        out.println("<h4>Type should be 1, 2 or 3 as follows:</h4><br><br>");
        out.println("<h5>1) Routine agreement</h5><br><br>");
        out.println("<h5>2) By order agreement</h5><br><br>");
        out.println("<h5>3) Self-Transport agreement</h5><br><br>");
        out.println("<h5>Enter Agreement Days with ',' between, like this: 1,3,5,6</h5><br><br>");
    //    out.println("</h4>");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, addAgreement)){
            addAgreement(req, resp);
        }

        switch (getIndexOfButtonPressed(req)){
            case 0:
                resp.sendRedirect("/ViewSupplier?showItems=true");
                //showSupplierInfo(req, resp);
                break;
            case 1:
                // TODO: Suppliers pass supplierId
                redirect(resp, ManageContacts.class);
                break;
            case 2:
                // TODO: Suppliers pass supplierId
                redirect(resp, ManageManufacturers.class);
                break;
            case 3:
                // TODO: Suppliers pass supplierId
                showAgreement(req, resp);
                break;
            case 4:
                showAllOrders(req, resp);
                break;
            case 5:
                showAllDiscountItems(req, resp);
                break;
            case 6:
                // TODO: Suppliers pass supplierId
                redirect(resp, EditCard.class);
                break;
        }
    }

    private void showAgreement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            if(!controller.hasAgreement(supplierId)){

                // TODO: Supplier change this to normal print!
                setError("No agreement with this supplier.");
                refresh(req, resp);
            }
            else{
                // TODO: Suppliers pass supplierId
                redirect(resp, ShowAgreement.class);
            }
            /* TODO: Don't think we need 3 windows
            if(controller.isRoutineAgreement(supplierId)){
                // TODO: Suppliers pass supplierId
                redirect(resp, ShowRoutineAgreement.class);
            }
            else{
                if(controller.isByOrderAgreement(supplierId)){
                    // TODO: Suppliers pass supplierId
                    redirect(resp, ShowBuOrderAgreement.class);
                }
                else{
                    // TODO: Suppliers pass supplierId
                    redirect(resp, ShowNotTransportingAgreement.class);
                }
            }

             */
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }


    private void showAllDiscountItems(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            ArrayList<ServiceOrderItemObject> r = controller.getAllOrdersItemsInDiscounts(supplierId);
            if(r != null && r.size() > 0){
                for(ServiceOrderItemObject orderItemObject : r){
                    float originalPrice = orderItemObject.getQuantity() * orderItemObject.getPricePerUnit();

                    // TODO: Supplier change this to normal print!
                    setError(orderItemObject.toStringDiscount(originalPrice) + "\n");
                    refresh(req, resp);
                }
            }
            else{
                // TODO: Supplier change this to normal print!
                setError("No order Items available!");
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

    private void showAllOrders(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            ArrayList<ServiceOrderObject> r = controller.getAllOrdersForSupplier(supplierId);
            if(r != null && r.size() > 0){
                for(ServiceOrderObject orderObject : r){

                    // TODO: Supplier change this to normal print!
                    setError(orderObject.toString());
                    refresh(req, resp);
                }
            }
            else{
                // TODO: Supplier change this to normal print!
                setError("No orders available!");
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

    private void addAgreement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int agreementType = Integer.parseInt(req.getParameter("agreementType"));
            String agreementDays = req.getParameter("agreementDays");
            if (!controller.hasAgreement(supplierId)) {
                if(agreementType == 1 || agreementType == 2 || agreementType == 3) {
                    if (controller.addAgreement(supplierId, agreementType, agreementDays)) {

                        // TODO: Supplier change this to normal print
                        //  If it stays error, it causes an error!, I think it's the refresh... just don't do this step...
                        //setError("Now, let's add the items included in the agreement.");
                        //refresh(req, resp);
                        redirect(resp, AddItemToAgreement.class);
                    } else {
                        setError("A problem has occurred, please try again later");
                        refresh(req, resp);
                    }
                } else {
                    setError("Wrong number!, enter 1, 2 or 3");
                    refresh(req, resp);
                }
            }
            else{
                // TODO: Supplier change this to normal print
                setError("Agreement Already Exists!, if you want to change it, go to Show Agreement Window");
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


    private void showSupplierInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            ServiceSupplierObject result = controller.getSupplierInfo(supplierId);
            if(result != null){

                /*

                // TODO: Supplier change this to normal print!
                setError(result.toString());
                refresh(req, resp);
                 */
                PrintWriter out = resp.getWriter();
                out.println("<h4>");
                out.println(result.toString());
                out.println("addition");
                out.println("</h4>");

            }
            else{
                setError("Something went wrong!");
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
