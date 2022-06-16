package Presentation.WebPresentation.Screens.Models.HR;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Cashier extends Employee{

    private static String GREETING = "Welcome Cashier ";

    private static String[] EXTRA_OPTIONS = {};

    protected Cashier(Domain.Service.Objects.Employee.Cashier sCashier) {
        super(sCashier, GREETING, EXTRA_OPTIONS);
    }
}
