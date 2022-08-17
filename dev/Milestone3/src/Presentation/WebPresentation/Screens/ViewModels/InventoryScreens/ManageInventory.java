package Presentation.WebPresentation.Screens.ViewModels.InventoryScreens;

import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Models.HR.Logistics_Manager;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;
import Domain.Service.Objects.InventoryObjects.Product;
import Domain.Service.util.Result;
import Presentation.WebPresentation.Screens.Models.HR.Employee;
import Presentation.WebPresentation.Screens.Models.HR.Logistics_Manager;
import Presentation.WebPresentation.Screens.Screen;
import Presentation.WebPresentation.Screens.ViewModels.HR.Login;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

    //ADD BUTTONS:
    //viewReports

public class ManageInventory extends Screen {

    private static final String greet = "Inventory and Reports";

    private static final String moveButton = "Move items";
    private static final String returnButton = "Return items";
    private static final String buyButton = "Buy items";
    private static final String arrivedButton = "Arrived items";
    private static final String reportDefectiveButton = "Report defective items";


    public static final Set<Class<? extends Employee>> ALLOWED = new HashSet<>();

    public ManageInventory() { super(greet, ALLOWED); }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //if(!isAllowed(req, resp)) {
        //    redirect(resp, Login.class);
        //}
        header(resp);
        greet(resp);
        //printForm(resp, new String[] {"ID"}, new String[]{"Product ID"}, new String[]{viewButton});
        //printForm(resp, new String[] {"ID"}, new String[]{"Product ID"}, new String[]{deleteButton});
        //printForm(resp, new String[] {"product name", "category ID", "weight", "price", "manufacturer"}, new String[]{"Product name", "Category ID", "Weight", "Price", "Manufacturer"}, new String[]{addButton});
        handleError(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (handleHeader(req, resp))
            return;

    }

}