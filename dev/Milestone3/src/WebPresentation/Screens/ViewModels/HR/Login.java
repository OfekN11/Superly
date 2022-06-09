package WebPresentation.Screens.ViewModels.HR;

import WebPresentation.Screens.Models.HR.Employee;
import WebPresentation.Screens.Models.HR.EmployeeFactory;
import WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends Screen {

    private static final String greet = "Login";
    private static Employee loggedUser = null;

    private final EmployeeFactory factory = new EmployeeFactory();

    public Login() {
        super(greet);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isLoggedIn()) {
            redirect(resp, EmployeeServlet.class);
            return;
        }
        greet(resp);
        printForm(resp, new String[]{"ID"}, new String[]{"Employee ID"}, new String[]{"Sign in!"});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (isButtonPressed(req, "Sign in!")){
            String id = req.getParameter("ID");
            try {
                loggedUser = factory.createEmployee(controller.getEmployee(id));
                redirect(resp, EmployeeServlet.class);
            } catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }
        }
    }

    public boolean isLoggedIn(){
        return loggedUser != null;
    }

    public static Employee getLoggedUser(){
        return loggedUser;
    }

    public static void logout(){
        loggedUser = null;
    }
}
