package WebPresentation.Screens.ViewModels.HR;

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
        if (!Login.isLoggedIn(req)){
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
