package Presentation.Screens.Transport;

import Presentation.Objects.Transport.TransportOrder;
import Presentation.Screens.Screen;

import java.util.Set;

public class UpdateTransportMenu extends Screen {

    private static final String[] menuOptions = {
            "Place truck",                  //1
            "Place carrier",                //2
            "Start transport",              //3
            "View orders",                  //4
            "View orders in same areas",    //5
            "Add order",                    //6
            "Exit"                          //7
    };
    public UpdateTransportMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Update Transport Menu!");
        int transportID = getID("Transport");
        System.out.println("\nWelcome to the Update Transport Menu!");
        int option = 0;
        while (option != 7) {
            option = runMenu();
            try {
                switch (option) {
                    case 1:
                        placeTruck(transportID);
                        break;
                    case 2:
                        placeCarrier(transportID);
                        break;
                    case 3:
                        startTransport(transportID);
                        break;
                    case 4:
                        viewOrders();
                        break;
                    case 5:
                        //TODO: viewOrdersInTheSameArea();
                        System.out.println("Features: Will implement View order in the same area");
                        break;
                    case 6:
                        addOrder(transportID);
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

    private void placeTruck(int transportID) throws Exception {
        int truckLN = getTruckNumber();
        controller.placeTruck(transportID, truckLN);
    }

    private void placeCarrier(int transportID) throws Exception {
        int carrierID = getID("Carrier");
        controller.placeCarrier(transportID, carrierID);
    }

    private int getID(String type) {
        System.out.println("Enter " + type + " ID:");
        int ID = scanner.nextInt();
        while(ID < 0){
            System.out.println("Please insert legal ID:");
            ID = scanner.nextInt();
        }
        return ID;
    }
    private int getTruckNumber() {
        System.out.println("Enter truck license number:");
        int licenseNumber = scanner.nextInt();
        while(licenseNumber > 0){
            System.out.println("Please insert license number:");
            licenseNumber = scanner.nextInt();
        }
        return licenseNumber;
    }

    private void startTransport(int transportID) throws Exception {
        controller.startTransport(transportID);
    }

    private void viewOrders() throws Exception {
        Set<TransportOrder> orders = controller.getPendingOrders();
        System.out.println("Pending Orders List: ");
        for(TransportOrder to: orders )
        {
            to.display();
        }
    }


    private void addOrder(int transportID) throws Exception {
        int orderID = getID("Order");
        controller.addOrderToTransport(transportID, orderID);
    }


}
