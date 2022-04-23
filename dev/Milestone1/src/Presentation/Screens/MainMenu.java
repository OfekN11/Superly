package Presentation.Screens;

import Presentation.BackendController;

public class MainMenu extends Screen{

    private static final String[] menuOptions = {
            "Manage Employees", //1
            "Manage Shifts",    //2
            "Exit"              //3
    };

    public MainMenu(BackendController controller) {
        super(controller, menuOptions);
    }

    public MainMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Main Menu!");
        switch (runMenu()) {
            case 1 -> new Thread(new EmployeesMenu(this)).start();
            case 2 -> new Thread(new ShiftsMenu(this)).start();
            case 3 -> endRun();
        }
    }
}
