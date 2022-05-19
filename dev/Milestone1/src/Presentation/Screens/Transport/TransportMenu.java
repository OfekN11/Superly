package Presentation.Screens.Transport;

import Domain.Business.Objects.TransportOrder;
import Presentation.Screens.EmployeesMenu;
import Presentation.Screens.Screen;

public class TransportMenu extends Screen {

    private static final String[] menuOptions = {
            "Add transport order",          //1
            "Create new transport",         //2
            "Update transport",             //3
            "Get pending transport",        //4
            "Get in progress transport",    //5
            "Get complete transport",       //6
            "Exit"                          //7
    };
    public TransportMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Transport Management Menu!");
        int option = 0;
        while (option != 7 && option != 1) {
            option = runMenu();
            try {
                switch (option) {
                    case 1:
                        new Thread(new TransportOrderMenu(this)).start();
                        break;
                    case 2:
                        //TODO: implement createNewTransport();
                        break;
                    case 3:
                        //TODO: implement updateTransport();
                        break;
                    case 4:
                        //TODO: implement getPendingTransport();
                        break;
                    case 5:
                        //TODO: implement getInProgressTransport();
                        break;
                    case 6:
                        //TODO: implement getCompleteTransport();
                        break;
                    case 7:
                        endRun();
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }

    }

}
