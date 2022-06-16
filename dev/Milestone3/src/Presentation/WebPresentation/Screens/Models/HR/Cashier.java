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

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int index = getIndexOfButtonPressed(req);
        if (index < BASE_OPTIONS_COUNT) {
            super.doPost(req, resp);
            return;
        }
        index -= BASE_OPTIONS_COUNT;
        switch (index) {
        }
    }
}
