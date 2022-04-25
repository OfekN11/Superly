package Presentation.Screens;

import Domain.Service.Objects.Constraint;
import Domain.Service.Objects.Employee;
import Domain.Service.Objects.Shift;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;
import Globals.util.ShiftComparator;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.*;
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
        Date start = buildDate();
        if (start == null)
            return;
        start.setSeconds(start.getSeconds() - 1);
        System.out.println("Enter ending date");
        Date end = buildDate();
        if (end == null)
            return;
        end.setSeconds(end.getSeconds() + 1);
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
        while (true) {
            System.out.println("Enter details to create shift (enter -1 at any point to stop the process)");

            //Date
            Date date = null;
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
                    System.out.println((i + 1) + " -- " + JobTitles.values()[i]);
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

            //Carrier Count
            int carrierCount = 0;
            success = false;
            while (!success) {
                System.out.println("\nHow many carriers do you need for this shift?");
                try {
                    carrierCount = Integer.parseInt(scanner.nextLine());
                    if (carrierCount == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else {
                        System.out.println("Entered count: " + carrierCount);
                        success = areYouSure();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a non-negative integer");
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                }
            }
            System.out.println("Chosen carrier count: " + carrierCount);

            //Cashier Count
            int cashierCount = 0;
            success = false;
            while (!success) {
                System.out.println("\nHow many cashiers do you need for this shift?");
                try {
                    cashierCount = Integer.parseInt(scanner.nextLine());
                    if (cashierCount == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else {
                        System.out.println("Entered count: " + cashierCount);
                        success = areYouSure();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a non-negative integer");
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                }
            }
            System.out.println("Chosen cashier count: " + cashierCount);

            //Storekeeper Count
            int storekeeperCount = 0;
            success = false;
            while (!success) {
                System.out.println("\nHow many storekeepers do you need for this shift?");
                try {
                    storekeeperCount = Integer.parseInt(scanner.nextLine());
                    if (storekeeperCount == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else {
                        System.out.println("Entered count: " + storekeeperCount);
                        success = areYouSure();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a non-negative integer");
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                }
            }
            System.out.println("Chosen storekeeper count: " + storekeeperCount);

            //Sorter Count
            int sorterCount = 0;
            success = false;
            while (!success) {
                System.out.println("\nHow many sorters do you need for this shift?");
                try {
                    sorterCount = Integer.parseInt(scanner.nextLine());
                    if (sorterCount == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else {
                        System.out.println("Entered count: " + sorterCount);
                        success = areYouSure();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a non-negative integer");
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                }
            }
            System.out.println("Chosen sorter count: " + sorterCount);

            //HR Manager Count
            int hr_managerCount = 0;
            success = false;
            while (!success) {
                System.out.println("\nHow many HR managers do you need for this shift?");
                try {
                    hr_managerCount = Integer.parseInt(scanner.nextLine());
                    if (hr_managerCount == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else {
                        System.out.println("Entered count: " + hr_managerCount);
                        success = areYouSure();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a non-negative integer");
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                }
            }
            System.out.println("Chosen HR manager count: " + hr_managerCount);

            //Logistics Manager Count
            int logistics_managerCount = 0;
            success = false;
            while (!success) {
                System.out.println("\nHow many logistics managers do you need for this shift?");
                try {
                    logistics_managerCount = Integer.parseInt(scanner.nextLine());
                    if (logistics_managerCount == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else {
                        System.out.println("Entered count: " + logistics_managerCount);
                        success = areYouSure();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter a non-negative integer");
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                }
            }
            System.out.println("Chosen logistics manager count: " + logistics_managerCount);

            //Shift Manager
            List<Employee> managers = controller.getEmployees(controller.getConstraint(date, type).employeeIDs)
                    .stream().filter((x) -> x.certifications.contains(Certifications.ShiftManagement)).collect(Collectors.toList());
            if (managers.size() == 0) {
                System.out.println("No employee who is certified to manage shifts has filled a possibility to work at this shift.");
                System.out.println("Cannot assign a shift manager. Operation Cancelled");
                return;
            }
            Employee manager = null;
            success = false;
            while (!success) {
                System.out.println("Choose manager for this shift");
                for (int i = 0; i < managers.size(); i++)
                    System.out.println((i + 1) + " -- " + managers.get(i).name + ": " + controller.getEmployeeWorkDetailsForCurrentMonth(managers.get(i).id));
                try {
                    int ordinal = Integer.parseInt(scanner.nextLine());
                    if (ordinal == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else if (ordinal < 1 || ordinal > managers.size())
                        System.out.println("Please enter an integer between 1 and " + managers.size());
                    else {
                        manager = managers.get(ordinal);
                        System.out.println("Entered manager: " + manager.name);
                        success = areYouSure();
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter an integer between 1 and " + managers.size());
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                }
            }
            System.out.println("Chosen shift manager: " + manager.name);

            try {
                controller.createShift(date, type, manager.id, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount, logistics_managerCount);
                System.out.println("Shift added successfully! Remember to assign employees");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }

    private void removeShifts() throws Exception {
        while (true) {
            System.out.println("Enter details of the shift you want to remove (enter -1 at any point to stop the process)");
            System.out.println("Be aware that this process is irreversible");

            //Date
            Date date = null;
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
                    System.out.println((i + 1) + " -- " + JobTitles.values()[i]);
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
                controller.removeShift(shift);
        }
    }

    private void manageShift() throws Exception {
        System.out.println("\nEnter details of the shift you want to manage");

        //Date
        System.out.println("\nEnter shift's date");
        Date date = buildDate();
        if (date == null)
            return;

        //ShiftType
        ShiftTypes type = null;
        while (type == null) {
            System.out.println("\nEnter shift's type");
            for (int i = 0; i < ShiftTypes.values().length; i++)
                System.out.println((i + 1) + " -- " + JobTitles.values()[i]);
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
