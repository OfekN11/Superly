package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.util.Result;
import Globals.Pair;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CashRegister extends Screen  {

    private static final String greet = "Cash Register";

    private static final String returnButton = "Return items";
    private static final String buyButton = "Buy items";

    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>();

    public CashRegister() { super(greet, ALLOWED); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        header(resp);
        greet(resp);
        String price = getParamVal(req, "Price");
        if (price!=null) {
            resp.getWriter().println(price);
        }
        printForm(resp, new String[] {"product", "amount"}, new String[]{"Product ID", "Amount"}, new String[]{buyButton});
        String returnPrice = getParamVal(req, "returnPrice");
        if (returnPrice!=null) {
            resp.getWriter().println(returnPrice);
        }
        printForm(resp, new String[] {"product", "amount", "date"}, new String[]{"Product ID", "Amount to Return", "Date Bought: DD/MM/YYYY"}, new String[]{returnButton});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handleHeader(req, resp))
            return;
        if (isButtonPressed(req,returnButton)) {
            returnItems(req,resp);
        }
        else if (isButtonPressed(req, buyButton)) {
            buyItems(req, resp);
        }
    }

    private LocalDate getDate(String dateInput) {
        String[] date = dateInput.split("/");
        return LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[1]), Integer.parseInt(date[0]));
    }

    private void buyItems(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       Result<Pair<Double, String>> r = controller.buyItems(1,Integer.parseInt(getParamVal(req,"product")), Integer.parseInt(getParamVal(req, "amount")));
       if (r.isOk()) {
           refresh(req, resp,new String[] {"Price"}, new String[] {String.format("Total Price is: %s", r.getValue().getLeft())});
       }
       else {
           setError(r.getError());
           refresh(req, resp);
       }
    }

    private void returnItems(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Result r = controller.returnItems(1,Integer.parseInt(getParamVal(req,"product")), Integer.parseInt(getParamVal(req, "amount")), getDate(getParamVal(req, "date")));
        if (r.isOk()) {
            refresh(req, resp,new String[] {"returnPrice"}, new String[] {String.format("Total Price is: %s", r.getValue())});
        }
        else {
            setError(r.getError());
            refresh(req, resp);
        }
    }
}
