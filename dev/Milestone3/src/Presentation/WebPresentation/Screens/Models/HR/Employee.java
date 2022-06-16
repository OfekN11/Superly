package Presentation.WebPresentation.Screens.Models.HR;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Employee extends Screen {

    private final String[] menuOption;

    public final String id;
    public final String name;
    public final String bankDetails;
    public final int salary;

    protected Employee(Domain.Service.Objects.Employee.Employee sEmployee, String greeting, String[] menuOptions) {
        super(greeting + sEmployee.name, null); //greeting is of structure "Welcome <type> "
        this.menuOption = menuOptions;
        id = sEmployee.id;
        name = sEmployee.name;
        bankDetails = sEmployee.bankDetails;
        salary = sEmployee.salary;
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
    public abstract void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
