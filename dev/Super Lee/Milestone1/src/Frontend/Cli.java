package Frontend;

import java.util.Scanner;

import Backend.Globals.Enums.LicenseTypes;
import Backend.ServiceLayer.Service;
import Frontend.Objects.TransportOrder;

public class Cli {
    private static Scanner reader = new Scanner(System.in);
    private static Service service = new Service();
    public static void main(String[] args)
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
    private void login()
    {
        System.out.println("Login:");
        System.out.println("Please enter your details:");
        System.out.print("Username: ");
        String username = reader.next();
        System.out.println("Password: ");
        String password = reader.next();

    }

    private static int mainMenu()
    {
        System.out.println("Main menu:\n1. Transport Manager\n2. Truck Driver");
        return getChoice(1, 2);
    }




    private static int getChoice(int a, int b)
    {
        int choice = 0;
        do {
            System.out.println("Enter a value in the range between " + a + " and " + b);
            choice = reader.nextInt();
        }while(choice > b | choice < a);
        return choice;
    }

    private static int mainMenuTM()
    {
        System.out.println("Transport Manager menu:\n1. Transport Management\n" +
                "2. Truck system management\n3. Document system management\n4. Back...");
        return getChoice(1, 3);
    }

    private static void transportManager()
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

    private static int menuTM()
    {
        System.out.println("Transport Manager menu:\n1. Transport Order\n2. Transport system management");
        return getChoice(1, 2);
    }
    private static void transportManagement()
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
    private static void transportSystemManagement()
    {
        //TODO
    }
    private static TransportOrder getTransportOrder()
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
    private static void getProductsAndCount(TransportOrder to)
    {
        System.out.println("Enter the product name: ");
        String productName = reader.next();
        System.out.println("Enter the required quantity of the product: ");
        int productCount = reader.nextInt();
        //TODO: add input validation
        to.addProduct(productName, productCount);
    }
    private static int productListMenu()
    {
        System.out.println("Product lists:\n" +
                "1. Add product\n" +
                "2. Remove product\n" +
                "3. Update product quantity\n" +
                "4. Close order");
        return getChoice(1, 4);
    }
    private static void getProductsList(TransportOrder to)
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

    private static int menuTSM()
    {
        System.out.println("Truck System Manager menu:\n" +
                "1. Add new truck\n" +
                "2. Remove truck");
        return getChoice(1, 2);
    }
    private static void truckSystemManagement()
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
    private static void addTruck()
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
    private static void removeTruck()
    {
        //TODO: Check validation of the input
        System.out.println("Enter truck license number:");
        int licenseNumber = reader.nextInt();
        service.removeTruck(licenseNumber);
        //TODO: Take the result
    }
    private static LicenseTypes getTruckModel()
    {
        String[] truckModel = {"B", "C1", "C", "CE"};
        System.out.println("Enter truck model:\n" +
                "1. B\n" +
                "2. C1\n" +
                "3. C\n" +
                "4. C+E");

        return LicenseTypes.valueOf(truckModel[getChoice(1, 4) - 1]);
    }
    private int mainMenuTD()
    {
        System.out.println("Truck driver menu:\n" +
                "1. Weight report");
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
        
    }
}
