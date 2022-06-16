package Presentation.WebPresentation.Screens.ViewModels.HR;

import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.Models.HR.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EmployeeServlet extends Screen {

    private static final Class<? extends Employee>[] ALLOWED = new Class<>[0];

    public EmployeeServlet() {
        super(null, ALLOWED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAllowed(req, resp)){
            redirect(resp, Login.class);
        }
        header(resp);
        Employee employee = Login.getLoggedUser(req);
        employee.greet(resp);
        employee.printMenu(resp);
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        Employee emp = Login.getLoggedUser(req);
        try {
            emp.doPost(req, resp);
        } catch (Exception e) {
            setError(e.getMessage());
            refresh(req, resp);
        }
    }
}
