package Presentation.WebPresentation.Screens.InventoryScreens;

import Domain.Service.Objects.InventoryObjects.Product;
import Domain.Service.Objects.SupplierObjects.ServiceOrderObject;
import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Products extends Screen{

    private static final String greet = "Products";

    private static final String viewButton = "View product";
    private static final String addButton = "Add product";
    private static final String removeButton = "Remove product";

    public Products() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printForm(resp, new String[] {"ID"}, new String[]{"Product ID"}, new String[]{viewButton});
        printForm(resp, new String[] {"ID"}, new String[]{"Product ID"}, new String[]{removeButton});
        printForm(resp, new String[] {"product name", "category ID", "weight", "price", "manufacturer"},
                new String[]{"Product name", "Category ID", "Weight", "Price", "Manufacturer"}, new String[]{addButton});
        printProducts(resp);
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, removeButton)){
            try {
                int productID = Integer.parseInt(req.getParameter("ID"));
                if(controller.deleteProduct(productID).getValue()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Removed product %d", productID)));

                    //setError(String.format("Removed supplier %d", supplierId));
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
            try {
                String productName = req.getParameter("product name");
                int categoryID = Integer.parseInt(req.getParameter("category ID"));
                int weight = Integer.parseInt(req.getParameter("weight"));
                int price = Integer.parseInt(req.getParameter("price"));
                String manufacturer = req.getParameter("manufacturer");

                if(controller.newProduct(productName, categoryID, weight, price, manufacturer).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("Added new product %d", productName)));

                    //setError(String.format("Removed supplier %d", supplierId));
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
            try {
                redirect(resp, Presentation.WebPresentation.Screens.InventoryScreens.Product.class);
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
            Result<List<Product>> products = controller.getProducts();
            PrintWriter out = resp.getWriter();
            out.println("number of products exist: " + products.getValue().size());
            for (Product p: products.getValue()) {
                out.println(p.getName() + ": " + p.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
