package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Models.HR.Logistics_Manager;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;
import Domain.Service.Objects.InventoryObjects.Product;
import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Models.HR.Logistics_Manager;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

    //ADD BUTTONS:
    //viewReports

public class ManageInventory extends Screen {

    private static final String greet = "Inventory and Reports";

    private static final String moveButton = "Move items";
    private static final String returnButton = "Return items";
    private static final String buyButton = "Buy items";
    private static final String arrivedButton = "Arrived items";
    private static final String reportDefectiveButton = "Report defective items";


    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>();

    public ManageInventory() { super(greet, ALLOWED); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        header(resp);
        greet(resp);
        //printForm(resp, new String[] {"ID"}, new String[]{"Product ID"}, new String[]{viewButton});
        //printForm(resp, new String[] {"ID"}, new String[]{"Product ID"}, new String[]{deleteButton});
        //printForm(resp, new String[] {"product name", "category ID", "weight", "price", "manufacturer"}, new String[]{"Product name", "Category ID", "Weight", "Price", "Manufacturer"}, new String[]{addButton});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handleHeader(req, resp))
            return;
        /*if (isButtonPressed(req, deleteButton)){
            if (!isAllowed(req, resp, new HashSet<>(Arrays.asList(Logistics_Manager.class)))) {
                setError("You have no permission to delete product");
                refresh(req, resp);
                return;
            }
            try {
                int productID = Integer.parseInt(req.getParameter("ID"));
                if(controller.deleteProduct(productID).getValue()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Deleted product %d", productID)));
                    refresh(req, resp);
                }
                else{
                    setError("Product ID " + productID + " doesn't exist!");
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
        else if(isButtonPressed(req, addButton)){
            if (!isAllowed(req, resp, new HashSet<>(Arrays.asList(Logistics_Manager.class)))) {
                setError("You have no permission to add product");
                refresh(req, resp);
                return;
            }
            try {
                String productName = req.getParameter("product name");
                int categoryID = Integer.parseInt(req.getParameter("category ID"));
                int weight = Integer.parseInt(req.getParameter("weight"));
                int price = Integer.parseInt(req.getParameter("price"));
                String manufacturer = req.getParameter("manufacturer");

                if(controller.newProduct(productName, categoryID, weight, price, manufacturer).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Added new product %d", productName)));
                    refresh(req, resp);
                }
                else{
                    setError("Product wasn't added");
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
        else if(isButtonPressed(req, viewButton)){
            if (!isAllowed(req, resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Product.ALLOWED)) {
                setError("You have no permission to view product");
                refresh(req, resp);
                return;
            }
            try {
                String productIDstr = req.getParameter("ID");
                int productID = Integer.parseInt(productIDstr);
                Result<Domain.Service.Objects.InventoryObjects.Product> product = controller.getProduct(productID);
                if(product.isOk() && product.getValue().getId()==productID)
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Product.class, new String[]{"ProductID"}, new String[]{productIDstr});
                else
                {
                    setError("Product ID " + productID + " doesn't exist");
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
        }*/
    }

    private void printProducts(HttpServletResponse resp) {
        try {
            Result<List<Domain.Service.Objects.InventoryObjects.Product>> products = controller.getProducts();
            PrintWriter out = resp.getWriter();
            products.getValue().sort(Comparator.comparingInt(Product::getId));
            for (Domain.Service.Objects.InventoryObjects.Product p: products.getValue()) {
                out.println(p.getName() + ": " + p.getId() + "<br>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}