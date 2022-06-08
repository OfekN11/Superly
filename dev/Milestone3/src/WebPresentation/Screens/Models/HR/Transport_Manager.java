package WebPresentation.Screens.Models.HR;

import WebPresentation.Screens.ViewModels.Login;
import WebPresentation.WebMain;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Transport_Manager extends Employee{

    private static String greeting = "Welcome Carrier ";

    private static String[] menuOptions = {"logout"};

    protected Transport_Manager(Domain.Service.Objects.Employee.Transport_Manager sTraMan) {
        super(sTraMan, greeting, menuOptions);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Login.logout();
        resp.sendRedirect(WebMain.servletToPath.get(Login.class));
    }
}
