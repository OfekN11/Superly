package Presentation.WebPresentation.Screens.ViewModels.HR;

import Presentation.WebPresentation.Screens.Models.HR.*;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class EmploymentConds extends Screen {
    private static final Set<Class<? extends Employee>> ALLOWED
            = new HashSet<>(Arrays.asList(Carrier.class, Cashier.class, HR_Manager.class, Logistics_Manager.class,
            Sorter.class, Storekeeper.class, Storekeeper.class, Transport_Manager.class));

    private static final String GREET = "";

    public EmploymentConds() {
        super(GREET, ALLOWED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAllowed(req, resp)) {
            redirect(resp, Login.class);
            return;
        }
        try {
            Employee employee = Login.getLoggedUser(req);
            PrintWriter out = resp.getWriter();
            out.println(String.format("<h1>%s's Employment Conditions</h1><br><br>", employee.name));
            out.println("<p>");
            out.println(controller.getEmploymentConditionsOf(employee.id).replaceAll("\n", "<br>"));
            out.println("</p>");
        } catch (Exception e) {
            setError(e.getMessage());
        }
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
    }
}
