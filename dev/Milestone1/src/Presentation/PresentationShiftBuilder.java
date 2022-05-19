package Presentation;

import Domain.Service.Objects.Employee;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;
import Presentation.Screens.Screen;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class PresentationShiftBuilder {
    private final BackendController controller;
    private LocalDate date;
    private ShiftTypes type;
    private String managerId;
    private int carrierCount;
    private int cashierCount;
    private int storekeeperCount;
    private int sorterCount;
    private int hr_managerCount;
    private int logistics_managerCount;
    private int transportManagerCount;
    protected final static Scanner scanner = new Scanner(System.in);


    public PresentationShiftBuilder() {
        controller = new BackendController();
        reset();
    }

    private void printExitMassage() {
        System.out.println("Enter details to create shift (enter -1 at any point to stop the process)");

    }

    public void setDate() {
        //Date
        printExitMassage();
        boolean success = false;
        while (!success) {
            System.out.println("\nEnter shift's date");
            date = Screen.buildDate();
            if (date == null)
                return;
            System.out.println("Entered date: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
            success = areYouSure();
        }
        System.out.println("Chosen starting date: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
    }

    public void setShiftType() {
        //ShiftType
        printExitMassage();
        StringBuilder loopString = new StringBuilder("\nEnter shift's type\n");

        for (int i = 0; i < ShiftTypes.values().length; i++)
            loopString.append(i + 1).append(" -- ").append(ShiftTypes.values()[i]).append("\n");
        type = ShiftTypes.values()[scanInt(loopString.toString(),"you chose : ",1,ShiftTypes.values().length) - 1];
    }

    public void setCarrierCount() {
        //Carrier Count
        carrierCount = scanInt("\nHow many carriers do you need for this shift?","the chosen count is:");
    }

    public void setCashierCount() {
        //Cashier Count
        cashierCount =  scanInt("\nHow many Cashiers do you need for this shift?","the chosen count is:");;
    }

    public void setStorekeeperCount() {
        //Storekeeper Count
         storekeeperCount = scanInt("\nHow many Storekeepers do you need for this shift?","the chosen count is:");;
    }

    public void setSorterCount() {
        //Sorter Count
         sorterCount = scanInt("\nHow many Sorters do you need for this shift?","the chosen count is:");;
    }

    public void setHr_managerCount() {
        //HR Manager Count
         hr_managerCount = scanInt("\nHow many HR Managers do you need for this shift?","the chosen count is:");;
    }


    public void setLogistics_managerCount() {
        //Logistics Manager Count
         logistics_managerCount = scanInt("\nHow many Logistics Managers do you need for this shift?","the chosen count is:");;
    }

    public void setTransportManagerCount() {
        //Transport Manager Count
         transportManagerCount = scanInt("\nHow many Transport Managers do you need for this shift?","the chosen count is:");;
    }

    public void setShiftManager() throws Exception {
        //Shift Manager
        printExitMassage();
        List<Employee> managers = null;
        try {
            managers = new ArrayList<>(controller.getAllEmployees()).stream().filter(employee -> employee.certifications.contains(Certifications.ShiftManagement)).collect(Collectors.toList());
            Set<String> cantWorkEmployees = controller.getConstraint(date, type).employeeIDs;
            managers = managers.stream().filter(employee -> !cantWorkEmployees.contains(employee.id)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (managers.size() == 0) {
            System.out.println("No employee who is certified to manage shifts has filled a possibility to work at this shift.");
            System.out.println("Cannot assign a shift manager. Operation Cancelled");
            throw new Exception("Cannot assign a shift manager. Operation Cancelled");
        }
        Employee manager;
        StringBuilder loopMsg = new StringBuilder("Choose manager for this shift\n");
        for (int i = 0; i < managers.size(); i++)
            loopMsg.append(i + 1).append(" -- ").append(managers.get(i).name).append(": ").append(controller.getEmployeeWorkDetailsForCurrentMonth(managers.get(i).id)).append("\n");
        manager = managers.get(scanInt(loopMsg.toString(),"Chosen shift manager id ",1,managers.size()) - 1);
        managerId = manager.id;
        System.out.println("Chosen shift manager: " + manager.name);
    }

    public void buildObject() throws RuntimeException{
        try {
            if (type == null || date == null|| managerId == null){
                throw new RuntimeException(" you should set the date type and manager before ");
            }
            controller.createShift(date, type, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount, logistics_managerCount,transportManagerCount );
            System.out.println("Shift added successfully! Remember to assign employees");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again");
            throw new RuntimeException(" you should set the date type and manager before ");
        }
        finally {
            reset();
        }
    }


    private int scanInt(String loopMassage,String successMsg){
        return scanInt(loopMassage,successMsg,-1,Integer.MAX_VALUE);
    }

    private int scanInt(String loopMassage,String successMsg, int min, int max){
        printExitMassage();
        int output=0;
        boolean success = false;
        while (!success) {
            System.out.println(loopMassage);
            try {
                output = Integer.parseInt(scanner.nextLine());
                if (output == -1) {
                    throw new Exception("Operation cancelled");
                }
                else if (output<min || output > max){
                    System.out.println("pls enter a valid value");
                }
                else {
                    System.out.println("Entered count: " + output);
                    success = areYouSure();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a non-negative integer");
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println(successMsg + output);
        return output;
    }

    private void reset(){
        date = null;
        type = null;
        managerId = null;
        this.carrierCount = 0;
        this.cashierCount = 0;
        this.storekeeperCount = 0;
        this.sorterCount = 0;
        this.hr_managerCount = 0;
        this.logistics_managerCount = 0;
        this.transportManagerCount = 0;
    }

    private static boolean areYouSure(){
        System.out.println("Are you sure?");
        return yesOrNo();
    }

    private static boolean yesOrNo(){
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
}
