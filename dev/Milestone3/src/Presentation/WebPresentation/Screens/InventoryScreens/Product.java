package Presentation.WebPresentation.Screens.InventoryScreens;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Product extends Screen {

    private final String[] menuOption;

    public final int id;
    public final String name;
    public final int categoryID;
    public final double originalPrice;
    public final double currentPrice;
    public final String weight;
    public final String manufacturer;

    protected Product(Domain.Service.Objects.InventoryObjects.Product sProduct, String[] menuOptions) {
        super("Product: " + sProduct.getName());
        this.menuOption = menuOptions;
        id = sProduct.getId();
        name = sProduct.getName();
        categoryID = sProduct.getCategoryID();
        originalPrice = sProduct.getOriginalPrice();
        currentPrice = sProduct.getCurrentPrice();
        weight = sProduct.getWeight();
        manufacturer = sProduct.getManufacturer();
    }
}
