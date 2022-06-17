package Presentation.WebPresentation.Screens.ViewModels.Transport.Transport;

import Domain.Service.Objects.Shift.Shift;
import Globals.Enums.ShiftTypes;
import Globals.Enums.TruckModel;
import Globals.Pair;
import Globals.util.ShiftComparator;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.Transport.Truck.TruckManagementMenu;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CreateTransport extends Screen {
    private static final String greet = "Create new transport:";
    private static final String NO_SHIFTS = "There are no shifts in the coming month!";
    //private static List<Shift> shifts;
    private static HashMap<String, Shift> shifts;
    public CreateTransport() {
        super(greet);
        getShiftInTheCloseMonth();
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printForm(resp, new String[]{"Create", "Cancel"});
        handleError(resp);
    }

    private static void printForm(HttpServletResponse resp, String[] buttons) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<h3>Choose shift to transport from the list:</h3>");
        out.println("<form method=\"post\">\n");
        out.println(String.format("<input list=\"Shifts\" name=\"shift\" id=\"shift\">"));
        out.println(String.format("<datalist id=\"Shifts\">"));
        if(shifts.isEmpty()){
            out.println(String.format("<option value=\"%s\">", NO_SHIFTS));
        }
        else{
            for(String shift: shifts.keySet()){
                out.println(String.format("<option value=\"%s\">", shift));
            }
        }
        out.println(String.format("</datalist><br><br>"));
        for (String button : buttons)
            out.println(String.format("<input type=\"submit\" name=\"%s\" value=\"%s\">", button, button));
        out.println("<br><br></form>");
    }
    private void getShiftInTheCloseMonth() {
        shifts = new LinkedHashMap();
        try {
            LocalDate today = LocalDate.now();
            LocalDate nextMonth = today.plusMonths(1);
            for(Shift shift: controller.getShiftsBetween(today, nextMonth).stream().sorted(new ShiftComparator()).collect(Collectors.toList())){
                shifts.put(shift.toString(), shift);
            }
        } catch (Exception e) {
        }
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, "Create")){
            try {
                Shift shift = getShift(req);
                controller.createNewTransport(new Pair<LocalDate, ShiftTypes>(shift.date, shift.getType()));
                //TODO: Add print of the iD of transport that create
                setError("e.getMessage()");
                refresh(req, resp);
            } catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }
        }
        else if(isButtonPressed(req, "Cancel")) {
            redirect(resp, TransportManagementMenu.class);
        }
    }
    private Shift getShift(HttpServletRequest req) throws Exception {
        String shift = getParamVal(req, "shift");
        if(shifts.isEmpty()){
            throw new Exception(NO_SHIFTS);
        } else if (shifts.containsKey(shift)) {
            return shifts.get(shift);
        }
        else{
            throw new Exception("Please choose shift from the list!");
        }
    }
}
