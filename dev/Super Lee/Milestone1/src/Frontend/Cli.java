package Frontend;

import java.util.List;
import java.util.Scanner;

import Backend.Globals.Enums.LicenseTypes;
import Backend.ServiceLayer.Objects.Transport;
import Backend.ServiceLayer.Result;
import Backend.ServiceLayer.Service;
import Frontend.Objects.TransportOrder;

public class Cli {
    private static Scanner reader = new Scanner(System.in);
    private static Service service = new Service();
    public static void main(String[] args)
    {
        boolean closeProgram = false;
        while(!closeProgram) {
            switch (mainMenu()) {
                case 1:
                    driverSystemManagement();
                    break;
                case 2:
                    truckSystemManagement();
                    break;
                case 3:
                    transportSystemManagement();
                    break;
                case 4:
                    documentSystemManagement();
                    break;
                case 5:
                    closeProgram = true;
                    System.out.println("Good Bye!!!");
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }
    private static void transportSystemManagement()
    {
        boolean back = false;
        while(!back) {
            switch (menuTransSM()) {
                case 1:
                    addTransportOrder();
                    break;
                case 2:
                    createNewTransport();
                    break;
                case 3:
                    getWaitingTransports();
                    break;
                case 4:
                    getInProgressTransports();
                    break;
                case 5:
                    getRedesignTransports();
                    break;
                case 6:
                    updateTransport();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private static void getRedesignTransports() {
        Result result = service.getRedesignTransports();
        if(result.isError())
        {
            System.out.println(result.getError());
        }
        else
        {
            System.out.println("Redesign Transports:");
            //TODO: casting
            printTransport((List<Transport>) result.getValue());
        }
    }

    private static void getInProgressTransports() {
        Result result = service.getInProgressTransports();
        if(result.isError())
        {
            System.out.println(result.getError());
        }
        else
        {
            System.out.println("In Progress Transports:");
            //TODO: casting
            printTransport((List<Transport>) result.getValue());
        }
    }

    private static void getWaitingTransports() {
        Result result = service.getWaitingTransports();
        if(result.isError())
        {
            System.out.println(result.getError());
        }
        else
        {
            System.out.println("Waiting Transports:");
            //TODO: casting
            printTransport((List<Transport>) result.getValue());
        }
    }

    private static void printTransport(List<Transport> value) {
        for(Transport transport: value)
        {
            System.out.println(transport.toString());
        }
    }

    private static void addTransportOrder() {
        TransportOrder to = getTransportOrder();
        service.addTransportOrder(to.getSrcID(), to.getDstID(), to.getProductList());
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
        //TODO:Later
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
    private static void updateTransport() {
        boolean back = false;
        System.out.println("Please enter transport serial number to update:");
        int transportSN = reader.nextInt();;
        while(!back) {
            switch (menuCNT()) {
                case 1:
                    placeDriver(transportSN);
                    break;
                case 2:
                    placeTruck(transportSN);
                    break;
                case 3:
                    startTransport(transportSN);
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private static void startTransport(int transportSN) {
        Result res = service.startTransport(transportSN);
        if(res.isError())
        {
            System.out.println(res.getError());
        }
    }

    private static void placeTruck(int transportSN) {
        System.out.println("Please enter truck license number to place:");
        int truckLN = reader.nextInt();
        Result res = service.placeTruck(transportSN, truckLN);
        if(res.isError())
        {
            System.out.println(res.getError());
        }
    }

    private static void placeDriver(int transportSN) {
        System.out.println("Please enter driver name to place:");
        String driverName = reader.next();
        Result res = service.placeDriver(transportSN, driverName);
        if(res.isError())
        {
            System.out.println(res.getError());
        }
    }

    private static void createNewTransport() {
        boolean back = false;
        while(!back) {
            switch (menuCNT()) {
                case 1:
                    getOrders();
                    break;
                case 2:
                    getOrdersInTransportArea();
                    break;
                case 3:
                    addOrderToTransport();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private static void addOrderToTransport() {
        //TODO: service.addOrderToTransport();
    }

    private static void getOrdersInTransportArea() {
        //TODO: service.getOrdersInTransportArea();
    }

    private static void getOrders() {
        //TODO: service.getOrders();
    }

    private static int menuCNT() {
        System.out.println("Create Transport\n" +
                "Please enter option number:\n" +
                "1. View orders\n" +
                "2. View orders in same areas\n" +
                "3. Add order\n" +
                "4. Back");
        return getChoice(1, 4);
    }

    private static int menuTransSM() {
        System.out.println("Transport system management\n" +
                "Please enter option number:\n" +
                "1. Add transport order\n" +
                "2. Create new transport\n" +
                "3. Get waiting transport\n" +
                "4. Get in progress transport\n" +
                "5. Get redesign transport\n" +
                "6. Update transport\n" +
                "7. Back");
        return getChoice(1, 7);
    }

    private static void documentSystemManagement() {
        boolean back = false;
        while(!back) {
            switch (menuDocSM()) {
                case 1:
                    //TODO: service.getTransportDocument(int docSN);
                    break;
                case 2:
                    //TODO: service.getDestinationDocument(int docSN);
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private static int menuDocSM() {
        System.out.println("Document system management\n" +
                "Please enter option number:\n" +
                "1. Get transport document\n" +
                "2. Get destination document\n" +
                "3. Back");
        return getChoice(1, 3);
    }

    private static void driverSystemManagement() {
        boolean back = false;
        while(!back) {
            switch (menuDSM()) {
                case 1:
                    addDriver();
                    break;
                case 2:
                    removeDriver();
                    break;
                case 3:
                    updateDriver();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
    }

    private static void updateDriver() {
        System.out.println("Please enter driver name:");
        String driverName = reader.next();
        Result  result = service.updateDriver(driverName, getDriverLicenseType());
        printIfError(result);
    }

    private static void removeDriver() {
        System.out.println("Please enter driver name:");
        String driverName = reader.next();
        Result  result = service.removeDriver(driverName);
        printIfError(result);
    }
    private static void printIfError(Result res)
    {
        if(res.isError())
        {
            System.out.println(res.getError());
        }
    }
    private static void addDriver() {
        System.out.println("Please enter driver name:");
        String driverName = reader.next();
        Result  result = service.addDriver(driverName, getDriverLicenseType());
        printIfError(result);
    }
    private static LicenseTypes getDriverLicenseType()
    {
        String[] truckModel = {"B", "C1", "C", "CE"};
        System.out.println("Enter driver license type:\n" +
                "1. B\n" +
                "2. C1\n" +
                "3. C\n" +
                "4. C+E");

        return LicenseTypes.valueOf(truckModel[getChoice(1, 4) - 1]);
    }
    private static int mainMenu()
    {
        System.out.println("Welcome to Transport's system\n" +
                "Please enter option number:\n" +
                "1. Driver system management\n" +
                "2. Truck system management\n" +
                "3. Transport system management\n" +
                "4. Document system management\n" +
                "5. Exit");
        return getChoice(1, 5);
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

    private static int menuDSM()
    {
        System.out.println("Driver system management\n" +
                "Please enter option number:\n" +
                "1. Add driver\n" +
                "2. Delete driver\n" +
                "3. Update driver lisence type\n" +
                "4. Back");
        return getChoice(1, 4);
    }

    private static int menuTSM()
    {
        System.out.println("Truck system management:\n" +
                "1. Add truck\n" +
                "2. Delete truck\n" +
                "3. Back");
        return getChoice(1, 3);
    }
    private static void truckSystemManagement()
    {
        boolean back = false;
        while(!back) {
            switch (menuTSM())
            {
                case 1:
                    addTruck();
                    break;
                case 2:
                    removeTruck();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid input!");
            }
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

}
