package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Domain.Service.Objects.SupplierObjects.ServiceItemObject;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShowAgreementItem extends Screen {



    private static final String greet = "View Agreement Item";
    private int supplierId;
    private int itemId;


    public ShowAgreementItem() {
        super(greet);
        // TODO: Supplier pass itemId and SupplierId
        itemId = 1;
        supplierId = 1;
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printMenu(resp, new String[]{"View Item"});

        printForm(resp, new String[] {"itemId"}, new String[]{"Item ID"}, new String[]{"Change Id"});
        printForm(resp, new String[] {"manufacturer"}, new String[]{"Item Manufacturer"}, new String[]{"Change manufacturer"});
        printForm(resp, new String[] {"ppu"}, new String[]{"Item price per unit"}, new String[]{"Change price per unit"});
        printForm(resp, new String[] {"quantity1", "discount1"}, new String[]{"Quantity", "Discount"}, new String[]{"Add Bulk"});
        printForm(resp, new String[] {"quantity2"}, new String[]{"Quantity"}, new String[]{"Remove Bulk"});
        printForm(resp, new String[] {"quantity3", "discount3"}, new String[]{"Quantity", "Discount"}, new String[]{"Change Discount for Quantity"});
        printForm(resp, new String[] {"quantity4"}, new String[]{"Quantity"}, new String[]{"Calculate total price of viewed item provided quantity"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Change Id")) {
            changeId(req, resp);
        }
        //else if (isButtonPressed(req, "Change Name")) {
        //    changeName(req, resp);
        //}
        else if (isButtonPressed(req, "Change manufacturer")) {
            changeManufacturer(req, resp);
        }
        else if (isButtonPressed(req, "Change price per unit")) {
            changePPU(req, resp);
        }
        else if (isButtonPressed(req, "Add Bulk")) {
            addBulkPrice(req, resp);
        }
        else if (isButtonPressed(req, "Remove Bulk")) {
            removeuBulkPrice(req, resp);
        }
        else if (isButtonPressed(req, "Change Discount for Quantity")) {
            changeBulkPrice(req, resp);
        }
        else if(isButtonPressed(req, "Calculate total price of viewed item provided quantity")){ //calculate...
            calculateTotalPrice(req, resp);
        }
        else if(getIndexOfButtonPressed(req) == 0){ //view Item
            viewItem(req, resp);
        }


    }

    private void calculateTotalPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int quantity = Integer.parseInt(req.getParameter("quantity4"));
            Double total = controller.calculatePriceForItemOrder(supplierId, itemId, quantity);
            // TODO: Supplier change this to normal print!
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
            ServiceItemObject r  = controller.getItem(supplierId, itemId);
            if(r != null){
                // TODO: Supplier change this to normal print!
                setError(String.format(r.toString()));
                refresh(req, resp);
            }
            else{
                setError("Something went wrong, please try again!");
                refresh(req, resp);
            }
        }
        catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void changeBulkPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int quantity = Integer.parseInt(req.getParameter("quantity3"));
            int discount = Integer.parseInt(req.getParameter("discount3"));

            if(controller.editBulkPriceForItem(supplierId, itemId, quantity, discount)){

                // TODO: Supplier change this to normal print!
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

    private void removeuBulkPrice(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int quantity = Integer.parseInt(req.getParameter("quantity2"));

            if(controller.removeBulkPriceForItem(supplierId, itemId, quantity)){

                // TODO: Supplier change this to normal print!
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
            int quantity = Integer.parseInt(req.getParameter("quantity1"));
            int discount = Integer.parseInt(req.getParameter("discount1"));

            if(controller.addBulkPriceForItem(supplierId, itemId, quantity, discount) ){

                // TODO: Supplier change this to normal print!
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
            float num = Float.parseFloat(req.getParameter("ppu"));
            if(controller.updatePricePerUnitForItem(supplierId, itemId, num) ){

                // TODO: Supplier change this to normal print!
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
            String name = req.getParameter("manufacturer");
            if (controller.updateItemManufacturer(supplierId, itemId, name)) {

                // TODO: Supplier change this to normal print!
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

    /*
    private void changeName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("itemName");
            if (controller.updateItemName(supplierId, itemId, name)) {

                // TODO: Supplier change this to normal print!
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

     */

    private void changeId(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int num = Integer.parseInt(req.getParameter("itemId"));
            if(controller.updateItemId(supplierId, itemId,num) ){
                itemId = num;
                // TODO: Supplier change this to normal print!
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


}
