package WebPresentation.Screens.Models.HR;

import WebPresentation.Screens.ViewModels.HR.Login;
import WebPresentation.WebMain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Sorter extends Employee{

    private static String greeting = "Welcome Sorter ";

    private static String[] menuOptions = {"logout"};

    protected Sorter(Domain.Service.Objects.Employee.Sorter sSorter) {
        super(sSorter, greeting, menuOptions);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Login.logout();
        resp.sendRedirect(WebMain.servletToPath.get(Login.class));
    }
}
