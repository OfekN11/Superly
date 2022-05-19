package Presentation.Screens.Transport;

import Domain.Business.Objects.Transport;
import Globals.Enums.ShiftTypes;
import Globals.Pair;
import Presentation.Screens.Screen;
import Presentation.Screens.Shift;

import java.time.LocalDate;
import java.util.List;

public class TransportsMenu extends Screen {

    private static final String[] menuOptions = {
            "Add transport order",          //1
            "Create new transport",         //2
            "Update transport",             //3
            "Get pending transport",        //4
            "Get in progress transport",    //5
            "Get complete transport",       //6
            "Exit"                          //7
    };
    public TransportsMenu(Screen caller) {
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
                        createNewTransport();
                        break;
                    case 3:
                        updateTransport();
                        break;
                    case 4:
                        getPendingTransports();
                        break;
                    case 5:
                        getInProgressTransports();
                        break;
                    case 6:
                        getCompleteTransports();
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

    private LocalDate getShiftDate()
    {
        System.out.println("\nEnter shift's date");
        LocalDate date = null;
        boolean Illegal = true;
        while (Illegal) {
            date = buildDate();
            Illegal = date == null;
            if (Illegal) {
                System.out.println("Please enter legal date!");
            }
        }
        return date;
    }

    private ShiftTypes getShiftType(){
        ShiftTypes type = null;
        while (type == null) {
            System.out.println("\nEnter shift's type");
            for (int i = 0; i < ShiftTypes.values().length; i++)
                System.out.println((i + 1) + " -- " + ShiftTypes.values()[i]);
            try {
                int ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal < 1 || ordinal > ShiftTypes.values().length)
                    System.out.println("Please enter an integer between 1 and " + ShiftTypes.values().length);
                else {
                    type = ShiftTypes.values()[ordinal - 1];
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and " + ShiftTypes.values().length);
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        return type;
    }

    private void createNewTransport() throws Exception {
        System.out.println("Create transport:");
        LocalDate date = getShiftDate();
        ShiftTypes shiftType = getShiftType();
        controller.createNewTransport(new Pair<LocalDate, ShiftTypes>(date, shiftType));
    }

    private void updateTransport() {

    }

    private void getPendingTransports() throws Exception {
        controller.getPendingTransports();
    }

    private void getInProgressTransports() throws Exception {
        controller.getInProgressTransports();
    }

    private void getCompleteTransports() throws Exception {
        controller.getCompleteTransports();
    }
    private void displayTransportList(String status, List<Transport> transports)
    {
        System.out.println(status + "Transports: ");
        for (Transport transport: transports)
        {
            //transport.display()
            System.out.println(transport.toString());
        }
    }
}
