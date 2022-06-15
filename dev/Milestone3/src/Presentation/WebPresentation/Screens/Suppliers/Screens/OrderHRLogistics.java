package Presentation.WebPresentation.Screens.Suppliers.Screens;

import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OrderHRLogistics extends RemoveViewOrder {


    private static final String greet = "Remove Order for HR & Logistics";


    public OrderHRLogistics() {
        super(greet);
    }


    // TODO: Do we need it here?

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }



}
