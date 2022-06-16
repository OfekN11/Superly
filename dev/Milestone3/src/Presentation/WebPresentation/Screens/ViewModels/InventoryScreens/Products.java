package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.Objects.InventoryObjects.Product;
import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Products extends Screen{

    private static final String greet = "Products";

    private static final String viewButton = "View product";
    private static final String addButton = "Add product";
    private static final String removeButton = "Remove product";

    private static final Map<String, Integer> viewedProductID = new HashMap<>();
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
                String productIDstr = req.getParameter("ID");
                int productID = Integer.parseInt(productIDstr);
                Result<Product> product = controller.getProduct(productID);
                if(product.isOk() && product.getValue().getId()==productID)
                {
                    String hash = hash(productIDstr);
                    viewedProductID.put(hash, productID);
                    Cookie c = new Cookie("viewed-product", hash);
                    c.setMaxAge((int)TimeUnit.MINUTES.toSeconds(2));
                    resp.addCookie(c);
                    redirect(resp, Presentation.WebPresentation.Screens.ViewModels.InventoryScreens.Product.class);
                }
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

    public static int getViewedProductID(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        for (Cookie c : cookies)
            if (c.getName().equals("viewed-product"))
                return viewedProductID.get(c.getValue());
        return -1;
    }

    private void printProducts(HttpServletResponse resp) {
        try {
            Result<List<Product>> products = controller.getProducts();
            PrintWriter out = resp.getWriter();
            List<Product> sortedProducts = sort(products.getValue());
            for (Product p: sortedProducts) {
                out.println(p.getName() + ": " + p.getId() + "<br>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Product> sort(List<Product> products) {
        List<Integer> ids = new ArrayList<>();
        for (Product p: products) {
            ids.add(p.getId());
        }
        Collections.sort(ids);
        List<Product> sortedList = new ArrayList<>();
        for (Integer id : ids) {
            Product p = findProduct(products, id);
            if (p!=null)
                sortedList.add(p);
        }
        return sortedList;
    }
    private Product findProduct(List<Product> products, int id) {
        for (Product p : products) {
            if (p.getId()==id)
                return p;
        }
        return null;
    }
    private static String hash(String toHash) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(
                toHash.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
