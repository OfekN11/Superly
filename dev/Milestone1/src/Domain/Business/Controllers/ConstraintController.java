package Domain.Business.Controllers;

import Domain.Business.Objects.*;
import Domain.DAL.Controllers.ConstraintDataMapper;
import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class ConstraintController {

    private final ConstraintDataMapper constraintDataMapper = new ConstraintDataMapper();
    private final EmployeeController employeeController = new EmployeeController();
    private final ShiftController shiftController = new ShiftController();

    //CREATE

    public void addConstraint(LocalDate workday, ShiftTypes shiftType) throws Exception{
        constraintDataMapper.save(new Constraint(workday, shiftType, new HashSet<>()));
    }

    //READ

    public Constraint getConstraint(LocalDate workday, ShiftTypes shiftType) throws Exception {
        Constraint constraint = constraintDataMapper.get(workday, shiftType);
        if (constraint != null)
            return constraint;
        addConstraint(workday, shiftType);
        constraint = constraintDataMapper.get(workday, shiftType);
        return constraint;
    }

    public Set<String> getAvailableEmployeeIDs(LocalDate workday, ShiftTypes shift) throws Exception{
        Constraint constraint = getConstraint(workday, shift);
        if (constraint == null)
            return new HashSet<>();
        return constraint.getEmployees();
    }

    //UPDATE

    public void registerToConstraint(String id, LocalDate workday, ShiftTypes shift) throws Exception{
        Constraint constraint = getConstraint(workday, shift);
        constraint.register(id);
        constraintDataMapper.save(constraint);
    }

    public void unregisterFromConstraint(String id, LocalDate workday, ShiftTypes shift) throws Exception{
        Constraint constraint = getConstraint(workday, shift);
        if (constraint == null)
            return;
        if (shiftController.getShift(workday, shift).isIdInclude(id))
            throw new Exception(String.format("Cannot unregister %s from constraint for %s at date %s: assigned to shift", id + " - " + employeeController.getEmployee(id).getName(), shift, workday.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        constraint.unregister(id);
        if (constraint.isEmpty())
            removeConstraint(constraint);
    }

    //DELETE

    public void removeConstraint(LocalDate workday, ShiftTypes shiftTypes) throws Exception{
        Constraint constraint = getConstraint(workday, shiftTypes);
        if (constraint != null)
            constraintDataMapper.delete(constraint);
    }

    public void removeConstraint(Constraint constraint) throws Exception{
        constraintDataMapper.delete(constraint);
    }

    //MISC

    public Set<Constraint> getEmployeeConstraintsBetween(String id, LocalDate start, LocalDate end) {
        Set<Constraint> constraints = constraintDataMapper.getConstraintsBetween(start, end);
        constraints = constraints.stream().filter(c -> c.getEmployees().contains(id)).collect(Collectors.toSet());
        return constraints;
    }

    public Set<Carrier> getAvailableCarriersFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getCarrier(getConstraint(workday,type).getEmployees());
    }

    public Set<Cashier> getAvailableCashiersFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getCashier(getConstraint(workday,type).getEmployees());
    }

    public Set<Sorter> getAvailableSortersFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getSorter(getConstraint(workday,type).getEmployees());
    }

    public Set<Storekeeper> getAvailableStorekeepersFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getStorekeeper(getConstraint(workday,type).getEmployees());
    }

    public Set<HR_Manager> getAvailableHR_ManagersFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getHR_Manager(getConstraint(workday,type).getEmployees());
    }

    public Set<Logistics_Manager> getAvailableLogistics_ManagersFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getLogistics_Manager(getConstraint(workday,type).getEmployees());
    }

    public Set<Transport_Manager> getAvailableTransport_ManagersFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getTransport_Manager(getConstraint(workday,type).getEmployees());
    }

    public Set<Employee> getAvailableEmployeesFor(LocalDate workday, ShiftTypes type) throws Exception{
        return employeeController.getEmployee(getConstraint(workday, type).getEmployees());
    }

    public Set<String> getAvailableCarriersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getCarrier(getConstraint(workday,type).getEmployees()).stream().map(Employee::getId).collect(Collectors.toSet());
    }

    public Set<String> getAvailableCashiersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getCashier(getConstraint(workday,type).getEmployees()).stream().map(Employee::getId).collect(Collectors.toSet());
    }

    public Set<String> getAvailableSortersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getSorter(getConstraint(workday,type).getEmployees()).stream().map(Employee::getId).collect(Collectors.toSet());
    }

    public Set<String> getAvailableStorekeepersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getStorekeeper(getConstraint(workday,type).getEmployees()).stream().map(Employee::getId).collect(Collectors.toSet());
    }

    public Set<String> getAvailableHR_ManagersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getHR_Manager(getConstraint(workday,type).getEmployees()).stream().map(Employee::getId).collect(Collectors.toSet());
    }

    public Set<String> getAvailableLogistics_ManagersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getLogistics_Manager(getConstraint(workday,type).getEmployees()).stream().map(Employee::getId).collect(Collectors.toSet());
    }

    public Set<String> getAvailableTransport_ManagersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        return employeeController.getTransport_Manager(getConstraint(workday,type).getEmployees()).stream().map(Employee::getId).collect(Collectors.toSet());
    }
}
