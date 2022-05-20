package Presentation.Screens;

import Domain.Service.Objects.Employee;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;
import Globals.util.Supplier;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static Globals.util.HumanInteraction.*;

public abstract class Shift extends Screen {

    private static final String[] menuOptions = {
            "Print shift details",          //1
            "Update employee count(s)",     //2
            "Assign employees",             //3
    };

    private final Map<JobTitles, Supplier<Set<Employee>>> getAssignedByType = Stream.of(
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Sorter, () -> controller.getAssignedSortersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Storekeeper, () -> controller.getAssignedStorekeepersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Carrier, () -> controller.getAssignedCarriersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Cashier, () -> controller.getAssignedCashiersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.HR_Manager, () -> controller.getAssignedHR_ManagersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Logistics_Manager, () -> controller.getAssignedLogistics_ManagersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Transport_Manager, () -> controller.getAssignedTransports_ManagersFor(this))
    ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    private final Map<JobTitles, Supplier<Set<Employee>>> getAvailableByType = Stream.of(
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Sorter, () -> controller.getAvailableSortersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Storekeeper, () -> controller.getAvailableStorekeepersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Carrier, () -> controller.getAvailableCarriersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Cashier, () -> controller.getAvailableCashiersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.HR_Manager, () -> controller.getAvailableHR_ManagersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Logistics_Manager, () -> controller.getAvailableLogistics_ManagersFor(this)),
            new AbstractMap.SimpleEntry<JobTitles, Supplier<Set<Employee>>>(JobTitles.Transport_Manager, () -> controller.getAvailableTransports_ManagersFor(this))
    ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    protected final LocalDate date;
    protected String shiftManagerId;

    protected int carrierCount;
    protected int cashierCount;
    protected int storekeeperCount;
    protected int sorterCount;
    protected int hr_managersCount;
    protected int logistics_managersCount;
    protected int transport_managersCount;

    private final Map<JobTitles, Integer> getCountByType = Stream.of(
            new AbstractMap.SimpleEntry<>(JobTitles.Sorter, sorterCount),
            new AbstractMap.SimpleEntry<>(JobTitles.Storekeeper, storekeeperCount),
            new AbstractMap.SimpleEntry<>(JobTitles.Carrier, carrierCount),
            new AbstractMap.SimpleEntry<>(JobTitles.Cashier, cashierCount),
            new AbstractMap.SimpleEntry<>(JobTitles.HR_Manager, hr_managersCount),
            new AbstractMap.SimpleEntry<>(JobTitles.Logistics_Manager, logistics_managersCount),
            new AbstractMap.SimpleEntry<>(JobTitles.Transport_Manager, transport_managersCount)
    ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));

    public Shift(Screen caller, Domain.Service.Objects.Shift sShift, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
        date = sShift.date;

        shiftManagerId = sShift.shiftManagerId;
        carrierCount = sShift.carrierCount;
        cashierCount = sShift.cashierCount;
        storekeeperCount = sShift.storekeeperCount;
        sorterCount = sShift.sorterCount;
        hr_managersCount = sShift.hr_managersCount;
        logistics_managersCount = sShift.logistics_managersCount;
        transport_managersCount = sShift.transport_managersCount;
    }

    protected void handleBaseOptions(int option) throws Exception {
        switch (option) {
            case 1:
                printDetails();
                break;
            case 2:
                updateCount();
                break;
            case 3:
                assignEmployees();
                break;
        }
    }

    private void assignEmployees() throws Exception {
        System.out.println("Which type of employee would you like to assign for this shift?");
        System.out.println("1 -- ShiftManager");
        for (int i = 0; i < JobTitles.values().length; i++)
            System.out.println((i + 2) + " -- " + JobTitles.values()[i]);
        int ordinal = getNumber(1, JobTitles.values().length + 1);
        if (ordinal == 1) {
            assignShiftManager();
        } else {
            assignEmployee(JobTitles.values()[ordinal - 2]);
        }
    }

    private void assignEmployee(JobTitles type) throws Exception {
        Set<Employee> assigned = getAssignedByType.get(type).get();
        Set<String> assignedIDs = assigned.stream().map(e -> e.id).collect(Collectors.toSet());
        System.out.println("\nCurrent " + type + "s assigned to this shift:");
        for (Employee employee : assigned)
            System.out.println(employee.id + " - " + employee.name + " : " + controller.getEmployeeWorkDetailsForCurrentMonth(employee.id));
        if (assignedIDs.size() == getCountByType.get(type)){
            System.out.println("Can't assign more " + type +"s. Max number of " + type + "s has been reached.");
            return;
        }
        Set<Employee> available = getAvailableByType.get(type).get();
        Set<String> availableIDs = available.stream().map(e -> e.id).collect(Collectors.toSet());
        System.out.println("Please add up to " + (getCountByType.get(type) - assignedIDs.size()) + " " + type + "s out of the following available employees");
        for (Employee employee : available)
            System.out.println(employee.id + " - " + employee.name + " : " + controller.getEmployeeWorkDetailsForCurrentMonth(employee.id));
        System.out.println("Enter ONLY the ID of the wanted employees!");
        boolean success = false;
        while (!success) {
            String id = scanner.nextLine();
            if (id.equals("-1"))
                operationCancelled();
            if (!availableIDs.contains(id))
                System.out.println(id + " is not an ID out of the available employee list. \nPlease try again");
            else {
                System.out.println(id + " successfully added");
                assignedIDs.add(id);
                availableIDs.remove(id);
                if (assignedIDs.size() == getCountByType.get(type))
                    System.out.println("Max number has been reached");
                System.out.println("Do you want to save?");
                success = yesOrNo();
                if (!success && assignedIDs.size() == getCountByType.get(type))
                    operationCancelled();
            }
        }

        switch (type) {
            case Sorter:
                controller.editShiftSorterIDs(this, assignedIDs);
                break;
            case Storekeeper:
                controller.editShiftStorekeeperIDs(this, assignedIDs);
                break;
            case Carrier:
                controller.editShiftCarrierIDs(this, assignedIDs);
                break;
            case Cashier:
                controller.editShiftCashierIDs(this, assignedIDs);
                break;
            case HR_Manager:
                controller.editShiftHR_ManagerIDs(this, assignedIDs);
                break;
            case Logistics_Manager:
                controller.editShiftLogistics_ManagerIDs(this, assignedIDs);
                break;
            case Transport_Manager:
                controller.editShiftTransport_ManagerIDs(this, assignedIDs);
                break;
        }
        System.out.println("Chosen group saved successfully");
        if (assignedIDs.size() < getCountByType.get(type)) {
            System.out.println("Notice that the number of assigned employees doesn't meet the requirement of " + getCountByType.get(type) + " " + type + "s");
            System.out.println("Please make sure more employees register to the constraint of this shift");
        }
    }

    private void assignShiftManager() throws Exception {
        Employee currManager = controller.getEmployee(shiftManagerId);
        System.out.println("\nCurrent shift manager: " + currManager.name + ", ID: " + currManager.id);
        List<Employee> availableManagers = new ArrayList<>(controller.getAvailableShiftManagersFor(this));
        if (availableManagers.size() == 0) {
            System.out.println("No employee who is certified to manage shifts has filled a possibility to work at this shift.");
            System.out.println("Cannot assign a shift manager");
            operationCancelled();
        }
        System.out.println("Choose manager for this shift");
        for (int i = 0; i < availableManagers.size(); i++)
            System.out.println((i + 1) + " -- " + availableManagers.get(i).name + ": " + controller.getEmployeeWorkDetailsForCurrentMonth(availableManagers.get(i).id));

        Employee manager = null;
        boolean success = false;
        while (!success) {
            manager = availableManagers.get(getNumber(1, availableManagers.size()) - 1);
            System.out.println("Entered manager: " + manager.name);
            success = areYouSure();
        }
        setShiftManagerId(manager.id);
        System.out.println("Successfully assigned " + manager.name + " as manager of this shift");
    }

    private void updateCount() throws Exception {
        System.out.println("\nWhich type of count would you like to update?");
        for (int i = 0; i < JobTitles.values().length; i++)
            System.out.println((i + 1) + " -- " + JobTitles.values()[i]);
        JobTitles type = JobTitles.values()[getNumber(1, JobTitles.values().length) - 1];
        System.out.println("\nCurrent " + type + " count: " + getCountByType.get(type));

        System.out.println("How many " + type + "s do you need for this shift?");
        int newCount = getNumber(0);

        switch (type) {
            case Carrier:
                setCarrierCount(newCount);
                break;
            case Cashier:
                setCashierCount(newCount);
                break;
            case Storekeeper:
                setStorekeeperCount(newCount);
                break;
            case Sorter:
                setSorterCount(newCount);
                break;
            case HR_Manager:
                setHr_managersCount(newCount);
                break;
            case Logistics_Manager:
                setLogistics_managersCount(newCount);
                break;
            case Transport_Manager:
                setTransport_managersCount(newCount);
                break;
        }
        System.out.println(type + " count successfully set to " + newCount);
    }

    protected void setShiftManagerId(String shiftManagerId) throws Exception {
        controller.editShiftManagerID(this, shiftManagerId);
        this.shiftManagerId = shiftManagerId;
    }

    protected void setCarrierCount(int newCount) throws Exception {
        controller.editShiftCarrierCount(this, newCount);
        this.carrierCount = newCount;
    }

    protected void setCashierCount(int newCount) throws Exception {
        controller.editShiftCashierCount(this, newCount);
        this.cashierCount = newCount;
    }

    protected void setStorekeeperCount(int newCount) throws Exception {
        controller.editShiftStorekeeperCount(this, newCount);
        this.storekeeperCount = newCount;
    }

    protected void setSorterCount(int newCount) throws Exception {
        controller.editShiftSorterCount(this, newCount);
        this.sorterCount = newCount;
    }

    protected void setHr_managersCount(int newCount) throws Exception {
        controller.editShiftHR_ManagerCount(this, newCount);
        this.hr_managersCount = newCount;
    }

    protected void setLogistics_managersCount(int newCount) throws Exception {
        controller.editShiftLogistics_ManagerCount(this, newCount);
        this.logistics_managersCount = newCount;
    }

    private void setTransport_managersCount(int newCount) {
        controller.editShiftTransport_ManagerCount(this, newCount);
        this.transport_managersCount = newCount;
    }

    protected void printEmployeesByType(JobTitles type){
        Set<Employee> assigned = getAssignedByType.get(type).get();
        System.out.println(type + "s (" + assigned.size() + " out of " + getCountByType.get(type) + ")");
        EmployeesMenu.EmployeesViewer.printEmployees(getAssignedByType.get(type).get());
    }

    public abstract ShiftTypes getType();

    protected void printDetails() throws Exception {
        System.out.println(getType() + " shift");
        System.out.println("Date: " + new SimpleDateFormat("dd-MM-yyyy").format(date));
        System.out.println("Shift Manager: " + shiftManagerId + " - " + controller.getEmployee(shiftManagerId).name);
        for (JobTitles type : JobTitles.values()) {
            printEmployeesByType(type);
            System.out.println();
        }
    }

    public LocalDate getDate() {
        return date;
    }
}