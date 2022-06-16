package Presentation.WebPresentation.Screens.ViewModels.Suppliers;

import Domain.Service.Objects.SupplierObjects.ServiceItemObject;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class ShowAgreementItem extends Screen {


    private static final String greet = "View Agreement Item";

    public ShowAgreementItem() {
        super(greet);

    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        int itemId = getItemId(req);
        int supId = getSupplierId(req);

        resp.getWriter().println(String.format("<h2>Item %d Management for Supplier %d.</h2><br>", itemId, supId));

        printMenu(resp, new String[]{"View Item"});
        printForm(resp, new String[] {"manufacturer"}, new String[]{"Item Manufacturer"}, new String[]{"Change manufacturer"});
        printForm(resp, new String[] {"ppu"}, new String[]{"Item price per unit"}, new String[]{"Change price per unit"});
        printForm(resp, new String[] {"quantity1", "discount1"}, new String[]{"Quantity", "Discount"}, new String[]{"Add Bulk"});
        printForm(resp, new String[] {"quantity2"}, new String[]{"Quantity"}, new String[]{"Remove Bulk"});
        printForm(resp, new String[] {"quantity3", "discount3"}, new String[]{"Quantity", "Discount"}, new String[]{"Change Discount for Quantity"});
        printForm(resp, new String[] {"quantity4"}, new String[]{"Quantity"}, new String[]{"Calculate total price of viewed item provided quantity"});

        String val;
        if ((val = getParamVal(req,"showItem")) != null && val.equals("true")){
            viewItem(req, resp);
        }

        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);


        if (isButtonPressed(req, "Change manufacturer")) {
            changeManufacturer(req, resp);
        }
        /*
        else if (isButtonPressed(req, "Change Id")) {
            changeId(req, resp);
        }
         */
        else if (isButtonPressed(req, "Change price per unit")) {
            changePPU(req, resp);
        }
        else if (isButtonPressed(req, "Add Bulk")) {
            addBulkPrice(req, resp);
        }
        else if (isButtonPressed(req, "Remove Bulk")) {
            removeBulkPrice(req, resp);
        }
        else if (isButtonPressed(req, "Change Discount for Quantity")) {
            changeBulkPrice(req, resp);
        }
        else if(isButtonPressed(req, "Calculate total price of viewed item provided quantity")){ //calculate...
            calculateTotalPrice(req, resp);
        }
        else if(getIndexOfButtonPressed(req) == 0){ //view Item
            String itemId = String.valueOf(getItemId(req));
            String supplierId = String.valueOf(getSupplierId(req));
            redirect(resp, ShowAgreementItem.class, new String[]{"showItem","supId","itemId"}, new String[]{"true", supplierId, itemId});
            //viewItem(req, resp);
        }


    }

    private void calculateTotalPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int itemId = getItemId(req);
            int supplierId = getSupplierId(req);

            int quantity = Integer.parseInt(req.getParameter("quantity4"));
            Double total = controller.calculatePriceForItemOrder(supplierId, itemId, quantity);
            setError(String.format("Bulk Price updated for quantity %f.", total));
            refresh(req, resp);
        } catch (NumberFormatException e1){
            setError("Please enter a number!");
            refresh(req, resp);
        }
        catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void viewItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int itemId = getItemId(req);
            int supplierId = getSupplierId(req);

            PrintWriter out = resp.getWriter();
            ServiceItemObject r  = controller.getItem(supplierId, itemId);
            if(r != null){
                out.println("<h4>" + r.toString() + "</h4>");
            }
            else{
                setError("Something went wrong, please try again!");
                //refresh(req, resp);
            }
        }
        catch (Exception e) {
            setError(e.getMessage());
            //refresh(req, resp);
        }
    }

    private void changeBulkPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int itemId = getItemId(req);
            int supplierId = getSupplierId(req);
            int quantity = Integer.parseInt(req.getParameter("quantity3"));
            int discount = Integer.parseInt(req.getParameter("discount3"));

            if(controller.editBulkPriceForItem(supplierId, itemId, quantity, discount)){
                setError(String.format("Bulk Price updated for quantity %d.", quantity));
                refresh(req, resp);
            }
            else{
                setError("Bulk Price wasn't updated!");
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

    private void removeBulkPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int itemId = getItemId(req);
            int supplierId = getSupplierId(req);
            int quantity = Integer.parseInt(req.getParameter("quantity2"));
            if(controller.removeBulkPriceForItem(supplierId, itemId, quantity)){
                setError(String.format("Bulk Price removed for quantity %d.", quantity));
                refresh(req, resp);
            }
            else{
                setError("Bulk Price wasn't removed!");
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

    private void addBulkPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int itemId = getItemId(req);
            int supplierId = getSupplierId(req);
            int quantity = Integer.parseInt(req.getParameter("quantity1"));
            int discount = Integer.parseInt(req.getParameter("discount1"));
            if(controller.addBulkPriceForItem(supplierId, itemId, quantity, discount) ){
                setError(String.format("Bulk Price added to quantity %d.", quantity));
                refresh(req, resp);
            }
            else{
                setError("Bulk Price wasn't added!");
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

    private void changePPU(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int itemId = getItemId(req);
            int supplierId = getSupplierId(req);
            float num = Float.parseFloat(req.getParameter("ppu"));
            if(controller.updatePricePerUnitForItem(supplierId, itemId, num) ){
                setError(String.format("Price per unit updated to %f", num));
                refresh(req, resp);
            }
            else{
                setError("Price per unit wasn't updated!");
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

    private void changeManufacturer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int itemId = getItemId(req);
            int supplierId = getSupplierId(req);
            String name = req.getParameter("manufacturer");
            if (controller.updateItemManufacturer(supplierId, itemId, name)) {
                setError(String.format("Manufacturer updated to %s", name));
                refresh(req, resp);
            } else {
                setError("Manufacturer wasn't updated!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private int getItemId(HttpServletRequest req) {
        return Integer.parseInt(getParamVal(req,"itemId"));

    }

    private int getSupplierId(HttpServletRequest req) {
        return Integer.parseInt(getParamVal(req,"supId"));

    }

    /*
    private void changeName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("itemName");
            if (controller.updateItemName(supplierId, itemId, name)) {

                setError(String.format("Name updated to %s", name));
                refresh(req, resp);
            } else {
                setError("Name wasn't updated!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }



    private void changeId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int itemId = getId(itemIdCookieName, req, resp);
            int supplierId = getSupplierId(req, resp);

            int num = Integer.parseInt(req.getParameter("itemId"));
            if(controller.updateItemId(supplierId, itemId,num) ){
                setError(String.format("ID updated to %d", num));
                refresh(req, resp);
            }
            else{
                setError("ID wasn't updated!");
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
 */


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
