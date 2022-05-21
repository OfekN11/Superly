package Presentation;

import Presentation.Screens.MainMenu;
import Presentation.Screens.ShiftsMenu;

public class Main {
    public static void main(String[] args) {
        //new Thread(new MainMenu(new BackendController())).start();

        ShiftsMenu shiftsMenu = new ShiftsMenu(new BackendController());
        shiftsMenu.run();
    }
}
