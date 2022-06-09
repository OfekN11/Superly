package Presentation;
import Presentation.Screens.MainMenu;


public class Main {
    public static void main(String[] args){
        new Thread(new MainMenu(new BackendController())).start();
    }

}
