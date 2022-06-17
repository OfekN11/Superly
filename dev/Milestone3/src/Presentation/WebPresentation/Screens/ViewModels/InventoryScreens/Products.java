package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

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

public class Products extends Screen{

    private static final String greet = "Products";

    private static final String viewButton = "View product";
    private static final String addButton = "Add product";
    private static final String deleteButton = "Delete product";

    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>();

    public Products() {
        super(greet, ALLOWED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        header(resp);
        greet(resp);
        printForm(resp, new String[] {"ID"}, new String[]{"Product ID"}, new String[]{viewButton});
        printForm(resp, new String[] {"ID"}, new String[]{"Product ID"}, new String[]{deleteButton});
        printForm(resp, new String[] {"product name", "category ID", "weight", "price", "manufacturer"},
                new String[]{"Product name", "Category ID", "Weight", "Price", "Manufacturer"}, new String[]{addButton});
        printProducts(resp);
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handleHeader(req, resp))
            return;
        if (isButtonPressed(req, deleteButton)){
            if (!isAllowed(req, resp, new HashSet<>(Arrays.asList(Logistics_Manager.class)))) {
                setError("You have no permission to delete product");
                refresh(req, resp);
                return;
            }
            try {
                int productID = Integer.parseInt(req.getParameter("ID"));
                if(controller.getProduct(productID).isError()) {
                    setError("Product ID " + productID + " doesn't exist");
                    refresh(req, resp);
                }
                else if (controller.deleteProduct(productID).getValue()){
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Deleted product %d", productID)));
                    refresh(req, resp);
                }
                else {
                    setError("Product ID " + productID + " is already has been used in other places in the system so it can't be deleted");
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
                double weight = Double.parseDouble(req.getParameter("weight"));
                double price = Double.parseDouble(req.getParameter("price"));
                String manufacturer = req.getParameter("manufacturer");

                if(controller.newProduct(productName, categoryID, weight, price, manufacturer).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Added new product %s", productName)));
                    refresh(req, resp);
                }
                else{
                    setError("Product wasn't added");
                    refresh(req, resp);
                }
            }catch (NumberFormatException e1){
                setError("Please enter a number in the following fields: category ID, weight, price");
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
                Result<Product> product = controller.getProduct(productID);
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
        }
    }

    private void printProducts(HttpServletResponse resp) {
        try {
           List<Product> products = controller.getProducts().getValue();
            PrintWriter out = resp.getWriter();
            products.sort(Comparator.comparingInt(Product::getId));
            for (Product p: products) {
                out.println(p.getName() + ": " + p.getId() + "<br>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
