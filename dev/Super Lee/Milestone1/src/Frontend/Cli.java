package Frontend;

import java.util.Scanner;

import Backend.Globals.Enums.LicenseTypes;
import Frontend.Objects.Truck;

public class Cli {
    Scanner reader = new Scanner(System.in);
    Service service = new Service();
    private void login()
    {
        System.out.println("Login:");
        System.out.println("Please enter your details:");
        System.out.print("Username: ");
        String username = reader.next();
        System.out.println("Password: ");
        String password = reader.next();

    }

    private int mainMenu()
    {
        System.out.println("""
            Main menu:
            1. Transport Manager
            2. Truck Driver
            """);
        return getChoice(1, 2);
    }


    public void main(String[] args)
    {
        //login();
        switch (mainMenu())
        {
            case 1:
                transportManager();
                break;
            case 2:
                //TODO: Truck system management
                break;
            default:
        }
    }

    private int getChoice(int a, int b)
    {
        int choice = 0;
        do {
            System.out.println("Enter a value in the range between " + a + "and " + b);
            choice = reader.nextInt();
        }while(choice > b | choice < a);
        return choice;
    }

    private int mainMenuTM()
    {
        System.out.println("""
            Transport Manager menu:
            1. Transport Management
            2. Truck system management
            """);
        return getChoice(1, 2);
    }

    private void transportManager()
    {
        switch (mainMenuTM())
        {
            case 1:
                transportManagement();
                break;
            case 2:
                truckSystemManagement();
                break;
            default:
        }
    }

    private int menuTM()
    {
        System.out.println("""
            Transport Manager menu:
            1. Transport Order
            2. Truck system management
            """);
        return getChoice(1, 2);
    }
    private void transportManagement()
    {
        switch (menuTM())
        {
            case 1:
                //TODO: Transport Management
                break;
            default:
        }
    }
    private int menuTSM()
    {
        System.out.println("""
            Truck System Manager menu:
            1. Add new truck
            2. Remove truck
            3. Update truck
            """);
        return getChoice(1, 3);
    }
    private void truckSystemManagement()
    {
        switch (menuTSM())
        {
            case 1:
                service.addTruck(createTruck());
                break;
            case 2:
                //TODO: Transport Management
                break;
            case 3:
                //TODO: Update truck implement later
                break;
            default:
        }
    }
    private Truck createTruck()
    {
        //TODO: Check validation of the input
        System.out.println("Enter data about the new truck:");
        System.out.println("Enter truck license number:");
        int licenseNumber = reader.nextInt();
        LicenseTypes truckModel = getTruckModel();
        System.out.println("Enter the weight of the truck:");
        int netWeight = reader.nextInt();
        System.out.println("Enter the maximum weight that a truck can load:");
        int maxCapacityWeight = reader.nextInt();
        return new Truck(licenseNumber, truckModel, netWeight, maxCapacityWeight);
    }
    private LicenseTypes getTruckModel()
    {
        String[] truckModel = {"B", "C1", "C", "CE"};
        System.out.println("""
            Enter truck model:
            1. B
            2. C1
            3. C
            4. C+E
            """);

        return LicenseTypes.valueOf(truckModel[getChoice(1, 4) - 1]);
    }
    private int mainMenuTD()
    {
        System.out.println("""
            Truck driver menu:
            1. Weight report
            2. 
            """);
        return getChoice(1, 1);
    }
    private void truckDriver()
    {
        switch (mainMenuTD())
        {
            case 1:
                weightReport();
                break;
            case 2:
                //TODO: Truck system management
                break;
            default:
        }
    }
    private void weightReport()
    {
        System.out.println("""
            Please 
            """);
    }
}
