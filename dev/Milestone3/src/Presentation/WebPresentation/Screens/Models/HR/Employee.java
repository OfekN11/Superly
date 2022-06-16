package Presentation.WebPresentation.Screens.Models.HR;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class Employee extends Screen {

    private static final String[] BASE_OPTIONS = {
            "View Upcoming shifts",
            "Manage Constraints",
            "Calculate Salary",
            "Print Employment Conditions"
    };
    private final String[] menuOption;

    public final String id;
    public final String name;
    public final String bankDetails;
    public final int salary;

    protected Employee(Domain.Service.Objects.Employee.Employee sEmployee, String greeting, String[] extraMenuOptions) {
        super(greeting + sEmployee.name); //greeting is of structure "Welcome <type> "
        this.menuOption = Stream.concat(Arrays.stream(BASE_OPTIONS), Arrays.stream(extraMenuOptions)).toArray(String[]::new);
        id = sEmployee.id;
        name = sEmployee.name;
        bankDetails = sEmployee.bankDetails;
        salary = sEmployee.salary;
    }

    //for admin purposes
    protected Employee(String greeting, String[] menuOption) {
        super(greeting);
        this.menuOption = menuOption;
        this.id = null;
        this.name = null;
        this.bankDetails = null;
        this.salary = 0;
    }

    @Override
    public void greet(HttpServletResponse resp) throws IOException {
        super.greet(resp);
    }

    /***
     * prints a form of submit buttons.
     * buttons names are the index of their value in menuOptions
     * @param resp the response to print to
     * @throws IOException
     */
    public void printMenu(HttpServletResponse resp) throws IOException {
        printMenu(resp, menuOption);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
