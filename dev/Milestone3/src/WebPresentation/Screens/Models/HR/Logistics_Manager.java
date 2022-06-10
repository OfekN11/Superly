package WebPresentation.Screens.Models.HR;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Logistics_Manager extends Employee{

    private static String greeting = "Welcome Logistics Manager ";

    private static String[] menuOptions = {};

    protected Logistics_Manager(Domain.Service.Objects.Employee.Logistics_Manager sLogMan) {
        super(sLogMan, greeting, menuOptions);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
