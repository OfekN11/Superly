package Presentation.Screens;

import Presentation.BackendController;

import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class Screen implements Runnable{
    protected final static Scanner scanner = new Scanner(System.in);
    protected final BackendController controller;
    private final String[] menuOptions;
    private final Screen caller;


    public Screen(BackendController controller, String[] menuOptions){
        this.controller = controller;
        this.menuOptions = menuOptions;
        caller = null;
    }

    public Screen(Screen caller, String[] menuOptions){
        this.controller = caller.controller;
        this.menuOptions = menuOptions;
        this.caller = caller;
    }

    protected void printMenu(){
        for (int i = 0 ; i < menuOptions.length; i++)
            System.out.println((i + 1) + " -- " + menuOptions[i]);
    }

    protected int runMenu(){
        System.out.println("What would you like to do?");
        int option = 0;
        while (option <= 0 || option > menuOptions.length){
            printMenu();
            try {
                option = scanner.nextInt();
                if (option <= 0 || option >menuOptions.length)
                    System.out.println("Please enter an integer value between 1 and " + menuOptions.length);
            }
            catch (InputMismatchException ex){
                System.out.println("Please enter an integer value between 1 and " + menuOptions.length);
                scanner.next();
            }
            catch (Exception ex){
                System.out.println("An unexpected error happened. Please try again");
                scanner.next();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return option;
    }

    protected void endRun(){
        if (caller != null)
            new Thread(caller).start();
        else
            System.out.println("Thanks for using software by Group_L!\nBye bye!");
    }

    protected static boolean areYouSure(){
        System.out.println("Are you sure?");
        return yesOrNo();
    }

    protected static boolean yesOrNo(){
        int ans = 0;
        while (ans != 1 && ans != 2){
            System.out.println("1 -- yes\n2 -- no");
            try {
                ans = scanner.nextInt();
            }
            catch (InputMismatchException ex){
                System.out.println("Please enter an integer value (1 or 2)");
                scanner.next();
            }
            catch (Exception ex){
                System.out.println("An unexpected error happened. Please try again");
                scanner.next();
            }
        }
        return ans == 1;
    }

    public static Date buildDate() {
        Date date = new Date();
        boolean success = false;
        while (!success) {
            System.out.println("Enter day");
            try {
                int day = scanner.nextInt();
                if (day == -1) {
                    System.out.println("Operation Canceled");
                    return null;
                } else if (day < 1 || day > 31) {
                    System.out.println("Enter valid day");
                } else {
                    date.setDate(day);
                    success = true;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Please enter an integer between 1 and 31");
                scanner.next();
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
                scanner.next();
            }
        }
        success = false;
        while (!success) {
            System.out.println("Enter month");
            try {
                int month = scanner.nextInt();
                if (month == -1) {
                    System.out.println("Operation Canceled");
                    return null;
                } else if (month < 1 || month > 12) {
                    System.out.println("Enter valid month");
                } else {
                    date.setMonth(month - 1);
                    success = true;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Please enter an integer between 1 and 12");
                scanner.next();
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
                scanner.next();
            }
        }
        success = false;
        while (!success) {
            System.out.println("Enter year");
            try {
                int year = scanner.nextInt();
                if (year == -1) {
                    System.out.println("Operation Canceled");
                    return null;
                } else if (year < 1900 || year > 2030) {
                    System.out.println("Enter valid year");
                } else {
                    date.setYear(year - 1900);
                    success = true;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Please enter an integer between 1900 and 2030");
                scanner.next();
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
                scanner.next();
            }
        }
        return date;
    }
}