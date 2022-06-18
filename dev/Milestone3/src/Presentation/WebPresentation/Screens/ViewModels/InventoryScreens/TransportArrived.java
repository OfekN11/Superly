package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Globals.Pair;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TransportArrived extends Screen {

    private static final String greet = "Transport Arrived";

    private static final String addReport = "Add Info";
    private static final String doneButton = "Done";

    //Map<OrderId<ProductId , ( (missingAmount,defectiveAmount), description)>>
    private static Map<Integer, Map<Integer, Pair<Pair<Integer, Integer>, String>>> info = new HashMap<>();
    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>();
    private Integer transport;
    public TransportArrived() { super(greet, ALLOWED); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!isAllowed(req, resp)) {
            redirect(resp, Login.class);
        }
        if (transport==null) {
            transport = Integer.parseInt(getParamVal(req,"Transport Arrived"));
        }
        header(resp);
        greet(resp);
        printForm(resp, new String[] {"order", "product", "missing", "defective", "description"}, new String[]{"Order ID", "Product ID", "Amount Missing", "Amount Defective", "Description"}, new String[]{addReport});
        printMenu(resp, new String[] {doneButton});
        printInfo(resp);
        handleError(resp);
    }

    private void printInfo(HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        for (Integer order : info.keySet()) {
            Map<Integer,Pair<Pair<Integer, Integer>, String>> orderInfo = info.get(order);
            for (Integer product : orderInfo.keySet()) {
                Pair<Pair<Integer, Integer>, String> pInfo = orderInfo.get(product);
                int missing = pInfo.getLeft().getLeft();
                int defective = pInfo.getLeft().getRight();
                String description = pInfo.getRight();
                out.println(String.format("Order: %s, Product: %s, Missing: %s, Defective: %s, Description: %s <br>", order, product, missing, defective, description));
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        handleHeader(req, resp);
        if (isButtonPressed(req,addReport)) {
            addReport(req, resp);
        }
        else if (isButtonPressed(req, doneButton)) {
            transportArrived(resp);
        }
    }

    private void transportArrived(HttpServletResponse resp) throws IOException {
        controller.transportArrived(transport,
                info);
        redirect(resp, ManageInventory.class);
    }

    private void addReport(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int order = Integer.parseInt(getParamVal(req, "order"));
        int product = Integer.parseInt(getParamVal(req, "product"));
        int missing = Integer.parseInt(getParamVal(req, "missing"));
        int defective = Integer.parseInt(getParamVal(req, "defective"));
        String description = getParamVal(req, "description");
        if (!info.containsKey(order))
            info.put(order, new HashMap<>());
        Map orderInfo = info.get(order);
        orderInfo.put(product,new Pair<>(new Pair(missing, defective), description));
        refresh(req, resp);
    }
}
