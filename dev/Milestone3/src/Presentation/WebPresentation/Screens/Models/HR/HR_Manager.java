package Presentation.WebPresentation.Screens.Models.HR;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HR_Manager extends Employee{

    private static final String GREETING = "Welcome HR Manager ";

    private static final String[] EXTRA_OPTIONS = {};

    protected HR_Manager(Domain.Service.Objects.Employee.HR_Manager sHRMan) {
        super(sHRMan, GREETING, EXTRA_OPTIONS);
    }
}
