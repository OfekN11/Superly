package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.Objects.InventoryObjects.Category;
import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class StockReport  extends Screen {

    private static final String greet = "Stock Reports";

    private static final String minButton = "Under Min";
    private static final String allButton = "All";

    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>();

    public StockReport() { super(greet, ALLOWED); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        header(resp);
        greet(resp);
        String underMin = getParamVal(req, "underMin");
        String all = getParamVal(req, "all");
        if (underMin!=null) {
            resp.getWriter().println("Showing stock reports of products under min value<br><br>");
            printMenu(resp, new String[]{minButton, allButton});
            printReports(req, resp, true);
        }
        else if (all!=null) {
            resp.getWriter().println("Showing all stock reports<br><br>");
            printMenu(resp, new String[]{minButton, allButton});
            printReports(req, resp, false);
        }
        else {
            printMenu(resp, new String[]{minButton, allButton});
        }
        handleError(resp);
    }

    private void printReports(HttpServletRequest req, HttpServletResponse resp, boolean onlyUnderMin) throws IOException {
        PrintWriter out = resp.getWriter();
        if (onlyUnderMin) {
            Result<List<Domain.Service.Objects.InventoryObjects.StockReport>> r = controller.getMinStockReport();
            if (r.isOk()) {
                List<Domain.Service.Objects.InventoryObjects.StockReport> reports = r.getValue();
                for (Domain.Service.Objects.InventoryObjects.StockReport s : reports) {
                    out.println(s);
                    out.println("<br>");
                }
            } else {
                setError(r.getError());
                refresh(req, resp);
            }
        }
        else {
            Result<List<Domain.Service.Objects.InventoryObjects.StockReport>> r = controller.storeStockReport(Arrays.asList(1), getCatIDs());
            if (r.isOk()) {
                List<Domain.Service.Objects.InventoryObjects.StockReport> reports = r.getValue();
                for (Domain.Service.Objects.InventoryObjects.StockReport s : reports) {
                    out.println(s);
                    out.println("<br>");
                }
            } else {
                setError(r.getError());
                refresh(req, resp);
            }
        }
    }

    protected List<Integer> getCatIDs() {
        List<Integer> cIDs = new ArrayList<>();
        List<Domain.Service.Objects.InventoryObjects.Category> c = controller.getCategories().getValue();
        for (Category cat: c) {
            cIDs.add(cat.getID());
        }
        return cIDs;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handleHeader(req, resp))
            return;
        switch (getIndexOfButtonPressed(req)) {
            case 0:
                refresh(req, resp, new String[] {"underMin"}, new String[] {"placeHolder"});
                break;
            case 1:
                refresh(req, resp, new String[] {"all"}, new String[] {"placeHolder"});
                break;
        }
    }
}
