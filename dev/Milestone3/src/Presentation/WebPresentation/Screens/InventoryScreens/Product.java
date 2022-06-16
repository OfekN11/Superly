package Presentation.WebPresentation.Screens.InventoryScreens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Product extends Screen {
    private static final String greet = "Product";

    /*private final String[] menuOption;
    public final int id;
    public final String name;
    public final int categoryID;
    public final double originalPrice;
    public final double currentPrice;
    public final String weight;
    public final String manufacturer;*/


    public Product() { super(greet); }

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
