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
    }

    protected boolean areYouSure(){
        System.out.println("Are you sure?");
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
}
