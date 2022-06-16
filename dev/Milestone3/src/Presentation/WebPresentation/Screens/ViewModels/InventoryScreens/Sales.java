package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.Objects.InventoryObjects.Category;
import Domain.Service.Objects.InventoryObjects.Product;
import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sales extends Screen{

    private static final String greet = "Sales";
    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>(0);

    private static final String viewButton = "View Sale";
    private static final String addButton = "Add Sale";
    private static final String deleteButton = "Delete Sale";

    public Sales() {
        super(greet,ALLOWED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        header(resp);
        greet(resp);
        printForm(resp, new String[] {"ID"}, new String[]{"Sale ID"}, new String[]{viewButton});
        printForm(resp, new String[] {"ID"}, new String[]{"Sale ID"}, new String[]{deleteButton});
        //categories, products, percent, start, end
        printForm(resp, new String[] {"categories", "products", "percent", "start", "end"},
                new String[]{"Categories(1,2,..7)", "Products (1,2..7)", "Percent", "Start Date", "End Date"}, new String[]{addButton});
        printSales(resp);
        printProducts(resp);
        printCategories(resp);
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);

        /*if (isButtonPressed(req, "Add Order")){
            addOrder(req, resp);
        }
        else if(isButtonPressed(req, "Remove Order")){
            removeOrder(req, resp);
        }
        else if(isButtonPressed(req, "Edit Order")){
            //get the order id...
            editOrder(req, resp);

        }
        else if(isButtonPressed(req, "View Order")){
            printOrder(req, resp);
        }*/

    }

    private void printSales(HttpServletResponse resp) {
        try {
            List<Domain.Service.Objects.InventoryObjects.Sale> sales = controller.getRemovableSales().getValue();
            PrintWriter out = resp.getWriter();
            sales.sort(Comparator.comparingInt(Domain.Service.Objects.InventoryObjects.Sale::getSaleID));
            for (Domain.Service.Objects.InventoryObjects.Sale s: sales) {
//                out.println(String.format("%s: %s%, %s-%s, %s, &s <br>"), s.getSaleID(), s.getPercent(), s.getStartDate(), s.getEndDate(), s.getCategories(), s.getProducts());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printProducts(HttpServletResponse resp) {
        try {
            List<Domain.Service.Objects.InventoryObjects.Product> products = controller.getProducts().getValue();
            PrintWriter out = resp.getWriter();
            products.sort(Comparator.comparingInt(Product::getId));
            for (Product p: products) {
                out.println(p.getName() + ": " + p.getId() + "<br>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void printCategories(HttpServletResponse resp) {
        try {
            List<Domain.Service.Objects.InventoryObjects.Category> categories = controller.getCategories().getValue();
            PrintWriter out = resp.getWriter();
            categories.sort(Comparator.comparingInt(Category::getID));
            for (Domain.Service.Objects.InventoryObjects.Category c: categories) {
                out.println(c.getName() + ": " + c.getID() + "<br>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}