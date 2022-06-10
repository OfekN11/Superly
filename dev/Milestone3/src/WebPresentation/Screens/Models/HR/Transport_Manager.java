package WebPresentation.Screens.Models.HR;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Transport_Manager extends Employee{

    private static String greeting = "Welcome Transport Manager ";

    private static String[] menuOptions = {};

    protected Transport_Manager(Domain.Service.Objects.Employee.Transport_Manager sTraMan) {
        super(sTraMan, greeting, menuOptions);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
