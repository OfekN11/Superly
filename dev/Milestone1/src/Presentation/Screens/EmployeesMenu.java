package Presentation.Screens;

import Domain.Service.Objects.Employee;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class EmployeesMenu extends Screen {
    private static class EmployeesViewer extends Screen {
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
                switch (option) {
                    case 1:
                        System.out.println("Printing all employees:\n");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        printEmployees(controller.getAllEmployees());
                    case 2:
                        System.out.println("Printing all cashiers:\n");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        printEmployees(controller.getAllCashiers());
                    case 3:
                        System.out.println("Printing all carriers:\n");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        printEmployees(controller.getAllCarriers());
                    case 4:
                        System.out.println("Printing all storekeepers:\n");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        printEmployees(controller.getAllStorekeepers());
                    case 5:
                        System.out.println("Printing all sorters:\n");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        printEmployees(controller.getAllSorters());
                    case 6:
                        System.out.println("Printing all HR managers:\n");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        printEmployees(controller.getAllHR_Managers());
                    case 7:
                        System.out.println("Printing all logistics managers:\n");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        printEmployees(controller.getAllLogistics_Managers());
                    case 8:
                        endRun();
                        break;
                }
            }
        }

        private void printEmployees(Set<Employee> employees) {
            for (Employee e : employees) {
                System.out.println(e.id + " - " + e.name);
            }
            System.out.println();
        }
    }

    private static final String[] menuOptions = {
            "View Employees",                                       //1
            "Add Employee",                                         //2
            "Edit Employee (this includes managing constraints)",   //3
            "Remove Employee",                                      //4
            "Exit"                                                  //5
    };

    public EmployeesMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Employee Management Menu!");
        int option = 0;
        while (option != 1 && option != 3 && option != 5) {
            option = runMenu();
            switch (option) {
                case 1 -> new Thread(new EmployeesViewer(this)).start();
                case 2 -> addEmployee();
                case 3 -> new Thread(new Employee());
                case 4 -> removeEmployee();
                case 5 -> endRun();
            }
        }
    }

    private void removeEmployee() {
        System.out.println("\nYou are choosing to remove an employee from the system. \nBe aware that this process is irreversible");
        boolean success = false;
        int id = 0;
        while (!success){
            System.out.println("Please enter ID of the employee you wish to remove (enter -1 to cancel this action)");
            try {
                id = scanner.nextInt();
                if (id != -1){
                   try {
                       Employee toBeRemoved = controller.getEmployee(id);
                       System.out.println("Employee " + toBeRemoved.name + ", ID: " + toBeRemoved.id + " is about to be removed");
                       if (areYouSure()) {
                           controller.removeEmployee(toBeRemoved.id);
                           success = true;
                           System.out.println(toBeRemoved.name + " has been successfully removed from the system\n");
                       }
                   }
                   catch (Exception e) {
                       System.out.println(e.getMessage());
                       System.out.println("Please try again");
                   }
                }
                else
                    success = true;
            }
            catch (InputMismatchException ex){
                System.out.println("Please enter a valid id");
                scanner.next();
            }
            catch (Exception ex){
                System.out.println("An unexpected error happened. Please try again");
                scanner.next();
            }
        }
    }

    private void addEmployee() {
        System.out.println("Lets add a new employee! (you can cancel this action at any point by entering -1");

        //ID
        Integer id = null;
        boolean success = false;
        while (!success){
            System.out.println("\nEnter new employee's ID");
            try {
                id = scanner.nextInt();
                if (id == -1) {
                    System.out.println("Operation Canceled");
                    return;
                }
                controller.checkUnusedEmployeeID();
                if (id < 0)
                    System.out.println("Please enter a non-negative integer");
                else {
                    System.out.println("Entered ID: " + id);
                    success = areYouSure();
                }
            }
            catch (InputMismatchException ex){
                System.out.println("Please enter a non-negative integer");
                scanner.next();
            }
            catch (Exception ex){
                System.out.println(ex.getMessage());
                System.out.println("Please try again");
                scanner.next();
            }
        }
        System.out.println("Chosen ID: " + id);

        //Name
        String name = null;
        success = false;
        while (!success){
            System.out.println("\nEnter new employee's name");
            try {
                name = scanner.nextLine();
                if (name.equals("-1")) {
                    System.out.println("Operation Canceled");
                    return;
                }
                else {
                    System.out.println("Entered name: " + name);
                        success = areYouSure();
                }
            }
            catch (Exception ex){
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
                scanner.next();
            }
        }
        System.out.println("Chosen name: " + name);

        //Bank Details
        String bankDetails = null;
        success = false;
        while (!success){
            System.out.println("\nEnter " + name +"'s bank details");
            try {
                bankDetails = scanner.nextLine();
                if (bankDetails.equals("-1")) {
                    System.out.println("Operation Canceled");
                    return;
                }
                else {
                    System.out.println("Entered bank details: " + bankDetails);
                        success = areYouSure();
                }
            }
            catch (Exception ex){
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
                scanner.next();
            }
        }
        System.out.println("Chosen bank details: " + bankDetails);

        //Job Title
        JobTitles jobTitle = null;
        success = false;
        while (!success){
            System.out.println("\nEnter " + name +"'s job");
            for (int i = 0; i < JobTitles.values().length; i++)
                System.out.println((i + 1) + " -- " + JobTitles.values()[i]);
            try {
                int ordinal = scanner.nextInt();
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
            }
            catch (InputMismatchException ex){
                System.out.println("Please enter an integer between 1 and " + JobTitles.values().length);
                scanner.next();
            }
            catch (Exception ex){
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
                scanner.next();
            }
        }
        System.out.println("Chosen job title: " + jobTitle);

        //Starting Date
        Date startingDate = new Date();
        success = false;
        while (!success){
            System.out.println("\nEnter " + name +"'s starting date");
            while (!success){
                System.out.println("Enter day");
                try {
                    int day = scanner.nextInt();
                    if (day == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    }
                    else if (day < 1 || day > 31) {
                        System.out.println("Enter valid day");
                    }
                    else {
                        startingDate.setDate(day);
                        success = true;
                    }
                }
                catch (InputMismatchException ex){
                    System.out.println("Please enter an integer between 1 and 31");
                    scanner.next();
                }
                catch (Exception ex){
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                    scanner.next();
                }
            }
            success = false;
            while (!success){
                System.out.println("Enter month");
                try {
                    int month = scanner.nextInt();
                    if (month == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    }
                    else if (month < 1 || month > 12) {
                        System.out.println("Enter valid month");
                    }
                    else {
                        startingDate.setMonth(month-1);
                        success = true;
                    }
                }
                catch (InputMismatchException ex){
                    System.out.println("Please enter an integer between 1 and 12");
                    scanner.next();
                }
                catch (Exception ex){
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                    scanner.next();
                }
            }
            success = false;
            while (!success){
                System.out.println("Enter year");
                try {
                    int year = scanner.nextInt();
                    if (year == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    }
                    else if (year < 1900 || year > 2030) {
                        System.out.println("Enter valid year");
                    }
                    else {
                        startingDate.setYear(year-1900);
                        success = true;
                    }
                }
                catch (InputMismatchException ex){
                    System.out.println("Please enter an integer between 1900 and 2030");
                    scanner.next();
                }
                catch (Exception ex){
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                    scanner.next();
                }
            }
            success = false;
            System.out.println("Entered date: " + new SimpleDateFormat("dd-MM-yyyy").format(startingDate));
            success = areYouSure();
        }
        System.out.println("Chosen starting date: " + new SimpleDateFormat("dd-MM-yyyy").format(startingDate));

        //salary
        Integer salary = null;
        success = false;
        while (!success){
            System.out.println("\nEnter " + name +"'s salary per shift");
            try {
                salary = scanner.nextInt();
                if (salary == -1) {
                    System.out.println("Operation Canceled");
                    return;
                }
                else if (salary < 0){
                    System.out.println("Enter a valid salary");
                }
                else {
                    System.out.println("Entered salary title: " + salary);
                    success = areYouSure();
                }
            }
            catch (InputMismatchException ex){
                System.out.println("Please enter an non-negative integer");
                scanner.next();
            }
            catch (Exception ex){
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
                scanner.next();
            }
        }
        System.out.println("Chosen salary: " + jobTitle);

        //certifications
        Set<Certifications> certifications = new HashSet<>();
        success = false;
        while (!success){
            System.out.println("\nEnter " + name +"'s certifications");
            int ordinal = -1;
            while (ordinal != 0) {
                System.out.println("0 -- stop adding certifications");
                for (int i = 0; i < Certifications.values().length; i++)
                    System.out.println((i + 1) + " -- " + Certifications.values()[i]);
                try {
                    ordinal = scanner.nextInt();
                    if (ordinal == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    } else if (ordinal < 0 || ordinal > Certifications.values().length) {
                        System.out.println("Please enter an integer between 0 and " + Certifications.values().length);
                    } else if (ordinal != 0){
                        certifications.add(Certifications.values()[ordinal-1]);
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Please enter an integer between 0 and " + Certifications.values().length);
                    scanner.next();
                } catch (Exception ex) {
                    System.out.println("Unexpected error occurred");
                    System.out.println("Please try again");
                    scanner.next();
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
        try {
            controller.addEmployee(id, name, bankDetails, salary, employmentConditions, startingDate, certifications);
            System.out.println(employmentConditions + "\nHas been successfully added");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(employmentConditions + "\nHasn't been added. Please try again");
        }
    }
}