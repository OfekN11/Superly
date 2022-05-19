package Presentation;

import Domain.Service.Objects.Employee;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static Globals.util.HumanInteraction.*;

public class PresentationShiftBuilder {

    private static final String NO_LESS = "There can't be less than %o %s in a shift";
    private static final String EMPLOYEE_LOOP_MSG ="\nHow many %s do you need for this shift?";
    private static final int MIN_CARRIERS= 1;
    private static final int MIN_CASHIERS = 1;
    private static final int MIN_STOREKEEPERS= 1;
    private static final int MIN_SORTERS = 1;
    private static final int MIN_HR_MANAGERS = 0;
    private static final int MIN_LOGISTICS_MANAGERS = 0;
    private static final int MIN_TRANSPORT_MANAGERS = 0;

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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

    public void setDate() throws OperationCancelledException {
        //Date
        printExitMassage();
        while (true) {
            System.out.println("\nEnter shift's date");
            date = buildDate();
            if (areYouSure())
                return;
        }
    }

    public void setShiftType() throws OperationCancelledException {
        //ShiftType
        String loopMsg = "\nEnter shift's type";
        for (int i = 0; i < ShiftTypes.values().length; i++)
            loopMsg += (i + 1) + " -- " + ShiftTypes.values()[i];
        loopMsg += "please choose the number of the shift type";
            type = ShiftTypes.values()[getNumberLoop(loopMsg,1, ShiftTypes.values().length)-1];
    }

    public void setCarrierCount() throws OperationCancelledException {
        //Carrier Count
        String jobName = JobTitles.Carrier+"s";
        carrierCount = getNumberLoop(String.format(EMPLOYEE_LOOP_MSG,jobName),String.format(NO_LESS,MIN_CARRIERS,jobName),MIN_CARRIERS);

    }

    public void setCashierCount() throws OperationCancelledException {
        //Cashier Count
        String jobName = JobTitles.Cashier+"s";
        cashierCount = getNumberLoop(String.format(EMPLOYEE_LOOP_MSG,jobName),String.format(NO_LESS,MIN_CARRIERS,jobName),MIN_CARRIERS);
    }

    public void setStorekeeperCount() throws OperationCancelledException {
        //Storekeeper Count
        String jobName = JobTitles.Storekeeper+"s";
        storekeeperCount = getNumberLoop(String.format(EMPLOYEE_LOOP_MSG,jobName),String.format(NO_LESS,MIN_CARRIERS,jobName),MIN_CARRIERS);
    }

    public void setSorterCount() throws OperationCancelledException {
        //Sorter Count
        String jobName = JobTitles.Sorter+"s";
        sorterCount = getNumberLoop(String.format(EMPLOYEE_LOOP_MSG,jobName),String.format(NO_LESS,MIN_CARRIERS,jobName),MIN_CARRIERS);
    }

    public void setHr_managerCount() throws OperationCancelledException {
        //HR Manager Count
        String jobName = JobTitles.HR_Manager+"s";
        hr_managerCount = getNumberLoop(String.format(EMPLOYEE_LOOP_MSG,jobName),String.format(NO_LESS,MIN_CARRIERS,jobName),MIN_CARRIERS);
    }


    public void setLogistics_managerCount() throws OperationCancelledException {
        //Logistics Manager Count
        String jobName = JobTitles.Logistics_Manager+"s";
        logistics_managerCount = getNumberLoop(String.format(EMPLOYEE_LOOP_MSG,jobName),String.format(NO_LESS,MIN_CARRIERS,jobName),MIN_CARRIERS);
    }

    public void setTransportManagerCount() throws OperationCancelledException {
        //Transport Manager Count
        String jobName = JobTitles.Transport_Manager+"s";
        transportManagerCount = getNumberLoop(String.format(EMPLOYEE_LOOP_MSG,jobName),String.format(NO_LESS,MIN_CARRIERS,jobName),MIN_CARRIERS);
    }

    public void setShiftManager() throws Exception{
        //Shift Manager
        printExitMassage();
        List<Employee> managers = null;
        managers = controller.getAvailableEmployeesFor(date, type).stream().filter(e -> e.certifications.contains(Certifications.ShiftManagement)).collect(Collectors.toList());
        if (managers.size() == 0) {
            System.out.println("No employee who is certified to manage shifts has filled a possibility to work at this shift.");
            System.out.println("Cannot assign a shift manager");
            operationCancelled();
        }
        String loopString = "Choose manager for this shift";
        for (int i = 0; i < managers.size(); i++)
            loopString += (i + 1) + " -- " + managers.get(i).name + ": " + controller.getEmployeeWorkDetailsForCurrentMonth(managers.get(i).id);
        loopString += "please enter the number of the manager u want";
        Employee manager = managers.get(getNumberLoop(loopString,1, managers.size()) - 1);
        managerId = manager.id;
        System.out.println("Chosen shift manager: " + managerId + " - " + manager.name);
    }

    public void buildObject(){
        try {
            if (type == null || date == null|| managerId == null){
                throw new RuntimeException(" you should set the date type and manager before ");
            }
            controller.createShift(date, type, managerId, carrierCount, cashierCount, storekeeperCount, sorterCount, hr_managerCount, logistics_managerCount,transportManagerCount );
            System.out.println("Shift added successfully! Remember to assign employees");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again");
        }
        finally {
            reset();
        }
    }

    public int getNumberLoop(String loopMsg, String becauseMsg, int minVal) throws OperationCancelledException {
        return getNumberLoop(loopMsg,minVal,Integer.MAX_VALUE);
    }

    public int getNumberLoop(String loopMsg, int minVal,int max) throws OperationCancelledException {
        printExitMassage();
        int output;
        while (true) {
            System.out.println(loopMsg);
            output = getNumber(minVal, max);
            System.out.println("you chose : " +output);
            if (areYouSure())
                return output;
        }
    }

    public void reset(){
        date = null;
        type = null;
        managerId = null;
        this.carrierCount = MIN_CARRIERS;
        this.cashierCount = MIN_CASHIERS;
        this.storekeeperCount = MIN_STOREKEEPERS;
        this.sorterCount = MIN_SORTERS;
        this.hr_managerCount = MIN_HR_MANAGERS;
        this.logistics_managerCount = MIN_LOGISTICS_MANAGERS;
        this.transportManagerCount = MIN_TRANSPORT_MANAGERS;
    }
}