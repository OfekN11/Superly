package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Domain.Service.Objects.SupplierObjects.ServiceOrderItemObject;
import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Domain.Service.Objects.SupplierObjects.ServiceSupplierObject;
import Presentation.CLIPresentation.Screens.SupplierScreens.ViewByOrderAgreement;
import Presentation.CLIPresentation.Screens.SupplierScreens.ViewNotTransportingAgreement;
import Presentation.CLIPresentation.Screens.SupplierScreens.ViewRoutineAgreement;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class ViewSupplier extends Screen {

    private static final String greet = "View Supplier for Storekeeper and Store Manager";
    private final int supplierId;
    private static final String addAgreement = "Add New Agreement";


    public ViewSupplier() {
        // TODO: Supplier pass supplierId
        super(greet);
        supplierId = 1;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printMenu(resp, new String[]{"Show Supplier Info", "Show Contacts","Show Manufacturers", "Show Agreement", "Show all Orders", "Show all discount items", "Edit Card"});


        printForm(resp, new String[] {"agreementType", "agreementDays" }, new String[]{"Agreement Type", "Agreement Days"}, new String[]{addAgreement});

        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        if (isButtonPressed(req, addAgreement)){
            addAgreement(req, resp);
        }

        switch (getIndexOfButtonPressed(req)){
            case 0:
                showSupplierInfo(req, resp);
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
            // TODO: Suppliers pass supplierId
            redirect(resp, ShowAgreement.class);
            /*
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
            if(r != null){
                for(ServiceOrderItemObject orderItemObject : r){
                    float originalPrice = orderItemObject.getQuantity() * orderItemObject.getPricePerUnit();

                    // TODO: Supplier change this to normal print!
                    setError(orderItemObject.toStringDiscount(originalPrice) + "\n");
                    refresh(req, resp);
                }
            }
            else{
                System.out.println("No Order Items available");
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
            if(r != null){
                for(ServiceOrderObject orderObject : r){

                    // TODO: Supplier change this to normal print!
                    setError(orderObject.toString());
                    refresh(req, resp);
                }
            }
            else{
                System.out.println("No Orders available");
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
        //check what type of agreement is in the system and open a new Window accordingly!
        // TODO: 12/06/2022 Print all the Options for numbers! and for the days!
        try {
            int agreementType = Integer.parseInt(req.getParameter("agreementType"));
            String agreementDays = req.getParameter("agreementDays");
            if(agreementType == 1 || agreementType == 2 || agreementType == 3){
                if(controller.addAgreement(supplierId, agreementType, agreementDays)){

                    // TODO: Supplier change this to normal print!
                    setError("Now, let's add the items included in the agreement.");
                    refresh(req, resp);
                    redirect(resp, AddItemToAgreement.class);
                }
                else{
                    setError("A problem has occurred, please try again later");
                    refresh(req, resp);
                }
            }
            else {
                setError("Wrong number!, enter 1, 2 or 3");
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

                // TODO: Supplier change this to normal print!
                setError(result.toString());
                refresh(req, resp);
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
