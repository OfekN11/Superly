package PresentationLayer.Screens;

import Domain.ServiceLayer.Result;
import PresentationLayer.BackendController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class Screen implements Runnable{
    protected final static Scanner scanner = new Scanner(System.in);
    protected final BackendController controller;
    private final String[] menuOptions;
    private final Screen caller;

    //For init with data
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
        System.out.println("\nWhat would you like to do?");
        int option = 0;
        while (option <= 0 || option > menuOptions.length){
            printMenu();
            try {
                option = Integer.parseInt(scanner.nextLine());
                if (option <= 0 || option >menuOptions.length)
                    System.out.println("Please enter an integer value between 1 and " + menuOptions.length);
            }
            catch (NumberFormatException ex){
                System.out.println("Please enter an integer value between 1 and " + menuOptions.length);
            }
            catch (Exception ex){
                System.out.println("An unexpected error happened. Please try again");
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

    public static Date buildDate() {
        Date date = new Date();
        boolean success = false;
        while (!success) {
            System.out.println("Enter day");
            try {
                int day = Integer.parseInt(scanner.nextLine());
                if (day == -1) {
                    System.out.println("Operation Canceled");
                    return null;
                } else if (day < 1 || day > 31) {
                    System.out.println("Enter valid day");
                } else {
                    date.setDate(day);
                    success = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and 31");
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        success = false;
        while (!success) {
            System.out.println("Enter month");
            try {
                int month = Integer.parseInt(scanner.nextLine());
                if (month == -1) {
                    System.out.println("Operation Canceled");
                    return null;
                } else if (month < 1 || month > 12) {
                    System.out.println("Enter valid month");
                } else {
                    date.setMonth(month - 1);
                    success = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and 12");
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        success = false;
        while (!success) {
            System.out.println("Enter year");
            try {
                int year = Integer.parseInt(scanner.nextLine());
                if (year == -1) {
                    System.out.println("Operation Canceled");
                    return null;
                } else if (year < 1900 || year > 2030) {
                    System.out.println("Enter valid year");
                } else {
                    date.setYear(year - 1900);
                    success = true;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1900 and 2030");
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        return date;
    }


    //Input returns only for int!
    protected int getInput(){
        boolean stopWait = true;
        int input = -1;

        while(stopWait){
            if(scanner.hasNextInt()) {
                input = scanner.nextInt();
                stopWait = false;
            }
            else{
                System.out.println("Enter an integer please!");
                scanner.nextLine();

            }
        }
        scanner.nextLine();

        return input;
    }

    //Input returns only for float!
    protected float getInputFloat(){
        boolean stopWait = true;
        float input = -1;

        while(stopWait){
            if(scanner.hasNextFloat()) {
                input = scanner.nextFloat();
                stopWait = false;
            }
            else{
                System.out.println("Enter an integer please!");
                scanner.nextLine();

            }
        }
        scanner.nextLine();

        return input;
    }

    public Date getDate() {
        while (true) {
            try {
                System.out.println("Please insert date in format: DD/MM/YYYY");
                String dateInput = scanner.nextLine();
                if (dateInput.equals("c")) {
                    System.out.println("Cancelling command");
                    return null;
                }
                return new SimpleDateFormat("dd/MM/yyyy").parse(dateInput);
            } catch (ParseException p) {
                System.out.println("Date in wrong format! please try again. c to cancel command");
            }
        }
    }

    public int getStoreID() {
        System.out.println("Please insert store ID of store you are interested in.");
        System.out.println("Current store IDs are:");
        System.out.println(controller.getStoreIDs().getValue());
        return scanner.nextInt();
    }

    public void listCategoryIDs() {
        List<Integer> cIDs = getCatIDs();
        System.out.println("Current category IDs are:");
        System.out.println(cIDs);
    }

    public List<Integer> getCatIDs() {
        List<Integer> cIDs = new ArrayList<>();
        List<Domain.ServiceLayer.InventoryObjects.Category> c = controller.getCategories().getValue();
        for (Domain.ServiceLayer.InventoryObjects.Category cat: c) {
            cIDs.add(cat.getID());
        }
        return cIDs;
    }
    public void isUnderMin(int store, int product) {
        Result<Boolean> r = controller.isUnderMin(store, product);
        if (r.isError())
            System.out.println(r.getError());
        else {
            System.out.println("WARNING: product with ID " + product + " is in low stock in store " + store);
        }
    }

    public double round(double price) {
        price = (int)(price*100);
        return price/100;
    }
}