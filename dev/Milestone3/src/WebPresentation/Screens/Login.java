package WebPresentation.Screens;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Login extends Screen{

    private static final String greet = "Login";

    public Login() {
        super(greet, null);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        greet(out);
        out.println("<form method=\"post\">\n");
        out.println("<input type=\"text\" name=\"ID\" placeholder=\"Enter ID\"><br><br>");
        out.println("<input type=\"submit\" name=\"login_button\" value=\"Sign in\"><br><br>");
        out.println("</form>");
        if (error!=null)
            printError(out);
        error = null;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("login_button") != null){
            String id = req.getParameter("ID");
            error = String.format("Error: No employee with ID (%s)",id);
            refresh(req, resp);
            return;
        }
    }
}
