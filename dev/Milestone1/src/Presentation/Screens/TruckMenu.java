package Presentation.Screens;

import Domain.Service.Objects.Shift;
import Globals.Enums.LicenseTypes;
import Globals.Enums.ShiftTypes;
import Globals.Enums.TruckModel;
import Globals.util.ShiftComparator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TruckMenu extends Screen{
    private static final String[] menuOptions = {
            "Add truck",                //1
            "Delete truck",             //2
            //update truck
            "Exit"                      //3
    };
    public TruckMenu(Screen caller) {
        super(caller, menuOptions);
    }

    @Override
    public void run() {
        System.out.println("\nWelcome to the Truck Management Menu!");
        int option = 0;
        while (option != 4 && option != 5) {
            option = runMenu();
            try {
                switch (option) {
                    case 1:
                        addTruck();
                        break;
                    case 2:
                        deleteTruck();
                        break;
                    case 3:
                        endRun();
                        break;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again");
            }
        }
    }

    private int getLicenseNumber()
    {
        System.out.println("Enter truck license number:");
        int licenseNumber = scanner.nextInt();
        while(licenseNumber > 0){
            System.out.println("Please insert legal license number:");
            licenseNumber = scanner.nextInt();
        }
        return licenseNumber;
    }
    private void deleteTruck() {
        //License number:
        int licenseNumber = getLicenseNumber();
        try {
            controller.removeTruck(licenseNumber);
            System.out.println("The truck was successfully removed!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void addTruck() {
        //License number:
        int licenseNumber = getLicenseNumber();
        //Truck model:
        TruckModel truckModel = getTruckModel();
        //Net weight:
        int netWeight = getTruckWeight();
        //Max capacity weight:
        int maxCapacityWeight = getMaxCapacityWeight(truckModel);
        try {
            controller.addTruck(licenseNumber, truckModel, netWeight, maxCapacityWeight);
            System.out.println("The truck was successfully added!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    private int getMaxCapacityWeight(TruckModel tm)
    {
        System.out.println("Enter the weight of the truck:");
        printLegalWeight();
        int maxWeight = scanner.nextInt();
        while(!checkModelWeight(tm, maxWeight)){
            System.out.println("Please insert legal weight:");
            printLegalWeight();
            maxWeight = scanner.nextInt();
        }
        return maxWeight;
    }
    private void printLegalWeight()
    {
        System.out.println("Van - 0 < weight <= 1000\n" +
                            "SemiTrailer - 1000 < weight <= 5000\n" +
                            "DoubleTrailer - 5000 < weight <= 10000\n" +
                            "FullTrailer - 10000 < weight <= 20000\n");
    }
    private boolean checkModelWeight(TruckModel tm, int weight)
    {
        boolean ans = weight > 0;
        switch (tm)
        {
            case Van:
                ans &= weight <= 1000;
                break;
            case SemiTrailer:
                ans &= weight > 1000 & weight <= 5000;
                break;
            case DoubleTrailer:
                ans &= weight > 5000 & weight <= 10000;
                break;
            case FullTrailer:
                ans &= weight > 10000 & weight <= 20000;
                break;
            default:
                ans = false;
        }
        return  ans;
    }
    private int getTruckWeight()
    {
        System.out.println("Enter the weight of the truck:");
        int netWeight = scanner.nextInt();
        while(netWeight > 0){
            System.out.println("Please insert legal weight:");
            netWeight = scanner.nextInt();
        }
        return netWeight;
    }
    private TruckModel getTruckModel()
    {
        System.out.println("Enter truck model:");
        for (int i = 0; i < TruckModel.values().length; i++)
        System.out.println((i + 1) + " -- " + ShiftTypes.values()[i]);
        return TruckModel.values()[getChoice(1, 4) - 1];
    }
    private int getChoice(int a, int b)
    {
        int choice = 0;
        do {
            System.out.println("Enter a value in the range between " + a + " and " + b);
            choice = scanner.nextInt();
        }while(choice > b | choice < a);
        return choice;
    }

}
