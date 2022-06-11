package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ViewSupplier extends Screen {

    private static final String greet = "View Supplier for Storekeeper and Store Manager";
    private final int supplierId;
    private static final String addAgreement = "submit Agreement";


    public ViewSupplier() {
        super(greet);
        supplierId = 1;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printMenu(resp, new String[]{"Show Supplier Info", "Show Contacts","Show Manufacturers", "Show Agreement", "Show all Orders", "Show all discount items", "Edit Card"});
        printForm(resp, new String[] {"agreementType", "agreementDays" }, new String[]{"Agreement Type", "Agreement Days"}, new String[]{addAgreement});

        //printInfo(); explain what is the BulkMap

        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, addAgreement)){
            /*
           //if agreement days and type work well => redirect(AddItemToAgreement.class);
            printForm(resp, new String[] {"productId", "idBySupplier", "name", "manufacturer", "pricePerUnit", "bulkPrices"}
        , new String[]{"Agreement Days", "Product ID", "ID by Supplier", "Name", "Manufacturer", "Price Per Unit", "Bulk Prices"}, new String[]{addAgreementItem});

             */
        /*    try {

            }catch (NumberFormatException e1){
                setError("Please enter a number!");
                refresh(req, resp);
            }
            catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }

         */
        }

        switch (getIndexOfButtonPressed(req)){
            case 0:
                showSupplierInfo();
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
                showAgreement();
                break;
            case 4:
                showAllOrders();
                break;
            case 5:
                showAllDiscountItems();
                break;
            case 6:
                // TODO: Suppliers pass supplierId
                redirect(resp, EditCard.class);
                break;
        }
    }


    private void showAllDiscountItems() {
    }

    private void showAllOrders() {
    }

    private void showAgreement() {
        //check what type of agreement is in the system and open a new Window accordingly!
    }


    private void showSupplierInfo() {
    }


}
