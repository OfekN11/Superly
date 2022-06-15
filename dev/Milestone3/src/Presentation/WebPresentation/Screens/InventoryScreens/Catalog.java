package Presentation.WebPresentation.Screens.InventoryScreens;

import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Presentation.WebPresentation.Screens.Screen;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Catalog extends Screen{

    private static final String greet = "Welcome to the products' catalog of Superly";

    public Catalog() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
    }
}
