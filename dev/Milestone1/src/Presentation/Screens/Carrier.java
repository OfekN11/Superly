package Presentation.Screens;

import Globals.Enums.JobTitles;
import Globals.Enums.LicenseTypes;
import static Globals.util.HumanInteraction.*;

import java.util.HashSet;
import java.util.Set;

public class Carrier extends Employee {
    private static final String[] extraMenuOptions = {
            "Update Licenses",  //9
            "Advance Site",     //10
            "Exit"              //11
    };

    private Set<LicenseTypes> licenses;

    public Carrier(Screen caller, Domain.Service.Objects.Carrier sCarrier) {
        super(caller, sCarrier, extraMenuOptions);
        licenses = new HashSet<>(sCarrier.licenses);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Management Menu of " + name + "!");
        int option = 0;
        while (option != 11) {
            option = runMenu();
            try {
                if (option <= 8)
                    handleBaseOptions(option);
                else if (option == 9)
                    updateLicenses();
                else if (option == 10)
                    advanceSite();
                else if (option == 11)
                    endRun();
            } catch (OperationCancelledException ignored) {
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }
    private int getSiteID(String siteType)
    {
        System.out.println("Enter " + siteType + " ID:");
        int siteID = scanner.nextInt();
        while(siteID < 0){
            System.out.println("Please insert legal ID:");
            siteID = scanner.nextInt();
        }
        return siteID;
    }
    private void advanceSite() {
        int transportSN = getTransportSN();
        int siteID = getSiteID("Site");
        controller.advanceSite(transportSN, siteID);
    }

    private int getTransportSN() {
        System.out.println("Enter Transport SN:");
        int transportSN = scanner.nextInt();
        while(transportSN < 0){
            System.out.println("Please insert legal ID:");
            transportSN = scanner.nextInt();
        }
        return transportSN;
    }

    private void setLicenses(Set<LicenseTypes> curr) throws Exception {
        controller.editCarrierLicenses(this, curr);
        licenses = new HashSet<>(curr);
    }

    private void updateLicenses() throws Exception {
        Set<LicenseTypes> curr = new HashSet<>(licenses);
        System.out.println(name + "'s current licenses:");
        for (LicenseTypes license : curr)
            System.out.println(license);
        System.out.println("Would you like to add or remove licenses?");
        int ans = 0;
        while (ans != 1 && ans != 2) {
            System.out.println("1 -- add\n2 -- remove");
            try {
                ans = Integer.parseInt(scanner.nextLine());
                if (ans == -1) {
                    System.out.println("Operation Canceled");
                    return;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer value (1 or 2)");
            } catch (Exception ex) {
                System.out.println("An unexpected error happened. Please try again");
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
        for (LicenseTypes license : curr)
            System.out.println(license);
        System.out.println("Would you like to save?");
        if (yesOrNo())
            setLicenses(curr);
    }

    private void addLicenses(Set<LicenseTypes> curr) {
        while (true) {
            System.out.println("\nEnter license to add (enter -1 to stop)");
            for (int i = 0; i < LicenseTypes.values().length; i++)
                System.out.println((i + 1) + " -- " + JobTitles.values()[i]);
            try {
                int ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Stopping");
                    return;
                } else if (ordinal < 1 || ordinal > JobTitles.values().length)
                    System.out.println("Please enter an integer between 1 and " + JobTitles.values().length);
                else {
                    curr.add(LicenseTypes.values()[ordinal]);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and " + JobTitles.values().length);
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
    }

    private void removeLicenses(Set<LicenseTypes> curr) {
        while (true) {
            System.out.println("\nEnter license to remove (enter -1 to stop)");
            for (int i = 0; i < LicenseTypes.values().length; i++)
                System.out.println((i + 1) + " -- " + JobTitles.values()[i]);
            try {
                int ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Stopping");
                    return;
                } else if (ordinal < 1 || ordinal > JobTitles.values().length)
                    System.out.println("Please enter an integer between 1 and " + JobTitles.values().length);
                else {
                    curr.remove(LicenseTypes.values()[ordinal]);
                }
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
    }
}