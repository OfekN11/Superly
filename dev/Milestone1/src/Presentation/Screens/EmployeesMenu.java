package Presentation.Screens;

import Domain.Service.Objects.Employee;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.util.EmployeeComparator;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeesMenu extends Screen {
    public static class EmployeesViewer extends Screen {
        private static final String[] menuOptions = {
                "Print all Employees",          //1
                "Print all Cashiers",           //2
                "Print all Carriers",           //3
                "Print all Storekeepers",       //4
                "Print all Sorters",            //5
                "Print all HR Managers",        //6
                "Print all Logistics Managers", //7
                "Exit"                          //8
        };

        public EmployeesViewer(Screen caller) {
            super(caller, menuOptions);
        }

        @Override
        public void run() {
            System.out.println("\nHere you can view all the employees");
            int option = 0;
            while (option != 8) {
                option = runMenu();
                try {
                    switch (option) {
                        case 1:
                            System.out.println("\nPrinting all employees:");
                            printEmployees(controller.getAllEmployees());
                            break;
                        case 2:
                            System.out.println("\nPrinting all cashiers:");
                            printEmployees(controller.getAllCashiers());
                            break;
                        case 3:
                            System.out.println("\nPrinting all carriers:");
                            printEmployees(controller.getAllCarriers());
                            break;
                        case 4:
                            System.out.println("\nPrinting all storekeepers:");
                            printEmployees(controller.getAllStorekeepers());
                            break;
                        case 5:
                            System.out.println("\nPrinting all sorters:");
                            printEmployees(controller.getAllSorters());
                            break;
                        case 6:
                            System.out.println("\nPrinting all HR managers:");
                            printEmployees(controller.getAllHR_Managers());
                            break;
                        case 7:
                            System.out.println("\nPrinting all logistics managers:");
                            printEmployees(controller.getAllLogistics_Managers());
                            break;
                        case 8:
                            endRun();
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please try again");
                }
            }
        }

        public static void printEmployees(Set<Employee> employees) {
            for (Employee e : employees.stream().sorted(new EmployeeComparator()).collect(Collectors.toList())) {
                System.out.println("ID: " + e.id + " , Name :" + e.name);
            }
            System.out.println();
        }
    }

    private static final String[] menuOptions = {
            "View Employees",                                       //1
            "Add Employee",                                         //2
            "Manage Employee (this includes managing constraints)", //3
            "Remove Employee",                                      //4
            "Exit"                                                  //5
    };

    private static final ScreenEmployeeFactory factory = new ScreenEmployeeFactory();

    public EmployeesMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Employee Management Menu!");
        int option = 0;
        while (option != 1 && option != 3 && option != 5) {
            option = runMenu();
            try {
                switch (option) {
                    case 1:
                        new Thread(new EmployeesViewer(this)).start();
                        break;
                    case 2:
                        addEmployee();
                        break;
                    case 3:
                        manageEmployee();
                        break;
                    case 4:
                        removeEmployee();
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

    private void manageEmployee() throws Exception {
        System.out.println("\nEnter ID of the employee you would like to manage:");
        String id = null;
        while (id == null) {
            id = scanner.nextLine();
            if (id.equals("-1")) {
                System.out.println("Operation Canceled");
                return;
            }
            new Thread(factory.createScreenEmployee(this, controller.getEmployee(id))).start();
        }
    }

    private void removeEmployee() throws Exception {
        System.out.println("\nYou are choosing to remove an employee from the system. \nBe aware that this process is irreversible");
        boolean success = false;
        String id = null;
        while (!success) {
            System.out.println("Please enter ID of the employee you wish to remove (enter -1 to cancel this action)");
            id = scanner.nextLine();
            if (id.equals("-1")) {
                System.out.println("Operation Canceled");
                return;
            }
            Employee toBeRemoved = controller.getEmployee(id);
            System.out.println("Employee " + toBeRemoved.name + ", ID: " + toBeRemoved.id + " is about to be removed");
            if (areYouSure()) {
                controller.removeEmployee(toBeRemoved.id);
                success = true;
                System.out.println(toBeRemoved.name + " has been successfully removed from the system\n");
            }
        }
    }

    private void addEmployee() throws Exception {
        System.out.println("\nLets add a new employee! (you can cancel this action at any point by entering -1");

        //ID
        String id = null;
        boolean success = false;
        while (!success) {
            System.out.println("\nEnter new employee's ID");
            id = scanner.nextLine();
            if (id.equals("-1")) {
                System.out.println("Operation Canceled");
                return;
            }
            controller.checkUnusedEmployeeID(id);
            System.out.println("Entered ID: " + id);
            success = areYouSure();
        }
        System.out.println("Chosen ID: " + id);

        //Name
        String name = null;
        success = false;
        while (!success) {
            System.out.println("\nEnter new employee's name");
            try {
                name = scanner.nextLine();
                if (name.equals("-1")) {
                    System.out.println("Operation Canceled");
                    return;
                } else {
                    System.out.println("Entered name: " + name);
                    success = areYouSure();
                }
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println("Chosen name: " + name);

        //Bank Details
        String bankDetails = null;
        success = false;
        while (!success) {
            System.out.println("\nEnter " + name + "'s bank details");
            try {
                bankDetails = scanner.nextLine();
                if (bankDetails.equals("-1")) {
                    System.out.println("Operation Canceled");
                    return;
                } else {
                    System.out.println("Entered bank details: " + bankDetails);
                    success = areYouSure();
                }
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println("Chosen bank details: " + bankDetails);

        //Job Title
        JobTitles jobTitle = null;
        success = false;
        while (!success) {
            System.out.println("\nEnter " + name + "'s job");
            for (int i = 0; i < JobTitles.values().length; i++)
                System.out.println((i + 1) + " -- " + JobTitles.values()[i]);
            try {
                int ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (ordinal < 1 || ordinal > JobTitles.values().length)
                    System.out.println("Please enter an integer between 1 and " + JobTitles.values().length);
                else {
                    jobTitle = JobTitles.values()[ordinal - 1];
                    System.out.println("Entered job title: " + jobTitle);
                    success = areYouSure();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and " + JobTitles.values().length);
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println("Chosen job title: " + jobTitle);

        //Starting Date
        Date startingDate = null;
        success = false;
        while (!success) {
            System.out.println("\nEnter " + name + "'s starting date");
            startingDate = buildDate();
            if (startingDate == null)
                return;
            System.out.println("Entered date: " + new SimpleDateFormat("dd-MM-yyyy").format(startingDate));
            success = areYouSure();
        }
        System.out.println("Chosen starting date: " + new SimpleDateFormat("dd-MM-yyyy").format(startingDate));

        //salary
        Integer salary = null;
        success = false;
        while (!success) {
            System.out.println("\nEnter " + name + "'s salary per shift");
            try {
                salary = Integer.parseInt(scanner.nextLine());
                if (salary == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (salary < 0) {
                    System.out.println("Enter a valid salary");
                } else {
                    System.out.println("Entered salary title: " + salary);
                    success = areYouSure();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an non-negative integer");
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println("Chosen salary: " + salary);

        //certifications
        Set<Certifications> certifications = new HashSet<>();
        success = false;
        while (!success) {
            System.out.println("\nEnter " + name + "'s certifications");
            int ordinal = -1;
            while (ordinal != 0) {
                System.out.println("0 -- stop adding certifications");
                for (int i = 0; i < Certifications.values().length; i++)
                    System.out.println((i + 1) + " -- " + Certifications.values()[i]);
                try {
                    ordinal = Integer.parseInt(scanner.nextLine());
                    if (ordinal == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else if (ordinal < 0 || ordinal > Certifications.values().length) {
                        System.out.println("Please enter an integer between 0 and " + Certifications.values().length);
                    } else if (ordinal != 0) {
                        certifications.add(Certifications.values()[ordinal - 1]);
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Please enter an integer between 0 and " + Certifications.values().length);
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                }
            }
            System.out.print("Entered certifications: ");
            for (Certifications c : certifications)
                System.out.print(c + ", ");
            System.out.println();
            success = areYouSure();
        }
        System.out.print("Chosen certifications: ");
        for (Certifications c : certifications)
            System.out.print(c + ", ");

        String employmentConditions =
                "Name: " + name
                        + "\nID: " + id
                        + "\nJob title: " + jobTitle
                        + "\nStarting date: " + new SimpleDateFormat("dd-MM-yyyy").format(startingDate)
                        + "\nSalary per shift: " + salary;
        controller.addEmployee(jobTitle, id, name, bankDetails, salary, employmentConditions, startingDate, certifications);
        System.out.println(employmentConditions + "\nHas been successfully added");
    }
}