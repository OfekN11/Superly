package PresentationLayer.Screens;

import PresentationLayer.BackendController;

public class MainMenu extends Screen{

    private static final String[] menuOptions = {
            "Manage Employees", //1
            "Manage Shifts",    //2
            "Exit"              //3
    };

    public MainMenu(BackendController controller) {
        super(controller, menuOptions, "Main Menu");
    }

    public MainMenu(Screen caller) {
        super(caller, menuOptions, "Main Menu");
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Main Menu!");
        switch (runMenu()) {
            case 1:
                new Thread(new Catalogs(this, new String[]{})).start();
                break;
            case 2:
                new Thread(new Reports(this, new String[]{})).start();
                break;
            case 3:
                endRun();
        }
    }

}
