package Domain.Service.Services;

import Domain.Business.Controllers.ConstraintController;
import Domain.Service.Objects.*;
import Domain.Service.ServiceEmployeeFactory;
import Globals.Enums.ShiftTypes;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class ConstraintService {

    private final ConstraintController controller = new ConstraintController();
    private final ServiceEmployeeFactory employeeFactory = new ServiceEmployeeFactory();

    //CREATE

    //READ

    public Result<Constraint> getConstraint(LocalDate workday, ShiftTypes shift){
        try {
            return Result.makeOk(new Constraint(controller.getConstraint(workday, shift)));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<String>> getAvailableEmployee(LocalDate workday, ShiftTypes shift){
        try {
            return Result.makeOk(controller.getAvailableEmployeeIDs(workday, shift));
        } catch (Exception e) {
            return Result.makeError(e.getMessage());
        }
    }

    //UPDATE

    public Result<Object> registerToConstraint(String id, LocalDate workday, ShiftTypes shift) {
        try {
            controller.registerToConstraint(id, workday, shift);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    public Result<Object> unregisterFromConstraint(String id, LocalDate workday, ShiftTypes shift) {
        try {
            controller.unregisterFromConstraint(id, workday, shift);
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
        return Result.makeOk(null);
    }

    //DELETE

    //MISC

    public Result<Set<Constraint>> getEmployeeConstraintsBetween(String id, LocalDate today, LocalDate nextMonth) {
        try {
            Set<Constraint> constraints = controller.getEmployeeConstraintsBetween(id, today, nextMonth).stream().map(Constraint::new).collect(Collectors.toSet());
            return Result.makeOk(constraints);
        }catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Carrier>> getAvailableCarriersFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableCarriersFor(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Cashier>> getAvailableCashiersFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableCashiersFor(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Sorter>> getAvailableSortersFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableSortersFor(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Storekeeper>> getAvailableStorekeepersFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableStorekeepersFor(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<HR_Manager>> getAvailableHR_ManagersFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableHR_ManagersFor(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Logistics_Manager>> getAvailableLogistics_ManagersFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableLogistics_ManagersFor(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Transport_Manager>> getAvailableTransport_ManagersFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableTransport_ManagersFor(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<Employee>> getAvailableEmployeesFor(LocalDate workday, ShiftTypes type) throws Exception{
        try {
            return Result.makeOk(controller.getAvailableEmployeesFor(workday, type).stream().map(employeeFactory::createServiceEmployee).collect(Collectors.toSet()));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<String>> getAvailableCarriersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableCarriersIDsFor(workday, type));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<String>> getAvailableCashiersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableCashiersIDsFor(workday, type));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<String>> getAvailableSortersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableSortersIDsFor(workday, type));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<String>> getAvailableStorekeepersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableStorekeepersIDsFor(workday, type));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<String>> getAvailableHR_ManagersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableHR_ManagersIDsFor(workday, type));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<String>> getAvailableLogistics_ManagersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableLogistics_ManagersIDsFor(workday, type));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }

    public Result<Set<String>> getAvailableTransport_ManagersIDsFor(LocalDate workday, ShiftTypes type) throws Exception {
        try {
            return Result.makeOk(controller.getAvailableTransport_ManagersIDsFor(workday, type));
        }
        catch (Exception e){
            return Result.makeError(e.getMessage());
        }
    }
}