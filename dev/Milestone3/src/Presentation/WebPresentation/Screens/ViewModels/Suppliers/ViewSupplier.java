package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Domain.Service.Objects.SupplierObjects.ServiceOrderItemObject;
import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Domain.Service.Objects.SupplierObjects.ServiceSupplierObject;
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
    private static final String addAgreement = "Add New Agreement";
    //private static boolean showItems = false;

    public ViewSupplier() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        int supId = getSupplierId(req, resp);
        resp.getWriter().println("<h2>Watching Supplier " + supId + ".</h2><br>");

        printMenu(resp, new String[]{"Show Supplier Info", "Show Contacts","Show Manufacturers", "Show Agreement", "Show all Orders", "Show all discount items", "Edit Card"});


        printForm(resp, new String[] {"agreementType", "agreementDays" }, new String[]{"Agreement Type", "Agreement Days"}, new String[]{addAgreement});
        printInstructions(resp);


        if (req.getParameter("showItems") != null && req.getParameter("showItems").equals("true")){
            showSupplierInfo(req, resp, supId);
        }
        else if (req.getParameter("showAllOrders") != null && req.getParameter("showAllOrders").equals("true")){
            showAllOrders(req, resp, supId);
        }
        else if (req.getParameter("showAllDiscountItems") != null && req.getParameter("showAllDiscountItems").equals("true")){
            showAllDiscountItems(req, resp, supId);
        }
        handleError(resp);
    }



    private void printInstructions(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<h4>Agreement Type should be 1, 2 or 3 as follows:</h4>");
        out.println("<h5>1) Routine agreement<br>");
        out.println("2) By order agreement<br>");
        out.println("3) Self-Transport agreement</h5>");
        out.println("<h4>Enter Agreement Days with ',' between, like this: 1,3,5,6</h4>");
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
                break;
            case 1:
                manageContacts(req, resp);
                break;
            case 2:
                manageManufacturers(req,resp);
                break;
            case 3:
                showAgreement(req, resp);
                break;
            case 4:
                resp.sendRedirect("/ViewSupplier?showAllOrders=true");
                break;
            case 5:
                resp.sendRedirect("/ViewSupplier?showAllDiscountItems=true");
                break;
            case 6:
                redirect(resp, EditCard.class);
                break;
        }
    }


    private void manageContacts(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            redirect(resp, ManageContacts.class);
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void manageManufacturers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            redirect(resp, ManageManufacturers.class);
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void showAgreement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int supId = getSupplierId(req, resp);
            if(!controller.hasAgreement(supId)){
                setError("No agreement with this supplier");
                refresh(req, resp);
            }
            else{
                redirect(resp, ShowAgreement.class);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }


    private void showAllDiscountItems(HttpServletRequest req, HttpServletResponse resp, int supId) throws IOException {
        try{
            PrintWriter out = resp.getWriter();
            ArrayList<ServiceOrderItemObject> r = controller.getAllOrdersItemsInDiscounts(supId);
            if(r != null && r.size() > 0){
                out.println("<h4>");
                for(ServiceOrderItemObject orderItemObject : r){
                    float originalPrice = orderItemObject.getQuantity() * orderItemObject.getPricePerUnit();
                    out.println(orderItemObject.toStringDiscount(originalPrice) + "<br>");
                }
                out.println("</h4>");
            }
            else{
                setError("No order Items available!");
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

    private void showAllOrders(HttpServletRequest req, HttpServletResponse resp, int supId) throws IOException {
        try {
            ArrayList<ServiceOrderObject> r = controller.getAllOrdersForSupplier(supId);
            if(r != null && r.size() > 0){
                for(ServiceOrderObject orderObject : r){
                    PrintWriter out = resp.getWriter();
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

    private void addAgreement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int supId = getSupplierId(req, resp);
            int agreementType = Integer.parseInt(req.getParameter("agreementType"));
            String agreementDays = req.getParameter("agreementDays");

            if (!controller.hasAgreement(supId)) {
                if(agreementType == 1 || agreementType == 2 || agreementType == 3) {
                    if (controller.addAgreement(supId, agreementType, agreementDays)) {

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


    private void showSupplierInfo(HttpServletRequest req, HttpServletResponse resp, int supId) throws IOException {
        try {
            ServiceSupplierObject result = controller.getSupplierInfo(supId);
            if(result != null){
                PrintWriter out = resp.getWriter();
                out.println("<h4>");
                out.println(result.toString("<br>"));
                out.println("</h4>");
            }
            else{
                setError("Something went wrong!");
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


    private int getSupplierId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int supId = -1;
        supId = Integer.parseInt(getCookie("supplierId", req, resp, 10));
        return supId;
    }

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



    private void addCookie(String value, String nameOfCookie, HttpServletResponse resp, int time) {
        Cookie c = new Cookie(nameOfCookie, value);
        c.setMaxAge((int) TimeUnit.MINUTES.toSeconds(time));
        resp.addCookie(c);
    }


}
