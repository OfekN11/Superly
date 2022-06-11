package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AddItemToAgreement extends Screen {


    private static final String greet = "Add Item";
    private final int supplierId;


    public AddItemToAgreement() {
        // TODO: Supplier pass ID
        super(greet);
        supplierId = 1;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);

        printForm(resp, new String[] {"productId", "idBySupplier", "name", "manufacturer", "pricePerUnit", "bulkPrices"}
        , new String[]{"Agreement Days", "Product ID", "ID by Supplier", "Name", "Manufacturer", "Price Per Unit", "Bulk Prices"}, new String[]{"Add Item To Agreement"});


        handleError(resp);
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, "Add Item To Agreement")) {

        }
    }
}
