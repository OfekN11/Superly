package Presentation.WebPresentation.Screens.ViewModels.Transport.Truck;

import Globals.Enums.TruckModel;
import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AddTruck extends Screen {
    private static final String greet = "Add Truck:";

    public AddTruck() {
        super(greet);
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printForm(resp, new String[]{"OK", "Cancel"});
        handleError(resp);
    }

    protected static void printForm(HttpServletResponse resp, String[] buttons) throws IOException {
        PrintWriter out = resp.getWriter();
        out.println("<form method=\"post\">\n");
        out.println(String.format("<input type=\"text\" name=\"%s\" placeholder=\"%s\"><br><br>", "LN", "License number"));
        out.println(String.format("<input list=\"Truck Models\" name=\"model\" id=\"model\">"));
        out.println(String.format("<datalist id=\"Truck Models\">"));
        out.println(String.format("<option value=\"%s\">", TruckModel.Van.toString()));
        out.println(String.format("<option value=\"%s\">", TruckModel.SemiTrailer.toString()));
        out.println(String.format("<option value=\"%s\">", TruckModel.FullTrailer.toString()));
        out.println(String.format("<option value=\"%s\">", TruckModel.DoubleTrailer.toString()));
        out.println(String.format("</datalist><br><br>"));
        out.println(String.format("<input type=\"text\" name=\"%s\" placeholder=\"%s\"><br><br>", "NetWeight", "Net weight"));
        out.println(String.format("<input type=\"text\" name=\"%s\" placeholder=\"%s\"><br><br>", "MaxCapacityWeight", "Max capacity weight"));
        for (String button : buttons)
            out.println(String.format("<input type=\"submit\" name=\"%s\" value=\"%s\">", button, button));
        out.println("<br><br></form>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req, "OK")){
            try {
                int ln = getLicenseNumber(req);
                TruckModel tm = getTransportModel(req);
                int netWeight = getNetWeight(req);
                int maxCapacityWeight = getMaxCapacityWeight(req, tm);
                controller.addTruck(ln, tm, netWeight, maxCapacityWeight);
                //TODO: Print Successful msg
                setError("e.getMessage()");
                refresh(req, resp);
            } catch (Exception e) {
                setError(e.getMessage());
                refresh(req, resp);
            }
        }
        else if(isButtonPressed(req, "Cancel")) {
            redirect(resp, TruckManagementMenu.class);
        }
    }
    private int getLicenseNumber(HttpServletRequest req) throws Exception {
        try {
            int ln = Integer.parseInt(req.getParameter("LN"));
            if(ln >= 0){
                return ln;
            }
        }
        catch (Exception e){
            throw new Exception("Enter a valid license number!");
        }
        throw new Exception("Enter a valid license number!");
    }
    private int getNetWeight(HttpServletRequest req) throws Exception {
        try {
            int netWeight = Integer.parseInt(req.getParameter("NetWeight"));
            if(netWeight >= 0){
                return netWeight;
            }
        }
        catch (Exception e){
            throw new Exception("Enter a valid weight of truck!");
        }
        throw new Exception("Enter a valid weight of truck!");
    }

    private boolean isValidWeight(TruckModel tm, int weight){
        boolean ans = false;
        switch (tm){
            case Van:
                ans = weight >= 200 & weight <= 1000;
                break;
            case SemiTrailer:
                ans = weight > 1000 & weight <= 5000;
                break;
            case DoubleTrailer:
                ans = weight > 5000 & weight <= 10000;
                break;
            case FullTrailer:
                ans = weight > 10000 & weight <= 20000;
                break;
        }
        return ans;
    }
    private int getMaxCapacityWeight(HttpServletRequest req, TruckModel tm) throws Exception {
        String error = "Enter valid mac capacity weight:\n" +
                "Van - 200 < weight <= 1000\n" +
                "SemiTrailer - 1000 < weight <= 5000\n" +
                "DoubleTrailer - 5000 < weight <= 10000\n" +
                "FullTrailer - 10000 < weight <= 20000\n";
        try {
            int maxCapacityWeight = Integer.parseInt(req.getParameter("MaxCapacityWeight"));
            if(isValidWeight(tm, maxCapacityWeight)){
                return maxCapacityWeight;
            }
        }
        catch (Exception e){
            throw new Exception(error);
        }
        throw new Exception(error);
    }

    private TruckModel getTransportModel(HttpServletRequest req) throws Exception {
        try {
            return TruckModel.valueOf(req.getParameter("model"));
        }
        catch (Exception e){
            throw new Exception("Please choose truck model from the lists!");
        }
    }
}
