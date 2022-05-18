package Presentation;

import Domain.Service.Objects.Employee;
import Globals.Enums.Certifications;
import Globals.Enums.ShiftTypes;
import Presentation.Screens.Screen;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class PresentationShiftBuilder {
    private final BackendController controller;
    private LocalDate date;
    private ShiftTypes type;
    private String managerId;
    private int carrierCount;
    private int cashierCount;
    private int storekeeperCount;
    private int sorterCount;
    private int hr_managerCount;
    private int logistics_managerCount;
    private int transportManagerCount;
    protected final static Scanner scanner = new Scanner(System.in);


    public PresentationShiftBuilder() {
        controller = new BackendController();
        date = null;
        type = null;
        managerId = null;
        this.carrierCount = 0;
        this.cashierCount = 0;
        this.storekeeperCount = 0;
        this.sorterCount = 0;
        this.hr_managerCount = 0;
        this.logistics_managerCount = 0;
        this.transportManagerCount = 0;
    }

    private void printExitMassage() {
        System.out.println("Enter details to create shift (enter -1 at any point to stop the process)");

    }

    public void setDate() {
        //Date
        printExitMassage();
        boolean success = false;
        while (!success) {
            System.out.println("\nEnter shift's date");
            date = Screen.buildDate();
            if (date == null)
                return;
            System.out.println("Entered date: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
            success = areYouSure();
        }
        System.out.println("Chosen starting date: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
    }

    public void setShiftType() {
        //ShiftType
        printExitMassage();
        boolean success = false;
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
    }

    public void setCarrierCount() {
        //Carrier Count
        printExitMassage();
        boolean success = false;
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
    }

    public void setCashierCount() {
        //Cashier Count
        printExitMassage();

        boolean success = false;
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
    }

    public void setStorekeeperCount() {
        //Storekeeper Count
        printExitMassage();

        boolean success = false;
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
    }

    public void setSorterCount() {
        //Sorter Count
        printExitMassage();

        boolean success = false;
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
    }

    public void setHr_managerCount() {
        //HR Manager Count
        printExitMassage();

        boolean success = false;
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
    }


    public void setLogistics_managerCount() {
        //Logistics Manager Count
        printExitMassage();

        boolean success = false;
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
    }

    public void setTransportManagerCount() {
        //Transport Manager Count
        printExitMassage();

        boolean success = false;
        while (!success) {
            System.out.println("\nHow many transports managers do you need for this shift?");
            try {
                transportManagerCount = Integer.parseInt(scanner.nextLine());
                if (transportManagerCount == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else {
                    System.out.println("Entered count: " + transportManagerCount);
                    success = areYouSure();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a non-negative integer");
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println("Chosen transports manager count: " + transportManagerCount);
    }

    public void setShiftManager() {
        //Shift Manager
        printExitMassage();
        List<Employee> managers = null;
        try {
            managers = new ArrayList<>(controller.getAllEmployees()).stream().filter(employee -> employee.certifications.contains(Certifications.ShiftManagement)).collect(Collectors.toList());
            Set<String> cantWorkEmployees = controller.getConstraint(date, type).employeeIDs;
            managers = managers.stream().filter(employee -> !cantWorkEmployees.contains(employee.id)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (managers.size() == 0) {
            System.out.println("No employee who is certified to manage shifts has filled a possibility to work at this shift.");
            System.out.println("Cannot assign a shift manager. Operation Cancelled");
            return;
        }
        Employee manager = null;
        boolean success = false;
        while (!success) {
            try {
                System.out.println("Choose manager for this shift");
                for (int i = 0; i < managers.size(); i++)
                    System.out.println((i + 1) + " -- " + managers.get(i).name + ": " + controller.getEmployeeWorkDetailsForCurrentMonth(managers.get(i).id));

                int ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (ordinal < 1 || ordinal > managers.size())
                    System.out.println("Please enter an integer between 1 and " + managers.size());
                else {
                    manager = managers.get(ordinal - 1);
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
        managerId = manager.id;
        System.out.println("Chosen shift manager: " + manager.name);
    }

    public void buildObject() throws RuntimeException{
        try {
            if (type == null || date == null|| managerId == null){
                throw new RuntimeException(" you should set the date type and manager before ");
            }
            controller.createShift(date, type, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount, logistics_managerCount,transportManagerCount );
            System.out.println("Shift added successfully! Remember to assign employees");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again");
            throw new RuntimeException(" you should set the date type and manager before ");
        }
    }

    private static boolean areYouSure(){
        System.out.println("Are you sure?");
        return yesOrNo();
    }

    private static boolean yesOrNo(){
        int ans = 0;
        while (ans != 1 && ans != 2){
            System.out.println("1 -- yes\n2 -- no");
            try {
                ans = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException ex){
                System.out.println("Please enter an integer value (1 or 2)");
            }
            catch (Exception ex){
                System.out.println("An unexpected error happened. Please try again");
            }
        }
        return ans == 1;
    }
}
