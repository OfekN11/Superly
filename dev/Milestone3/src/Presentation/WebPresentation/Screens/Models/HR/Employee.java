package Presentation.WebPresentation.Screens.Models.HR;

import Presentation.WebPresentation.Screens.Screen;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class Employee extends Screen {

    protected static final String[] BASE_OPTIONS = {
            "View Upcoming shifts",         //0
            "Manage Constraints",           //1
            "Calculate Salary",             //2
            "Print Employment Conditions"   //3
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

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        header(resp);
        greet(resp);
        printMenu(resp, menuOption);
        handleError(resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (getIndexOfButtonPressed(req)){
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
