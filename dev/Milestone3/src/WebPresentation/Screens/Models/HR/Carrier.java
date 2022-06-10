package WebPresentation.Screens.Models.HR;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Carrier extends Employee{

    private static String greeting = "Welcome Carrier ";

    private static String[] menuOptions = {};

    protected Carrier(Domain.Service.Objects.Employee.Carrier sCarrier) {
        super(sCarrier, greeting, menuOptions);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
