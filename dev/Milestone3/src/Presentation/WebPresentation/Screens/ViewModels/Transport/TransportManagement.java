package Presentation.WebPresentation.Screens.ViewModels.Transport;

import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TransportManagement extends Screen {
    public TransportManagement() {
        super(null);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*if (!Login.isLoggedIn(req)){
            redirect(resp, Login.class);
        }*/
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
