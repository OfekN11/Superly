package WebPresentation.Screens.Models.HR;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Storekeeper extends Employee{

    private static String greeting = "Welcome Storekeeper ";

    private static String[] menuOptions = {};

    protected Storekeeper(Domain.Service.Objects.Employee.Storekeeper sStorekeeper) {
        super(sStorekeeper, greeting, menuOptions);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
