package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.util.Result;
import Globals.Pair;
import Presentation.WebPresentation.Screens.Models.HR.*;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InventoryManagement extends Screen {
    private static final String greet = "Inventory Management";

    private static final String moveButton = "Move items";
    private static final String returnButton = "Return items";
    private static final String buyButton = "Buy items";
    private static final String reportDefectiveButton = "Report defective items";
    private static final String arrivedButton = "Arrived items";

    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>();

    public InventoryManagement() {
        super(greet, ALLOWED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        header(resp);
        greet(resp);
        printForm(resp, new String[]{"store ID", "product ID", "amount"}, new String[]{"Store ID", "Product ID", "Amount"}, new String[]{moveButton});
        printForm(resp, new String[]{"store ID", "product ID", "amount", "date bought"}, new String[]{"Store ID", "Product ID", "Amount", "Date bought"}, new String[]{returnButton});
        printForm(resp, new String[]{"store ID", "product ID", "amount"}, new String[]{"Store ID", "Product ID", "Amount"}, new String[]{buyButton});
        printForm(resp, new String[]{"store ID", "product ID", "amount", "description", "store or warehouse", "damaged or expired"}, new String[]{"Store ID", "Product ID", "Amount", "Description", "Store or warehouse", "damaged or expired"}, new String[]{reportDefectiveButton});
        printForm(resp, new String[]{}, new String[]{}, new String[]{arrivedButton}); //WHAT FIELDS?
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handleHeader(req, resp))
            return;
        if (isButtonPressed(req, moveButton)){
            if (!isAllowed(req, resp, new HashSet<>(Arrays.asList(Logistics_Manager.class, Storekeeper.class, Sorter.class)))) {
                setError("You have no permission to move items from warehouse to store");
                refresh(req, resp);
                return;
            }
            try {
                int storeID = Integer.parseInt(req.getParameter("store ID"));
                int productID = Integer.parseInt(req.getParameter("product ID"));
                int amount = Integer.parseInt(req.getParameter("amount"));
                if(controller.moveItems(storeID, productID, amount).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format(amount + " items of product " + productID + " were moved from store " + storeID + " to the warehouse")));
                    refresh(req, resp);
                }
                else{
                    setError("Items weren't moved");
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
        else if (isButtonPressed(req, returnButton)){
            if (!isAllowed(req, resp, new HashSet<>(Arrays.asList(Cashier.class, Sorter.class)))) {
                setError("You have no permission to return items to the store");
                refresh(req, resp);
                return;
            }
            try {
                int storeID = Integer.parseInt(req.getParameter("store ID"));
                int productID = Integer.parseInt(req.getParameter("product ID"));
                int amount = Integer.parseInt(req.getParameter("amount"));
                LocalDate dateBought = LocalDate.parse(req.getParameter("date bought"));
                Result<Double> refund = controller.returnItems(storeID, productID, amount, dateBought);
                if(refund.isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format(amount + " items of product " + productID + " were returned to store " + storeID + " with " + refund.getValue() + " refund to the customer")));
                    refresh(req, resp);
                }
                else{
                    setError("Items weren't returned");
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
        else if (isButtonPressed(req, buyButton)){
            if (!isAllowed(req, resp, new HashSet<>(Arrays.asList(Cashier.class)))) {
                setError("You have no permission to make a purchase");
                refresh(req, resp);
                return;
            }
            try {
                int storeID = Integer.parseInt(req.getParameter("store ID"));
                int productID = Integer.parseInt(req.getParameter("product ID"));
                int amount = Integer.parseInt(req.getParameter("amount"));
                Result<Pair<Double, String>> priceAndMessage = controller.buyItems(storeID, productID, amount);
                if(priceAndMessage.isOk()) {
                    double price = priceAndMessage.getValue().getLeft();
                    String message = priceAndMessage.getValue().getRight();
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format(amount + " items of product " + productID + " were bought in store " + storeID + " at a price of " + price)));
                    if (message!=null)
                        setError(message);
                    refresh(req, resp);
                }
                else{
                    setError("Items weren't bought");
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
        else if (isButtonPressed(req, reportDefectiveButton)){
            if (!isAllowed(req, resp, new HashSet<>(Arrays.asList(Logistics_Manager.class, Storekeeper.class, Sorter.class, Cashier.class)))) {
                setError("You have no permission to report defective items in the store or in the warehouse");
                refresh(req, resp);
                return;
            }
            try {
                int storeID = Integer.parseInt(req.getParameter("store ID"));
                int productID = Integer.parseInt(req.getParameter("product ID"));
                int amount = Integer.parseInt(req.getParameter("amount"));
                String description = req.getParameter("description");
                String location = req.getParameter("store or warehouse");
                String damagedOrExpired = req.getParameter("damaged or expired");
                int employeeID = Integer.parseInt(Login.getLoggedUser(req).id);
                if(damagedOrExpired=="damaged" ? controller.reportDamaged(storeID, productID, amount, employeeID, description, location=="warehouse").isOk() : controller.reportExpired(storeID, productID, amount, employeeID, description, location=="warehouse").isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format(amount + " items of product " + productID + " were found " + damagedOrExpired + " in " + location + " " + storeID + " by employee " + employeeID + ". Description: " + description)));
                    refresh(req, resp);
                }
                else{
                    setError("Report failed");
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
        else if (isButtonPressed(req, arrivedButton)){
            if (!isAllowed(req, resp, new HashSet<>(Arrays.asList(Logistics_Manager.class, Storekeeper.class)))) {
                setError("You have no permission to accept order to the warehouse");
                refresh(req, resp);
                return;
            }
            try {
                if(controller.orderArrived(1, new HashMap<>()).isOk()) {
                    PrintWriter out = resp.getWriter();
                    out.println(String.format("<p style=\"color:green\">%s</p><br><br>", String.format("SOME SUCCESS MESSAGE")));
                    refresh(req, resp);
                }
                else{
                    setError("Order weren't arrived");
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
}
