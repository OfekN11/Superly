package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Domain.Service.Objects.SupplierObjects.ServiceItemObject;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ShowAgreement extends Screen {

    //private final int supplierId;
    //private int agreementType;
    private static final String greet = "Agreement Info";
    private static final String greetRoutine = "Routine Supplier";
    private static final String greetByOrder = "By Order Supplier";
    private static final String greetNotTransporting = "Not Transporting Supplier";


    public ShowAgreement(){
        super(greet);
        //this.supplierId = 1;

        //agreementType = getAgreementType();
    }

    private int getAgreementType(int supId) {
        try {
            if(controller.isRoutineAgreement(supId))
                return 1;
            else if(controller.isByOrderAgreement(supId))
                return 2;
        } catch (Exception e) {
            setError(e.getMessage());
        }
        return 3;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        int supId = getSupplierId(req, resp);
        int agreementType = getAgreementType(supId);

        printMenu(resp, new String[]{"Show All Items", "Add item to agreement"});
        printForm(resp, new String[] {"idBySupplier"}, new String[]{"ID By Supplier"}, new String[]{"Remove Item"});
        printForm(resp, new String[] {"idBySupplier2"}, new String[]{"ID By Supplier"}, new String[]{"View Item"});

        // TODO: 11/06/2022 Should we do it? maybe it can cause problems...
        printForm(resp, new String[] {"agreementType", "agreementDays" }, new String[]{"Agreement Type", "Agreement Days"}, new String[]{"Change Agreement Type"});
        printInstructions(resp);

        if(agreementType == 1){  //routine
            printForm(resp, new String[] {"agreementDays2" }, new String[]{"Delivery Days"}, new String[]{"Change Delivery Days"});
            printInstructionsDeliveryDays(req, resp);

        }
        else if(agreementType == 2){  //byOrder
            printForm(resp, new String[] {"day" }, new String[]{"Days Until Delivery"}, new String[]{"Change Days Until Delivery"});
            printInstructionsDaysUntilDelivery(resp);
        }

        chooseAction(req, resp, supId);

        handleError(resp);
    }

    private void chooseAction(HttpServletRequest req, HttpServletResponse resp, int supId) throws IOException {

        if (req.getParameter("showItems") != null && req.getParameter("showItems").equals("true")){
            showAllItems(req, resp, supId);
        }
        else if (req.getParameter("viewItem") != null && req.getParameter("viewItem").equals("true")){
            int itemId = Integer.parseInt(getCookie("itemId2ShowAgreement", req, resp, 5));
            viewItem(req, resp, itemId, supId);
        }
    }


    private void printInstructions(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<h4>Agreement Type should be 1, 2 or 3 as follows:</h4>");
        out.println("<h5>1) Routine agreement<br>");
        out.println("2) By order agreement<br>");
        out.println("3) Self-Transport agreement</h5>");
        out.println("<h4>Enter Agreement Days with ',' between, like this: 1,3,5,6</h4>");
    }


    private void printInstructionsDaysUntilDelivery(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<h4>");
        out.println("Enter a number which will be the days until delivery.<br>");
        out.println("</h4>");
    }

    private void printInstructionsDeliveryDays(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<h4>");
        out.println("Enter delivery days with ',' between, like this: 1,3,5,6 <br>");
        out.println("</h4>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Remove Item")) {
            removeItemFromAgreement(req, resp);
        }
        else if(isButtonPressed(req, "View Item")){
            try {
                addCookiesForViewItem(req, resp);
                resp.sendRedirect("/ShowAgreement?viewItem=true");
            } catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }
        }
        else if(isButtonPressed(req, "Change Agreement Type")) {
            changeAgreementType(req, resp);
        }

        else if(isButtonPressed(req, "Change Delivery Days")){
            changeRoutineDays(req, resp);
        }
        else if(isButtonPressed(req, "Change Days Until Delivery")){
            changeByOrderDay(req, resp);
        }


        switch (getIndexOfButtonPressed(req)){
            case 0:
                resp.sendRedirect("/ShowAgreement?showItems=true");
                break;
            case 1:
                addItemToAgreement(req, resp);
                break;

        }
    }



    private void addItemToAgreement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            redirect(resp, AddItemToAgreement.class);
        } catch (Exception e) {
            setError("Item is not in the system!, Please enter Id By supplier!");
            refresh(req, resp);
        }
    }

    private void viewItem(HttpServletRequest req, HttpServletResponse resp, int itemId, int supId) throws IOException {
        try {

            addCookie(String.valueOf(supId), "supIdShowAgreementItem", resp,5);
            addCookie(String.valueOf(itemId), "ItemIdShowAgreementItem", resp,5);
            redirect(resp, ShowAgreementItem.class);
        } catch (Exception e) {
            setError("Item is not in the system!, Please enter Id By supplier!");
            refresh(req, resp);
        }
    }

    private void changeByOrderDay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int day = Integer.parseInt(req.getParameter("day"));
            int supId = getSupplierId(req, resp);
            if(controller.changeDaysUntilDelivery(supId, day)){

                setError(String.format("Days until delivery updated to %s", day));
                refresh(req, resp);
            }
            else{
                setError("Day wasn't updated!");
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

    private void changeRoutineDays(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String days = req.getParameter("agreementDays2");
            int supId = getSupplierId(req, resp);
            if(days.length() > 0 && controller.setDaysOfDelivery(supId, days) ){

                setError(String.format("Days updated to %s", days));
                refresh(req, resp);
            }
            else{
                setError("Days had not been updated!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void changeAgreementType(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int type = Integer.parseInt(req.getParameter("agreementType"));
            String days = req.getParameter("agreementDays");
            int supId = getSupplierId(req, resp);

            // TODO: Supplier check what it does when not inserting anything for not transporting! should be ""
            if((type == 1 || type == 2 || type == 3) && controller.changeAgreementType(supId, type, days)){

                setError("Agreement type was changed successfully");
                refresh(req, resp);

                //agreementType = type;
            }
            else{
                setError("Agreement wasn't changed!");
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


    private void removeItemFromAgreement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int idBySupplier = Integer.parseInt(req.getParameter("idBySupplier"));
            int supId = getSupplierId(req, resp);
            int itemId = controller.getMatchingProductIdForIdBySupplier(idBySupplier);

            if(controller.deleteItemFromAgreement(supId, itemId) ){
                setError(String.format("Deleted Item %d", idBySupplier));
                refresh(req, resp);
            }
            else{
                setError("Item wasn't deleted!");
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

    private void showAllItems(HttpServletRequest req, HttpServletResponse resp, int supId) throws IOException {

        try {
            List<ServiceItemObject> list = controller.itemsFromOneSupplier(supId);
            if(list.isEmpty()){
                setError("[NO ITEMS ARE IN THE AGREEMENT]");
                refresh(req, resp);
            }

            for(ServiceItemObject orderObject : list) {
                PrintWriter out = resp.getWriter();
                out.println(orderObject.toString() + "<br><br>");
                //refresh(req, resp);
            }

        }
        catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
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



    private void addCookiesForChangeAgreement(HttpServletRequest req, HttpServletResponse resp) {
        //int type = Integer.parseInt(req.getParameter("agreementType"));
        //String days = req.getParameter("agreementDays");
        //addCookie(String.valueOf(type), "typeShowAgreement", resp, 2);
        //addCookie(days, "daysShowAgreement", resp, 2);

    }

    private void addCookiesForViewItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int num = Integer.parseInt(req.getParameter("idBySupplier2"));
        int itemId = controller.getMatchingProductIdForIdBySupplier(num);
        addCookie(String.valueOf(itemId), "itemId2ShowAgreement", resp, 5);
    }


    private void addCookiesForChangeDeliveryDays(HttpServletRequest req, HttpServletResponse resp) {
        //String days = req.getParameter("agreementDays2");
        //addCookie(days, "agreementDays2ShowAgreement", resp, 1);
    }

    private void addCookiesForChangeDaysUntilDelivery(HttpServletRequest req, HttpServletResponse resp) {
        //int day = Integer.parseInt(req.getParameter("day"));
        //addCookie(String.valueOf(day), "dayShowAgreement", resp, 1);
    }


    private void addCookie(String value, String nameOfCookie, HttpServletResponse resp, int time) {
        Cookie c = new Cookie(nameOfCookie, value);
        c.setMaxAge((int) TimeUnit.MINUTES.toSeconds(time));
        resp.addCookie(c);
    }



}
