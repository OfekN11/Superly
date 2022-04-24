package Frontend;

import java.util.Scanner;

import Backend.Globals.Enums.LicenseTypes;
import Backend.ServiceLayer.Service;
import Frontend.Objects.TransportOrder;

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
            3. Document system management
            4. Back...
            """);
        return getChoice(1, 3);
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
            case 3:
                //documentSystemManagement();
                break;
            default:
        }
    }

    private int menuTM()
    {
        System.out.println("""
            Transport Manager menu:
            1. Transport Order
            2. Transport system management
            """);
        return getChoice(1, 2);
    }
    private void transportManagement()
    {
        switch (menuTM())
        {
            case 1:
                TransportOrder to = getTransportOrder();
                service.addTransportOrder(to.getSrcID(), to.getDstID(), to.getProductList());
                break;
            case 2:
                transportSystemManagement();
                break;
            default:
        }
    }
    private void transportSystemManagement()
    {
        //TODO
    }
    private TransportOrder getTransportOrder()
    {
        //TODO: Check validation of the input and enable to get input of many dests and srcs
        System.out.println("Get new transport order:");
        System.out.println("Enter source ID:");
        int srcID = reader.nextInt();
        System.out.println("Enter destination ID:");
        int dstID = reader.nextInt();
        TransportOrder to = new TransportOrder(srcID, dstID);
        getProductsList(to);
        if(to.isValidOrder())
        {
            return to;
        }
        System.out.println("The order of the shipment is invalid!");
        return null;
    }
    private void getProductsAndCount(TransportOrder to)
    {
        System.out.print("Enter the product name: ");
        String productName = reader.next();
        System.out.print("Enter the required quantity of the product: ");
        int productCount = reader.nextInt();
        //TODO: add input validation
        to.addProduct(productName, productCount);
    }
    private int productListMenu()
    {
        System.out.println("""
                    Product lists:
                    1. Add product
                    2. Remove product
                    3. Update product quantity
                    4. Close order
                    """);
        return getChoice(1, 4);
    }
    private void getProductsList(TransportOrder to)
    {
        boolean close = false;
        do {
            switch (productListMenu())
            {
                case 1:
                    getProductsAndCount(to);
                    break;
                case 2:
                    //TODO: Feature - getProductsAndCount(to);
                    break;
                case 3:
                    //TODO: Feature - getProductsAndCount(to);
                    break;
                case 4:
                    close = true;
                    break;
                default:
            }
        }while(!close);
    }

    private int menuTSM()
    {
        System.out.println("""
            Truck System Manager menu:
            1. Add new truck
            2. Remove truck
            """);
        return getChoice(1, 2);
    }
    private void truckSystemManagement()
    {
        switch (menuTSM())
        {
            case 1:
                addTruck();
                break;
            case 2:
                removeTruck();
                break;
            default:
        }
    }
    private void addTruck()
    {
        //TODO: Check validation of the input
        System.out.println("Enter truck license number:");
        int licenseNumber = reader.nextInt();
        LicenseTypes truckModel = getTruckModel();
        System.out.println("Enter the weight of the truck:");
        int netWeight = reader.nextInt();
        System.out.println("Enter the maximum weight that a truck can load:");
        int maxCapacityWeight = reader.nextInt();
        service.addTruck(licenseNumber, truckModel, netWeight, maxCapacityWeight);
        //TODO: Take the result
    }
    private void removeTruck()
    {
        //TODO: Check validation of the input
        System.out.println("Enter truck license number:");
        int licenseNumber = reader.nextInt();
        service.removeTruck(licenseNumber);
        //TODO: Take the result
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
