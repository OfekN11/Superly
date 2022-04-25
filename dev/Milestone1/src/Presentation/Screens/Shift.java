package Presentation.Screens;

import Domain.Service.Objects.Constraint;
import Domain.Service.Objects.Employee;
import Globals.Enums.Certifications;
import Globals.Enums.JobTitles;
import Globals.Enums.ShiftTypes;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Shift extends Screen {

    private static final String[] menuOptions = {
            "Print shift details",          //1
            "Update employee count(s)",     //2
            "Assign employees",             //3
    };

    protected final Date date;
    protected String shiftManagerId;

    protected int carrierCount;
    protected int cashierCount;
    protected int storekeeperCount;
    protected int sorterCount;
    protected int hr_managersCount;
    protected int logistics_managersCount;

    protected Set<String> carrierIDs;
    protected Set<String> cashierIDs;
    protected Set<String> storekeeperIDs;
    protected Set<String> sorterIDs;
    protected Set<String> hr_managerIDs;
    protected Set<String> logistics_managerIDs;

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

        carrierIDs = new HashSet<>(sShift.carrierIDs);
        cashierIDs = new HashSet<>(sShift.cashierIDs);
        storekeeperIDs = new HashSet<>(sShift.storekeeperIDs);
        sorterIDs = new HashSet<>(sShift.sorterIDs);
        hr_managerIDs = new HashSet<>(sShift.hr_managerIDs);
        logistics_managerIDs = new HashSet<>(sShift.logistics_managerIDs);
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
        while (true) {
            System.out.println("1 -- ShiftManager");
            for (int i = 0; i < JobTitles.values().length; i++)
                System.out.println((i + 2) + " -- " + JobTitles.values()[i]);
            try {
                int ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (ordinal < 1 || ordinal > (JobTitles.values().length + 1))
                    System.out.println("Please enter an integer between 1 and " + (JobTitles.values().length + 1));
                else if (ordinal == 1) {
                    assignShiftManager();
                    return;
                } else {
                    assignEmployee(JobTitles.values()[ordinal - 2]);
                    return;
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and " + (JobTitles.values().length + 1));
            }
        }
    }

    private void assignEmployee(JobTitles type) throws Exception {
        Set<Employee> curr = controller.getEmployees(getIDsByType(type));
        System.out.println("\nCurrent " + type + "s assigned to this shift:");
        for (Employee employee : curr)
            System.out.println(employee.id + " - " + employee.name + " : " + controller.getEmployeeWorkDetailsForCurrentMonth(employee.id));
        Set<Employee> available = controller.getEmployees(controller.getConstraint(date, getType()).employeeIDs)
                .stream().filter((x) -> x.getType() == type).filter((x) -> !x.id.equals(shiftManagerId)).collect(Collectors.toSet());
        Set<String> newGroup = null;
        boolean success = false;
        while (!success) {
            System.out.println("Please create a group of " + getCountByType(type) + " out of the following candidates");
            for (Employee employee : available)
                System.out.println(employee.id + " - " + employee.name + " : " + controller.getEmployeeWorkDetailsForCurrentMonth(employee.id));
            System.out.println("Enter ONLY the ID of the wanted employees!");
            newGroup = new HashSet<>();
            while (newGroup.size() < Math.min(getCountByType(type), available.size())) {
                try {
                    newGroup.add(scanner.nextLine());
                } catch (Exception e) {
                    System.out.println("Unexpected error occurred. Please try again");
                }
            }
            System.out.println("Do you want to save the following IDs?");
            for (String id : newGroup) {
                System.out.print(id + ", ");
            }
            success = yesOrNo();
        }
        switch (type) {
            case Sorter:
                setSorterIDs(newGroup);
                break;
            case Storekeeper:
                setStorekeeperIDs(newGroup);
                break;
            case Carrier:
                setCarrierIDs(newGroup);
                break;
            case Cashier:
                setCashierIDs(newGroup);
                break;
            case HR_Manager:
                setHr_managerIDs(newGroup);
                break;
            case Logistics_Manager:
                setLogistics_managerIDs(newGroup);
                break;
        }
        System.out.println("Chosen group saved successfully");
        if (newGroup.size() < getCountByType(type)) {
            System.out.println("Notice that the number of assigned employees doesn't meet the requirement of " + getCountByType(type) + " " + type + "s");
            System.out.println("Please make sure more employees register to the constraint of this shift");
        }
    }

    private void assignShiftManager() throws Exception {
        Employee currManager = controller.getEmployee(shiftManagerId);
        System.out.println("\nCurrent shift manager: " + currManager.name + ", ID: " + currManager.id);
        Constraint constraint = controller.getConstraint(date, getType());
        Set<Domain.Service.Objects.Employee> employees = controller.getEmployees(constraint.employeeIDs);
        List<Domain.Service.Objects.Employee> managers = employees.stream().filter((x) -> x.certifications.contains(Certifications.ShiftManagement)).collect(Collectors.toList());
        if (managers.size() == 0) {
            System.out.println("No employee who is certified to manage shifts has filled a possibility to work at this shift.");
            System.out.println("Cannot assign a shift manager. Operation Cancelled");
            return;
        }
        Employee manager = null;
        boolean success = false;
        while (!success) {
            System.out.println("Choose manager for this shift");
            for (int i = 0; i < managers.size(); i++)
                System.out.println((i + 1) + " -- " + managers.get(i).name + ": " + controller.getEmployeeWorkDetailsForCurrentMonth(managers.get(i).id));
            try {
                int ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (ordinal < 1 || ordinal > managers.size())
                    System.out.println("Please enter an integer between 1 and " + managers.size());
                else {
                    manager = managers.get(ordinal);
                    System.out.println("Entered manager: " + manager.name);
                    success = areYouSure();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and " + managers.size());
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        try {
            setShiftManagerId(manager.id);
            System.out.println("Successfully assigned " + manager.name + " as manager of this shift");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Please try again");
        }
    }

    private void updateCount() throws Exception {
        System.out.println("\nWhich type of count would you like to update?");
        JobTitles type = null;
        while (type == null) {
            for (int i = 0; i < JobTitles.values().length; i++)
                System.out.println((i + 1) + " -- " + JobTitles.values()[i]);
            try {
                int ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (ordinal < 1 || ordinal > JobTitles.values().length)
                    System.out.println("Please enter an integer between 1 and " + JobTitles.values().length);
                else {
                    type = JobTitles.values()[ordinal - 1];
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 1 and " + JobTitles.values().length);
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println("\nCurrent " + type + " count: " + getCountByType(type));

        System.out.println("How many " + type + "s do you need for this shift?");
        int newCount = -2;
        while (newCount < 0) {
            try {
                newCount = Integer.parseInt(scanner.nextLine());
                if (newCount == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (newCount < 0)
                    System.out.println("Please enter a non-negative integer");
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a non-negative integer");
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }

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
        }
        System.out.println(type + " count successfully set to " + newCount);
    }

    protected void setShiftManagerId(String shiftManagerId) throws Exception {
        controller.editShiftManagerID(this, shiftManagerId);
        this.shiftManagerId = shiftManagerId;
    }

    protected void setCarrierCount(int carrierCount) throws Exception {
        controller.editShiftCarrierCount(this, carrierCount);
        this.carrierCount = carrierCount;
    }

    protected void setCashierCount(int cashierCount) throws Exception {
        controller.editShiftCashierCount(this, cashierCount);
        this.cashierCount = cashierCount;
    }

    protected void setStorekeeperCount(int storekeeperCount) throws Exception {
        controller.editShiftStorekeeperCount(this, storekeeperCount);
        this.storekeeperCount = storekeeperCount;
    }

    protected void setSorterCount(int sorterCount) throws Exception {
        controller.editShiftSorterCount(this, sorterCount);
        this.sorterCount = sorterCount;
    }

    protected void setHr_managersCount(int hr_managersCount) throws Exception {
        controller.editShiftHR_ManagerCount(this, hr_managersCount);
        this.hr_managersCount = hr_managersCount;
    }

    protected void setLogistics_managersCount(int logistics_managersCount) throws Exception {
        controller.editShiftLogistics_ManagerCount(this, logistics_managersCount);
        this.logistics_managersCount = logistics_managersCount;
    }

    protected void setCarrierIDs(Set<String> carrierIDs) throws Exception {
        controller.editShiftCarrierIDs(this, carrierIDs);
        this.carrierIDs = new HashSet<>(carrierIDs);
    }

    protected void setCashierIDs(Set<String> cashierIDs) throws Exception {
        controller.editShiftCashierIDs(this, cashierIDs);
        this.cashierIDs = new HashSet<>(cashierIDs);
    }

    protected void setStorekeeperIDs(Set<String> storekeeperIDs) throws Exception {
        controller.editShiftStorekeeperIDs(this, storekeeperIDs);
        this.storekeeperIDs = new HashSet<>(storekeeperIDs);
    }

    protected void setSorterIDs(Set<String> sorterIDs) throws Exception {
        controller.editShiftSorterIDs(this, sorterIDs);
        this.sorterIDs = new HashSet<>(sorterIDs);
    }

    protected void setHr_managerIDs(Set<String> hr_managerIDs) throws Exception {
        controller.editShiftHR_ManagerIDs(this, hr_managerIDs);
        this.hr_managerIDs = new HashSet<>(hr_managerIDs);
    }

    protected void setLogistics_managerIDs(Set<String> logistics_managerIDs) throws Exception {
        controller.editShiftLogistics_ManagerIDs(this, logistics_managerIDs);
        this.logistics_managerIDs = new HashSet<>(logistics_managerIDs);
    }

    protected int getCountByType(JobTitles type) {
        switch (type) {
            case Sorter:
                return sorterCount;
            case Storekeeper:
                return storekeeperCount;
            case Carrier:
                return carrierCount;
            case Cashier:
                return cashierCount;
            case HR_Manager:
                return hr_managersCount;
            case Logistics_Manager:
                return logistics_managersCount;
            default:
                return 0;
        }
    }

    protected Set<String> getIDsByType(JobTitles type) {
        switch (type) {
            case Sorter:
                return sorterIDs;
            case Storekeeper:
                return storekeeperIDs;
            case Carrier:
                return carrierIDs;
            case Cashier:
                return cashierIDs;
            case HR_Manager:
                return hr_managerIDs;
            case Logistics_Manager:
                return logistics_managerIDs;
            default:
                return null;
        }
    }

    protected void printEmployeesByType(JobTitles type) throws Exception {
        System.out.println(type + "s (" + getCountByType(type) + ")");
        EmployeesMenu.EmployeesViewer.printEmployees(controller.getEmployees(getIDsByType(type)));
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

    public Date getDate() {
        return date;
    }
}