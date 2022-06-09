package WebPresentation.Screens.Models.HR;

import WebPresentation.Screens.ViewModels.HR.Login;
import WebPresentation.WebMain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Cashier extends Employee{

    private static String greeting = "Welcome Cashier ";

    private static String[] menuOptions = {"logout"};

    protected Cashier(Domain.Service.Objects.Employee.Cashier sCashier) {
        super(sCashier, greeting, menuOptions);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Login.logout();
        resp.sendRedirect(WebMain.servletToPath.get(Login.class));
    }
}
