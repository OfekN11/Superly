package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class EditCard extends Screen {


    private static final String greet = "Edit Card";
    private final int supplierId;

    public EditCard() {
        super(greet);
        supplierId = 1;
    }



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printForm(resp, new String[] {"bankNumber"}, new String[]{"Bank Number"}, new String[]{"Update Bank Number"});
        printForm(resp, new String[] {"address" }, new String[]{"Address"}, new String[]{"Update Address"});
        printForm(resp, new String[] {"name" }, new String[]{"name"}, new String[]{"Update Name"});
        printForm(resp, new String[] {"payingAgreement" }, new String[]{"Paying Agreement"}, new String[]{"Update Paying Agreement"});


        handleError(resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, "Update Bank Number")) {
            updateBankNumber(req,resp);
        }
        else if(isButtonPressed(req, "Update Address")){
            updateAddress(req,resp);
        }
        else if(isButtonPressed(req, "Update Name")){
            updateName(req,resp);
        }
        else if(isButtonPressed(req, "Update Paying Agreement")){
            updatePayingAgreement(req,resp);
        }
    }

    private void updateBankNumber(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int num = Integer.parseInt(req.getParameter("bankNumber"));
            if(controller.updateSupplierBankNumber(supplierId, num) ){

                // TODO: Supplier change this to normal print!
                setError(String.format("Bank number updated to %d", num));
                refresh(req, resp);
            }
            else{
                setError("Bank number wasn't updated!");
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

    private void updateAddress(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("address");
            if(controller.updateSupplierAddress(supplierId, name) ){

                // TODO: Supplier change this to normal print!
                setError(String.format("Address updated to %s", name));
                refresh(req, resp);
            }
            else{
                setError("Address wasn't updated!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void updateName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("name");
            if(controller.updateSupplierName(supplierId, name) ){

                // TODO: Supplier change this to normal print!
                setError(String.format("Name updated to %s", name));
                refresh(req, resp);
            }
            else{
                setError("Name wasn't updated!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

    private void updatePayingAgreement(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            String name = req.getParameter("payingAgreement");
            if(controller.updateSupplierPayingAgreement(supplierId, name) ){

                // TODO: Supplier change this to normal print!
                setError(String.format("Paying Agreement updated to %s", name));
                refresh(req, resp);
            }
            else{
                setError("Paying Agreement wasn't updated!");
                refresh(req, resp);
            }
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }

}
