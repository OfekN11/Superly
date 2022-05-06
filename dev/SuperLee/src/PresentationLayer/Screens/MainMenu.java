package PresentationLayer.Screens;

import PresentationLayer.BackendController;
import PresentationLayer.Screens.InventorySreens.InventoryMenu;
import PresentationLayer.Screens.InventorySreens.Reports;
import PresentationLayer.Screens.SupplierScreens.SuppliersMenu;

public class MainMenu extends Screen {

    private static final String[] menuOptions = {
            "Manage Suppliers", //1
            "Manage Inventory",    //2
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
            case 1:
                new Thread(new SuppliersMenu(this)).start();
                break;
            case 2:
                new Thread(new InventoryMenu(this)).start();
                break;
            case 3:
                endRun();
        }
    }

}
