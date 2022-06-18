package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Globals.Defect;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

//ADD BUTTONS:
    //viewReports

public class ManageInventory extends Screen {

    private static final String greet = "Inventory and Reports";

    private static final String transportButton = "Transport Arrived";
    private static final String moveButton = "Move items";
    private static final String reportDamagedButton = "Report Damaged Items";
    private static final String reportExpiredButton = "Report Expired Items";


    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>();

    public ManageInventory() { super(greet, ALLOWED); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        header(resp);
        greet(resp);
        printMenu(resp, new String[] {"Cash Register", "Stock Reports"});
        printForm(resp, new String[] {"transport"}, new String[]{"Transport ID"}, new String[]{transportButton});
        printForm(resp, new String[] {"product", "amount"}, new String[]{"Product ID", "Amount Moved to Store"}, new String[]{moveButton});
        printForm(resp, new String[] {"product", "amount", "description", "loc"}, new String[]{"Product ID", "Amount Expired", "Description", "Location: warehouse/store"}, new String[]{reportExpiredButton});
        printForm(resp, new String[] {"product", "amount", "description", "loc"}, new String[]{"Product ID", "Amount Damaged", "Description", "Location: warehouse/store"}, new String[]{reportDamagedButton});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handleHeader(req, resp))
            return;
        switch (getIndexOfButtonPressed(req)) {
            case 0:
                redirect(resp,CashRegister.class);
                break;
            case 1:
                redirect(resp,StockReport.class);
                break;
        }
        if (isButtonPressed(req, moveButton)) {
            moveItems(req, resp);
        }
        else if (isButtonPressed(req, transportButton)) {
            redirect(resp, TransportArrived.class, new String[] {transportButton}, new String[] {getParamVal(req,"transport")});
        }
        else if (isButtonPressed(req, reportExpiredButton)) {
            reportDefective(req, resp, Defect.Expired);
        }
        else if (isButtonPressed(req, reportExpiredButton)) {
            reportDefective(req, resp, Defect.Damaged);
        }
    }

    private void reportDefective(HttpServletRequest req, HttpServletResponse resp, Defect defect) throws IOException {
        try {
            boolean loc = getLoc(getParamVal(req,"loc"));
            switch (defect) {
                case Damaged:
                    controller.reportDamaged(1,
                            Integer.parseInt(getParamVal(req, "product")),
                            Integer.parseInt(getParamVal(req, "amount")),
                            Integer.parseInt(Login.getLoggedUser(req).id),
                            getParamVal(req, "description"),
                            loc);
                    break;
                case Expired:
                    controller.reportExpired(1,
                            Integer.parseInt(getParamVal(req, "product")),
                            Integer.parseInt(getParamVal(req, "amount")),
                            3,
                            getParamVal(req, "description"),
                            loc);
                    break;
            }
        }
        catch (Exception e) {
            setError(e.getMessage());
        }
        finally {
            refresh(req, resp);
        }
    }

    private void moveItems(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        controller.moveItems(1, Integer.parseInt(getParamVal(req, "product")),
                Integer.parseInt(getParamVal(req, "amount")));
        refresh(req, resp);
    }

    private boolean getLoc(String loc) throws Exception {
        if (loc.equals("warehouse"))
            return true;
        if (loc.equals("store"))
            return false;
        throw new Exception("Location Must be either 'warehouse' or 'store'");
    }


}