package Presentation.Screens;

import Globals.Enums.JobTitles;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

public class Carrier extends Employee{
    private static String[] extraMenuOptions  = {
            "Update Licenses",  //9
            "Exit"              //10
    };

    private Set<String> licenses;

    public Carrier(Screen caller, Domain.Service.Objects.Carrier sCarrier) {
        super(caller, sCarrier, extraMenuOptions);
        licenses = new HashSet<>(sCarrier.licenses);
    }

    @Override
    JobTitles getJobTitle() {
        return JobTitles.Carrier;
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Management Menu of " + name + "!");
        int option = 0;
        while (option != 10) {
            option = runMenu();
            if (option <= 8)
                handleBaseOptions(option);
            else if (option == 9)
                updateLicenses();
            else if (option == 10)
                endRun();
        }
    }

    private void setLicenses(Set<String> curr) {
        licenses = new HashSet<>(curr);
        controller.editCarrierLicenses(this, curr);
    }

    private void updateLicenses() {
        Set<String> curr = new HashSet<>(licenses);
        System.out.println(name + "'s current licenses:");
        for (String license: curr)
            System.out.println(license);
        System.out.println("Would you like to add or remove licenses?");
        int ans = 0;
        while (ans != 1 && ans != 2){
            System.out.println("1 -- add\n2 -- remove");
            try {
                ans = scanner.nextInt();
                if (ans == -1) {
                    System.out.println("Operation Canceled");
                    return;
                }
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
        switch (ans) {
            case 1:
                addLicenses(curr);
                break;
            case 2:
                removeLicenses(curr);
                break;
        }
        System.out.println("New licenses:");
        for (String license: curr)
            System.out.println(license);
        System.out.println("Would you like to save?");
        if (yesOrNo())
            setLicenses(curr);
    }

    private void addLicenses(Set<String> curr) {
        while (true) {
            System.out.println("\nEnter license to add (enter -1 to stop)");
            try {
                String license = scanner.nextLine();
                if (license.equals("-1")) {
                    System.out.println("Stopping");
                    return;
                }
                curr.add(license);
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
                scanner.next();
            }
        }
    }

    private void removeLicenses(Set<String> curr) {
        while (true) {
            System.out.println("\nEnter license to remove (enter -1 to stop)");
            try {
                String license = scanner.nextLine();
                if (license.equals("-1")) {
                    System.out.println("Stopping");
                    return;
                }
                curr.remove(license);
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
                scanner.next();
            }
        }
    }
}
