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

public class UpcomingShifts extends Screen {

    private static final Set<Class<? extends Employee>> ALLOWED
            = new HashSet<>(Arrays.asList(Carrier.class, Cashier.class, HR_Manager.class, Logistics_Manager.class,
                                            Sorter.class, Storekeeper.class, Storekeeper.class, Transport_Manager.class));

    public UpcomingShifts() {
        super("", ALLOWED);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAllowed(req, resp)){
            redirect(resp, Login.class);
        }
        Employee emp = Login.getLoggedUser(req);
        PrintWriter out = resp.getWriter();
        out.println(String.format("<h1>Upcoming shifts in the next 30 days for %s", emp.name));
    }
}
