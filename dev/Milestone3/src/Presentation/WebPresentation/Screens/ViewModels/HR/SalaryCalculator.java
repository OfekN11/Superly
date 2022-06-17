package Presentation.WebPresentation.Screens.ViewModels.HR;

import Globals.util.HumanInteraction;
import Presentation.WebPresentation.Screens.Models.HR.*;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SalaryCalculator extends Screen {

    private static final Set<Class<? extends Employee>> ALLOWED
            = new HashSet<>(Arrays.asList(Carrier.class, Cashier.class, HR_Manager.class, Logistics_Manager.class,
            Sorter.class, Storekeeper.class, Storekeeper.class, Transport_Manager.class));

    private static final String GREET = "Salary Calculator";

    public SalaryCalculator() {
        super(GREET, ALLOWED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAllowed(req, resp)) {
            redirect(resp, Login.class);
            return;
        }
        header(resp);
        greet(resp);
        String val;
        PrintWriter out = resp.getWriter();
        LocalDate start = LocalDate.now(), end = LocalDate.now();
        boolean startGiven = false, endGiven = false;
        if (startGiven = ((val = getParamVal(req, "start")) != null)) {
            start = LocalDate.parse(val);
        }
        if (endGiven = ((val = getParamVal(req, "end")) != null)) {
            end = LocalDate.parse(val);
        }
        out.println("<form method=\"post\">");
        out.println("<p>Enter start date to calculate from: </p>");
        out.println(String.format("<input type=\"date\" name=\"start\" value=\"%s\"><br><br>", start));
        out.println("<p>Enter end date to calculate to: </p>");
        out.println(String.format("<input type=\"date\" name=\"end\" value=\"%s\"><br><br>", end));
        out.println("<input type=\"submit\" name=\"calculate\" value=\"calculate\"><br><br>");
        out.println("</form>");

        if (startGiven && endGiven) {
            Employee emp = Login.getLoggedUser(req);
            int numOfShifts = 0;
            try {
                numOfShifts = controller.getEmployeeShiftsBetween(emp.id, start, end).size();
                out.println("<p>");
                out.println(String.format("Between %s and %s, %s has done %s shifts",
                        start.format(HumanInteraction.dateFormat), end.format(HumanInteraction.dateFormat), emp.name, numOfShifts));
                out.println("<br>");
                out.println(String.format("With a salary of %s per shift", emp.salary));
                out.println("<br>");
                out.println(String.format("Calculated salary between these dates is: %s ", (numOfShifts * emp.salary)));
                out.println("</p><br><br>");
            } catch (Exception e) {
                setError(e.getMessage());
            }
        }

        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handleHeader(req, resp))
            return;
        if (isButtonPressed(req, "calculate"))
            refresh(req, resp, new String[]{"start", "end"}, new String[]{req.getParameter("start"), req.getParameter("end")});
    }
}
