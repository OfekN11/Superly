package Presentation;

import Presentation.Screens.MainMenu;

public class Main {
    public static void main(String[] args) {
        BackendController controller = new BackendController();
        //TODO: controller.loadData();
        new Thread(new MainMenu(controller)).start();
    }
}
