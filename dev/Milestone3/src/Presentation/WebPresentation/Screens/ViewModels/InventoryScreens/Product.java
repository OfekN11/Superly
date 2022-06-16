package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.Objects.Employee.Sorter;
import Domain.Service.Objects.Employee.Storekeeper;
import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

    private static final String setPriceButton = "Set price";
    private static final String setMinButton = "Set min";
    private static final String setTargetButton = "Set target";
    private static final String setNameButton = "Set name";

    public static final Class<? extends Employee>[] ALLOWED = new Class[]{};

    private int productID;

    public Product() {
        super(greet, ALLOWED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }

        header(resp);
        greet(resp);
        String s = getParamVal(req, "ProductID");
        if (s!=null) {
            productID = Integer.parseInt(s);
            Result<Domain.Service.Objects.InventoryObjects.Product> product = controller.getProduct(productID);
            if (product.isOk() && product.getValue() != null) {
                printForm(resp, new String[]{"new price"}, new String[]{"New price"}, new String[]{setPriceButton});
                printForm(resp, new String[]{"storeID", "new min"}, new String[]{"StoreID", "New min"}, new String[]{setMinButton});
                printForm(resp, new String[]{"storeID", "new target"}, new String[]{"StoreID", "New target"}, new String[]{setTargetButton});
                printForm(resp, new String[]{"new name"}, new String[]{"New name"}, new String[]{setNameButton});
                printProduct(resp, product.getValue());
            }
        }
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, setPriceButton)){
            try {
                double newPrice = Double.parseDouble(req.getParameter("new price"));
                if(controller.editProductPrice(productID, newPrice).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Changed price of product %d to %f", productID, newPrice)));
                    refresh(req, resp);
                }
                else{
                    setError("Price hasn't been changed");
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
        else if (isButtonPressed(req, setMinButton)){
            try {
                int storeID = Integer.parseInt(req.getParameter("storeID"));
                int newMin = Integer.parseInt(req.getParameter("new min"));
                if(controller.changeProductMin(storeID, productID, newMin).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Changed minimum amount of product %d in store %d to %d", productID, storeID, newMin)));
                    refresh(req, resp);
                }
                else{
                    setError("Minimum amount hasn't been changed");
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
        else if (isButtonPressed(req, setTargetButton)){
            try {
                int storeID = Integer.parseInt(req.getParameter("storeID"));
                int newTarget = Integer.parseInt(req.getParameter("new target"));
                if(controller.changeProductTarget(storeID, productID, newTarget).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Changed target amount of product %d in store %d to %d", productID, storeID, newTarget)));
                    refresh(req, resp);
                }
                else{
                    setError("Target amount hasn't been changed");
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
        else if (isButtonPressed(req, setNameButton)){
            try {
                String newName = req.getParameter("new name");
                if(controller.editProductName(productID, newName).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Changed name of product %d to %s", productID, newName)));
                    refresh(req, resp);
                }
                else{
                    setError("Target amount hasn't been changed");
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
    }
    private void printProduct(HttpServletResponse resp, Domain.Service.Objects.InventoryObjects.Product p) {
        try {
            Result<Domain.Service.Objects.InventoryObjects.Product> product = controller.getProduct(productID);
            PrintWriter out = resp.getWriter();
            out.println("Product ID: " + p.getId() + "<br>");
            out.println("Product name: " + p.getName() + "<br>");
            out.println("Category: " + controller.getCategory(p.getCategoryID()).getValue().getName() + "<br>");
            out.println("Original price: " + p.getOriginalPrice() + "<br>");
            out.println("Current price: " + p.getCurrentPrice() + "<br>");
            out.println("Weight: " + p.getWeight() + "<br>");
            out.println("Manufacturer: " + p.getManufacturer() + "<br>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
