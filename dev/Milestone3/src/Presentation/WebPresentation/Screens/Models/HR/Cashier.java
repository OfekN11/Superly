package Presentation.WebPresentation.Screens.Models.HR;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Cashier extends Employee{

    private static String greeting = "Welcome Cashier ";

    private static String[] menuOptions = {};

    protected Cashier(Domain.Service.Objects.Employee.Cashier sCashier) {
        super(sCashier, greeting, menuOptions);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
