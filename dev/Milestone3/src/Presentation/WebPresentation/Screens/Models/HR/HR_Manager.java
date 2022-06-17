package Presentation.WebPresentation.Screens.Models.HR;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HR_Manager extends Employee{

    private static final String GREETING = "Welcome HR Manager ";

    private static final String[] EXTRA_OPTIONS = {
            "View Employees",       //BASE + 0
            "Register Employee",    //BASE + 1
            "Manage Employee",      //BASE + 2
            "Remove Employee"       //BASE + 3
    };

    protected HR_Manager(Domain.Service.Objects.Employee.HR_Manager sHRMan) {
        super(sHRMan, GREETING, EXTRA_OPTIONS);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        int index = getIndexOfButtonPressed(req) - BASE_OPTIONS_COUNT;
        switch (index) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }
}
