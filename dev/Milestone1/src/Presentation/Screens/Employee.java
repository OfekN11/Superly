package Presentation.Screens;

import Domain.Business.Objects.Constraint;
import Domain.Service.Objects.Shift;
import Globals.Enums.Certifications;
import Globals.Enums.ShiftTypes;
import Globals.util.ShiftComparator;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

import static Globals.util.HumanInteraction.*;

public abstract class Employee extends Screen {
    private static final String[] menuOptions = {
            "Print employment conditions",  //1
            "Update name",                  //2
            "Update bank details",          //3
            "Update salary per shift",      //4
            "Update certifications",        //5
            "Calculate Salary",             //6
            "Manage work constraints",      //7
            "Print upcoming shifts"         //8
    };

     protected final String id;
     protected String name;

     protected String bankDetails;
     protected int salary;
     protected String employmentConditions;
     protected final LocalDate startingDate;
     protected Set<Certifications> certifications;

    public Employee(Screen caller, Domain.Service.Objects.Employee sEmployee, String[] extraMenuOptions) {
        super(caller, Stream.concat(Arrays.stream(menuOptions), Arrays.stream(extraMenuOptions)).toArray(String[]::new));
        id = sEmployee.id;
        name = sEmployee.name;
        bankDetails = sEmployee.bankDetails;
        salary = sEmployee.salary;
        employmentConditions = sEmployee.employmentConditions;
        startingDate = sEmployee.startingDate;
        certifications = new HashSet<>(sEmployee.certifications);
    }

    protected void handleBaseOptions(int option) throws Exception {
        switch (option) {
            case 1 : System.out.println(employmentConditions);
            break;
            case 2 : updateName();
            break;
            case 3 : updateBankDetails();
            break;
            case 4 : updateSalary();
            break;
            case 5 : updateCertifications();
            break;
            case 6 : calculateSalary();
            break;
            case 7 : manageConstraints();
            break;
            case 8 : printUpcomingShifts();
        }
    }

    protected void setName(String name) throws Exception {
        controller.editEmployeeName(this, name);
        this.name = name;
        updateEmploymentConditions();
    }

    protected void setBankDetails(String bankDetails) throws Exception {
        controller.editEmployeeBankDetails(this, bankDetails);
        this.bankDetails = bankDetails;
    }

    protected void setSalary(int salary) throws Exception {
        controller.editEmployeeSalary(this, salary);
        this.salary = salary;
        updateEmploymentConditions();
    }

    protected void setCertifications(Set<Certifications> certifications) throws Exception {
        controller.editEmployeeCertifications(this, certifications);
        this.certifications = certifications;
    }

    private void updateEmploymentConditions() throws Exception {
        this.employmentConditions = this.controller.getEmploymentConditionsOf(id);
    }

    private void updateName() throws Exception {
        String name = null;
        boolean success = false;
        while (!success){
            System.out.println("\nEnter employee's new name");
            try {
                name = scanner.nextLine();
                if (name.equals("-1")) {
                    System.out.println("Operation Canceled");
                    return;
                }
                else {
                    System.out.println("Entered name: " + name);
                    success = areYouSure();
                }
            }
            catch (Exception ex){
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println("Updated name: " + name);
        setName(name);
    }

    private void updateBankDetails() throws Exception {
        String bankDetails = null;
        boolean success = false;
        while (!success){
            System.out.println("\nEnter " + name +"'s bank details");
            try {
                bankDetails = scanner.nextLine();
                if (bankDetails.equals("-1")) {
                    System.out.println("Operation Canceled");
                    return;
                }
                else {
                    System.out.println("Entered bank details: " + bankDetails);
                    success = areYouSure();
                }
            }
            catch (Exception ex){
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println("Updated bank details: " + bankDetails);
        setBankDetails(bankDetails);
    }

    private void updateSalary() throws Exception {
        Integer salary = null;
        boolean success = false;
        while (!success){
            System.out.println("\nEnter " + name +"'s salary per shift");
            try {
                salary = Integer.parseInt(scanner.nextLine());
                if (salary == -1) {
                    System.out.println("Operation Canceled");
                    return;
                }
                else if (salary < 0){
                    System.out.println("Enter a valid salary");
                }
                else {
                    System.out.println("Entered salary title: " + salary);
                    success = areYouSure();
                }
            }
            catch (NumberFormatException ex){
                System.out.println("Please enter an non-negative integer");
            }
            catch (Exception ex){
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
        System.out.println("Updated salary: " + salary);
        setSalary(salary);
    }

    private void updateCertifications() throws Exception {
        Set<Certifications> curr = new HashSet<>(certifications);
        System.out.println(name + "'s current certifications:");
        for (Certifications c: curr)
            System.out.println(c);
        System.out.println("Would you like to add or remove certifications?");
        int ans = 0;
        while (ans != 1 && ans != 2){
            System.out.println("1 -- add\n2 -- remove");
            try {
                ans = Integer.parseInt(scanner.nextLine());
                if (ans == -1) {
                    System.out.println("Operation Canceled");
                    return;
                }
            }
            catch (NumberFormatException ex){
                System.out.println("Please enter an integer value (1 or 2)");
            }
            catch (Exception ex){
                System.out.println("An unexpected error happened. Please try again");
            }
        }
        switch (ans) {
            case 1 : addCertifications(curr);
            break;
            case 2 : removeCertifications(curr);
        }
        System.out.println("New certifications:");
        for (Certifications c: curr)
            System.out.println(c);
        System.out.println("Would you like to save?");
        if (yesOrNo())
            setCertifications(curr);
    }

    private void addCertifications(Set<Certifications> curr) {
        System.out.println("\nChoose which certifications to add");
        int ordinal = -1;
        while (ordinal != 0) {
            System.out.println("0 -- stop adding certifications");
            for (int i = 0; i < Certifications.values().length; i++)
                System.out.println((i + 1) + " -- " + Certifications.values()[i]);
            try {
                ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (ordinal < 0 || ordinal > Certifications.values().length) {
                    System.out.println("Please enter an integer between 0 and " + Certifications.values().length);
                } else if (ordinal != 0){
                    curr.add(Certifications.values()[ordinal-1]);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 0 and " + Certifications.values().length);
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
    }

    private void removeCertifications(Set<Certifications> curr) {
        System.out.println("\nChoose which certifications to remove");
        int ordinal = -1;
        while (ordinal != 0) {
            System.out.println("0 -- stop removing certifications");
            for (int i = 0; i < Certifications.values().length; i++)
                System.out.println((i + 1) + " -- " + Certifications.values()[i]);
            try {
                ordinal = Integer.parseInt(scanner.nextLine());
                if (ordinal == -1) {
                    System.out.println("Operation Canceled");
                    return;
                } else if (ordinal < 0 || ordinal > Certifications.values().length) {
                    System.out.println("Please enter an integer between 0 and " + Certifications.values().length);
                } else if (ordinal != 0){
                    curr.remove(Certifications.values()[ordinal-1]);
                }
            } catch (NumberFormatException ex) {
                System.out.println("Please enter an integer between 0 and " + Certifications.values().length);
            } catch (Exception ex) {
                System.out.println("Unexpected error occurred");
                System.out.println("Please try again");
            }
        }
    }

    private void calculateSalary() throws Exception {
        System.out.println("Enter date to start calculating from: ");
        LocalDate start = buildDate();
        if (start == null)
            return;
        System.out.println("Enter last date to calculate: ");
        LocalDate end = buildDate();
        if (end == null)
            return;
        int numOfShifts = controller.getEmployeeShiftsBetween(id, start, end).size();
        System.out.println("Between the dates entered " + name + " has done " + numOfShifts + "shifts");
        System.out.println("With a salary of " + salary + " per shift");
        System.out.println("Calculated salary between these date is: " + (numOfShifts * salary));
    }

    private void manageConstraints() throws Exception {
        LocalDate today = LocalDate.now();
        LocalDate nextMonth = today.plusMonths(1);
        List<Constraint> constraints = new ArrayList<>(controller.getEmployeeConstraintsBetween(id, today, nextMonth));
        constraints.sort(new ConstraintComparator());
        System.out.println("Current constraints for the following month");
        for (Constraint constraint : constraints)
            System.out.println(constraint);

        System.out.println("Would you like to add or remove constraints?");
        int ans = 0;
        while (ans != 1 && ans != 2){
            System.out.println("1 -- add\n2 -- remove");
            try {
                ans = Integer.parseInt(scanner.nextLine());
                if (ans == -1) {
                    System.out.println("Operation Canceled");
                    return;
                }
            }
            catch (NumberFormatException ex){
                System.out.println("Please enter an integer value (1 or 2)");
            }
            catch (Exception ex){
                System.out.println("An unexpected error happened. Please try again");
            }
        }
        switch (ans) {
            case 1 : addConstraints();
            break;
            case 2 : removeConstraints();
        }
    }

    private void removeConstraints() throws Exception {
        while (true) {
            System.out.println("When can't you work? (enter -1 to stop adding constraints)");
            LocalDate date = buildDate();
            if (date == null)
                return;
            System.out.println("What shift?");
            int ans = 0;
            while (ans != 1 && ans != 2){
                System.out.println("1 -- Morning\n2 -- Evening");
                try {
                    ans = Integer.parseInt(scanner.nextLine());
                    if (ans == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    }
                }
                catch (NumberFormatException ex){
                    System.out.println("Please enter an integer value (1 or 2)");
                }
                catch (Exception ex){
                    System.out.println("An unexpected error happened. Please try again");
                }
            }
            ShiftTypes shift = ans == 1 ? ShiftTypes.Morning : ShiftTypes.Evening;
            controller.unregisterToConstraint(id, date, shift);
        }
    }

    private void addConstraints() throws Exception {
        while (true) {
            System.out.println("When can you work? (enter -1 to stop adding constraints)");
            LocalDate date = buildDate();
            if (date == null)
                return;
            System.out.println("What shift?");
            int ans = 0;
            while (ans != 1 && ans != 2){
                System.out.println("1 -- Morning\n2 -- Evening");
                try {
                    ans = Integer.parseInt(scanner.nextLine());
                    if (ans == -1) {
                        System.out.println("Operation Canceled");
                        return;
                    }
                }
                catch (NumberFormatException ex){
                    System.out.println("Please enter an integer value (1 or 2)");
                }
                catch (Exception ex){
                    System.out.println("An unexpected error happened. Please try again");
                }
            }
            ShiftTypes shift = ans == 1 ? ShiftTypes.Morning : ShiftTypes.Evening;
            controller.registerToConstraint(id, date, shift);
        }
    }

    private void printUpcomingShifts() throws Exception {
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusWeeks(1);
        List<Shift> shifts= new ArrayList<>(controller.getEmployeeShiftsBetween(id, today, nextWeek));
        shifts.sort(new ShiftComparator());
        System.out.println("Upcoming shift for the following week");
        for (Shift shift : shifts)
            System.out.println(shift);
    }

    public String getID() {
        return id;
    }
}
