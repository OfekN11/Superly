package Presentation.Screens;

import Domain.Service.Objects.Employee;
import Domain.Service.Objects.Shift;
import Globals.Enums.Certifications;
import Globals.Enums.ShiftTypes;
import Globals.util.ShiftComparator;
import Presentation.PresentationShiftBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ShiftsMenu extends Screen {
    private static final String[] menuOptions = {
            "View existing shifts",     //1
            "Add shifts",               //2
            "Remove shifts",            //3
            "Manage existing shifts",   //4
            "Exit"                      //5
    };

    private static final ScreenShiftFactory factory = new ScreenShiftFactory();
    private static final PresentationShiftBuilder presentationShiftBuilder = new PresentationShiftBuilder();

    public ShiftsMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Shift Management Menu!");
        int option = 0;
        while (option != 4 && option != 5) {
            option = runMenu();
            try {
                switch (option) {
                    case 1:
                        viewShifts();
                        break;
                    case 2:
                        addShifts();
                        break;
                    case 3:
                        removeShifts();
                        break;
                    case 4:
                        manageShift();
                        break;
                    case 5:
                        endRun();
                        break;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }

    private void viewShifts() {
        System.out.println("Enter first and last dates to see shifts between the dates");
        System.out.println("Enter first date");
        LocalDate start = buildDate();
        if (start == null)
            return;
        System.out.println("Enter ending date");
        LocalDate end = buildDate();
        if (end == null)
            return;
        try {
            List<Shift> shifts = new ArrayList<>(controller.getShiftsBetween(start, end));
            shifts.sort(new ShiftComparator());
            for (Shift shift : shifts)
                System.out.println(shift);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addShifts() throws Exception {
        presentationShiftBuilder.setDate();
        presentationShiftBuilder.setShiftType();
        presentationShiftBuilder.setShiftManager();
        presentationShiftBuilder.setCarrierCount();
        presentationShiftBuilder.setCashierCount();
        presentationShiftBuilder.setStorekeeperCount();
        presentationShiftBuilder.setSorterCount();
        presentationShiftBuilder.setHr_managerCount();
        presentationShiftBuilder.setLogistics_managerCount();
        presentationShiftBuilder.setTransportManagerCount();
        try {
            presentationShiftBuilder.buildObject();
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
            addShifts();
        }
    }

    private void removeShifts() throws Exception {
        while (true) {
            System.out.println("Enter details of the shift you want to remove (enter -1 at any point to stop the process)");
            System.out.println("Be aware that this process is irreversible");

            //Date
            LocalDate date = null;
            boolean success = false;
            while (!success) {
                System.out.println("\nEnter shift's date");
                date = buildDate();
                if (date == null)
                    return;
                System.out.println("Entered date: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
                success = areYouSure();
            }
            System.out.println("Chosen starting date: " + new SimpleDateFormat("dd-MM-yyyy").format(date));

            //ShiftType
            ShiftTypes type = null;
            success = false;
            while (!success) {
                System.out.println("\nEnter shift's type");
                for (int i = 0; i < ShiftTypes.values().length; i++)
                    System.out.println((i + 1) + " -- " + ShiftTypes.values()[i]);
                try {
                    int ordinal = Integer.parseInt(scanner.nextLine());
                    if (ordinal == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else if (ordinal < 1 || ordinal > ShiftTypes.values().length)
                        System.out.println("Please enter an integer between 1 and " + ShiftTypes.values().length);
                    else {
                        type = ShiftTypes.values()[ordinal - 1];
                        System.out.println("Entered type: " + type);
                        success = areYouSure();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter an integer between 1 and " + ShiftTypes.values().length);
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                }
            }
            System.out.println("Chosen shift type: " + type);

            Shift shift = controller.getShift(date, type);
            System.out.println("We are about to delete");
            System.out.println(shift);
            if (areYouSure())
                controller.removeShift(date, type);
        }
    }

    private void manageShift() throws Exception {
        System.out.println("\nEnter details of the shift you want to manage");

        //Date
        System.out.println("\nEnter shift's date");
        LocalDate date = buildDate();
        if (date == null)
            return;

        //ShiftType
        ShiftTypes type = null;
        while (type == null) {
            System.out.println("\nEnter shift's type");
            for (int i = 0; i < ShiftTypes.values().length; i++)
                System.out.println((i + 1) + " -- " + ShiftTypes.values()[i]);
            try {
                int ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (ordinal < 1 || ordinal > ShiftTypes.values().length)
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

            new Thread(factory.createScreenShift(this, controller.getShift(date, type))).start();
    }
}
