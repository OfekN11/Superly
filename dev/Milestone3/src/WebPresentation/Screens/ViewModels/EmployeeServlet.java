package WebPresentation.Screens.ViewModels;

import WebPresentation.Screens.Models.HR.*;
import WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmployeeServlet extends Screen {
    public EmployeeServlet() {
        super(null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Employee employee = Login.getLoggedUser();
        employee.greet(resp);
        employee.printMenu(resp);
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Login.getLoggedUser().doPost(req, resp);
    }
}
