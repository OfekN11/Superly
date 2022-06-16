package Presentation.WebPresentation.Screens.Models.HR;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Logistics_Manager extends Employee{

    private static final String GREETING = "Welcome Logistics Manager ";

    private static final String[] EXTRA_OPTIONS = {};

    protected Logistics_Manager(Domain.Service.Objects.Employee.Logistics_Manager sLogMan) {
        super(sLogMan, GREETING, EXTRA_OPTIONS);
    }
}